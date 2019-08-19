package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class ServerHttp {
    public static void main(String[] args) {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new TestServerInitializer());
        try {
            bootstrap.bind(8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
