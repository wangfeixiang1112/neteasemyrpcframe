package com.netease.rpc.rpc.protocol.nrpc.codec.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.util.CharsetUtil;

public class NrpcHttpClientHandler extends ChannelInboundHandlerAdapter {
    private Object response;

    public Object getResponse(){
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //用FullHttpResponse解析服务器返回来的信息
        FullHttpResponse res = (FullHttpResponse) msg;
        ByteBuf content = res.content();
        response = content.toString(CharsetUtil.UTF_8);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
