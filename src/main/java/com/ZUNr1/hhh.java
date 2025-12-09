package com.ZUNr1;

import com.ZUNr1.util.DataBaseConnection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class hhh {
    private static HikariDataSource dataSource;
    static {
        loadConfig();
    }
    private static void loadConfig(){
        try (InputStream input = DataBaseConnection.class.getClassLoader()
                .getResourceAsStream("hhh.properties")){
            Properties properties = new Properties();
            properties.load(input);
            HikariConfig config = new HikariConfig(properties);
            dataSource = new HikariDataSource(config);
        }catch (Exception e){

        }
    }
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        }catch (SQLException e){

        }
    }
}
