package com.netease.rpc.rpc.protocol.nrpc.codec.handler;

import com.netease.rpc.remoting.Handler;
import com.netease.rpc.remoting.NrpcChannel;
import com.netease.rpc.rpc.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

public class NrpcServerHandler implements Handler {
    // 收到数据 【发过来的请求、服务器给的响应】
    @Override
    public void onReceive(NrpcChannel nrpcChannel, Object message) throws Exception {
        System.out.println("onReceive message:"+message);
        if(message instanceof DefaultHttpRequest){
            System.out.println("onReceive DefaultHttpRequest:"+message);
            FullHttpRequest request = (FullHttpRequest)message;
            ByteBuf requestBody = request.content();
            System.out.println("FullHttpRequest requestBody:"+requestBody);
        }
        if(message instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest)message;
            ByteBuf requestBody = request.content();
            System.out.println("FullHttpRequest requestBody:"+requestBody);
        }
        if(message instanceof DefaultFullHttpRequest){
            System.out.println("message HttpRequest:"+message);
            DefaultFullHttpRequest request = (DefaultFullHttpRequest)message;
            ByteBuf requestBody = request.content();
            System.out.println("DefaultFullHttpRequest requestBody:"+requestBody);
        }

        System.out.println("*******************************");
       // RpcInvocation rpcInvocation = (RpcInvocation) message;
       // System.out.println("收到rpcInvocation信息:"+rpcInvocation);
        //TODO 发起具体的方法调用

    }

    @Override
    public void onWrite(NrpcChannel nrpcChannel, Object message) throws Exception {

    }
}
