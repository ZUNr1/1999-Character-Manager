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

public class OtherInformationTab {
    CharacterFormData formData;
    private Map<String, TextArea> characterCoverInformationFields = new HashMap<>();
    private Map<String, TextField> dressNameFields = new HashMap<>();
    private Map<String, Map<String, TextArea>> characterItemsFields = new HashMap<>();
    private Map<String, Map<String, TextArea>> characterStoryFields = new HashMap<>();
    public OtherInformationTab(CharacterFormData formData){
        this.formData = formData;
    }
    public ScrollPane createTab() {
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setPrefWidth(120);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.ALWAYS);
        col2.setPrefWidth(400);
        content.getColumnConstraints().addAll(col1, col2);

        int currentRow = 0;
        // 封面信息部分
        Label coverInformationTitle = new Label("封面信息");
        coverInformationTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        content.add(coverInformationTitle, 0, currentRow, 2, 1);
        currentRow++;

        // 角色介绍
        Label introductionLabel = new Label("角色介绍");
        TextArea introductionArea = new TextArea();
        introductionArea.setPromptText("请输入角色背景介绍");
        introductionArea.setPrefRowCount(4);
        introductionArea.setWrapText(true);
        characterCoverInformationFields.put("introduction", introductionArea);
        content.add(introductionLabel, 0, currentRow);
        content.add(introductionArea, 1, currentRow);
        currentRow++;
        // 角色尺寸
        Label sizeLabel = new Label("角色尺寸");
        TextArea sizeArea = new TextArea();
        sizeArea.setPromptText("例如：");
        sizeArea.setWrapText(true);
        sizeArea.setPrefRowCount(1);
        characterCoverInformationFields.put("size", sizeArea);
        content.add(sizeLabel, 0, currentRow);
        content.add(sizeArea, 1, currentRow);
        currentRow++;

        // 角色香调
        Label fragranceLabel = new Label("角色香调");
        TextArea fragranceArea = new TextArea();
        fragranceArea.setPromptText("请输入角色香调描述");
        fragranceArea.setPrefRowCount(2);
        fragranceArea.setWrapText(true);
        characterCoverInformationFields.put("fragrance", fragranceArea);
        content.add(fragranceLabel, 0, currentRow);
        content.add(fragranceArea, 1, currentRow);
        currentRow++;

        // 详细灵感
        Label detailedAfflatusLabel = new Label("详细灵感");
        TextArea detailedAfflatusArea = new TextArea();
        detailedAfflatusArea.setPromptText("请输入更详细的灵感类型描述");
        characterCoverInformationFields.put("detailedAfflatus", detailedAfflatusArea);
        content.add(detailedAfflatusLabel, 0, currentRow);
        content.add(detailedAfflatusArea, 1, currentRow);
        currentRow++;

        Label dressTitle = new Label("服装信息");
        dressTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        content.add(dressTitle, 0, currentRow, 2, 1);
        currentRow++;

        Button addDressButton = new Button("+ 添加服装");
        VBox dressContainer = new VBox(10);
        dressContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");

        addDressButton.setOnAction(actionEvent -> addNewDress(dressContainer));
        content.add(addDressButton, 0, currentRow, 2, 1);
        currentRow++;

        ScrollPane dressScrollPane = new ScrollPane(dressContainer);
        dressScrollPane.setFitToWidth(true);
        dressScrollPane.setPrefViewportHeight(150);
        content.add(dressScrollPane, 0, currentRow, 2, 1);
        currentRow++;

        // 角色单品部分
        Label itemsTitle = new Label("角色单品");
        itemsTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        content.add(itemsTitle, 0, currentRow, 2, 1);
        currentRow++;

        Button addItemsButton = new Button("+ 添加角色单品");
        VBox itemsContainer = new VBox(10);
        itemsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");

        addItemsButton.setOnAction(actionEvent -> addNewCharacterItems(itemsContainer));
        content.add(addItemsButton, 0, currentRow, 2, 1);
        currentRow++;

