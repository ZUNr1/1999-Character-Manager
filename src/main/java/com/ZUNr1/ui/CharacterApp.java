package com.ZUNr1.ui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CharacterApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        CharacterMainController mainController = new CharacterMainController();
        Scene scene = new Scene(mainController.getRoot(), 800, 600);
        stage.setTitle("角色录入");
        stage.setScene(scene);
        stage.show();
    }

    private Parent createBasicInterface() {
        return new VBox();
    }
}
