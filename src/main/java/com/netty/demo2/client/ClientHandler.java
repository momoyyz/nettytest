package com.netty.demo2.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;


public class ClientHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端接收到数据：" + msg.toString());
         ctx.writeAndFlush("totototto");

    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        System.out.println("client 客户端接受的消息 2: " + textWebSocketFrame);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端通道读取完毕！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("***** client exceptionCaught****");
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("******client channelActive");
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("******client handlerRemoved");
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("******client handlerAdded");
    }
}
