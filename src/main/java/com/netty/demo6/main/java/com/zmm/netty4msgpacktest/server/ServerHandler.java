package com.netty.demo6.main.java.com.zmm.netty4msgpacktest.server;



import com.netty.demo6.main.java.com.zmm.netty4msgpacktest.common.CustomHeartbeatHandler;
import com.netty.demo6.main.java.com.zmm.netty4msgpacktest.domain.DeviceValue;
import com.netty.demo6.main.java.com.zmm.netty4msgpacktest.domain.TypeData;
import io.netty.channel.ChannelHandlerContext;

/**
 * Description:
 * Author:Giousa
 * Date:2017/2/9
 * Email:65489469@qq.com
 */
public class ServerHandler extends CustomHeartbeatHandler {

    public ServerHandler() {
        super("server");
    }

    @Override
    protected void handleData(ChannelHandlerContext channelHandlerContext, Object msg) {

        DeviceValue s = new DeviceValue();
        s.setType(TypeData.CUSTOME);
        s.setSpeed(0);
        s.setAngle(15);
        s.setSeatId(TypeData.SERVER_RESPONSE);
        channelHandlerContext.writeAndFlush(s);
        System.out.println("服务端 发送数据:"+s.toString());

        DeviceValue deviceValue = (DeviceValue) msg;
        System.out.println("服务端 接收数据:"+deviceValue.toString());


    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接的客户端地址:" + ctx.channel().remoteAddress());
    }
    @Override
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        super.handleReaderIdle(ctx);
        System.err.println("---client " + ctx.channel().remoteAddress().toString() + " reader timeout, close it---");
        ctx.close();
    }

    /**NettyClientBootstrap
     * 异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(name+" exception"+cause.toString());
    }
}