package com.netty.controller;


import com.netty.demo2.client.Client;
import com.netty.demo2.server.Server;
import com.netty.demo5.NettyClientFilter;
import com.netty.demo5.NettyServerFilter;
import com.netty.demo2.util.NettySocketHolder;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController
public class NettyController {
    /**
     * server
     */
    private static final int port = 6789; //设置服务端端口
    private static EventLoopGroup group = new NioEventLoopGroup();
    ;   // 通过nio方式来接收连接和处理连接
    private static ServerBootstrap b = new ServerBootstrap();
    private static ChannelFuture f;
    private SocketChannel socketChannel;


    /**
     * client
     */
    public static String host = "127.0.0.1";  //ip地址
    /// 通过nio方式来接收连接和处理连接
    private static EventLoopGroup group1;
    private static Bootstrap b1;
    private static ChannelFuture ch;

    /**
     *
     *
     */
    public static String newServerStr;
    private final static Logger LOGGER = LoggerFactory.getLogger(NettyController.class);

    @RequestMapping("test")
    public String test() {

        // NettyServerHandler.sendMessage(NettyServerHandler.myCtx,"11236**-----------");
        return "success";
    }

    @RequestMapping("test1")
    protected String messageReceived(String serverStr) {
        newServerStr = serverStr;
        try {
            if (f == null) {
                b.group(group);
                b.channel(NioServerSocketChannel.class);
                // 服务器绑定端口监听

                b.childHandler(new NettyServerFilter()); //设置过滤器
                f = b.bind(port).sync();
                if (f.isSuccess()) {
                    System.out.println("服务端启动成功...");

                } else {
                    System.out.println("服务端启动失败...");
                }
            } else {
                System.out.println("else");
                System.out.println(serverStr);
                b.childHandler(new NettyServerFilter()); //设置过滤器

            }
            // 监听服务器关闭监听
            f.channel().closeFuture().sync();

        } catch (Exception e) {
            System.out.println("服务器错误");
            System.out.println(e.getMessage());
        } finally {
            group.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }

        return "success";
    }

    @RequestMapping("test2")
    protected String messageRecseived() {
        group1 = new NioEventLoopGroup();
        b1 = new Bootstrap();
        try {
            // 进行连接
            ChannelFuture future;

            b1.group(group1);
            b1.channel(NioSocketChannel.class);
            b1.handler(new NettyClientFilter());
            System.out.println("客户端准备启动...");
            ///if(ch==null){
            System.out.println("客户端成功启动...");
            ch = b1.connect(host, port).sync();
            // }
            // 监听服务器关闭监听
            //ch.closeFuture().sync();
        } catch (Exception e) {
            System.out.println("客户器错误");
            System.out.println(e.getMessage());
        } finally {
            //  group1.shutdownGracefully(); ////关闭EventLoopGroup，释放掉所有资源包括创建的线程
        }

        return "success";
    }

    @RequestMapping("test3")
    protected String messagweReceived(String serverStr) {
        newServerStr = serverStr;
        b.childHandler(new NettyServerFilter()); //设置过滤器
        return "success";
    }


    @RequestMapping("test4")
    protected String messageRecsreived() throws InterruptedException {

        // b1.group(group1);
        //   b1.channel(NioSocketChannel.class);
        b1.handler(new NettyClientFilter());
        ch = b1.connect(host, port).sync();

        return "success";
    }

    @RequestMapping("sendMsg")
    @ResponseBody
    public String sendMsg() {
        NioSocketChannel socketChannel;
        Map<Integer, NioSocketChannel> map = NettySocketHolder.getMAP();
        //发送数据给每一个客户端
        for (Integer key : map.keySet()) {
            socketChannel = NettySocketHolder.get(key);
            if (null == socketChannel) {
                throw new NullPointerException("没有[" + 1 + "]的socketChannel");
            }
            String message = "to Client";
            System.out.println("发送给客户端的数据:" + message);
            ChannelFuture future = socketChannel.writeAndFlush(message);
        }

        return "success";
    }

    @RequestMapping("test5")
    public String test5() {
        new Server(8080);
        return "success";
    }

    @RequestMapping("test6")
    public String test6() {
        Client bootstrap = new Client(8080, "127.0.0.1");
        return "success";
    }
    @RequestMapping("test7")
    public String test7() {
        //new Server(8080).destroy();
        return "success";
    }
}
