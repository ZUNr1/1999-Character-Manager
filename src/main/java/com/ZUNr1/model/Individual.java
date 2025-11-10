package com.ZUNr1.model;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;


import java.util.List;
import java.util.Objects;

public abstract class Individual {
    protected String id;
    //注意，protected修饰的字段同一个包里面也可以访问，
    // 但是以后可能分包，所以在CharactersManage里面还是用getter方法
    protected String name;
    protected Gender gender;
    protected Afflatus afflatus;
    protected DamageType damageType;
    protected int level;
    protected Attributes attributes;
    protected Skills skills;
    protected List<String> usedTerm;
    //因为字段太多，构造器写的非常臃肿，我们使用Builder类实现构造
    public abstract static class Builder {
        //这里因为Individual不允许实例化，所以Builder类也用abstract修饰，提醒子类实现
        protected String id;
        //同样protected主要方便子类的Builder使用
        protected String name;
        protected Gender gender;
        protected Afflatus afflatus;
        protected DamageType damageType;
        protected int level;
        protected Attributes attributes;
        protected Skills skills;
        protected List<String> usedTerm;
        public Builder(String id,String name,Gender gender,int level){
            validate(id,name,level);
            this.gender = gender;
            this.id = id;
            this.name = name.trim();
            this.level = level;
        }
        public abstract Builder attributes(Attributes attributes);
        public abstract Builder afflatus(Afflatus afflatus);
        public abstract Builder damageType(DamageType damageType);
        public abstract Builder skills(Skills skills);
        public abstract Builder usedTerm(List<String> usedTerm);
        public abstract Individual build();
        private void validate(String id, String name, int level){

            if (id == null || id.trim().isEmpty()){
                throw new IllegalArgumentException("id不能为空");
            }
            if (id.length() > 10){
                throw new IllegalArgumentException("id长度不能超过10位");
            }
            if (name == null || name.trim().isEmpty()){//trim()先去掉空格再比较
                throw new IllegalArgumentException("姓名不能为空");
            }
            if (name.length() > 10){
                throw new IllegalArgumentException("姓名长度不能超过10位");
            }
            if (level <= 0 || level > 200){
                throw new IllegalArgumentException("等级超出范围");
            }
        }
    }
    protected Individual(Builder builder){
        this.id = builder.id;
        this.name = builder.name;
        this.gender = builder.gender;
        this.level = builder.level;
        this.afflatus = builder.afflatus;
        this.damageType = builder.damageType;
        this.attributes = builder.attributes;
        this.skills = builder.skills;
        this.usedTerm = builder.usedTerm;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getLevel() {
        return level;
    }

    public Afflatus getAfflatus() {
        return afflatus;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public List<String> getUsedTerm() {
        return usedTerm;
    }

    public Skills getSkills() {
        return skills;
    }

    @Override
    public boolean equals(Object obj){//equals编写先检查是不是本身，再看是不是空，，再看是不是同一类
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){//想想为什么不用this.getClass
            return false;
        }
        Individual that = (Individual) obj;//obj要先变成实际的类型才能继续比较
        return Objects.equals(id,that.getId());
        //这里使用了Objects的equals方法
        //return this.getId().equals(that.getId());为什么不能使用这段代码(空指针异常)
        //假如this的id是null，我们实际上对空的指针使用equals，会抛异常
    }
    @Override
    public int hashCode(){
        return Objects.hash(getId());
        //Object和Objects是不一样的，Object是所有类的超类，Objects是工具类，有equals和hash等安全的方法
    }

}
