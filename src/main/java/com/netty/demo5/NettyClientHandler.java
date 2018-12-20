package com.netty.demo5;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.netty.demo5.NettyClient.star;

/**
 *
 * Title: NettyClientHandler
 * Description: 客户端业务逻辑实现
 * Version:1.0.0
 * @author Administrator
 * @date 2017-8-31
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("userEventTriggered");
        /*if (evt instanceof IdleStateEvent){
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt ;

            if (idleStateEvent.state() == IdleState.WRITER_IDLE){
                LOGGER.info("已经 10 秒没有发送信息！");
                //向服务端发送消息
                CustomProtocol heartBeat = SpringBeanFactory.getBean("heartBeat", CustomProtocol.class);
                ctx.writeAndFlush(heartBeat).addListener(ChannelFutureListener.CLOSE_ON_FAILURE) ;
            }


        }*/

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        //客户端和服务端建立连接时调用
        System.out.println("已经建立了联系。。");
    }

    //    @Override
//    protected void channelRead0(ChannelHandlerContext channelHandlerContext, BaseResponseProto.ResponseProtocol responseProtocol) throws Exception {
//
//        //从服务端收到消息时被调用
//        //LOGGER.info("客户端收到消息={}",in.toString(CharsetUtil.UTF_8)) ;
//
//        LOGGER.info("客户端收到消息={}" ,responseProtocol.getResMsg());
//    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("客户端收到数据" + s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //异常时断开连接
        cause.printStackTrace();
        ctx.close();
    }
}
