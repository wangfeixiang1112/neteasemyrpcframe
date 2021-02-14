package com.netease.rpc.remoting;

import java.net.URI;

/**
 * 启动网络，访问服务
 */
public interface Server {

    void start(URI uri);
}
