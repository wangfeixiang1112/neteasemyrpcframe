package com.netease.rpc.config.spring;

import com.netease.rpc.config.ProtocolConfig;
import com.netease.rpc.config.RegistryConfig;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Field;

/**
 * 如何把自己创建的对象放到spring - BeanDefinition
 */
public class NRpcConfiguration implements ImportBeanDefinitionRegistrar {

    StandardEnvironment environment;
    public NRpcConfiguration(Environment environment){
        this.environment = (StandardEnvironment) environment;
    }

    //让spring启动的时候，装载没有注解/xml配置
    @Override
    public  void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry,
                                         BeanNameGenerator importBeanNameGenerator) {

        BeanDefinitionBuilder  beanDefinitionBuilder = null;

        //1.2 ProtocolConfig - 读取配置赋值 - nrpc.protocol.name
        beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ProtocolConfig.class);
        for(Field field : ProtocolConfig.class.getDeclaredFields()){
            String value = environment.getProperty("nrpc.protocol."+field.getName());//从配置文件找到相匹配的值
            beanDefinitionBuilder.addPropertyValue(field.getName(),value);

        }
        registry.registerBeanDefinition("protocolConfig",beanDefinitionBuilder.getBeanDefinition());

        //1.2 RegistryConfig - 读取配置赋值 - nrpc.registry.address
        beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(RegistryConfig.class);
        for(Field field : RegistryConfig.class.getDeclaredFields()){
            String value = environment.getProperty("nrpc.registry."+field.getName());//从配置文件找到相匹配的值
            beanDefinitionBuilder.addPropertyValue(field.getName(),value);

        }
        registry.registerBeanDefinition("registryConfig",beanDefinitionBuilder.getBeanDefinition());
    }
}
