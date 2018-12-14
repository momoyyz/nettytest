package com.netty.demo4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;


public class TimeServerHandler extends ChannelInboundHandlerAdapter {

        Integer s;
    public TimeServerHandler(Integer s) {
        this.s=s;
    }

    //ChannelHandlerContext通道处理上下文
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception { // (1)
        System.out.println("服务端发送");
            ByteBuf time = ctx.alloc().buffer(4); //为ByteBuf分配四个字节
        System.out.println((int) (System.currentTimeMillis() / 1000L + 2208988800L));
            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L)+s);
            ctx.writeAndFlush(time); // (3)
        super.channelActive(ctx);
//        while (true) {
//            ByteBuf time = ctx.alloc().buffer(4); //为ByteBuf分配四个字节
//            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//            ctx.writeAndFlush(time); // (3)
//            Thread.sleep(2000);
//        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("服务端接收");
        ByteBuf m = (ByteBuf) msg;
        try {
            long currentTimeMillis = (m.readUnsignedInt() - 2208988800L) * 1000L;
            System.out.println(new Date(currentTimeMillis));

        } finally {
            m.release();
        }

    }

    /**
     * 客户端 注册
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("sssss"); // (3)
    }

    /**
     * 心跳机制  用户事件触发
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    }
}