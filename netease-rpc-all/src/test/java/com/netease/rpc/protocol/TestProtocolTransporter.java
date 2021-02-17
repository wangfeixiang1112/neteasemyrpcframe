package com.netease.rpc.protocol;

import com.netease.rpc.common.serialize.json.JsonSerialization;
import com.netease.rpc.remoting.Codec;
import com.netease.rpc.remoting.Handler;
import com.netease.rpc.remoting.NrpcChannel;
import com.netease.rpc.remoting.netty.NettyTransporter;
import com.netease.rpc.rpc.RpcInvocation;
import com.netease.rpc.rpc.protocol.nrpc.codec.NrpcCodec;
import com.netease.rpc.rpc.protocol.nrpc.codec.handler.NrpcServerHandler;

import java.net.URI;

public class TestProtocolTransporter {
    public static void main(String[] args) throws Exception{
        NrpcCodec codec = new NrpcCodec();
        codec.setDecodeType(RpcInvocation.class);
        codec.setSerialization(new JsonSerialization());
        NrpcServerHandler nrpcServerHandler = new NrpcServerHandler();
        new NettyTransporter().start(new URI("http://127.0.0.1:18080"),codec, nrpcServerHandler);
    }
}