package com.netease.rpc.remoting.netty;

import com.netease.rpc.remoting.NrpcChannel;
import io.netty.channel.Channel;

public class NettyChannel implements NrpcChannel {
    Channel channel;

    public NettyChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void send(byte[] message) {
        channel.writeAndFlush(message);
    }
}
