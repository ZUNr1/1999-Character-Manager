package com.ZUNr1.ui;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class CharacterMainController {
    private BorderPane root;

    // 基本信息
    private TextField idField;
    private TextField nameField;
    private ComboBox<String> genderComboBox;
    private ComboBox<String> afflatusComboBox;
    private ComboBox<String> damageTypeComboBox;
    private Spinner<Integer> raritySpinner;

    // 属性信息
    private TextField healthField;
    private TextField attackField;
    private TextField realityDefenseField;
    private TextField mentalDefenseField;
    private TextField techniqueField;

    //技能信息
    private Map<String,TextField> skillNameFields = new HashMap<>();
    private Map<String,Map<String, TextArea>> skillDescribeFields = new HashMap<>(); // 技能 -> 星级 -> 描述
    private Map<String,Map<String, TextArea>> skillStoryFields = new HashMap<>();    // 技能 -> 星级 -> 故事
    private Map<String,Map<String, ComboBox<String>>> skillTypeFields = new HashMap<>(); // 技能 -> 星级 -> 类型

    //传承与塑造
    private Map<String,TextArea> inheritanceFields = new HashMap<>();
    private Map<String,TextArea> portraitFields = new HashMap<>();

    private Map<String,TextField> usedTermNameFields = new HashMap<>();
    private Map<String,TextArea> usedTermDescribeFields = new HashMap<>();

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

        HBox buttonBox = createButtonBox();
        root.setBottom(buttonBox);

        setUpWindowsCloseHandle();
        //设置关闭窗口时的操作
    }
    private void setUpWindowsCloseHandle(){
        Platform.runLater(() -> {
            // 这行代码的意思是："等当前代码执行完后，在JavaFX应用线程中执行括号里的代码"
            //"当前代码"指的是调用 setupWindowCloseHandler() 方法的代码。
            // - 在构造函数中，root可能还没有被添加到Scene
            // - 在Scene显示之前，getScene()可能返回null
            // - runLater确保在界面完全初始化后再执行
        Stage stage = (Stage)root.getScene().getWindow();
        // 这时候root已经确定在Scene中了，所以getScene()不会返回null
        stage.setOnCloseRequest(event -> {
            // 设置窗口关闭事件监听器
            // 当用户点击窗口的X按钮时，会触发这个事件
            event.consume();
            //这是阻止事件的默认执行，默认是关闭程序，我们阻止，然后才能运行后面的代码显示提示框
            showExitConfirmation();
            //这是执行操作，我们在里面实现显示提示框与关闭程序
        });
        });

    }
    private void showExitConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("退出程序");
        alert.setHeaderText("真的要离开我吗 😢");
        alert.setContentText("是否要退出程序？未保存的数据将会丢失。");

        // 普通按钮版本（防止误按Enter）
        ButtonType exitButton = new ButtonType("狠心离开");
        ButtonType stayButton = new ButtonType("再陪陪你");
        alert.getButtonTypes().setAll(exitButton, stayButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == exitButton) {
            Platform.exit(); // 退出程序
        }
        // 如果点击"再陪陪你"或关闭对话框，什么都不做（窗口保持打开）
    }
    private HBox createButtonBox(){
        HBox buttonBox = new HBox(20);
        buttonBox.setPadding(new Insets(15));
        buttonBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #dee2e6; -fx-border-width: 1 0 0 0;");
        buttonBox.setAlignment(Pos.CENTER);
        //setAlignment() 方法用于设置容器内所有子元素的对齐方式
        //Pos.CENTER 是一个常量，表示居中对齐
        Button confirmButton = new Button("确认录入");
        confirmButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16;");
        confirmButton.setOnAction(actionEvent -> confirmInput());

        Button clearButton = new Button("重新开始");
        clearButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 16;");
        clearButton.setOnAction(actionEvent -> createNewCharacter());

        buttonBox.getChildren().addAll(confirmButton,clearButton);
        return buttonBox;
    }
    private void confirmInput(){

    }
    private void createNewCharacter(){
        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            //一个确认类型的对话框Alert
            alert.setTitle("新建角色");
            alert.setHeaderText("开始新的角色编辑");
            alert.setContentText("确定要开始编辑新角色吗？当前窗口的所有输入将被重置。");
            // 先获取默认的按钮类型
            ButtonType defaultOkButton = ButtonType.OK;
            ButtonType defaultCancelButton = ButtonType.CANCEL;
            // 移除默认按钮
            alert.getButtonTypes().removeAll(defaultOkButton, defaultCancelButton);
            //我们选择使用新的按钮，因为默认按钮不能做到按enter键确定
            ButtonType newCharacterButton = new ButtonType("新建角色", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("我手滑了", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().addAll(newCharacterButton, cancelButton);
            //按 Enter 键默认会触发：
            //具有 ButtonData.OK_DONE、ButtonData.YES、ButtonData.FINISH 类型的按钮
            //如果没有上述按钮，则触发第一个定义的按钮
            //OK_DONE, YES - 通常放在右侧，表示确认   CANCEL_CLOSE, NO - 通常放在左侧，表示取消
            //APPLY, FINISH - 应用或完成   HELP - 帮助按钮
            //LEFT, RIGHT - 控制左右位置   BIG_GAP, SMALL_GAP - 添加间距
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == newCharacterButton) {
                //检查用户是否做出了选择（不是直接关闭对话框）同时点击了"确定"按钮
                reuseCurrentWindows();
            }
        } catch (Exception e) {
            showAlert("错误", "重新开始失败: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    private void reuseCurrentWindows(){
        Stage currentStage = (Stage) root.getScene().getWindow();
        //getScene() 返回这个组件所在的 Scene 对象(CharacterApp类里面的scene)
        //scene.getWindow() 获取包含这个 Scene 的 Window
        // 我们使用Stage是因为 Window 只有基础功能 Stage 有更多控制方法，这样我们可以对窗口本身进行调整
        double currentWidth = currentStage.getWidth();
        double currentHeight = currentStage.getHeight();
        //获取窗口的长度和宽度
        double currentX = currentStage.getX();
        double currentY = currentStage.getY();
        //获得窗口在界面的x，y坐标，这样可以原位置新建窗口
        boolean isMaximized = currentStage.isMaximized();
        //窗口是否最大化
        CharacterMainController newController = new CharacterMainController();
        Scene newScene = new Scene(newController.getRoot(),currentWidth,currentHeight);//创建新场景
        currentStage.setScene(newScene);//把当前窗口的场景变为新的
        currentStage.setTitle("新建角色 - 角色信息录入系统");

        // 恢复窗口位置和大小（如果不是最大化状态）
        if (!isMaximized) {
            currentStage.setX(currentX);
            currentStage.setY(currentY);
            currentStage.setWidth(currentWidth);
            currentStage.setHeight(currentHeight);
        }
        showAlert("提示", "已开始编辑新角色", Alert.AlertType.INFORMATION);


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

    private GridPane createAttributesInformationTab(){
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-padding: 20px;");

        Label titleLabel = new Label("角色属性（默认满级）");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label healthLabel = new Label("生命值");
        healthField = createAttributeField(30000,0);

        Label attackLabel = new Label("攻击力");
        attackField = createAttributeField(2000,0);

        Label realityDefenseLabel = new Label("现实防御");
        realityDefenseField = createAttributeField(2000,0);

        Label mentalDefenseLabel = new Label("精神防御");
        mentalDefenseField = createAttributeField(2000,0);

        Label techniqueLabel = new Label("暴击技巧");
        techniqueField = createAttributeField(2000,0);

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

    private TextField createAttributeField(int maxValue,int minValue){
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

    private ScrollPane createOtherInformationTab(){
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

    private GridPane createUsedTermInformationTab(){
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

    private void showAlert(String title, String message, Alert.AlertType type) {
        //写这个方法主要是为了方便新建提示框
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public BorderPane getRoot() {
        return root;
    }

    public TextField getIdField() {
        return idField;
    }

    public TextField getNameField() {
        return nameField;
    }

    public ComboBox<String> getAfflatusComboBox() {
        return afflatusComboBox;
    }

    public ComboBox<String> getDamageTypeComboBox() {
        return damageTypeComboBox;
    }

    public ComboBox<String> getGenderComboBox() {
        return genderComboBox;
    }

    public Spinner<Integer> getRaritySpinner() {
        return raritySpinner;
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

    public Map<String, TextArea> getInheritanceFields() {
        return inheritanceFields;
    }

    public Map<String, TextArea> getPortraitFields() {
        return portraitFields;
    }

    public Map<String, TextField> getUsedTermNameFields() {
        return usedTermNameFields;
    }

    public Map<String, TextArea> getUsedTermDescribeFields() {
        return usedTermDescribeFields;
    }
}
