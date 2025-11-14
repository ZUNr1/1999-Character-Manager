package com.ZUNr1.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

public class CharacterMainController {
    private BorderPane root;
    private List<TextField> extraSkillFields = new ArrayList<>();

    public CharacterMainController(){
        createInterface();
    }
    private void createInterface(){
        root = new BorderPane();

        //中心标题
        Label titleLabel = new Label("角色信息录入系统");
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
    private GridPane createSkillInformationTab(){
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
        col2.setPrefWidth(200);
        //设置首选的宽度（长度）
        //布局优先级顺序：
        //1.  ColumnConstraints/RowConstraints (最高优先级)
        //2.  父容器的布局策略 (GridPane、VBox等)
        //3.  组件自身的setPrefSize() (最低优先级)
        content.getColumnConstraints().addAll(col1, col2);
        //为GridPane的第0列设置col1规则，第1列设置col2规则

        int currentRow = 0;

        Label titleLabel = new Label("神秘术信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        content.add(titleLabel,0,currentRow,2,1);
        currentRow++;

        Label skillNameLabel = new Label("神秘术名称");
        skillNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(skillNameLabel,0,currentRow,2,1);
        currentRow++;
        //神秘术I
        Label skill1NameLabel = new Label("神秘术I");
        TextField skill1NameField = new TextField();
        skill1NameField.setPromptText("请输入神秘术名称");
        content.add(skill1NameLabel,0,currentRow);
        //第二列够长，不用横跨
        content.add(skill1NameField,1,currentRow);
        currentRow++;
        //神秘术II
        Label skill2NameLabel = new Label("神秘术II");
        TextField skill2NameField = new TextField();
        skill2NameField.setPromptText("请输入神秘术名称");
        content.add(skill2NameLabel,0,currentRow);
        content.add(skill2NameField,1,currentRow);
        currentRow++;
        //至终的仪式
        Label skill3NameLabel = new Label("至终的仪式");
        TextField skill3NameField = new TextField();
        skill3NameField.setPromptText("请输入至终的仪式名称");
        content.add(skill3NameLabel,0,currentRow);
        content.add(skill3NameField,1,currentRow);
        currentRow++;
        //额外技能区域
        Label extraSkillsNameLabel = new Label("额外神秘术");
        content.add(extraSkillsNameLabel,0,currentRow);

        Button extraSkillsAdd = new Button("+ 添加额外技能");
        content.add(extraSkillsAdd,1,currentRow);
        currentRow++;

        VBox extraSkillsContainer = new VBox(10);//间距10像素
        //将其所有子节点（控件）在垂直方向（Vertical）上一个接一个地排列。
        //这个布局接收所有可能的额外技能，添加额外技能就在这个布局上修改
        extraSkillsContainer.setStyle("-fx-padding: 10px; -fx-border-color: #bdc3c7; -fx-border-width: 1;");
        content.add(extraSkillsContainer,0,currentRow,2,1);

        extraSkillsAdd.setOnAction(actionEvent -> addExtraSkills(extraSkillsContainer));
        //思路是使用VBox和HBox，添加


        return content;
    }
    private void addExtraSkills(VBox container){
        HBox skillRow = new HBox(10);//间距10像素
        //将其所有子节点在水平方向（Horizontal）上一个接一个地排列。
        skillRow.setAlignment(Pos.CENTER_LEFT);
        //设置子节点在容器内垂直居中、水平靠左对齐
        skillRow.setStyle("-fx-padding: 8px; -fx-border-color: #ecf0f1;-fx-border-width: 1; -fx-background-color: #f8f9fa;");

        Label extraSkillNameLabel = new Label("额外神秘术名称");
        TextField extraSkillNameField = new TextField();
        extraSkillNameField.setPromptText("请输入额外神秘术名称");

        Button removeExtraSkill = new Button("删除");
        removeExtraSkill.setOnAction
                (actionEvent -> {
                    container.getChildren().remove(skillRow);
                    //删除这个组件，skillRow不再被container引用
                    //所以后续代码还会执行，但是不再关联container
                    extraSkillFields.remove(extraSkillNameField);
                    //把存的对应数据的List里面的值也删除了
                });
        skillRow.getChildren().addAll(extraSkillNameLabel,extraSkillNameField,removeExtraSkill);
        //把组件加入HBox，就会水平排序
        container.getChildren().add(skillRow);
        //把HBox加入总布局
        //注意，这行代码是在button按下前执行的，先关联container，然后再处理
        extraSkillFields.add(extraSkillNameField);
    }
    private GridPane createSkill(String skillInformation,int startRow){
        GridPane skillPane = new GridPane();
        skillPane.setHgap(8);
        skillPane.setVgap(10);
        skillPane.setPadding(new Insets(12));
        skillPane.setStyle("-fx-border-color: #bdc3c7; -fx-border-width: 1; -fx-background-color: #ecf0f1;");

        int row = 0;
        Label nameLabel = new Label("神秘术名称");
        TextField nameField = new TextField();
        nameField.setPromptText("请输入" + skillInformation + "名称");
        skillPane.add(nameLabel,0,row);
        skillPane.add(nameField,1,row,3,1);
        row++;

        Label level1Label = new Label("一星牌");


        return new GridPane();
    }
    private GridPane createAttributesInformationTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色属性（默认满级）");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label healthLabel = new Label("生命值");
        TextField healthField = attributesInput(30000,0,"10000");

        Label attackLabel = new Label("攻击力");
        TextField attackField = attributesInput(2000,0,"1000");

        Label realityDefenseLabel = new Label("现实防御");
        TextField realityDefenseField = attributesInput(2000,0,"500");

        Label mentalDefenseLabel = new Label("精神防御");
        TextField mentalDefenseField = attributesInput(2000,0,"500");

        Label techniqueLabel = new Label("暴击技巧");
        TextField techniqueField = attributesInput(2000,0,"500");

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
    private TextField attributesInput(int maxValue,int minValue,String defaultValue){
        if (minValue > maxValue){
            throw new IllegalArgumentException("最大限制小于最小限制");
        }
        TextField field = new TextField(defaultValue);
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
    private VBox createOtherInformationTab(){
        return new VBox(new Label("开发中"));
    }
    private VBox createUsedTermInformationTab(){
        return new VBox(new Label("开发中"));
    }

    public BorderPane getRoot() {
        return root;
    }

}
