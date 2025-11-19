package com.ZUNr1.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class JsonDataPersister {
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        //美化json文件中东西的排列，原本是紧凑的，现在改为有缩进
        //注意SerializationFeature.INDENT_OUTPUT 只影响 序列化（输出） 过程
        //在加载的时候不需要考虑美化
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //与load中的一样，configure忽略未知的字段
    }

    public static void saveCharacters(List<JsonDataLoader.CharactersJson> characters) {
        //这个方法的目的是简化输入，我们调用保存东西的时候，只写数据，不用再写保存的位置了
        //相当于输入的时候不指定保存目录，我们选择默认的目录保存
        String userDataPath = JsonDataLoader.getUserDataPath();
        //始终保存到用户数据目录，不修改内置数据
        saveCharacters(characters,userDataPath);
    }


    public static void saveCharacters(List<JsonDataLoader.CharactersJson> characters, String filePath) {
        //这个方法是实际保存的方法，我们直接调用这个方法可以实现指定目录位置的保存，
        // 也可以使用上一个方法，那个默认路径的保存，这是重载的好处
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("文件路径不能为空");
        }
        try {
            JsonDataLoader.CharactersData charactersData = new JsonDataLoader.CharactersData();
            //创建了一个容器类对象，为了储存参数的这个list
            charactersData.characters = characters;
            ensureDirectoryExists(filePath);
            //专门写一个方法，实现假如没有这个文件的父目录，就创建这个文件的父目录
            mapper.writeValue(new File(filePath), charactersData);
            //这里可以保证没有文件就创建文件
            //writeValue可以实现写入json
            //filePath只是对象的地址，是String，但是writeValue左边的参数是File对象
            //我们new一个File对象做参数
        } catch (IOException e) {
            throw new RuntimeException("保存角色数据失败", e);
            //提供上下文信息
        }
    }

    private static void ensureDirectoryExists(String filePath) {
        //确保路径中的文件的父目录存在，不然就创建
        File file = new File(filePath);
        File parentFile = file.getParentFile();
        //找到文件的父目录位置
        if (parentFile != null && !parentFile.exists()) {
            //什么时候 getParentFile() 会返回 null？文件路径没有父目录（在当前目录）或者询问根目录的父目录
            //不存在表示文件路径写的父目录是合理的，但是没有
            //我们先确定是不是null，短路求值，这样调用exists的时候不用担心空指针
            boolean isCreate = parentFile.mkdirs();
            if (!isCreate) {
                throw new RuntimeException("无法创建目录" + parentFile.getAbsolutePath());
            }
        }

    }
}
