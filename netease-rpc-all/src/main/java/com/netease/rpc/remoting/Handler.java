package com.netease.rpc.remoting;

// 由具体的协议去实现
public interface Handler {
    // 收到数据 【发过来的请求、服务器给的响应】
    void onReceive(NrpcChannel nrpcChannel, Object message) throws Exception;

    void onWrite(NrpcChannel nrpcChannel, Object message) throws Exception;
}
