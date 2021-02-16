package com.netease.rpc.rpc.protocol;


import java.net.URI;

/** 协议 */
public interface Protocol {
    /**
     * 开放服务
     * @param exportUri 协议名称://IP:端口/service全类名?参数名称=参数值&参数1名称=参数2值
     * @param //invoker 调用具体实现类的代理对象
     */
    public void export(URI exportUri);

}
