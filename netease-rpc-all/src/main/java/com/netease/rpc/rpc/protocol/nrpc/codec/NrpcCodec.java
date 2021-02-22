package com.netease.rpc.rpc.protocol.nrpc.codec;

import com.netease.rpc.common.serialize.Serialization;
import com.netease.rpc.remoting.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.List;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NrpcCodec implements Codec {
    @Override
    public String  encode(Object msg) throws Exception {
        System.out.println("编码:"+msg);
        if (msg instanceof DefaultFullHttpRequest){
            DefaultFullHttpRequest request = (DefaultFullHttpRequest)msg;
            ByteBuf jsonBuf = request.content();
            String jsonStr = jsonBuf.toString(CharsetUtil.UTF_8);
            System.out.println(jsonStr);
            return jsonStr;
        }
        return null;
    }

    @Override
    public void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) throws Exception {
        System.out.println("解码msg:"+msg);
        if (!msg.decoderResult().isSuccess()) {
            sendError(ctx, HttpResponseStatus.BAD_REQUEST);
            return;
        }
    }
    /**
     * 测试的话，直接封装，实战中需要更健壮的处理
     */
    private static void sendError(ChannelHandlerContext ctx,
                                  HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                status, Unpooled.copiedBuffer("Failure: " + status.toString()
                + "\r\n", CharsetUtil.UTF_8));
        response.headers().set("CONTENT-TYPE", "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    /*@Override
    public List<Object> decode(byte[] message) throws Exception {
        return null;
    }*/

    @Override
    public Codec createInstance() {
        NrpcCodec nrpcCodec = new NrpcCodec();
        nrpcCodec.setDecodeType(this.decodeType);
        nrpcCodec.setSerialization(this.serialization);
        return nrpcCodec;
    }

    Serialization serialization;

    public void setSerialization(Serialization serialization) {
        this.serialization = serialization;
    }

    public Serialization getSerialization() {
        return this.serialization;
    }

    Class decodeType;

    public Class getDecodeType() {
        return decodeType;
    }

    public void setDecodeType(Class decodeType) {
        this.decodeType = decodeType;
    }
}
