package com.ZUNr1.enums;

public enum SkillType {
    ATTACK("攻击"),
    BUFF("增益"),
    DEBUFF("减益"),
    HEALTH("治疗"),
    CHANNEL("吟诵"),
    COUNTER("反制"),
    VERSATILE("特殊"),
    IMPROMPTU("即兴咒语");
    private final String chineseName;

    private SkillType(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }
}
