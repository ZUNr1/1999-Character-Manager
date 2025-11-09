package com.ZUNr1.util;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader {
    private static final ObjectMapper mapper = new ObjectMapper();
    //ObjectMapper是jackson的类，用于序列化与反序列化，实现json和Java互转的类
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        // 配置：忽略JSON中的未知字段
        // 这是假如json中有类中没有声明的东西，原本会UnsupportedOperationException
        //加上这个configure忽略了未知字段，就避免了报错
    }
    public static class CharactersJson{
        //这个内部类是存放从json中读取到的Character类型，分类放好
        public String id;
        public String name;
        public Gender gender;
        public Afflatus afflatus;
        public DamageType damageType;
        public int level;
        public Attributes attributes;
        public Skills skills;
        public List<String> usedTerm;
        public Inheritance inheritance;
        public Portrait portrait;
        public OtherInformation otherInformation;
        public int rarity;
    }
    public static class CharactersData{
        //这个内部类是合并所有对象成集合
        public List<CharactersJson> characters;
    }
    public static List<CharactersJson> loadCharacters(){
        //这是从classpath加载JSON数据的标准方法，
        try (InputStream inputStream = JsonDataLoader.class.getClassLoader()
                    .getResourceAsStream("data/characters.json")){
            //inputStream是用来接收数据流，
            //JsonDataLoader.class是我们这个类本身，是我的位置（基准点）
            //.getClassLoader()作用：获取加载这个类的类加载器
            //.getResourceAsStream("data/characters.json")
            // 让类加载器从特定路径读取文件，并返回一个数据流

            if (inputStream == null) {
                throw new RuntimeException("在classpath中找不到文件: data/characters.json");
            }

            CharactersData data = mapper.readValue(inputStream, CharactersData.class);
            //ObjectMapper的readValue方法是从左边的位置读取json，然后以右边的参数的形式解析
            //左边的可以是转为json的字符串格式，也可以是文件位置，也可以是流
            //这里要用流，单纯文件位置找不到
            return data.characters != null ? data.characters : new ArrayList<>();

        } catch (Exception e) {
            throw new RuntimeException("加载角色数据失败: " + e.getMessage(), e);
            //这个异常是要catch的
        }
        //上述代码是一个try-with-resources，相当于在finally语句块中关闭了input流
    }
}
