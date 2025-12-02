module com.ZUNr1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires javafx.graphics;
    requires java.sql;

    // 关键修复：导出 ui 包给 javafx.graphics
    exports com.ZUNr1.ui to javafx.graphics;

    // 为 FXML 和控制器打开包
    opens com.ZUNr1.ui to javafx.fxml;

    // 导出其他包
    exports com.ZUNr1.model;
    exports com.ZUNr1.enums;
    exports com.ZUNr1.util;  // 添加这一行
    exports com.ZUNr1.service;  // 添加这一行
    exports com.ZUNr1.manager;  // 添加这一行
    exports com.ZUNr1.dao;      // 添加这一行

    // 为 Jackson 序列化打开包
    opens com.ZUNr1.model to com.fasterxml.jackson.databind;
    opens com.ZUNr1.enums to com.fasterxml.jackson.databind;
    opens com.ZUNr1.util to com.fasterxml.jackson.databind;    // 添加这一行
    opens com.ZUNr1.service to com.fasterxml.jackson.databind; // 添加这一行

    exports com.ZUNr1.ui.controller to javafx.graphics;
    opens com.ZUNr1.ui.controller to javafx.fxml;
    exports com.ZUNr1.ui.service to javafx.graphics;
    opens com.ZUNr1.ui.service to javafx.fxml;
    exports com.ZUNr1.ui.validation to javafx.graphics;
    opens com.ZUNr1.ui.validation to javafx.fxml;
}