package netty.test1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class NettyServer {
    public static void main(String[] args) {
        // 首先看到，我们创建了两个NioEventLoopGroup，这两个对象可以看做是传统IO编程模型的两大线程组，boosGroup表示监听端口，创建新连接的线程组，workerGroup表示处理每一条连接的数据读写的线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        // 这个类将引导我们进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                // 我们通过.group(boosGroup, workerGroup)给引导类配置两大线程，这个引导类的线程模型也就定型了
                .group(boosGroup, workerGroup)
                // 指定IO模型为BIO，那么这里配置上OioServerSocketChannel.class
                .channel(NioServerSocketChannel.class)
                // 我们调用childHandler()方法，给这个引导类创建一个ChannelInitializer，这里主要就是定义后续每条连接的数据读写，业务处理逻辑，ChannelInitializer这个类中，泛型参数NioSocketChannel，就是Netty对NIO类型的连接的抽象，而我们前面NioServerSocketChannel也是对NIO类型的连接的抽象，NioServerSocketChannel和NioSocketChannel的概念可以和BIO编程模型中的ServerSocket以及Socket两个概念对应上
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                    }
                });

        // serverBootstrap.bind(8000);




        // serverBootstrap.bind(8000);这个方法呢，它是一个异步的方法，调用之后是立即返回的，他的返回值是一个ChannelFuture，我们可以给这个ChannelFuture添加一个监听器GenericFutureListener，然后我们在GenericFutureListener的operationComplete方法里面，我们可以监听端口是否绑定成功
        serverBootstrap.bind(8000).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) {
                if (future.isSuccess()) {
                    System.out.println("端口绑定成功!");
                } else {
                    System.err.println("端口绑定失败!");
                }
            }
        });
       // childHandler()用于指定处理新连接数据的读写处理逻辑，handler()用于指定在服务端启动过程中的一些逻辑
       //  serverBootstrap.handler(new ChannelInitializer<NioServerSocketChannel>() {
       //      protected void initChannel(NioServerSocketChannel ch) {
       //          System.out.println("服务端启动中");
       //      }
       //  });
   //
   //      attr()方法可以给服务端的channel，也就是NioServerSocketChannel指定一些自定义属性，然后我们可以通过channel.attr()取出这个属性，比如，上面的代码我们指定我们服务端channel的一个serverName属性，属性值为nettyServer，其实说白了就是给NioServerSocketChannel维护一个map而已

        // serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer")


        // 上面的childAttr可以给每一条连接指定自定义属性，然后后续我们可以通过channel.attr()取出该属性，详情请看视频演示
        // serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue")


    }
}