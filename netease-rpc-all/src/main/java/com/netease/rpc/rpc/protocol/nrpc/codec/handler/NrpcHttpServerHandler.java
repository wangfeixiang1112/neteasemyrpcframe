package com.netease.rpc.rpc.protocol.nrpc.codec.handler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netease.rpc.rpc.Content;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
public class NrpcHttpServerHandler  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(msg instanceof FullHttpRequest){

            FullHttpRequest req = (FullHttpRequest)msg;

            try {

                // 1.获取URI
                String uri = req.uri();

                // 2.获取请求体
                ByteBuf buf = req.content();
                String content = buf.toString(CharsetUtil.UTF_8);

                // 3.获取请求方法
                HttpMethod method = req.method();

                // 4.获取请求头
                HttpHeaders headers = req.headers();

                // 5.根据method，确定不同的逻辑
                if(method.equals(HttpMethod.GET)){

                    // TODO
                }

                if(method.equals(HttpMethod.POST)){
                    // 接收用户输入，并将输入返回给用户
                    Content c = new Content();
                    c.setUri(uri);
                    c.setContent(content);

                    response(ctx, c);
                }

                if(method.equals(HttpMethod.PUT)){
                    // TODO
                }

                if(method.equals(HttpMethod.DELETE)){
                    // TODO
                }
            } finally {
                req.release();
            }
        }
    }

    private void response(ChannelHandlerContext ctx, Content c) {
//2.创建objectmappter对象
        ObjectMapper mapper = new ObjectMapper();
        //3.调用mapper的writeValueAsString()方法把一个对象或集合转为json字符串
        // 1.设置响应
        FullHttpResponse resp = null;
        try {
            resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    Unpooled.copiedBuffer(mapper.writeValueAsString(c), CharsetUtil.UTF_8));
            resp.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");

            // 2.发送
            // 注意必须在使用完之后，close channel
            ctx.writeAndFlush(resp).addListener(ChannelFutureListener.CLOSE);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }
}
