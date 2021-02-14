package com.netease.rpc.service.impl;

import com.netease.rpc.service.MyServiceProviderInterface;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class MyServiceProviderInterfaceImpl implements MyServiceProviderInterface {
    @Override
    public String sayHi(String name,String words){
        System.out.println("》》》》》》》》》》》》》》》》》》》》》服务端方法执行");
        return "Hi, "+name+". "+words;
    }
}
