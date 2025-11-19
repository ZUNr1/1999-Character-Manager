package com.ZUNr1.util;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonDataLoader {
    private static final ObjectMapper mapper = new ObjectMapper();

    //ObjectMapper是jackson的类，用于序列化与反序列化，实现json和Java互转的类
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 配置：忽略JSON中的未知字段
        // 这是假如json中有类中没有声明的东西，原本会UnsupportedOperationException
        //加上这个configure忽略了未知字段，就避免了报错
    }
    public static List<CharactersJson> loadCharacters() {
        // 1. 先尝试加载用户数据（可修改）
        List<CharactersJson> userData = loadUserData();
        if (!userData.isEmpty()) {
            return userData;
        }
        // 2. 如果没有用户数据，加载内置默认数据
        return loadDefaultData();
    }
    private static List<CharactersJson> loadUserData(){
        String userDataPath = getUserDataPath();
        File userFile = new File(userDataPath);
        if (userFile.exists()){
            //try-with-resources 语法
            try (InputStream inputStream = new FileInputStream(userFile)) {
                //new FileInputStream(userFile) - 创建文件输入流
                //mapper.readValue(inputStream, CharactersData.class) - Jackson将JSON转换为Java对象
                CharactersData data = mapper.readValue(inputStream, CharactersData.class);
                return data.characters != null ? data.characters : new ArrayList<>();
            } catch (Exception e) {
                System.err.println("加载用户数据失败，使用默认数据: " + e.getMessage());
            }
        }
        return new ArrayList<>();
    }


    private static List<CharactersJson> loadDefaultData() {
        //这是从classpath加载JSON数据的标准方法，
        try (InputStream inputStream = JsonDataLoader.class.getClassLoader()
                .getResourceAsStream("data/characters.json")) {
            //inputStream是用来接收数据流，
            //JsonDataLoader.class是我们这个类本身，是我的位置（基准点）
            //.getClassLoader()作用：获取加载这个类的类加载器
            //.getResourceAsStream("data/characters.json")
            // 让类加载器从特定路径读取文件，并返回一个数据流

            if (inputStream == null) {
                System.out.println("内置文件有问题，返回空链表");
                return new ArrayList<>();
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
    public static String getUserDataPath(){//这个方法要public，因为持久化类也要使用
        String userHome = System.getProperty("user.home");
        //Java 中的 System 类有一个 Properties 对象，用于存储当前工作环境的不同属性和配置。它还保存用户的主目录。
        //我们可以使用此类的 getProperty() 方法访问这些属性。"user.home"就是获得用户的主目录（c盘用户）
        return userHome + "/.zunr1-guide/characters.json";
    }

    public static class CharactersJson {
        //这个内部类是存放从json中读取到的Character类型，分类放好
        public String id;
        public String name;
        public String enName;
        public Gender gender;
        public boolean isCustom;
        public String creator;
        public Afflatus afflatus;
        public DamageType damageType;
        public Attributes attributes;
        public Skills skills;
        public List<String> usedTerm;
        public Inheritance inheritance;
        public Portrait portrait;
        public List<Euphoria> euphoria;
        public OtherInformation otherInformation;
        public int rarity;
    }

    public static class CharactersData {
        //这个内部类是合并所有对象成集合
        public List<CharactersJson> characters;
    }
}
