package com.netease.rpc.config.spring.annotation;

import com.netease.rpc.config.spring.NRpcConfiguration;
import com.netease.rpc.config.spring.NRpcPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启动 nrpc功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({NRpcPostProcessor.class, NRpcConfiguration.class})
public @interface EnableNRpc {
}
