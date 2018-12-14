package com.netty.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.IOException;

/**
 * Created by Andy on 2016/10/8.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter implements IMClientConfig{
    private ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("用户["+ UID + "]成功连接服务器");
        this.ctx = ctx;

        //通道建立时发送认证消息给服务器
//        IMMessage message = new IMMessage(
//                APP_IM,
//                CLIENT_VERSION,
//                UID,
//                TYPE_MSG_AUTH.value(),
//                DEFAULT_RECEIVE_ID,
//                MSG_DEFAULT);
      //  sendMsg(message);
    }

    public boolean sendMsg(IMMessage msg) throws IOException {
        boolean result = msg.getMsg().equals("quit") ? false:true;
        if(result){
            if(msg.getMsgType() != MessageType.TYPE_AUTH.value()){
                System.out.println("认证消息： " + "client[" + msg.getUid() + "]:" + msg.getMsg());
            }
            //设置接收端ID和发送消息
            if(msg.getMsgType() == MessageType.TYPE_TEXT.value()){
                if(msg.getMsg().contains(":")){
                    String[] msgs = msg.getMsg().split(":");
                    String receiveIdStr =msgs[0].substring(1);
                    msg.setReceiveId(Integer.valueOf(receiveIdStr));
                    msg.setMsg(msgs[1]);
                }
            }
            ctx.writeAndFlush(msg);
        }
        return result;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        IMMessage m = (IMMessage)msg;
        System.out.println("receive[" + m.getUid() + "]:" + m.getMsg());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("与服务器断开连接：" + cause.getMessage());
        ctx.close();
    }
}