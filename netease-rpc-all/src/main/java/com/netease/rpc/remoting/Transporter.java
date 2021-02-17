package com.netease.rpc.remoting;

import java.net.URI;

/**
 * 底层网络 - 传输统一入口【服务端、客户端】
 */
public interface Transporter {
    /**
     * dubbo://192.168.1.100:80
     * @param uri 服务器IP 端口
     * @return
     */
    Server start(URI uri, Codec codec, Handler handler);
}
