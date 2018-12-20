package com.netty.demo5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *
 * Title: HelloServerHandler
 * Description:  服务端业务逻辑
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     * 取消绑定
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        NettySocketHolder.remove((NioSocketChannel) ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channelActive");
        NettySocketHolder.put(1L,(NioSocketChannel)ctx.channel()) ;

        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("server userEventTriggered");

        super.userEventTriggered(ctx, evt);
    }

    //    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, BaseRequestProto.RequestProtocol msg) throws Exception {
//        LOGGER.info("收到msg={}", msg.getReqMsg());
//
//        if (999 == msg.getRequestId()){
//            BaseResponseProto.ResponseProtocol responseProtocol = BaseResponseProto.ResponseProtocol.newBuilder()
//                    .setResponseId(1000)
//                    .setResMsg("服务端响应")
//                    .build();
//            ctx.writeAndFlush(responseProtocol) ;
//        }
//
//        //保存客户端与 Channel 之间的关系
//        //NettySocketHolder.put(CustomProtocolProtocol.getId(),(NioSocketChannel)ctx.channel()) ;
//    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("收到数据"+s);
    }

}