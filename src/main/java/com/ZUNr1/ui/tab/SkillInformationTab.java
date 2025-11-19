package com.ZUNr1.ui.tab;

import com.ZUNr1.ui.service.CharacterFormData;
import com.ZUNr1.ui.validation.ValidationResult;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

public class SkillInformationTab {
    private CharacterFormData formData;
    private Map<String, TextField> skillNameFields = new HashMap<>();
    private Map<String,Map<String, TextArea>> skillDescribeFields = new HashMap<>(); // 技能 -> 星级 -> 描述
    private Map<String,Map<String, TextArea>> skillStoryFields = new HashMap<>();    // 技能 -> 星级 -> 故事
    private Map<String,Map<String, ComboBox<String>>> skillTypeFields = new HashMap<>(); // 技能 -> 星级 -> 类型

    public SkillInformationTab(CharacterFormData formData){
        this.formData = formData;
    }
    public GridPane createTab(){
        //嵌套布局，GirdPane包住ScrollPane包住skillsContainer
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        //我们要解决每一列我们的布局是不一样的，放输入框的应该长一点，接收长段文字
        //使用ColumnConstraints可以控制页面的布局，每一列（竖列）的布局设置
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        col1.setPrefWidth(100);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        //限制不扩展，优先级低的操作不能改变布局
        //Always是尽可能扩展到最大
        col2.setPrefWidth(300);
        //设置首选的宽度（长度）
        //布局优先级顺序：
        //1.  ColumnConstraints/RowConstraints (最高优先级)
        //2.  父容器的布局策略 (GridPane、VBox等)
        //3.  组件自身的setPrefSize() (最低优先级)
        content.getColumnConstraints().addAll(col1, col2);
        //为GridPane的第0列设置col1规则，第1列设置col2规则

        int currentRow = 0;

        VBox skillsContainer = new VBox(15);
        skillsContainer.setStyle("-fx-padding: 10px;");
        skillsContainer.getChildren().addAll(createDetailedSkillPanel("神秘术I"),
                createDetailedSkillPanel("神秘术II"),
                createDetailedSkillPanel("至终的仪式"));
        //创建一个垂直排列的容器，存放3个详细的技能面板，间距15像素。

        //额外技能区域
        Label extraSkillsNameLabel = new Label("额外神秘术");
        Button extraSkillsAdd = new Button("+ 添加额外技能");

        VBox extraSkillsContainer = new VBox(10);//间距10像素
        //将其所有子节点（控件）在垂直方向（Vertical）上一个接一个地排列。
        //这个布局接收所有可能的额外技能，添加额外技能就在这个布局上修改
        extraSkillsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");

        extraSkillsAdd.setOnAction(actionEvent -> addExtraSkills(extraSkillsContainer));

        skillsContainer.getChildren().addAll(extraSkillsNameLabel,extraSkillsAdd,extraSkillsContainer);

        ScrollPane scrollPane = new ScrollPane(skillsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(350);
        //ScrollPane：让内容可以上下滚动,每个技能内容很多，可能会超限，我们使用滚动布局
        //setFitToWidth(true)：内容自动适应宽度
        //setPrefViewportHeight(350)：设置可见区域高度为350像素
        scrollPane.setStyle("-fx-background: white; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        content.add(scrollPane,0,currentRow,2,1);
        currentRow++;

        return content;
    }
    private GridPane createDetailedSkillPanel(String skillInformation){
        GridPane skillPane = new GridPane();
        skillPane.setHgap(10);
        skillPane.setVgap(12);
        skillPane.setPadding(new Insets(15));
        skillPane.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #ecf0f1;");
        int row = 0;

        Label titleLabel = new Label(skillInformation);
        if ("至终的仪式".equals(skillInformation)) {
            titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #e74c3c;");
        } else {
            titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        }
        skillPane.add(titleLabel,0,row,2,1);
        row++;

        Label nameLabel = new Label("神秘术名称");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入" + skillInformation + "名称");
        this.skillNameFields.put(skillInformation,nameField);
        skillPane.add(nameLabel,0,row);
        skillPane.add(nameField,1,row);
        row++;

        // 初始化嵌套Map
        Map<String, TextArea> describeMap = new HashMap<>();
        Map<String, TextArea> storyMap = new HashMap<>();
        Map<String, ComboBox<String>> typeMap = new HashMap<>();

        String[] skillLevels = {"一星牌","二星牌","三星牌"};

        for (String skillLevel : skillLevels){
            skillPane.add(createSkillLevelSection(skillLevel,describeMap,storyMap,typeMap),0,row,2,1);
            row += 4;
        }
        // 存储到对应的Map中
        this.skillDescribeFields.put(skillInformation, describeMap);
        //使用神秘术I这样的名字作为key
        this.skillStoryFields.put(skillInformation, storyMap);
        this.skillTypeFields.put(skillInformation, typeMap);

        return skillPane;
    }
    private GridPane createSkillLevelSection
            (String skillLevel, Map<String, TextArea> describeMap,
             Map<String, TextArea> storyMap, Map<String, ComboBox<String>> typeMap) {
        GridPane levelPane = new GridPane();
        levelPane.setHgap(10);
        levelPane.setVgap(8);
        levelPane.setPadding(new Insets(10));
        levelPane.setStyle("-fx-border-color: #d5dbdb; -fx-border-width: 1; -fx-background-color: #f4f6f6;");

        int levelRow = 0;

        Label levelLabel = new Label(skillLevel);
        levelLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #7f8c8d;");
        levelPane.add(levelLabel,0,levelRow,2,1);
        levelRow++;

        Label describeLabel = new Label("神秘术描述");
        TextArea describeArea = new TextArea();
        //与 TextField（单行文本框）不同，TextArea 可以容纳和显示多行文本。
        //当文本超出显示区域时，它会自动出现滚动条。
        describeArea.setPromptText("输入" + skillLevel + "技能效果");
        describeArea.setPrefRowCount(4);
        //设置初始行数row为4行
        describeArea.setWrapText(true);
        //设置自动换行，当输入不能一行显示，就换行

        describeMap.put(skillLevel, describeArea);
        //存储的是 TextField对象的引用（内存地址），而不是TextField的当前文本值。因此会收集到值

        levelPane.add(describeLabel,0,levelRow);
        levelPane.add(describeArea,1,levelRow);
        levelRow++;

        Label storyLabel = new Label("神秘术故事");
        TextArea storyArea = new TextArea();
        storyArea.setPromptText("输入" + skillLevel + "背景故事");
        storyArea.setPrefRowCount(2);
        storyArea.setWrapText(true);

        storyMap.put(skillLevel, storyArea);


        levelPane.add(storyLabel,0,levelRow);
        levelPane.add(storyArea,1,levelRow);
        levelRow++;

        Label typeLabel = new Label("神秘术类型");
        ComboBox<String> skillTypeComBox = new ComboBox<>();
        skillTypeComBox.getItems().addAll("攻击","增益","减益","治疗","吟诵","特殊","即兴咒语");

        typeMap.put(skillLevel, skillTypeComBox);

        levelPane.add(typeLabel,0,levelRow);
        levelPane.add(skillTypeComBox,1,levelRow);

        return levelPane;
    }
    private void addExtraSkills(VBox container){
        String extraSkillName = "额外神秘术_" + (System.currentTimeMillis());
        //System.currentTimeMillis()可以创建当前事件的时间戳，也就是显示创建时的时间字符串
        // 这个的目的是让createDetailedSkillPanel方法的名字唯一
        //因为我需要存储到Map中，这个时候key就要不同，如果加的额外神秘术都是一个名字，就无法唯一
        GridPane extraSkillPane = createDetailedSkillPanel(extraSkillName);

        Button removeExtraSkill = new Button("删除");
        removeExtraSkill.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

        VBox skillSelection = new VBox(15);
        skillSelection.getChildren().addAll(extraSkillPane,removeExtraSkill);
        //把组件加入VBox，就会垂直排序
        container.getChildren().add(skillSelection);
        //注意，这行代码是在button按下前执行的，先关联container，然后再处理
        removeExtraSkill.setOnAction
                (actionEvent -> {
                    container.getChildren().remove(skillSelection);
                    //删除这个组件，skillSelection不再被container引用
                    //所以后续代码还会执行，但是不再关联container
                    //注意，与container直接管联的是skillSelection而不是extraSkillPane

                    // 从所有Map中移除对应的数据
                    skillNameFields.remove(extraSkillName);
                    skillDescribeFields.remove(extraSkillName);
                    skillStoryFields.remove(extraSkillName);
                    skillTypeFields.remove(extraSkillName);
                });
    }
    public ValidationResult validate() {
        // 验证固定技能
        String[] fixedSkills = {"神秘术I", "神秘术II", "至终的仪式"};
        for (String skillKey : fixedSkills) {
            TextField nameField = skillNameFields.get(skillKey);
            if (nameField == null || nameField.getText().trim().isEmpty()) {
                return ValidationResult.failure(skillKey + "的名称不能为空", nameField);
            }
            ValidationResult detailResult = validateSkillDetails(skillKey);
            if (!detailResult.isValid()) {
                return detailResult;
            }
        }
        for (String skillKey : skillNameFields.keySet()) {
            if (skillKey.startsWith("额外神秘术_")) {
                TextField nameField = skillNameFields.get(skillKey);
                if (nameField.getText().trim().isEmpty()) {
                    return ValidationResult.failure("额外技能名称不能为空", nameField);
                }

                ValidationResult detailResult = validateSkillDetails(skillKey);
                if (!detailResult.isValid()) {
                    return detailResult;
                }
            }
        }

        return ValidationResult.success();
    }
    private ValidationResult validateSkillDetails(String skillKey) {
        Map<String, TextArea> describeMap = skillDescribeFields.get(skillKey);
        if (describeMap != null) {
            for (Map.Entry<String, TextArea> entry : describeMap.entrySet()) {
                //Map.Entry 是Map接口中的一个内部接口
                //每个Entry对象代表Map中的一个键值对(key-value pair)
                String level = entry.getKey();
                TextArea area = entry.getValue();
                if (area.getText().trim().isEmpty()) {
                    return ValidationResult.failure(skillKey + "的" + level + "描述不能为空", area);
                }
            }
        }
        Map<String, TextArea> storyMap = skillStoryFields.get(skillKey);
        if (storyMap != null) {
            for (Map.Entry<String, TextArea> entry : storyMap.entrySet()) {
                String level = entry.getKey();
                TextArea area = entry.getValue();
                if (area.getText().trim().isEmpty()) {
                    return ValidationResult.failure(skillKey + "的" + level + "故事不能为空", area);
                }
            }
        }
        Map<String, ComboBox<String>> typeMap = skillTypeFields.get(skillKey);
        if (typeMap != null) {
            for (Map.Entry<String, ComboBox<String>> entry : typeMap.entrySet()) {
                String level = entry.getKey();
                ComboBox<String> combo = entry.getValue();
                if (combo.getValue() == null) {
                    return ValidationResult.failure(skillKey + "的" + level + "类型必须选择", combo);
                }
            }
        }
        return ValidationResult.success();
    }

    public Map<String, TextField> getSkillNameFields() {
        return skillNameFields;
    }

    public Map<String, Map<String, TextArea>> getSkillDescribeFields() {
        return skillDescribeFields;
    }

    public Map<String, Map<String, TextArea>> getSkillStoryFields() {
        return skillStoryFields;
    }

    public Map<String, Map<String, ComboBox<String>>> getSkillTypeFields() {
        return skillTypeFields;
    }
}
