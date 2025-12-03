package com.ZUNr1.ui.viewtab;

import com.ZUNr1.model.Characters;
import com.ZUNr1.model.Skills;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class SkillInformationViewTab {
    private Characters character;
    public SkillInformationViewTab(Characters character){
        this.character = character;
    }
    public GridPane createViewTab(){
        GridPane content = new GridPane();
        content.setHgap(15);
        content.setVgap(15);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: #f8f9fa;");

        Label titleLabel = new Label("角色技能信息");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        content.add(titleLabel, 0, 0);

        Accordion skillsAccordion = new Accordion();
        //它允许用户通过点击标题来展开或折叠多个内容面板,有效节省屏幕空间，同时保持内容的组织性
        //Accordion 由多个 TitledPane 组成
        //每个 TitledPane 包含一个标题区域和内容区域
        //标题区域始终可见，内容区域可折叠/展开
        //同一时间通常只有一个 TitledPane 处于展开状态
        skillsAccordion.setPadding(new Insets(10,0,0,0));
        skillsAccordion.setPrefWidth(500);
        //setPadding设置页边距，需要Inserts对象，Inserts一个参数确定上下左右，或者完整4个参数上右下左，两个参数对应上下和左右
        addSkillToAccordion(skillsAccordion,"神秘术I",character.getSkills().getArcaneSkill1());
        addSkillToAccordion(skillsAccordion,"神秘术II",character.getSkills().getArcaneSkill2());
        addSkillToAccordion(skillsAccordion,"至终的仪式",character.getSkills().getArcaneSkill3());
        List<Skills.SkillDetail> extraSkills = character.getSkills().getExtraSkills();
        int extraSkillCount = 1;
        for (Skills.SkillDetail extraSkill : extraSkills){
            addSkillToAccordion(skillsAccordion,"额外神秘术" + extraSkillCount,extraSkill);
            extraSkillCount++;
        }
        content.add(skillsAccordion,0,1);
        return content;
    }
    private void addSkillToAccordion(Accordion skillAccordion, String skillName, Skills.SkillDetail skill){
        if (skill == null){
            return;
        }
        VBox skillContent = new VBox(10);
        skillContent.setPadding(new Insets(15));
        skillContent.setStyle("-fx-background-color: #ffffff;");
        Label nameLabel = new Label("技能名称：" + (skill.getSkillName() != null ? skill.getSkillName() : "未命名"));
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        HBox levelsBox = createLevelPanels(skill);
        skillContent.getChildren().addAll(nameLabel,levelsBox);

        TitledPane titledPane = new TitledPane(skillName,skillContent);
        //TitlePane作为独属于Accordion的子节点，包含标题和内容，标题一直存在，内容默认折叠
        titledPane.setAnimated(false);
        //禁用展开/折叠时的动画效果
        skillAccordion.getPanes().add(titledPane);
        //getPanes和getChildren不同点在于，Accordion只允许添加TitledPane，使用getPanes添加可以确保类型安全
        //getChildren一般用在自由的类型上，不限制类型,所以Accordion就没有getChildren
    }
    private String getLevelName(int level) {
        switch (level) {
            case 1: return "一星牌";
            case 2: return "二星牌";
            case 3: return "三星牌";
            default: return "未知等级";
        }
    }
    private HBox createLevelPanels(Skills.SkillDetail skill){
        HBox levelsContainer = new HBox(15);
        levelsContainer.setPadding(new Insets(10));
        Map<Integer, Skills.SkillInLevel> skillLevels = skill.getSkillInLevel();
        for (int i = 1;i <= 3;i++){
            if (skillLevels.containsKey(i)){
                Skills.SkillInLevel level = skillLevels.get(i);
                VBox levelPanel = createSingleLevelPanel(getLevelName(i),level);
                levelsContainer.getChildren().add(levelPanel);
            }
        }
        return levelsContainer;
    }
    private VBox createSingleLevelPanel(String levelName, Skills.SkillInLevel level){
        VBox levelBox = new VBox(8);
        levelBox.setPadding(new Insets(10));
        levelBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        levelBox.setPrefWidth(200);

        Label levelLabel = new Label(levelName);
        levelLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #7f8c8d;");

        Label describeLabel = new Label("技能描述:");
        describeLabel.setStyle("-fx-font-weight: bold;");
        Label describeValueLabel = new Label(level.getSkillDescribe() != null ? level.getSkillDescribe() : "无");
        describeValueLabel.setWrapText(true);
        describeValueLabel.setMaxWidth(180);

        Label storyLabel = new Label("背景故事:");
        storyLabel.setStyle("-fx-font-weight: bold;");
        Label storyValueLabel = new Label(level.getSkillStory() != null ? level.getSkillStory() : "无");
        storyValueLabel.setWrapText(true);
        describeValueLabel.setMaxWidth(180);

        Label typeLabel = new Label("技能类型:");
        typeLabel.setStyle("-fx-font-weight: bold;");
        Label typeValueLabel = new Label(level.getSkillType() != null ? level.getSkillType().getChineseName() : "未指定");
        levelBox.getChildren().addAll(levelLabel,describeLabel,describeValueLabel,storyLabel,storyValueLabel,typeLabel,typeValueLabel);
        return levelBox;
    }


}
