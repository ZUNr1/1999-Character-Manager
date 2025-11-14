package com.ZUNr1.model;

import com.ZUNr1.enums.SkillType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Skills {
    private SkillDetail arcaneSkill1;
    //包含了信息的技能
    private SkillDetail arcaneSkill2;
    private SkillDetail arcaneSkill3;
    private List<SkillDetail> extraSkills;

    private Skills(SkillsBuilder skillsBuilder) {
        this.arcaneSkill1 = skillsBuilder.arcaneSkill1;
        this.arcaneSkill2 = skillsBuilder.arcaneSkill2;
        this.arcaneSkill3 = skillsBuilder.arcaneSkill3;
        this.extraSkills = new ArrayList<>(skillsBuilder.extraSkills);
        //this.extraSkills = skillsBuilder.extraSkills;
        //这行是错误的，因为这样skillsBuilder和Skills会共享同一个对象，不好
        //这叫防御性拷贝
    }

    public SkillDetail getArcaneSkill1() {
        return arcaneSkill1;
    }

    public SkillDetail getArcaneSkill2() {
        return arcaneSkill2;
    }

    public SkillDetail getArcaneSkill3() {
        return arcaneSkill3;
    }

    public List<SkillDetail> getExtraSkills() {
        return new ArrayList<>(extraSkills);
        //防御性拷贝，这样外界修改这个数组时不会把内部的数组也修改
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== 技能图鉴 ===\n");
        sb.append(String.format("技能1: %s\n", arcaneSkill1.getSkillName()));
        sb.append(String.format("技能2: %s\n", arcaneSkill2.getSkillName()));
        sb.append(String.format("至终的仪式: %s\n", arcaneSkill3.getSkillName()));

        if (!extraSkills.isEmpty()) {
            sb.append("额外技能:\n");
            for (SkillDetail skill : extraSkills) {
                sb.append(String.format("  - %s\n", skill.getSkillName()));
            }
        }
        return sb.toString();
    }

    public static class SkillDetail {
        private String skillName;
        //技能名字
        private Map<Integer, SkillInLevel> skillInLevel;

        private SkillDetail(SkillDetailBuilder skillDetailBuilder) {
            this.skillName = skillDetailBuilder.skillName;
            this.skillInLevel = skillDetailBuilder.skillInLevelMap;
        }

        public String getSkillName() {
            return skillName;
        }

        public Map<Integer, SkillInLevel> getSkillInLevel() {
            return new HashMap<>(skillInLevel);
        }

        //存放三个等级的技能描述，技能类型，技能故事
        //个体是又一个新的内部类，对应其中一个等级的技能
        public static class SkillDetailBuilder {
            private String skillName;
            private Map<Integer, SkillInLevel> skillInLevelMap;

            public SkillDetailBuilder(String skillName) {
                if (skillName != null && !skillName.trim().isEmpty()) {
                    this.skillName = skillName;
                    this.skillInLevelMap = new HashMap<>();
                } else {
                    throw new IllegalArgumentException("技能名不能为空");
                }
            }

            public SkillDetailBuilder addSkillInLevel
                    (int level, String skillDescribe, String skillStory, SkillType skillType) {
                if (this.skillInLevelMap.containsKey(level)) {
                    throw new IllegalArgumentException("该等级的技能已经存在");
                }
                SkillInLevel skillInLevel = new SkillInLevel(level, skillDescribe, skillStory, skillType);
                this.skillInLevelMap.put(level, skillInLevel);
                return this;
            }

            public SkillDetail build() {
                if (skillInLevelMap.isEmpty()) {
                    throw new IllegalStateException("至少需要添加一个技能等级");
                }
                return new SkillDetail(this);
            }
        }
    }

    public static class SkillInLevel {
        private int level;
        private String skillDescribe;
        private String skillStory;
        private SkillType skillType;

        public SkillInLevel(int level, String skillDescribe, String skillStory, SkillType skillType) {
            validate(level, skillDescribe, skillStory);
            //检验
            this.level = level;
            this.skillDescribe = skillDescribe;
            this.skillStory = skillStory;
            this.skillType = skillType;
        }

        private void validate(int level, String skillDescribe, String skillStory) {
            if (level < 1 || level > 3) {
                throw new IllegalArgumentException("技能等级超出范围");
            }
            if (skillDescribe == null || skillDescribe.trim().isEmpty()) {
                throw new IllegalArgumentException("技能描述为空");
            }
            if (skillStory == null || skillStory.trim().isEmpty()) {
                throw new IllegalArgumentException("技能故事为空");
            }
        }

        public int getLevel() {
            return level;
        }

        public String getSkillDescribe() {
            return skillDescribe;
        }

        public String getSkillStory() {
            return skillStory;
        }

        public SkillType getSkillType() {
            return skillType;
        }
    }

    public static class SkillsBuilder {
        private SkillDetail arcaneSkill1;
        private SkillDetail arcaneSkill2;
        private SkillDetail arcaneSkill3;
        private List<SkillDetail> extraSkills;

        public SkillsBuilder() {

            this.arcaneSkill1 = createEmptySkill("未设置技能1");
            //避免空指针，我们设置一个默认值，这样可以清楚看到没有的值
            this.arcaneSkill2 = createEmptySkill("未设置技能2");
            this.arcaneSkill3 = createEmptySkill("未设置大招");
            this.extraSkills = new ArrayList<>();
        }

        private SkillDetail createEmptySkill(String skillName) {
            return new SkillDetail.SkillDetailBuilder(skillName)
                    .addSkillInLevel(1, "暂无描述", "暂无故事", SkillType.VERSATILE)
                    .build();
            //builder构建
        }

        public SkillsBuilder arcaneSkill1(SkillDetail arcaneSkill1) {
            if (arcaneSkill1 != null) {
                this.arcaneSkill1 = arcaneSkill1;
            }
            return this;
        }

        public SkillsBuilder arcaneSkill2(SkillDetail arcaneSkill2) {
            if (arcaneSkill2 != null) {
                this.arcaneSkill2 = arcaneSkill2;
            }
            return this;
        }

        public SkillsBuilder arcaneSkill3(SkillDetail arcaneSkill3) {
            if (arcaneSkill3 != null) {
                this.arcaneSkill3 = arcaneSkill3;
            }
            return this;
        }

        public SkillsBuilder addExtraSkills(SkillDetail extraSkill) {
            if (extraSkill != null) {
                this.extraSkills.add(extraSkill);
            }
            return this;
        }

        public Skills build() {
            return new Skills(this);
        }
    }
}
