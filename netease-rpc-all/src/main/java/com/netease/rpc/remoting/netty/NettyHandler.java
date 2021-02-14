package com.netease.rpc.remoting.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;

/**
 * 处理内容
 */
public class NettyHandler extends ChannelDuplexHandler {
    /**
     * 入栈事件 - 收到数据【接收网络请求/响应】
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("内容:"+msg);
    }
}
