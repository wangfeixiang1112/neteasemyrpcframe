package com.netease.rpc.service;

public interface MyServiceProviderInterface {
    /**
     * 向某人打招呼
     * @param name
     * @param words
     * @return
     */
    public String sayHi(String name, String words);
}
