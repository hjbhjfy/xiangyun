package com.rebox.manager;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通用的业务功能，放在manager包下
 *
 * 此类就是一个redis的通用功能
 *
 */
@Component
public class RedisManager {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //redis值的类型： string、hash、list、set、zset

    /**
     * 向redis中放数据（值的类型为string结构）
     *
     * @param key
     * @param value
     */
    public void setValue(String key, String value, long expireTime, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, expireTime, timeUnit);
    }

    /**
     * 根据key从redis中获取值（值的类型为string结构）
     *
     * @param key
     */
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除redis的key
     *
     * @param key
     * @return
     */
    public Boolean removeKey(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 给keu设置过期时间
     *
     * @param key
     * @param timeout
     * @param unit
     * @return
     */
    public Boolean flushExpireTime(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    /**
     * 向redis写入hash结构的数据
     *
     * @param key
     * @param hashKey
     * @param hashValue
     */
    public void setHashValue(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 从redis的hash结构中获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object getHashValue(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }
}