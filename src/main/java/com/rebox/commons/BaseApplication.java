package com.rebox.commons;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * ClassName:BaseApplication
 * Package:com.rebox.commons
 * Descriptionn:
 *
 * @Author wqh
 * @Create 2024/5/8 20:38
 * @Version 1.0
 */


public class BaseApplication implements CommandLineRunner {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 1、该方法在springboot的main的最后一行调用；
     * 2、该方法只有一次执行机会，只有在启动springboot程序的时候执行一次，所以该方法可以用于做系统启动时的初始化工作；
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        //设置value的序列化方式使用json的序列化方式（默认情况下是jdk序列化） （在项目开发中者4行代码写一次就行了，也是固定的）
        ObjectMapper objectMapper = new ObjectMapper(); //对象映射工具类， 可以实现 java对象 <----> json对象 的相互转化
        //设置对象里面的字段的可见性
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);// 可见度; 能见度; 能见距离; 可见性; 明显性;
        //设置多态行为的
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.EVERYTHING);
        //创建jackson序列化器
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(objectMapper, Object.class);


        //把redis的key和value的序列化方式调整一下，不使用默认的，采用json序列化
        //设置key的序列化方式使用字符串的序列化方式（默认情况下是jdk序列化）
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //这样放进去的数据，可有直接还原为原始对象，放进去是json，取出来是对象
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);


        //对hash键的序列化 (用字符串序列化)
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //对hash值的序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
    }
}
