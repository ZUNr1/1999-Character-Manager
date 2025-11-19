package com.ZUNr1.ui.tab;

import com.ZUNr1.ui.service.CharacterFormData;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class UsedTermInformationTab {
    CharacterFormData formData;
    private Map<String, TextField> usedTermNameFields = new HashMap<>();
    private Map<String, TextArea> usedTermDescribeFields = new HashMap<>();
    public UsedTermInformationTab(CharacterFormData formData){
        this.formData = formData;
    }
    public GridPane createTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setPrefWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        col2.setPrefWidth(300);
        content.getColumnConstraints().addAll(col1,col2);

        int currentRow = 0;

        Label usedTermTitle = new Label("专有名词");
        content.add(usedTermTitle,0,currentRow,2,1);
        currentRow++;
        Label usedTermExplanation = new Label
                ("专有名词指角色在技能，传承，塑造等地方出现的，含有特殊含意的词语，即在游戏中可以点击查看描述的就算专有名词");
        content.add(usedTermExplanation,0,currentRow,2,1);
        currentRow++;

        Button addUsedTermButton = new Button("+ 添加专有名词");

        VBox usedTermsContainer = new VBox(10);
        usedTermsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");

        addUsedTermButton.setOnAction(actionEvent -> addNewUsedTerm(usedTermsContainer));
        content.add(addUsedTermButton,0,currentRow,2,1);
        currentRow++;
        ScrollPane scrollPane = new ScrollPane(usedTermsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(300);
        content.add(scrollPane,0,currentRow,2,1);
        return content;
    }
    private void addNewUsedTerm(VBox container) {
        String newUsedTermName = "专有名词" + System.currentTimeMillis();
        GridPane usedTermPane = new GridPane();
        usedTermPane.setHgap(10);
        usedTermPane.setVgap(12);
        usedTermPane.setPadding(new Insets(15));
        usedTermPane.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #ecf0f1;");
        int row = 0;
        Label usedTermNameLabel = new Label("专有名词名称");
        TextField usedTermNameField = new TextField();
        usedTermPane.add(usedTermNameLabel,0,row);
        usedTermPane.add(usedTermNameField,1,row);
        row++;

        Label usedTermDescribeLabel = new Label("专有名词描述");
        TextArea usedTermDescribeArea = new TextArea();
        usedTermDescribeArea.setPrefRowCount(3);
        usedTermDescribeArea.setWrapText(true);
        usedTermPane.add(usedTermDescribeLabel,0,row);
        usedTermPane.add(usedTermDescribeArea,1,row);
        row++;
        // 添加删除按钮
        Button deleteButton = new Button("删除");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        usedTermPane.add(deleteButton, 0, row,2,1);
        GridPane.setHalignment(deleteButton, HPos.RIGHT);
        //这个可以设置一个组件靠右对齐（组件在的格子的右边）
        row++;
        // 创建包装容器
        VBox termContainer = new VBox(5, usedTermPane);
        container.getChildren().add(termContainer);

        usedTermNameFields.put(newUsedTermName,usedTermNameField);
        usedTermDescribeFields.put(newUsedTermName,usedTermDescribeArea);
        deleteButton.setOnAction(actionEvent -> {
            container.getChildren().remove(termContainer);
            usedTermNameFields.remove(newUsedTermName);
            usedTermDescribeFields.remove(newUsedTermName);
        });
    }

    public Map<String, TextField> getUsedTermNameFields() {
        return usedTermNameFields;
    }

    public Map<String, TextArea> getUsedTermDescribeFields() {
        return usedTermDescribeFields;
    }
}
