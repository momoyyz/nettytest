package com.netty.demo2;

import com.netty.controller.NettyController;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Iterator;


public class ServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端连接开始...");
        System.out.println("服务端给客户端发送测试数据"+NettyController.newServerStr);
        ctx.writeAndFlush("服务端给客户端发送数据："+ NettyController.newServerStr+" \n");

       // NettyConfig.group.add(ctx.channel());
//        while (true) {
//            ByteBuf time = ctx.alloc().buffer(4); //为ByteBuf分配四个字节
//            time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//            ctx.writeAndFlush(time); // (3)
//            Thread.sleep(2000);
//        }
    }

    /**
     * 客户端与服务端断开连接时调用
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端连接关闭...");

        NettyConfig.group.remove(ctx.channel());
    }

    /**
     * 服务端接收客户端发送过来的数据结束之后调用
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        System.out.println("信息接收完毕...");
    }

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 服务端处理客户端websocket请求的核心方法，这里接收了客户端发来的信息
     */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object info) throws Exception {
        System.out.println("我是服务端，我接受到了：" + ((RequestInfo)info).getInfo()+System.currentTimeMillis());
        //服务端使用这个就能向 每个连接上来的客户端群发消息
//        NettyConfig.group.writeAndFlush(info);
//        Iterator<Channel> iterator = NettyConfig.group.iterator();
//        while(iterator.hasNext()){
//            //打印出所有客户端的远程地址
//            System.out.println((iterator.next()).remoteAddress());
//        }
    	//单独回复客户端信息
    	channelHandlerContext.writeAndFlush("dbashfas f");
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("服务端接受的消息 : " + s);
    }


}
