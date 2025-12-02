package com.ZUNr1.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonConverter {
    //这个类主要负责任意对象与json格式互相转换
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    //把Java的对象转换为json格式的字符串
    public static String toString(Object object){
        //这接收的是对象
        if (object == null){
            return null;
        }
        try {
            return mapper.writeValueAsString(object);
            //这个方法可以将Java的对象（Object）转换为json格式的字符串
            //writeValue方法是直接写入json文件，writeValueAsString是转换为字符串，没有写入
        } catch (Exception e) {
            throw new RuntimeException("JSON序列化失败: " + object.getClass().getName(), e);
        }
    }
    public static <T> T fromJson(String json,Class<T> clazz){
        //泛型，传入一个Class类，未知的，返回的同一个类型的对象
        //我们不传入对象Object，传入的是类，因为我们是要让jackson知道这段json字符串是什么类的
        if (json == null || json.trim().isEmpty()){
            return null;
        }
        try {
            return mapper.readValue(json,clazz);
            //这个方法读取json类型的字符串，转换为对应的类的对象
            //需要json类型的字符串和一个类Class本身（对象.class或者直接类名）
        }catch (Exception e){
            throw new RuntimeException("JSON反序列化失败: " + json, e);
        }
    }
    public static <T> T fromJson(String json, TypeReference<T> typeReference){
        //我们考虑一个问题，对于泛型类，如List和Map，它们会进行类型擦除，也就是说像上面那个fromJson，我们接收的类是List<Object>
        //我们需要保留泛型类型信息如List<String>，这样readValue才可以转换为对应的
        //TypeReference就是一个用于json的类，可以保留泛型的类型信息
        //TypeReference<List<String>> typeReference = new TypeReference<List<String>>() {};
        //更厉害的，TypeReference可以处理嵌套的泛型，如嵌套map
        if (json == null || json.trim().isEmpty()){
            return null;
        }
        try {
            return mapper.readValue(json, typeReference);
            //将TypeReference传给readValue，ObjectMapper可以自动读取里面藏的类型信息，转换为对应的
        }catch (Exception e){
            throw new RuntimeException("JSON反序列化失败: " + json, e);
        }
    }
}
