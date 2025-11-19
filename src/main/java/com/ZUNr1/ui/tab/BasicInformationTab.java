package com.ZUNr1.ui.tab;

import com.ZUNr1.ui.service.CharacterFormData;
import com.ZUNr1.ui.validation.ValidationResult;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class BasicInformationTab {
    private CharacterFormData formData;
    private TextField idField;
    private TextField nameField;
    private TextField enNameField;
    private TextField creatorField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> afflatusComboBox;
    private ComboBox<String> damageTypeComboBox;
    private Spinner<Integer> raritySpinner;
    public BasicInformationTab(CharacterFormData formData){
        this.formData = formData;
    }

    public GridPane createTab(){
        GridPane content = new GridPane();//GridPane布局可以像表格一样划分
        content.setHgap(10);//设置水平间距
        content.setVgap(15);//设置垂直间距
        content.setPadding(new Insets(20));
        //为 GridPane 布局容器设置内边距
        //内边距 (Padding) 会在 GridPane 的内容区域和边框之间创建空白空间。
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色基本信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label creatorLabel = new Label("创作者名称");
        creatorField = new TextField();
        creatorField.setPromptText("请输入你的名称(不要超过20个字符)");
        creatorField.textProperty().addListener((observable,oldValue,newValue) -> {
            if (newValue.length() > 20){
                creatorField.setText(newValue.substring(0,20));
            }
        });


        Label idLabel = new Label("角色id");
        idField = new TextField();
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
        nameField = new TextField();
        nameField.setPromptText("请输入角色姓名（最多20个字符）");
        nameField.textProperty().addListener
                ((observable,oldValue,newValue ) ->{
                    if (newValue.length() > 20){
                        nameField.setText(newValue.substring(0,20));
                    }
                });

        Label enNameLabel = new Label("角色英文姓名");
        enNameField = new TextField();
        enNameField.setPromptText("请输入角色英文姓名（最多40个字符）");
        enNameField.textProperty().addListener
                ((observable,oldValue,newValue ) ->{
                    if (newValue.length() > 40){
                        enNameField.setText(newValue.substring(0,40));
                    }
                });

        Label rarityLabel = new Label("稀有度");
        //下一行代码是数字选择器 (Spinner)
        //new Spinner<>(最小值, 最大值, 初始值)
        //可以设置输入的数字的最大值最小值还有初始值
        raritySpinner = new Spinner<>(2,6,6);
        raritySpinner.setEditable(true);
        //允许用户直接在 Spinner 的文本框中输入数值，而不仅仅是通过上下箭头按钮来调整。

        Label genderLabel = new Label("角色性别");
        //下一行代码是下拉选择框 (ComboBox) ，产生下拉选择框选，getItems().setAll可以设置选项的名字
        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().setAll("男","女","其他");
        genderComboBox.setPromptText("请选择角色性别");

        Label afflatusLabel = new Label("灵感类型");
        afflatusComboBox = new ComboBox<>();
        afflatusComboBox.getItems().setAll("星系","岩系","兽系","木系","灵系","智系");
        afflatusComboBox.setPromptText("请选择角色灵感类型");

        Label damageTypeLabel = new Label("角色创伤类型");
        damageTypeComboBox = new ComboBox<>();
        damageTypeComboBox.getItems().setAll("现实创伤","精神创伤","本源创伤");
        damageTypeComboBox.setPromptText("请选择角色创伤类型");

        content.add(titleLabel,0,0,2,1);
        //将 titleLabel 添加到 GridPane 中，从第 0 列第 0 行开始，横跨 2 列，占据 1 行。
        content.add(idLabel,0,1);
        //不跨行就两个参数，列和行
        content.add(idField,1,1);
        content.add(nameLabel,0,2);
        content.add(nameField,1,2);
        content.add(enNameLabel,0,3);
        content.add(enNameField,1,3);
        content.add(rarityLabel,0,4);
        content.add(raritySpinner,1,4);
        content.add(genderLabel, 0, 5);
        content.add(genderComboBox, 1, 5);
        content.add(afflatusLabel,0,6);
        content.add(afflatusComboBox,1,6);
        content.add(damageTypeLabel,0,7);
        content.add(damageTypeComboBox,1,7);
        content.add(creatorLabel,0,8);
        content.add(creatorField,1,8);
        return content;
    }
    public ValidationResult validate() {
        if (idField.getText() == null || idField.getText().trim().isEmpty()) {
            return ValidationResult.failure("角色ID不能为空", idField);
        }
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            return ValidationResult.failure("角色姓名不能为空", nameField);
        }
        if (enNameField.getText() == null || enNameField.getText().trim().isEmpty()) {
            return ValidationResult.failure("角色英文姓名不能为空", enNameField);
        }
        if (creatorField.getText() == null || creatorField.getText().trim().isEmpty()) {
            return ValidationResult.failure("创作者名称不能为空", creatorField);
        }
        if (genderComboBox.getValue() == null) {
            return ValidationResult.failure("请选择角色性别", genderComboBox);
        }
        if (afflatusComboBox.getValue() == null) {
            return ValidationResult.failure("请选择灵感类型", afflatusComboBox);
        }
        if (damageTypeComboBox.getValue() == null) {
            return ValidationResult.failure("请选择创伤类型", damageTypeComboBox);
        }
        if (raritySpinner.getValue() == null) {
            return ValidationResult.failure("请选择稀有度", raritySpinner);
        }
        return ValidationResult.success();
    }

    public TextField getIdField() {
        return idField;
    }

    public TextField getNameField() {
        return nameField;
    }

    public TextField getEnNameField() {
        return enNameField;
    }

    public TextField getCreatorField() {
        return creatorField;
    }

    public ComboBox<String> getGenderComboBox() {
        return genderComboBox;
    }

    public ComboBox<String> getAfflatusComboBox() {
        return afflatusComboBox;
    }

    public ComboBox<String> getDamageTypeComboBox() {
        return damageTypeComboBox;
    }

    public Spinner<Integer> getRaritySpinner() {
        return raritySpinner;
    }
}
