package com.netease.rpc.protocol;

import com.netease.rpc.common.serialize.Serialization;
import com.netease.rpc.common.tools.ByteUtil;
import com.netease.rpc.common.tools.SpiUtils;
import com.netease.rpc.rpc.RpcInvocation;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ClientMock {
    public static void main(String[] args) throws Exception {
        // 1. 构建body
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setServiceName("com.netease.rpc.service.MyServiceProviderInterface");
        rpcInvocation.setMethodName("sayHi");
        rpcInvocation.setParameterTypes(new Class[]{String.class, String.class});
        rpcInvocation.setArguments(new Object[]{"岳不群", "你是个伪君子"});
        Serialization serialization =
                (Serialization) SpiUtils.getServiceImpl("JsonSerialization", Serialization.class);
        byte[] requestBody = serialization.serialize(rpcInvocation);

        // 2. 构建header
        ByteBuf requestBuffer = Unpooled.buffer();
        requestBuffer.writeByte(0xda);
        requestBuffer.writeByte(0xbb);
        requestBuffer.writeBytes(ByteUtil.int2bytes(requestBody.length));
        requestBuffer.writeBytes(requestBody);
        // 3. 发起请求
        SocketChannel trpcClient = SocketChannel.open();
        trpcClient.connect(new InetSocketAddress("127.0.0.1", 18080));
        trpcClient.write(ByteBuffer.wrap(requestBuffer.array()));
        // 4. 接收响应
       /* ByteBuffer response = ByteBuffer.allocate(1025);
        trpcClient.read(response);
        System.out.println("响应内容：");
        System.out.println(new String(response.array()));*/
    }
}
