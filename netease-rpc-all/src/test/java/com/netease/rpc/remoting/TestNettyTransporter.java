package com.netease.rpc.remoting;

import com.netease.rpc.remoting.netty.NettyTransporter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.net.URI;
import java.util.List;

public class TestNettyTransporter {
     /*  public static void main(String[] args) throws Exception{
     new NettyTransporter().start(new URI("http://127.0.0.1:18080"), new Codec() {
            @Override
            public byte[] encode(Object msg) throws Exception {
                return new byte[0];
            }

           *//* @Override
            public List<Object> decode(byte[] message) throws Exception {
                return null;
            }*//*

            @Override
            public void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {

            }

            @Override
            public Codec createInstance() {
                return null;
            }
        }, new Handler() {
            @Override
            public void onReceive(NrpcChannel nrpcChannel, Object message) throws Exception {
                System.out.println("收到数据:"+message);
            }

            @Override
            public void onWrite(NrpcChannel nrpcChannel, Object message) throws Exception {

            }
        });
    }*/
}