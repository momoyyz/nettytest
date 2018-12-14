package com.netty.demo7;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<String>{

//    @Override
//    protected void channelRead(ChannelHandlerContext ctx, String msg) throws Exception {
//        //打印出客户端地址
//        System.out.println(ctx.channel().remoteAddress()+", "+msg);
//        ctx.channel().writeAndFlush("form server: "+ UUID.randomUUID());
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {//打印出客户端地址
        System.out.println(ctx.channel().remoteAddress()+", "+msg);
       // ctx.channel().writeAndFlush("form server: "+ LocalDateTime.now()+"服务端数据");
        super.channelRead(ctx, msg);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String s) throws Exception {
//        //打印出客户端地址
//        System.out.println(ctx.channel().remoteAddress()+", "+s);
//        ctx.channel().writeAndFlush("form server: "+ UUID.randomUUID());
    }
}
