package com.netease.rpc.config.spring;

import com.netease.rpc.common.tools.SpiUtils;
import com.netease.rpc.config.ProtocolConfig;
import com.netease.rpc.config.RegistryConfig;
import com.netease.rpc.config.annotation.NRpcService;
import com.netease.rpc.remoting.Transporter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.net.URI;
import java.net.URISyntaxException;

public class NRpcPostProcessor implements ApplicationContextAware, InstantiationAwareBeanPostProcessor {
    private  ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //NRpcService注解修饰的类是需要向外暴露的服务
        if(bean.getClass().isAnnotationPresent(NRpcService.class)){
            System.out.println("找到了需要开放网络的service实现类,启动网络服务，接收请求");
            ProtocolConfig protocolConfig = applicationContext.getBean(ProtocolConfig.class);
            String name = protocolConfig.getName();
            String transporterName = protocolConfig.getTransporter();
            Transporter transporter = (Transporter) SpiUtils.getServiceImpl(transporterName, Transporter.class);
            /*try {
                transporter.start(new URI("xxxx://127.0.0.1:8080/"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }*/
            //Transporter

        }

        if(bean.getClass().equals(RegistryConfig.class)){
            RegistryConfig config = (RegistryConfig) bean;
            System.out.println("证明成功加载了配置文件并且spring创建Bean:"+config.getAddress());
        }
        return bean;
    }
}
