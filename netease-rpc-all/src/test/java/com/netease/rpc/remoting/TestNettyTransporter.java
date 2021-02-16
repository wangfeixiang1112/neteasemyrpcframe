package com.netease.rpc.remoting;

import com.netease.rpc.remoting.netty.NettyTransporter;

import java.net.URI;
public class TestNettyTransporter {
    public static void main(String[] args) throws Exception{
        new NettyTransporter().start(new URI("http://127.0.0.1:18080"), new Handler() {
            @Override
            public void onReceive(NrpcChannel nrpcChannel, Object message) throws Exception {
                System.out.println("收到数据:"+message);
            }

            @Override
            public void onWrite(NrpcChannel nrpcChannel, Object message) throws Exception {

            }
        });
    }
}