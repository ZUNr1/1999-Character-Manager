package com.ZUNr1.ui;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class CharacterMainController {
    private BorderPane root;
    public CharacterMainController(){
        createInterface();
    }
    private void createInterface(){
        root = new BorderPane();

        //中心标题
        Label titleLabel = new Label("角色管理系统");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 20px;");
        root.setTop(titleLabel);

        //选项卡，包含很多Tab标签选项
        TabPane tabPane = new TabPane();

        //角色基本信息
        Tab basicInformationTab = new Tab("角色基本信息");
        basicInformationTab.setContent(createBasicInformationTab());
        //设置内容
        basicInformationTab.setClosable(false);
        //这个可以把Tab标签页设为不可关闭，去掉x，防止不小心关了
        //包含id，name，稀有度，灵感类型，创伤类型，性别，
        Tab skillInformationTab = new Tab("神秘术信息");
        skillInformationTab.setContent(createSkillInformationTab());
        skillInformationTab.setClosable(false);
        //包含技能
        Tab attributesInformationTab = new Tab("属性信息（默认满级）");
        attributesInformationTab.setContent(createAttributesInformationTab());
        attributesInformationTab.setClosable(false);
        //包含属性
        Tab otherInformationTab = new Tab("塑造与传承");
        otherInformationTab.setContent(createOtherInformationTab());
        otherInformationTab.setClosable(false);
        //包含Portrait和Inheritance
        Tab usedTermInformationTab = new Tab("专有名词");
        usedTermInformationTab.setContent(createUsedTermInformationTab());
        usedTermInformationTab.setClosable(false);
        //包含usedTerm
        tabPane.getTabs().addAll
                (basicInformationTab,skillInformationTab,attributesInformationTab,
                        otherInformationTab,usedTermInformationTab);
        //这一行获得所有标签然后添加所有我们要加的标签
        root.setCenter(tabPane);
    }
    private GridPane createBasicInformationTab(){
        GridPane content = new GridPane();//GridPane布局可以像表格一样划分
        content.setHgap(10);//设置水平间距
        content.setVgap(15);//设置垂直间距
        content.setPadding(new Insets(20));
        //为 GridPane 布局容器设置内边距
        //内边距 (Padding) 会在 GridPane 的内容区域和边框之间创建空白空间。
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色基本信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label idLabel = new Label("角色id");
        TextField idField = new TextField();
        idField.setPromptText("请输入角色id（最多10个字符）");
        //下一行代码，使用了属性变化监听器，textProperty获得这个文本，
        // 设置addListener监听，里面一个lambda表达式，监听文本的变化做出行动
        // observable: 被观察的属性对象（就是textProperty）
        // oldValue: 变化前的旧文本
        // newValue: 变化后的新文本
        //当文本发生变化（如删除添加，就会触发监听器）
        //当文本长度超出数额，我们截断（用户可能会粘贴很长一段过来，表现出来就是只有前面一截）
        //这里我们不用trim处理空格，在Manage类里面就处理了这些
        idField.textProperty().addListener
                ((observable,oldValue,newValue) -> {
                    if (newValue.length() > 10){
                        idField.setText(newValue.substring(0,10));
                    }
                });

        Label nameLabel = new Label("角色姓名");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入角色姓名（最多20个字符）");
        nameField.textProperty().addListener
                ((observable,oldValue,newValue ) ->{
                    if (newValue.length() > 20){
                        nameField.setText(newValue.substring(0,20));
                    }
                });

        Label rarityLabel = new Label("稀有度");
        //下一行代码是数字选择器 (Spinner)
        //new Spinner<>(最小值, 最大值, 初始值)
        //可以设置输入的数字的最大值最小值还有初始值
        Spinner raritySpinner = new Spinner<>(2,6,6);
        raritySpinner.setEditable(true);
        //允许用户直接在 Spinner 的文本框中输入数值，而不仅仅是通过上下箭头按钮来调整。

        Label genderLabel = new Label("角色性别");
        //下一行代码是下拉选择框 (ComboBox) ，产生下拉选择框选，getItems().setAll可以设置选项的名字
        ComboBox<String> genderComboBox = new ComboBox<>();
        genderComboBox.getItems().setAll("男","女","其他");
        genderComboBox.setPromptText("请选择角色性别");

        Label afflatusLabel = new Label("灵感类型");
        ComboBox<String> afflatusComboBox = new ComboBox<>();
        afflatusComboBox.getItems().setAll("星系","岩系","兽系","木系","灵系","智系");
        afflatusComboBox.setPromptText("请选择角色灵感类型");

        Label damageTypeLabel = new Label("角色创伤类型");
        ComboBox<String> damageTypeComboBox = new ComboBox<>();
        damageTypeComboBox.getItems().setAll("现实创伤","精神创伤","本源创伤");
        damageTypeComboBox.setPromptText("请选择角色创伤类型");

        content.add(titleLabel,0,0,2,1);
        //将 titleLabel 添加到 GridPane 中，从第 0 列第 0 行开始，横跨 2 列，占据 1 行。
        content.add(idLabel,0,1);
        //不跨行就两个参数，列和行
        content.add(idField,1,1);
        content.add(nameLabel,0,2);
        content.add(nameField,1,2);
        content.add(rarityLabel,0,3);
        content.add(raritySpinner,1,3);
        content.add(genderLabel, 0, 4);
        content.add(genderComboBox, 1, 4);
        content.add(afflatusLabel,0,5);
        content.add(afflatusComboBox,1,5);
        content.add(damageTypeLabel,0,6);
        content.add(damageTypeComboBox,1,6);
        return content;
    }
    private VBox createSkillInformationTab(){

    }
    private VBox createAttributesInformationTab(){

    }
    private VBox createOtherInformationTab(){

    }
    private VBox createUsedTermInformationTab(){

    }

    public BorderPane getRoot() {
        return root;
    }
}
