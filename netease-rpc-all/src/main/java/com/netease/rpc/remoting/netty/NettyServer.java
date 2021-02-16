package com.netease.rpc.remoting.netty;
import com.netease.rpc.remoting.Handler;
import com.netease.rpc.remoting.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.InetSocketAddress;
import java.net.URI;

public class NettyServer implements Server {
    //开启一个网络服务
    //创建时间循环组
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    @Override
    public void start(URI uri,Handler handler) {
        InetSocketAddress socketAddress = new InetSocketAddress(uri.getHost(),uri.getPort());
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker)//boss用来接收请求，worker用来处理请求
                    //指定所使用的nio传输channel
                    .channel(NioServerSocketChannel.class)
                    //指定要监听的地址
                    .localAddress(socketAddress)
                    //添加handler - 有链接之后处理逻辑
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //网络
                            //socketChannel.pipeline().addLast(new NettyHandler());
                            socketChannel.pipeline().addLast(new HttpResponseEncoder(),
                                    new HttpRequestDecoder(),
                                    new NettyHandler(handler));

                        }
                    }).option(ChannelOption.SO_BACKLOG, 128) //设置队列大小
                    // 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    ;
            ChannelFuture future = bootstrap.bind(socketAddress).sync();//同步绑定
            System.out.println("完成了端口绑定和服务启动");
            System.out.println("服务器启动开始监听端口"+socketAddress.getPort());
            future.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭主线程组
            boss.shutdownGracefully();
            //关闭工作线程组
            worker.shutdownGracefully();
        }
    }
}
