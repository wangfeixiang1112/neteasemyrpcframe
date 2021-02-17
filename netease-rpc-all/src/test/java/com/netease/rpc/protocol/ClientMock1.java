package com.netease.rpc.protocol;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.rpc.common.serialize.Serialization;
import com.netease.rpc.common.serialize.json.JsonSerialization;
import com.netease.rpc.common.tools.SpiUtils;
import com.netease.rpc.remoting.netty.NettyCodec;
import com.netease.rpc.remoting.netty.NettyHandler;
import com.netease.rpc.rpc.RpcInvocation;
import com.netease.rpc.rpc.protocol.nrpc.codec.NrpcCodec;
import com.netease.rpc.rpc.protocol.nrpc.codec.handler.NrpcHttpClientHandler;
import com.netease.rpc.rpc.protocol.nrpc.codec.handler.NrpcHttpServerHandler;
import com.netease.rpc.rpc.protocol.nrpc.codec.handler.NrpcServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
//https://www.cnblogs.com/carl10086/p/6185095.html
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
        NrpcCodec codec = new NrpcCodec();
        codec.setDecodeType(RpcInvocation.class);
        codec.setSerialization(new JsonSerialization());
        NrpcServerHandler nrpcServerHandler = new NrpcServerHandler();
        RpcInvocation rpcInvocation = new RpcInvocation();
        rpcInvocation.setServiceName("com.netease.rpc.service.MyServiceProviderInterface");
        rpcInvocation.setMethodName("sayHi");
        rpcInvocation.setParameterTypes(new Class[]{String.class, String.class});
        rpcInvocation.setArguments(new Object[]{"岳不群", "你是个伪君子"});
        Serialization serialization =
                (Serialization) SpiUtils.getServiceImpl("JsonSerialization", Serialization.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonStr = objectMapper.writeValueAsString(rpcInvocation);
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
                        pipeline.addLast(new NettyCodec(codec.createInstance()),
                                new NettyHandler(nrpcServerHandler));
                        //包含编码器和解码器:
                        // pipeline.addLast(new HttpClientCodec())等价于new HttpRequestEncoder()  + new HttpResponseDecoder()
                        //request编码
                       /* pipeline.addLast(new HttpRequestEncoder())
                                //respon解码
                                .addLast(new HttpResponseDecoder())
                                .addLast(new HttpObjectAggregator(10240))

                                .addLast(nrpcHttpClientHandler);*/
                    }
                });
        try {
            ChannelFuture future = client.connect("localhost", 18080).sync();
            future.channel().writeAndFlush(httpParams(jsonStr)).sync();
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
    private static DefaultFullHttpRequest httpParams(String content) throws URISyntaxException {
        //URI uri = new URI("?id=123");
        URI uri = new URI("");
       /* DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1,
                HttpMethod.POST,
                uri.toASCIIString(),requestBuffer);*/

        DefaultFullHttpRequest request = null;
        try {
            request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                    uri.toASCIIString(), Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            request.headers().set(HttpHeaders.Names.HOST, "127.0.0.1");
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.CONTENT_LENGTH,
                    request.content().readableBytes());
            request.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/json");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        return request;
    }
}