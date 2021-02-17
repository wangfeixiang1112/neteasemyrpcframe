package com.netease.rpc.remoting.netty;

import com.netease.rpc.remoting.Codec;
import com.netease.rpc.remoting.Handler;
import com.netease.rpc.remoting.Server;
import com.netease.rpc.remoting.Transporter;

import java.net.URI;

/**
 * 底层网络 - 传输统一入口【服务端、客户端】
 */
public class NettyTransporter implements Transporter {
    @Override
    public Server start(URI uri, Codec codec, Handler handler) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.start(uri,codec,handler);
        return nettyServer;
    }
}
