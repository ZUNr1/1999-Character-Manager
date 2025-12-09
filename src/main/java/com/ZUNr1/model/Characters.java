package com.ZUNr1.model;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Characters extends Individual {
    private Inheritance inheritance;
    //这种字段，应该让其类的本身为不可变类
    private Portrait portrait;
    private List<Euphoria> euphoria;
    private OtherInformation otherInformation;
    private int rarity;
    //图片不要放在后端模型中，放在数据库中就好

    //构造器传入的类型是父类Builder的子类实现CharacterBuilder，运用多态的思想
    private Characters(CharactersBuilder charactersBuilder) {
        //使用private，强制使用builder来构造
        super(charactersBuilder);
        //子类cB含有父类B的所有字段，是父类的具体实现，所以传入cB而不是B
        this.inheritance = charactersBuilder.inheritance;
        this.portrait = charactersBuilder.portrait;
        this.euphoria = charactersBuilder.euphoria;
        this.otherInformation = charactersBuilder.otherInformation;
        this.rarity = charactersBuilder.rarity;
        //对额外字段赋值
    }

    public Inheritance getInheritance() {
        return inheritance;
    }

    public Portrait getPortrait() {
        return portrait;
    }

    public List<Euphoria> getEuphoria() {
        if (euphoria == null){
            return Collections.emptyList();
            //返回空的不可变表
        }
        return Collections.unmodifiableList(euphoria);
        //对于不可变对象中的字段，我们应该尽量让其不可变，这里返回的是不可变的list版本，或者直接返回新对象副本
    }

    public OtherInformation getOtherInformation() {
        return otherInformation;
    }

    public int getRarity() {
        return rarity;
    }

    public static class CharactersBuilder extends Builder {
        //Builder内部类设计为静态主要是为了不依靠外部类的实例，
        // 就是即使我没有实例化外部的类，也可以直接调用Builder，最后使用Build方法才会实例化外部对象
        //你想，我们要实例化外部类，构造器让我们用Builder，所以Builder一定是先于外部类实例化的
        private Inheritance inheritance;
        private Portrait portrait;
        private List<Euphoria> euphoria;
        private OtherInformation otherInformation;
        private int rarity;

        public CharactersBuilder(String id, String name, String enName,Gender gender,boolean isCustom,String creator) {
            super(id, name,enName,gender,isCustom,creator);
            //继承
        }

        @Override
        public CharactersBuilder attributes(Attributes attributes) {
            if (attributes == null) {
                throw new IllegalArgumentException("attributes不能为null");
            }
            //对于Builder的这个，我们需要检查空指针，但是对于不同的类型，检查方法要用不同的方法
            //枚举必须有明确值，null没有意义，直接报错
            this.attributes = attributes;
            return this;
            //返回this可以实现链式调用，一串代码完成所有构造
        }

        @Override
        public CharactersBuilder afflatus(Afflatus afflatus) {
            if (afflatus == null) {
                throw new IllegalArgumentException("afflatus不能为null");
            }
            //同样是集合类
            this.afflatus = afflatus;
            return this;
        }

        @Override
        public CharactersBuilder damageType(DamageType damageType) {
            this.damageType = damageType;
            return this;
        }

        @Override
        public CharactersBuilder skills(Skills skills) {
            this.skills = skills;
            //
            return this;
        }

        @Override
        public CharactersBuilder usedTerm(List<String> usedTerm) {
            this.usedTerm = (usedTerm != null) ? new ArrayList<>(usedTerm) : new ArrayList<>();
            //对于集合应该避免null，用空集合代替
            return this;
        }

        public CharactersBuilder inheritance(Inheritance inheritance) {
            this.inheritance = inheritance;
            return this;
        }

        public CharactersBuilder portrait(Portrait portrait) {
            this.portrait = portrait;
            return this;
        }

        public CharactersBuilder euphoria(List<Euphoria> euphoria) {
            this.euphoria = euphoria;
            return this;
        }

        public CharactersBuilder otherInformation(OtherInformation otherInformation) {
            this.otherInformation = otherInformation;
            return this;
        }

        public CharactersBuilder rarity(int rarity) {
            rarityValidate(rarity);
            this.rarity = rarity;
            return this;
        }

        //注意注意，Builder类里面必须要build方法，
        // 链式构造的时候build方法放在最后，Builder写了一大串，用build直接用在产生的对象上
        @Override
        public Characters build() {
            //build里面做最后验证
            return new Characters(this);
            //这复制副本也是防御性拷贝
        }

        private void rarityValidate(int rarity) {
            if (rarity < 2 || rarity > 6) {
                throw new IllegalArgumentException("稀有度超出范围");
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        //先检查是不是同一个对象，节省性能
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        //再检查空指针和是不是同一个Class
        Characters character = (Characters)obj;
        return Objects.equals(id,character.id);
        //Objects的equals可以处理id为null的情况，不用我们再手动检查
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}