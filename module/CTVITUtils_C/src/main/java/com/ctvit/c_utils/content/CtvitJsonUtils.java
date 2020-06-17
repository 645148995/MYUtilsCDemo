package com.ctvit.c_utils.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;
import java.util.Map;

/**
 * 基于fastjson封装的json转换工具类
 */
public class CtvitJsonUtils {
    /**
     * 把JSON数据转换成指定的java对象
     */
    public static <T> T jsonToBean(String jsonData, Class<T> clazz) {
        return JSON.parseObject(jsonData, clazz);
    }

    /**
     * 把java对象转换成JSON数据
     */
    public static String beanToJson(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 把java对象转换成JSON数据
     */
    public static String beanToJson(Object object, SerializerFeature... features) {
        return JSON.toJSONString(object, features);
    }

    /**
     * 把JSON数据转换成指定的java对象列表
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> clazz) {
        return JSON.parseArray(jsonData, clazz);
    }

    /**
     * 把JSON数据转换成较为复杂的List<Map<String, Object>>
     */
    public static List<Map<String, Object>> jsonToListMap(String jsonData) {
        return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
        });
    }
}
