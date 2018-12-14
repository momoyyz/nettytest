package com.netty.demo3;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;


/**
 * Created by Andy on 2016/10/8.
 */
public class MsgPackEncode extends MessageToByteEncoder<IMMessage> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMMessage msg, ByteBuf out) throws Exception {
        out.writeBytes(new MessagePack().write(msg));
    }
}