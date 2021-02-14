package com.netease.rpc.service.impl;
import com.netease.rpc.config.annotation.NRpcReference;
import  com.netease.rpc.service.MyServiceCustomerService;
import com.netease.rpc.service.MyServiceProviderInterface;
import org.springframework.stereotype.Service;

@Service
public class MyServiceCustomerServiceImpl implements MyServiceCustomerService {

    //@DubboReference
    @NRpcReference
    private MyServiceProviderInterface myServiceProviderInterface;
    @Override
    public String seeSomeBody(String name) {
        String words = "I saw "+name+" in the street";
        System.out.println("执行客户端方法,调用服务端");
        String result = myServiceProviderInterface.sayHi(name,words);
        System.out.println("服务端返回结果:"+result);
        return words;
    }

}
