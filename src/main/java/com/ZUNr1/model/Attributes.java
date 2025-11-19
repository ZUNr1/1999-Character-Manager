package com.ZUNr1.model;

public class Attributes {
    private int health;
    private int attack;
    private int realityDefense;
    private int mentalDefense;
    private int technique;
    // 添加无参构造函数（Jackson需要）
    /*todo 搞明白为什么json反序列化需要无参构造器*/
    public Attributes() {
        // Jackson会通过setter方法设置值
    }

    public Attributes(int health, int attack, int realityDefense, int mentalDefense, int technique) {
        this.health = health;
        this.attack = attack;
        this.realityDefense = realityDefense;
        this.mentalDefense = mentalDefense;
        this.technique = technique;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMentalDefense() {
        return mentalDefense;
    }

    public void setMentalDefense(int mentalDefense) {
        this.mentalDefense = mentalDefense;
    }

    public int getRealityDefense() {
        return realityDefense;
    }

    public void setRealityDefense(int realityDefense) {
        this.realityDefense = realityDefense;
    }

    public int getTechnique() {
        return technique;
    }

    public void setTechnique(int technique) {
        this.technique = technique;
    }
}

