package com.ZUNr1.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static HikariDataSource dataSource;
    static{
        loadConfig();//static块为静态初始化块，在类加载的时候执行，且只执行一次
    }
    private static void loadConfig(){
        // [学习记录] 这里原本使用DriverManager，现在升级为HikariCP连接池
        // 原因：连接池提供更好的性能和管理能力
        try (InputStream input = DataBaseConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")){
            //FileInputStream也可以读取配置文件流，但是使用这个类本身的类加载器更加通用，各种情况都可以用
            if (input == null) {
                throw new RuntimeException("请创建 db.properties 文件，参考 db.properties.template");
            }//是否找到这个文件，如果InputStream为null，说明classpath中不存在这个文件
            //这里为了不泄露密码，我们写一个例子db.properties.template给别人看
            Properties properties = new Properties();
            //这行代码是Java中用于处理属性文件（通常是 .properties 文件）的一个非常经典和常用的操作。
            //Properties是键值对集合，键和值都必须是String
            properties.load(input);
            //Properties目的就是读取从配置文件吸收的流，把 key=value 格式的配置项加载到Properties对象中
            HikariConfig config = new HikariConfig(properties);
            //这个方法可以解析配置文件转换为数据库连接池认识的配置数据源，
            //db.properties存储了HikariCP数据库连接池所必须的配置，如密码等
            dataSource = new HikariDataSource(config);
            //使用翻译后的配置，初始化


        }catch (Exception e){
            throw new RuntimeException("数据库配置加载失败：" + e.getMessage(),e);
        }
    }

    public static Connection getConnection() throws SQLException{
        return dataSource.getConnection();
        //直接从连接池获取数据，不再像原本那样使用JDBC的DriverManager
    }
    public static void closeDataSource(){
        if (dataSource != null && !dataSource.isClosed()){
            dataSource.close();
        }
        //这个方法在一般情况下没有用，因为会自动关闭，但是我的是javafx桌面应用，要注意
        //当程序正常结束的时候，会自动帮我们关闭数据库连接池，但是程序很可能不是正常结束，这个时候我们要在stop方法里面使用这个关闭代码
    }
    public static boolean testConnection(){
        //这就是一个简单的测试方法，测试有没有成功，外界先用这个测试，防止后续的业务错误覆盖这个SQLException错误
        try (Connection connection = getConnection()){
            System.out.println("数据库链接测试成功");
            return true;
        }catch (SQLException e){
            System.out.println("数据库链接测试失败：" + e.getMessage());
            return false;
        }
    }
}

