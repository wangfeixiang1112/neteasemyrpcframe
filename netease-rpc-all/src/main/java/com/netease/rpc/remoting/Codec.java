package com.netease.rpc.remoting;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.List;

public interface Codec {
    byte[] encode(Object msg) throws Exception;

    //List<Object> decode(byte[] message) throws Exception;
    void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception;

    Codec createInstance();
}
