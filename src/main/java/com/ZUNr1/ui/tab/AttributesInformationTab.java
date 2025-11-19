package com.ZUNr1.ui.tab;

import com.ZUNr1.ui.service.CharacterFormData;
import com.ZUNr1.ui.validation.ValidationResult;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AttributesInformationTab {
    private CharacterFormData formData;
    private TextField healthField;
    private TextField attackField;
    private TextField realityDefenseField;
    private TextField mentalDefenseField;
    private TextField techniqueField;
    public AttributesInformationTab(CharacterFormData formData){
        this.formData = formData;
    }
    public GridPane createTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色属性（默认满级）");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label healthLabel = new Label("生命值");
        healthField = createField(30000,0);

        Label attackLabel = new Label("攻击力");
        attackField = createField(2000,0);

        Label realityDefenseLabel = new Label("现实防御");
        realityDefenseField = createField(2000,0);

        Label mentalDefenseLabel = new Label("精神防御");
        mentalDefenseField = createField(2000,0);

        Label techniqueLabel = new Label("暴击技巧");
        techniqueField = createField(2000,0);

        content.add(titleLabel, 0, 0, 2, 1);
        content.add(healthLabel, 0, 1);
        content.add(healthField, 1, 1);
        content.add(attackLabel, 0, 2);
        content.add(attackField, 1, 2);
        content.add(realityDefenseLabel, 0, 3);
        content.add(realityDefenseField, 1, 3);
        content.add(mentalDefenseLabel, 0, 4);
        content.add(mentalDefenseField, 1, 4);
        content.add(techniqueLabel, 0, 5);
        content.add(techniqueField, 1, 5);

        return content;
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
    public ValidationResult validate() {
        if (healthField.getText() == null || healthField.getText().trim().isEmpty()) {
            return ValidationResult.failure("生命值不能为空", healthField);
        }
        if (attackField.getText() == null || attackField.getText().trim().isEmpty()) {
            return ValidationResult.failure("攻击力不能为空", attackField);
        }
        if (realityDefenseField.getText() == null || realityDefenseField.getText().trim().isEmpty()) {
            return ValidationResult.failure("现实防御不能为空", realityDefenseField);
        }
        if (mentalDefenseField.getText() == null || mentalDefenseField.getText().trim().isEmpty()) {
            return ValidationResult.failure("精神防御不能为空", mentalDefenseField);
        }
        if (techniqueField.getText() == null || techniqueField.getText().trim().isEmpty()) {
            return ValidationResult.failure("暴击技巧不能为空", techniqueField);
        }
        return ValidationResult.success();
    }

    public TextField getAttackField() {
        return attackField;
    }

    public TextField getHealthField() {
        return healthField;
    }

    public TextField getRealityDefenseField() {
        return realityDefenseField;
    }

    public TextField getMentalDefenseField() {
        return mentalDefenseField;
    }

    public TextField getTechniqueField() {
        return techniqueField;
    }
}
