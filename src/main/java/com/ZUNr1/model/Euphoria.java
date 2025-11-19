package com.ZUNr1.model;

public class Euphoria {
    private String euphoriaName;
    private Attributes euphoriaAttributesAddition;
    private String firstEuphoria;
    private String secondEuphoria;
    private String thirdEuphoria;
    private String fourthEuphoria;
    public Euphoria(){}
    private Euphoria(EuphoriaBuilder euphoriaBuilder){
        this.euphoriaName = euphoriaBuilder.euphoriaName;
        this.euphoriaAttributesAddition = euphoriaBuilder.euphoriaAttributesAddition;
        this.firstEuphoria = euphoriaBuilder.firstEuphoria;
        this.secondEuphoria = euphoriaBuilder.secondEuphoria;
        this.thirdEuphoria = euphoriaBuilder.thirdEuphoria;
        this.fourthEuphoria = euphoriaBuilder.fourthEuphoria;
    }
    public static class EuphoriaBuilder{
        private String euphoriaName;
        private Attributes euphoriaAttributesAddition;
        private String firstEuphoria;
        private String secondEuphoria;
        private String thirdEuphoria;
        private String fourthEuphoria;
        public EuphoriaBuilder(String euphoriaName){
            validate(euphoriaName,"名称");
            this.euphoriaName = euphoriaName;
            euphoriaAttributesAddition = new Attributes(0,0,0,0,0);
            firstEuphoria = "";
            secondEuphoria = "";
            thirdEuphoria = "";
            fourthEuphoria = "";
        }
        public EuphoriaBuilder euphoriaAttributesAddition(Attributes attributes){
            if (attributes == null){
                throw new IllegalArgumentException("狂想属性提升未知");
            }
            this.euphoriaAttributesAddition = attributes;
            return this;
        }
        public EuphoriaBuilder firstEuphoria(String firstEuphoria) {
            validate(firstEuphoria,"一阶");
            this.firstEuphoria = firstEuphoria;
            return this;
        }
        public EuphoriaBuilder secondEuphoria(String secondEuphoria) {
            validate(secondEuphoria, "二阶");
            this.secondEuphoria = secondEuphoria;
            return this;
        }

        public EuphoriaBuilder thirdEuphoria(String thirdEuphoria) {
            validate(thirdEuphoria, "三阶");
            this.thirdEuphoria = thirdEuphoria;
            return this;
        }

        public EuphoriaBuilder fourthEuphoria(String fourthEuphoria) {
            validate(fourthEuphoria, "四阶");
            this.fourthEuphoria = fourthEuphoria;
            return this;
        }
        public Euphoria build(){
            return new Euphoria(this);
        }
        private void validate(String s,String name){
            if (s == null || s.trim().isEmpty()){
                throw new IllegalArgumentException("狂想" + name + "为空");
            }
        }
    }

    public String getEuphoriaName() {
        return euphoriaName;
    }

    public Attributes getEuphoriaAttributesAddition() {
        return euphoriaAttributesAddition;
    }

    public String getFirstEuphoria() {
        return firstEuphoria;
    }

    public String getSecondEuphoria() {
        return secondEuphoria;
    }

    public String getThirdEuphoria() {
        return thirdEuphoria;
    }

    public String getFourthEuphoria() {
        return fourthEuphoria;
    }
}