        ScrollPane itemsScrollPane = new ScrollPane(itemsContainer);
        itemsScrollPane.setFitToWidth(true);
        itemsScrollPane.setPrefViewportHeight(200);
        content.add(itemsScrollPane, 0, currentRow, 2, 1);
        currentRow++;
        // 角色文化部分 - 固定三个
        Label storyTitle = new Label("角色文化");
        storyTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        content.add(storyTitle, 0, currentRow, 2, 1);
        currentRow++;

        // 第一个文化
        Label firstStoryNameLabel = new Label("第一个文化名称");
        TextArea firstStoryNameArea = new TextArea();
        firstStoryNameArea.setPromptText("请输入第一个文化名称");
        firstStoryNameArea.setWrapText(true);
        firstStoryNameArea.setPrefRowCount(1);
        content.add(firstStoryNameLabel, 0, currentRow);
        content.add(firstStoryNameArea, 1, currentRow);
        currentRow++;

        Label firstStoryDescribeLabel = new Label("第一个文化描述");
        TextArea firstStoryDescribeArea = new TextArea();
        firstStoryDescribeArea.setPromptText("请输入第一个文化描述");
        firstStoryDescribeArea.setPrefRowCount(3);
        firstStoryDescribeArea.setWrapText(true);
        content.add(firstStoryDescribeLabel, 0, currentRow);
        content.add(firstStoryDescribeArea, 1, currentRow);
        currentRow++;

        // 第二个文化
        Label secondStoryNameLabel = new Label("第二个文化名称");
        TextArea secondStoryNameArea = new TextArea();
        secondStoryNameArea.setPromptText("请输入第二个文化名称");
        secondStoryNameArea.setPrefRowCount(1);
        secondStoryNameArea.setWrapText(true);
        content.add(secondStoryNameLabel, 0, currentRow);
        content.add(secondStoryNameArea, 1, currentRow);
        currentRow++;

        Label secondStoryDescribeLabel = new Label("第二个文化描述");
        TextArea secondStoryDescribeArea = new TextArea();
        secondStoryDescribeArea.setPromptText("请输入第二个文化描述");
        secondStoryDescribeArea.setPrefRowCount(3);
        secondStoryDescribeArea.setWrapText(true);
        content.add(secondStoryDescribeLabel, 0, currentRow);
        content.add(secondStoryDescribeArea, 1, currentRow);
        currentRow++;

        // 第三个文化
        Label thirdStoryNameLabel = new Label("第三个文化名称");
        TextArea thirdStoryNameArea = new TextArea();
        thirdStoryNameArea.setPromptText("请输入第三个文化名称");
        thirdStoryNameArea.setPrefRowCount(1);
        thirdStoryNameArea.setWrapText(true);
        content.add(thirdStoryNameLabel, 0, currentRow);
        content.add(thirdStoryNameArea, 1, currentRow);
        currentRow++;

        Label thirdStoryDescribeLabel = new Label("第三个文化描述");
        TextArea thirdStoryDescribeArea = new TextArea();
        thirdStoryDescribeArea.setPromptText("请输入第三个文化描述");
        thirdStoryDescribeArea.setPrefRowCount(3);
        thirdStoryDescribeArea.setWrapText(true);
        content.add(thirdStoryDescribeLabel, 0, currentRow);
        content.add(thirdStoryDescribeArea, 1, currentRow);
        // 存储到Map中
        Map<String, TextArea> storyNameMap = new HashMap<>();
        storyNameMap.put("first", firstStoryNameArea);
        storyNameMap.put("second", secondStoryNameArea);
        storyNameMap.put("third", thirdStoryNameArea);
        characterStoryFields.put("names", storyNameMap);

        Map<String, TextArea> storyDescribeMap = new HashMap<>();
        storyDescribeMap.put("first", firstStoryDescribeArea);
        storyDescribeMap.put("second", secondStoryDescribeArea);
        storyDescribeMap.put("third", thirdStoryDescribeArea);
        characterStoryFields.put("describes", storyDescribeMap);

