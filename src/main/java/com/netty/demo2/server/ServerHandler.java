package com.netty.demo2.server;


import com.netty.demo2.util.NettySocketHolder;
import com.netty.demo2.util.NettyConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 客户端与服务端创建连接的时候调用
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端与服务端连接开始...");
        System.out.println();
        //保存用户channel
       int size= NettySocketHolder.getMAP().size();
        if(size==0){
            NettySocketHolder.put(1, (NioSocketChannel) ctx.channel());
        }else{
            NettySocketHolder.put(size+1, (NioSocketChannel) ctx.channel());

        }
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
        System.out.println("服务端信息接收完毕！");
        ctx.flush();
    }

    /**
     * 工程出现异常的时候调用
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("******server exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 服务端处理客户端websocket请求的核心方法，这里接收了客户端发来的信息
     */
//    @Override
//    public void channelRead(ChannelHandlerContext channelHandlerContext, Object info) throws Exception {
//        System.out.println("服务到接收到的数据：" + info.toString());
//        //服务端使用这个就能向 每个连接上来的客户端群发消息
////        NettyConfig.group.writeAndFlush(info);
////        Iterator<Channel> iterator = NettyConfig.group.iterator();
////        while(iterator.hasNext()){
////            //打印出所有客户端的远程地址
////            System.out.println((iterator.next()).remoteAddress());
////        }
////       单独回复客户端信息
////       channelHandlerContext.writeAndFlush("===");
//    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("服务到接收到的数据：" + s);
    }
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("******server handlerRemoved");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("******server handlerAdded");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("******server channelWritabilityChanged");
    }
}
