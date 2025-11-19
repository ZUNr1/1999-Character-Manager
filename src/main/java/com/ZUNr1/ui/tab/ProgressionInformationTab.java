package com.ZUNr1.ui.tab;

import com.ZUNr1.ui.service.CharacterFormData;
import com.ZUNr1.ui.validation.ValidationResult;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.HashMap;
import java.util.Map;

public class ProgressionInformationTab {
    private CharacterFormData formData;
    private Map<String, TextArea> inheritanceFields = new HashMap<>();
    private Map<String,TextArea> portraitFields = new HashMap<>();
    public ProgressionInformationTab(CharacterFormData formData){
        this.formData = formData;
    }
    public ScrollPane createTab(){
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
        // 标题
        Label titleLabel = new Label("塑造与传承信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        content.add(titleLabel, 0, currentRow, 2, 1);
        currentRow++;

        // 传承部分
        Label inheritanceTitle = new Label("传承信息");
        inheritanceTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(inheritanceTitle, 0, currentRow, 2, 1);
        currentRow++;

        Label inheritanceNameLabel = new Label("传承名称");
        TextArea inheritanceNameField = new TextArea();
        inheritanceNameField.setPromptText("请输入传承名称");
        inheritanceNameField.setPrefRowCount(1);
        inheritanceNameField.setWrapText(true);
        inheritanceFields.put("inheritance",inheritanceNameField);
        content.add(inheritanceNameLabel,0,currentRow);
        content.add(inheritanceNameField,1,currentRow);
        currentRow++;

        String[] inheritanceLevels = {"基础传承", "一阶传承", "二阶传承", "三阶传承"};
        String[] inheritanceKeys = {"basicInheritance", "firstInheritance", "secondInheritance", "thirdInheritance"};
        for (int i = 0;i < inheritanceLevels.length;i++){
            Label levelLabel = new Label(inheritanceLevels[i]);
            TextArea inheritanceArea = new TextArea();
            inheritanceArea.setPromptText("请输入" + inheritanceLevels[i] + "效果描述");
            inheritanceArea.setPrefRowCount(3);
            inheritanceArea.setWrapText(true);
            inheritanceFields.put(inheritanceKeys[i],inheritanceArea);
            content.add(levelLabel,0,currentRow);
            content.add(inheritanceArea,1,currentRow);
            currentRow++;
        }
        currentRow++;

        Label portraitTitle = new Label("塑造信息");
        portraitTitle.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(portraitTitle,0,currentRow,2,1);
        currentRow++;

        Label portraitDescribeLabel = new Label("塑造描述");
        TextArea portraitDescribeArea = new TextArea();
        portraitDescribeArea.setPromptText("请输入塑造物品的文案");
        portraitDescribeArea.setPrefRowCount(2);
        portraitDescribeArea.setWrapText(true);
        portraitFields.put("portraitDescribe", portraitDescribeArea);
        content.add(portraitDescribeLabel,0,currentRow);
        content.add(portraitDescribeArea,1,currentRow);
        currentRow++;

        // 各级塑造
        String[] portraitLevels = {"一阶塑造", "二阶塑造", "三阶塑造", "四阶塑造", "五阶塑造"};
        String[] portraitKeys = {"firstPortrait", "secondPortrait", "thirdPortrait", "fourthPortrait", "fifthPortrait"};
        for (int i = 0;i < portraitLevels.length;i++){
            Label levelLabel = new Label(portraitLevels[i]);
            TextArea portraitArea = new TextArea();
            portraitArea.setPrefRowCount(3);
            portraitArea.setWrapText(true);
            portraitFields.put(portraitKeys[i],portraitArea);
            content.add(levelLabel,0,currentRow);
            content.add(portraitArea,1,currentRow);
            currentRow++;
        }

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(400);
        scrollPane.setStyle("-fx-background: white; -fx-border-color: #bdc3c7;");
        return scrollPane;
    }
    public ValidationResult validate() {
        String[] requiredInheritanceFields = {
                "inheritance", "firstInheritance", "secondInheritance", "thirdInheritance"
        };
        String[] fieldNames = {
                "传承名称", "一阶传承", "二阶传承", "三阶传承"
        };

        for (int i = 0; i < requiredInheritanceFields.length; i++) {
            TextArea area = inheritanceFields.get(requiredInheritanceFields[i]);
            if (area == null || area.getText().trim().isEmpty()) {
                return ValidationResult.failure(fieldNames[i] + "不能为空", area);
            }
        }

        String[] requiredPortraitFields = {
                "portraitDescribe", "firstPortrait", "secondPortrait",
                "thirdPortrait", "fourthPortrait", "fifthPortrait"
        };
        String[] portraitNames = {
                "塑造描述", "一阶塑造", "二阶塑造", "三阶塑造", "四阶塑造", "五阶塑造"
        };

        for (int i = 0; i < requiredPortraitFields.length; i++) {
            TextArea area = portraitFields.get(requiredPortraitFields[i]);
            if (area == null || area.getText().trim().isEmpty()) {
                return ValidationResult.failure(portraitNames[i] + "不能为空", area);
            }
        }

        return ValidationResult.success();
    }
    public Map<String, TextArea> getInheritanceFields() {
        return inheritanceFields;
    }

    public Map<String, TextArea> getPortraitFields() {
        return portraitFields;
    }
}
