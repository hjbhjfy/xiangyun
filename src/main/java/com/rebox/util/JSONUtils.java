package com.rebox.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json处理的工具类
 *
 */
public class JSONUtils {

    //映射工具类，java对象 <-----> json字符串 进行相互转化
    private final static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 把java对象转成json字符串
     *
     * @param object
     * @return
     */
    public static String writeValueAsString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            //把检查式异常 转化为 运行时异常
            throw new RuntimeException(e);
        }
    }

    /**
     * 把json字符串转成Java对象
     *
     * @param json
     * @param clazz
     * @return
     * @param <T>
     */
    public static <T> T readValue (String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}