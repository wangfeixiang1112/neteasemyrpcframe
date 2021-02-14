package com.netease.rpc;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.netease.rpc.service.MyServiceCustomerService;
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
public class ServiceCustomerApp
{
    public static void main( String[] args ) throws Exception
    {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceCustomerApp.class);
        context.start();

        MyServiceCustomerService service = context.getBean(MyServiceCustomerService.class);
        String words = service.seeSomeBody("劉備");
        System.out.println(words);
        System.in.read();//阻塞不推出
        context.close();
    }
}
