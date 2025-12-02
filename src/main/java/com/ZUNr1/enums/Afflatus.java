package com.ZUNr1.enums;

public enum Afflatus {
    //对于这种常量，使用枚举类比较好，比起常量类SystemConstants可以检查和附上额外信息
    STAR(1, "星系"),
    //创建枚举的时候附上信息（要实现有参构造器）
    MINERAL(2, "岩系"),
    BEAST(3, "兽系"),
    PLANT(4, "木系"),
    SPIRIT(-1, "灵系"),
    INTELLECT(-2, "智系");
    //关于要不要添加UNKNOWN类型，我选择不添加，因为每个角色是必须要的信息，而且每个枚举还有附加的信息
    private final int type;
    private final String chineseName;

    //私有化的构造方法，用于创建枚举的时候附上额外的信息
    private Afflatus(int type, String chineseName) {
        this.type = type;
        this.chineseName = chineseName;
    }

    //根据type信息找对应值
    public static Afflatus fromType(int type) {
        //注意，这里应该是静态的，不需要实例就可以
        for (Afflatus afflatus : values()) {
            //values()是遍历枚举类的数组，set遍历中for each直接用set本身就行，map遍历用keySet()
            if (afflatus.type == type) {
                return afflatus;
            }
        }
        throw new IllegalArgumentException("无效的灵感类型");
    }

    //检查灵感的分类（原生灵感和自然灵感）
    public boolean isNatureAfflatus() {
        return this.type > 0;
    }

    public boolean isNativeAfflatus() {
        return this.type < 0;
    }

    public int getType() {
        return type;
    }

    public String getChineseName() {
        return chineseName;
    }
}
