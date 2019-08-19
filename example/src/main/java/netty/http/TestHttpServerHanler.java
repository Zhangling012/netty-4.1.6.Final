package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class TestHttpServerHanler extends SimpleChannelInboundHandler<HttpObject> {
    // 读取客户端请求 响应
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        ByteBuf content = Unpooled.copiedBuffer("hello word", CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plan");
        ctx.writeAndFlush(response);
    }
       @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("channelActive");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        System.out.println("channelRegistered");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("handlerAdded");
    }
}
