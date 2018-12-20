package com.netty.demo7;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyServer {
    private static final Map<Long, NioSocketChannel> MAP = new ConcurrentHashMap<>(16);

    public static NioServerSocketChannel serverSocketChannel;
    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup wokerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //在服务器端的handler()方法表示对bossGroup起作用，而childHandler表示对wokerGroup起作用
            serverBootstrap.group(bossGroup,wokerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            NioSocketChannel socketChannel=MAP.get(1);
            //socketChannel.writeAndFlush("12222");
            serverSocketChannel = (NioServerSocketChannel) channelFuture.channel();

            channelFuture.channel().closeFuture().sync();
            MyServer.sendMessage("122222223");
        }finally {
            bossGroup.shutdownGracefully();
            wokerGroup.shutdownGracefully();
        }
    }
    public static void sendMessage(String msg){

        if(serverSocketChannel != null){
            serverSocketChannel.writeAndFlush(msg);
            System.out.println(serverSocketChannel.isActive());
            System.out.println("服务端已发出");
        }

    }
}
