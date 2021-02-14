package com.netease.rpc;

//import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.netease.rpc.config.spring.annotation.EnableNRpc;
import com.netease.rpc.service.impl.MyServiceProviderInterfaceImpl;
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
@PropertySource("classpath:/nrpc.properties")
@EnableNRpc
//@EnableDubbo(scanBasePackages = "com.netease.rpc")
public class ServiceProviderApp
{
    public static void main( String[] args ) throws Exception
    {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceProviderApp.class);
        context.start();

        MyServiceProviderInterfaceImpl serviceProviderInterface = context.getBean(MyServiceProviderInterfaceImpl.class);
        String result = serviceProviderInterface.sayHi("岳不群","你是个伪君子");
        System.out.println("调用结果:"+result);

        System.in.read();//阻塞不推出
        context.close();
    }
}
