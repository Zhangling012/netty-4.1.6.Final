package netty.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;
import netty.ch6.AuthHandler;

/**
 * @author
 */
public final class Server {
    // 服务端的socket在哪里初始化？
    // 在哪里accept连接？

    public static void main(String[] args) throws Exception {
        // 服务启动过程　：
        // 创建服务端chnnel
        // 初始化服务端chnnel
        // 注册selector
        // 端口绑定

        // 关于NioEventLoopGroup
        // 默认情况下 netty 服务端起多少个线程？何时启动？
        //　如何解决jdk 空轮训bug
        //　如何保证异步串行无锁化

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childAttr(AttributeKey.newInstance("childAttr"), "childAttrValue")
                    // 在初始化时就会执行 监听Channel的各种动作以及状态的改变，包括连接，绑定，接收消息等  给bossgroup 用
                    .handler(new ServerHandler())
                    // 在客户端成功connect后才执行 用来监听已经连接的客户端的Channel的动作和状态  给 workgroup 用
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new AuthHandler());
                            //..

                        }
                    });

            ChannelFuture f = b.bind(8888).sync();

            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}