package com.netease.rpc.remoting.netty;

import com.netease.rpc.remoting.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.net.URI;

public class NettyServer implements Server {
    //开启一个网络服务
    //创建时间循环组
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup worker = new NioEventLoopGroup();
    @Override
    public void start(URI uri) {
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker)//boss用来接收请求，worker用来处理请求
                    //指定所使用的nio传输channel
                    .channel(NioServerSocketChannel.class)
                    //指定要监听的地址
                    .localAddress(new InetSocketAddress(uri.getHost(),uri.getPort()))
                    //添加handler - 有链接之后处理逻辑
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //网络
                            socketChannel.pipeline().addLast(new NettyHandler());

                        }
                    })
                    ;
            ChannelFuture future = bootstrap.bind().sync();//同步绑定
            System.out.println("完成了端口绑定和服务启动");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
