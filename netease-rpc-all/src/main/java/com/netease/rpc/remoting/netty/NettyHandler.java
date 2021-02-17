package com.netease.rpc.remoting.netty;

import com.netease.rpc.remoting.Handler;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理内容
 */
public class NettyHandler extends ChannelDuplexHandler {
    private Handler handler;

    public NettyHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * 入栈事件 - 收到数据【接收网络请求/响应】
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("入栈事件 - 收到数据【接收网络请求/响应】"+msg);
        handler.onReceive(new NettyChannel(ctx.channel()), msg);
    }
}
