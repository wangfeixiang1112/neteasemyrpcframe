package com.netease.rpc;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Hello world!
 *
 */
@Configuration
@ComponentScan("com.netease.rpc")//Spring注解掃描
@PropertySource("classpath:/dubbo.properties")
@EnableDubbo(scanBasePackages = "com.netease.rpc")
public class ServiceProviderApp
{
    public static void main( String[] args ) throws Exception
    {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceProviderApp.class);
        context.start();

        System.in.read();//阻塞不推出
        context.close();
    }
}
