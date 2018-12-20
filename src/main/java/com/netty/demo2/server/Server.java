package com.netty.demo2.server;

import com.netty.demo2.util.NettySocketHolder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.Charset;

public class Server {
    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup work = new NioEventLoopGroup();

    private static ServerSocketChannel serverSocketChannel;

    public Server(int serverPort){
        bind(serverPort);
    }

    private void bind(int serverPort) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //服务端要建立两个group，一个负责接收客户端的连接，一个负责处理数据传输
                //连接处理group
                 //boss = new NioEventLoopGroup();
                //事件处理group
                // worker = new NioEventLoopGroup();
                ServerBootstrap bootstrap = new ServerBootstrap();
                // 绑定处理group
                bootstrap.group(boss, work).channel(NioServerSocketChannel.class)
                        //保持连接数
                        .option(ChannelOption.SO_BACKLOG, 128)
                        //有数据立即发送
                        .option(ChannelOption.TCP_NODELAY, true)
                        //保持连接
                        .childOption(ChannelOption.SO_KEEPALIVE, true)
                        //处理新连接
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel sc) throws Exception {
                                // 增加任务处理
                                ChannelPipeline p = sc.pipeline();
                                p.addLast(
//										//使用了netty自带的编码器和解码器
										new StringDecoder(Charset.forName("utf-8")),
										new StringEncoder(Charset.forName("utf-8")),
                                        //new MessageDecoder(),
                                        //new MessageEncoder(),
                                        //自定义的处理器
                                        new ServerHandler());
                            }
                        });

                //绑定端口，同步等待成功
                ChannelFuture future;
                try {
                    future = bootstrap.bind(serverPort).sync();
                    if (future.isSuccess()) {
                        serverSocketChannel = (ServerSocketChannel) future.channel();
                        System.out.println("服务端开启成功");
                    } else {
                        System.out.println("服务端开启失败");
                    }

                    //等待服务监听端口关闭,就是由于这里会将线程阻塞，导致无法发送信息，所以我这里开了线程
                    future.channel().closeFuture().sync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    //优雅地退出，释放线程池资源
                    boss.shutdownGracefully();
                    work.shutdownGracefully();
                }
            }
        });
        thread.start();
    }

    public static void sendMessage(TextWebSocketFrame msg){
        NioSocketChannel socketChannel = com.netty.demo5.NettySocketHolder.get(1L);

        if (null == socketChannel) {
            throw new NullPointerException("没有[" + 1 + "]的socketChannel");
        }
        System.out.println(NettySocketHolder.getMAP());
        String message="to Client";
        System.out.println("发送给客户端的数据:"+message);
        ChannelFuture future = socketChannel.writeAndFlush(message);

    }
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        System.out.println("关闭 Netty 成功");
    }
}
