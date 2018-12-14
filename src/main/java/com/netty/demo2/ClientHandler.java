package com.netty.demo2;

import com.netty.controller.NettyController;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;


public class ClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("我是客户端1：" + ((RequestInfo)msg).getInfo()+System.currentTimeMillis());
        System.out.println("客户端接收的消息: " + msg.toString());
        ctx.writeAndFlush("客户端已经接收到消息\n");    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道读取完毕！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(null != cause) cause.printStackTrace();
        if(null != ctx) ctx.close();
    }
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("客户端接受的消息 : " + s);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户给客户端发送测试数据"+NettyController.newServerStr);
        ctx.writeAndFlush("客户端给客户端发送数据："+ NettyController.newServerStr+" \n");

    }

}
