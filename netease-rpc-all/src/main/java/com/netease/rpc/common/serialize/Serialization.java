package com.netease.rpc.common.serialize;

/**
 * 序列化接口
 */
public interface Serialization {
    byte[] serialize(Object output) throws Exception;

    Object deserialize(byte[] input, Class clazz) throws Exception;
}
