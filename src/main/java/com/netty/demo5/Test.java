package com.netty.demo5;


public class Test {

    public static void main(String[] args) {
        NettyServerFilter nettyServerFilter=new NettyServerFilter("");
        nettyServerFilter.ph.addLast("handler", new NettyServerHandler());// 服务端业务逻辑
    }
}
