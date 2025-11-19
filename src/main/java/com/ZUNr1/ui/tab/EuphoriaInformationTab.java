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

public class EuphoriaInformationTab {
    CharacterFormData formData;
    private Map<String, Map<String, TextArea>> euphoriaDescribeFields = new HashMap<>();
    private Map<String,Map<String, TextField>> euphoriaAttributesFields = new HashMap<>();
    public EuphoriaInformationTab(CharacterFormData formData){
        this.formData = formData;
    }
    public GridPane createTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-padding: 20px;");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setPrefWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        col2.setPrefWidth(300);
        content.getColumnConstraints().addAll(col1, col2);

        int currentRow = 0;
        Label titleLabel = new Label("狂想(如果有)");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        content.add(titleLabel,0,currentRow);
        currentRow++;

        Button addEuphoriaButton = new Button("添加新狂想");
        content.add(addEuphoriaButton,0,currentRow);
        currentRow++;
        VBox euphoriaContainer = new VBox(10);
        euphoriaContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        addEuphoriaButton.setOnAction(actionEvent -> addNewEuphoria(euphoriaContainer));
        //每一次点击，创建一个GirdPane，，用VBox包装，添加进euphoriaContainer里面

        ScrollPane scrollPane = new ScrollPane(euphoriaContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(350);
        //ScrollPane：让内容可以上下滚动，可能会超限，我们使用滚动布局把设置的VBox包含进去
        //setFitToWidth(true)：内容自动适应宽度
        //setPrefViewportHeight(350)：设置可见区域高度为350像素
        scrollPane.setStyle("-fx-background: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        content.add(scrollPane,0,currentRow,2,1);
        return content;

    }
    private void addNewEuphoria(VBox container){
        String newEuphoriaName = "狂想 _ " + System.currentTimeMillis();
        GridPane euphoriaPane = new GridPane();
        euphoriaPane.setHgap(10);
        euphoriaPane.setVgap(12);
        euphoriaPane.setPadding(new Insets(15));
        euphoriaPane.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #ecf0f1;");
        int row = 0;
        Label euphoriaNameLabel = new Label("狂想名称");
        TextField euphoriaNameField = new TextField();
        euphoriaPane.add(euphoriaNameLabel,0,row);
        euphoriaPane.add(euphoriaNameField,1,row);
        row++;
        // 一阶狂想
        Label firstEuphoriaLabel = new Label("一阶狂想");
        TextArea firstEuphoriaArea = new TextArea();
        firstEuphoriaArea.setPrefRowCount(3);
        firstEuphoriaArea.setWrapText(true);
        euphoriaPane.add(firstEuphoriaLabel, 0, row);
        euphoriaPane.add(firstEuphoriaArea, 1, row);
        row++;
        // 二阶狂想
        Label secondEuphoriaLabel = new Label("二阶狂想");
        TextArea secondEuphoriaArea = new TextArea();
        secondEuphoriaArea.setPrefRowCount(3);
        secondEuphoriaArea.setWrapText(true);
        euphoriaPane.add(secondEuphoriaLabel, 0, row);
        euphoriaPane.add(secondEuphoriaArea, 1, row);
        row++;
        // 三阶狂想
        Label thirdEuphoriaLabel = new Label("三阶狂想");
        TextArea thirdEuphoriaArea = new TextArea();
        thirdEuphoriaArea.setPrefRowCount(3);
        thirdEuphoriaArea.setWrapText(true);
        euphoriaPane.add(thirdEuphoriaLabel, 0, row);
        euphoriaPane.add(thirdEuphoriaArea, 1, row);
        row++;
        // 四阶狂想
        Label fourthEuphoriaLabel = new Label("四阶狂想");
        TextArea fourthEuphoriaArea = new TextArea();
        fourthEuphoriaArea.setPrefRowCount(3);
        fourthEuphoriaArea.setWrapText(true);
        euphoriaPane.add(fourthEuphoriaLabel, 0, row);
        euphoriaPane.add(fourthEuphoriaArea, 1, row);
        row++;
        // 属性加成标题
        Label attributesTitle = new Label("属性加成");
        attributesTitle.setStyle("-fx-font-weight: bold;");
        euphoriaPane.add(attributesTitle, 0, row, 2, 1);
        row++;
        // 生命值加成
        Label healthLabel = new Label("生命值");
        TextField healthField = createField(30000, 0);
        euphoriaPane.add(healthLabel, 0, row);
        euphoriaPane.add(healthField, 1, row);
        row++;
        // 攻击力加成
        Label attackLabel = new Label("攻击力");
        TextField attackField = createField(2000, 0);
        euphoriaPane.add(attackLabel, 0, row);
        euphoriaPane.add(attackField, 1, row);
        row++;
        // 现实防御加成
        Label realityDefenseLabel = new Label("现实防御");
        TextField realityDefenseField = createField(2000, 0);
        euphoriaPane.add(realityDefenseLabel, 0, row);
        euphoriaPane.add(realityDefenseField, 1, row);
        row++;
        // 精神防御加成
        Label mentalDefenseLabel = new Label("精神防御");
        TextField mentalDefenseField = createField(2000, 0);
        euphoriaPane.add(mentalDefenseLabel, 0, row);
        euphoriaPane.add(mentalDefenseField, 1, row);
        row++;
        // 暴击技巧加成
        Label techniqueLabel = new Label("暴击技巧");
        TextField techniqueField = createField(2000, 0);
        euphoriaPane.add(techniqueLabel, 0, row);
        euphoriaPane.add(techniqueField, 1, row);
        row++;
        Button deleteButton = new Button("删除");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        euphoriaPane.add(deleteButton, 0, row,2,1);
        GridPane.setHalignment(deleteButton, HPos.RIGHT);
        row++;

        VBox euphoriaContainer = new VBox(5, euphoriaPane);
        container.getChildren().add(euphoriaContainer);

        storeEuphoriaData(newEuphoriaName, euphoriaNameField,
                healthField, attackField, realityDefenseField,
                mentalDefenseField, techniqueField,
                firstEuphoriaArea, secondEuphoriaArea,
                thirdEuphoriaArea, fourthEuphoriaArea);

        deleteButton.setOnAction(actionEvent -> {
            container.getChildren().remove(euphoriaContainer);
            removeEuphoriaData(newEuphoriaName);
        });


    }
    private void storeEuphoriaData(String euphoriaId, TextField nameField,
                                   TextField healthField, TextField attackField,
                                   TextField realityDefenseField, TextField mentalDefenseField,
                                   TextField techniqueField, TextArea firstEuphoriaArea,
                                   TextArea secondEuphoriaArea, TextArea thirdEuphoriaArea,
                                   TextArea fourthEuphoriaArea) {

        // 存储描述字段
        Map<String, TextArea> describeMap = new HashMap<>();
        describeMap.put("first", firstEuphoriaArea);
        describeMap.put("second", secondEuphoriaArea);
        describeMap.put("third", thirdEuphoriaArea);
        describeMap.put("fourth", fourthEuphoriaArea);
        euphoriaDescribeFields.put(euphoriaId, describeMap);

        // 存储属性字段
        Map<String, TextField> attributesMap = new HashMap<>();
        attributesMap.put("name", nameField); // 名称字段
        attributesMap.put("health", healthField);
        attributesMap.put("attack", attackField);
        attributesMap.put("realityDefense", realityDefenseField);
        attributesMap.put("mentalDefense", mentalDefenseField);
        attributesMap.put("technique", techniqueField);
        euphoriaAttributesFields.put(euphoriaId, attributesMap);
    }

    private void removeEuphoriaData(String euphoriaId) {
        euphoriaDescribeFields.remove(euphoriaId);
        euphoriaAttributesFields.remove(euphoriaId);
    }
    private TextField createField(int maxValue, int minValue){
        if (minValue > maxValue){
            throw new IllegalArgumentException("最大限制小于最小限制");
        }
        TextField field = new TextField();
        field.setPromptText(minValue + "~" + maxValue + "之间");
        field.textProperty().addListener
                ((observable,oldValue,newValue) -> {
                    if (newValue == null || newValue.trim().isEmpty()){
                        return;
                        //为什么要return，因为如果我们不return结束这次的监听器，就会执行监听器的下一步操作（下一行的代码）
                    }
                    if (!newValue.matches("\\d*")){
                        //String类的match()方法用于检查字符串是否与给定的正则表达式匹配
                        //\d 表示匹配一个且仅一个数字字符（0-9）。  \d* 表示匹配零个或多个数字字符。
                        //这里进行输入验证，新newValue如果没有完全数字，就去除（设为空）
                        field.setText(newValue.replaceAll("[^\\d]",""));
                        //[^ ] 表示否定字符类（匹配不在方括号内的字符）
                        return;
                    }
                    if (!newValue.isEmpty()){
                        //这里可以保证传来的数据一定是纯数字，当然不包含空格（空格也会被正则表达式检测到）
                        int value = Integer.parseInt(newValue);
                        if (value > maxValue){
                            field.setText(String.valueOf(maxValue));
                        }else if (value < minValue){
                            field.setText(String.valueOf(minValue));
                        }
                    }
                });
        return field;
    }

    public Map<String, Map<String, TextArea>> getEuphoriaDescribeFields() {
        return euphoriaDescribeFields;
    }

    public Map<String, Map<String, TextField>> getEuphoriaAttributesFields() {
        return euphoriaAttributesFields;
    }
}