        ScrollPane mainScrollPane = new ScrollPane(content);
        mainScrollPane.setFitToWidth(true);
        mainScrollPane.setPrefViewportHeight(500);
        mainScrollPane.setStyle("-fx-background: white; -fx-border-color: #bdc3c7;");

        return mainScrollPane;
    }
    private void addNewDress(VBox container) {
        String dressId = "dress_" + System.currentTimeMillis();

        GridPane dressPane = new GridPane();
        dressPane.setHgap(10);
        dressPane.setVgap(8);
        dressPane.setPadding(new Insets(10));
        dressPane.setStyle("-fx-border-color: #d5dbdb; -fx-border-width: 1; -fx-background-color: #f4f6f6;");

        int row = 0;

        Label nameLabel = new Label("衣着名称");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入衣着名称");

        dressPane.add(nameLabel, 0, row);
        dressPane.add(nameField, 1, row);
        row++;

        Button deleteButton = new Button("删除此衣着");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        dressPane.add(deleteButton, 0, row, 2, 1);
        GridPane.setHalignment(deleteButton, HPos.RIGHT);

        VBox dressContainer = new VBox(5, dressPane);
        container.getChildren().add(dressContainer);

        // 存储到Map中
        dressNameFields.put(dressId, nameField);

        deleteButton.setOnAction(actionEvent -> {
            container.getChildren().remove(dressContainer);
            dressNameFields.remove(dressId);
        });
    }
    private void addNewCharacterItems(VBox container) {
        String itemsId = "items_" + System.currentTimeMillis();

        GridPane itemsPane = new GridPane();
        itemsPane.setHgap(10);
        itemsPane.setVgap(12);
        itemsPane.setPadding(new Insets(15));
        itemsPane.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #ecf0f1;");

        int row = 0;

        // 所属装扮名称
        Label dressNameLabel = new Label("所属装扮名称");
        TextArea dressNameArea = new TextArea();
        dressNameArea.setPromptText("请输入所属装扮名称");
        dressNameArea.setPrefRowCount(1);
        dressNameArea.setWrapText(true);
        itemsPane.add(dressNameLabel, 0, row);
        itemsPane.add(dressNameArea, 1, row);
        row++;

        // 第一个单品
        Label firstItemLabel = new Label("第一个单品名称");
        TextArea firstItemNameArea = new TextArea();
        firstItemNameArea.setPromptText("请输入第一个单品名称");
        firstItemNameArea.setPrefRowCount(1);
        firstItemNameArea.setWrapText(true);
        itemsPane.add(firstItemLabel, 0, row);
        itemsPane.add(firstItemNameArea, 1, row);
        row++;

        Label firstItemDescribeLabel = new Label("第一个单品描述");
        TextArea firstItemDescribeArea = new TextArea();
        firstItemDescribeArea.setPromptText("请输入第一个单品描述");
        firstItemDescribeArea.setPrefRowCount(2);
        firstItemDescribeArea.setWrapText(true);
        itemsPane.add(firstItemDescribeLabel, 0, row);
        itemsPane.add(firstItemDescribeArea, 1, row);
        row++;

        Label firstItemPriceLabel = new Label("第一个单品价格");
        TextArea firstItemPriceArea = new TextArea();
        firstItemPriceArea.setPromptText("请输入第一个单品价格");
        firstItemPriceArea.setPrefRowCount(1);
        firstItemPriceArea.setWrapText(true);
        itemsPane.add(firstItemPriceLabel, 0, row);
        itemsPane.add(firstItemPriceArea, 1, row);
        row++;

        // 第二个单品
        Label secondItemLabel = new Label("第二个单品名称");
        TextArea secondItemNameArea = new TextArea();
        secondItemNameArea.setPromptText("请输入第二个单品名称");
        secondItemNameArea.setPrefRowCount(1);
        secondItemNameArea.setWrapText(true);
        itemsPane.add(secondItemLabel, 0, row);
        itemsPane.add(secondItemNameArea, 1, row);
        row++;

        Label secondItemDescribeLabel = new Label("第二个单品描述");
        TextArea secondItemDescribeArea = new TextArea();
        secondItemDescribeArea.setPromptText("请输入第二个单品描述");
        secondItemDescribeArea.setPrefRowCount(2);
        secondItemDescribeArea.setWrapText(true);
        itemsPane.add(secondItemDescribeLabel, 0, row);
        itemsPane.add(secondItemDescribeArea, 1, row);
        row++;

        Label secondItemPriceLabel = new Label("第二个单品价格");
        TextArea secondItemPriceArea = new TextArea();
        secondItemPriceArea.setPromptText("请输入第二个单品价格");
        secondItemPriceArea.setPrefRowCount(1);
        secondItemPriceArea.setWrapText(true);
        itemsPane.add(secondItemPriceLabel, 0, row);
        itemsPane.add(secondItemPriceArea, 1, row);
        row++;

        // 第三个单品
        Label thirdItemLabel = new Label("第三个单品名称");
        TextArea thirdItemNameArea = new TextArea();
        thirdItemNameArea.setPromptText("请输入第三个单品名称");
        thirdItemNameArea.setPrefRowCount(1);
        thirdItemNameArea.setWrapText(true);
        itemsPane.add(thirdItemLabel, 0, row);
        itemsPane.add(thirdItemNameArea, 1, row);
        row++;

        Label thirdItemDescribeLabel = new Label("第三个单品描述");
        TextArea thirdItemDescribeArea = new TextArea();
        thirdItemDescribeArea.setPromptText("请输入第三个单品描述");
        thirdItemDescribeArea.setPrefRowCount(2);
        thirdItemDescribeArea.setWrapText(true);
        itemsPane.add(thirdItemDescribeLabel, 0, row);
        itemsPane.add(thirdItemDescribeArea, 1, row);
        row++;

        Label thirdItemPriceLabel = new Label("第三个单品价格");
        TextArea thirdItemPriceArea = new TextArea();
        thirdItemPriceArea.setPromptText("请输入第三个单品价格");
        thirdItemPriceArea.setPrefRowCount(1);
        thirdItemPriceArea.setWrapText(true);
        itemsPane.add(thirdItemPriceLabel, 0, row);
        itemsPane.add(thirdItemPriceArea, 1, row);
        row++;

        Button deleteButton = new Button("删除此角色单品");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        itemsPane.add(deleteButton, 0, row, 2, 1);
        GridPane.setHalignment(deleteButton, HPos.RIGHT);

        VBox itemsContainer = new VBox(5, itemsPane);
        container.getChildren().add(itemsContainer);

        // 存储到Map中
        Map<String, TextArea> itemsMap = new HashMap<>();

        itemsMap.put("dressNameArea", dressNameArea);
        itemsMap.put("firstItemNameArea", firstItemNameArea);
        itemsMap.put("secondItemNameArea", secondItemNameArea);
        itemsMap.put("thirdItemNameArea", thirdItemNameArea);
        itemsMap.put("firstItemDescribeArea", firstItemDescribeArea);
        itemsMap.put("secondItemDescribeArea", secondItemDescribeArea);
        itemsMap.put("thirdItemDescribeArea", thirdItemDescribeArea);
        itemsMap.put("firstItemPriceArea", firstItemPriceArea);  // 注意这是TextField
        itemsMap.put("secondItemPriceArea", secondItemPriceArea);
        itemsMap.put("thirdItemPriceArea", thirdItemPriceArea);

        characterItemsFields.put(itemsId, itemsMap);

        deleteButton.setOnAction(actionEvent -> {
            container.getChildren().remove(itemsContainer);
            characterItemsFields.remove(itemsId);
        });
    }

    public Map<String, TextArea> getCharacterCoverInformationFields() {
        return characterCoverInformationFields;
    }

    public Map<String, TextField> getDressNameFields() {
        return dressNameFields;
    }

    public Map<String, Map<String, TextArea>> getCharacterItemsFields() {
        return characterItemsFields;
    }

    public Map<String, Map<String, TextArea>> getCharacterStoryFields() {
        return characterStoryFields;
    }
}
