package com.netease.rpc.protocol;

import com.netease.rpc.common.serialize.Serialization;
import com.netease.rpc.common.tools.SpiUtils;
import com.netease.rpc.rpc.RpcInvocation;
import com.netease.rpc.rpc.protocol.nrpc.codec.handler.NrpcHttpClientHandler;
import com.netease.rpc.rpc.protocol.nrpc.codec.handler.NrpcHttpServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import java.net.URI;
import java.net.URISyntaxException;

public class ClientMock1 {
/*    public static void main(String[] args) throws Exception {
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
    }*/

    public static void main(String[] args)throws Exception {
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setServiceName("com.netease.rpc.service.MyServiceProviderInterface");
        rpcInvocation.setMethodName("sayHi");
        rpcInvocation.setParameterTypes(new Class[]{String.class, String.class});
        rpcInvocation.setArguments(new Object[]{"岳不群", "你是个伪君子"});
        Serialization serialization =
                (Serialization) SpiUtils.getServiceImpl("JsonSerialization", Serialization.class);
        byte[] requestBody = serialization.serialize(rpcInvocation);

        ByteBuf requestBuffer = Unpooled.buffer();
        requestBuffer.writeBytes(requestBody);

        final NrpcHttpClientHandler nrpcHttpClientHandler = new NrpcHttpClientHandler();
        EventLoopGroup group=new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        client.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //包含编码器和解码器:
                        // pipeline.addLast(new HttpClientCodec())等价于new HttpRequestEncoder()  + new HttpResponseDecoder()
                        //request编码
                        pipeline.addLast(new HttpRequestEncoder())
                                //respon解码
                                .addLast(new HttpResponseDecoder())
                                .addLast(new HttpObjectAggregator(10240))

                                .addLast(nrpcHttpClientHandler);
                    }
                });
        try {
            ChannelFuture future = client.connect("localhost", 18080).sync();
            future.channel().writeAndFlush(httpParams(requestBuffer)).sync();
            future.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
        //服务端返回的结果
        System.out.println("服务端返回的结果\n"+nrpcHttpClientHandler.getResponse());
    }

    /**
     * 构建FullHttpRequest 发送请求
     * @return
     * @throws URISyntaxException
     */
    private static FullHttpRequest httpParams(ByteBuf requestBuffer) throws URISyntaxException {
        //URI uri = new URI("?id=123");
        URI uri = new URI("");
        FullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1,
                HttpMethod.GET,
                uri.toASCIIString(),requestBuffer);
        return request;
    }
}