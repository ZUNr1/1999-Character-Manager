package com.ZUNr1.model;
//todo后续引入spring boot后要将其变成完全不可变，去掉无参构造器，使用jackson的注解等各种方法
public final class Inheritance {
    private String inheritanceName;
    private String basicInheritance;
    private String firstInheritance;
    private String secondInheritance;
    private String thirdInheritance;
    public Inheritance(){}
    private Inheritance(InheritanceBuilder inheritanceBuilder) {
        this.inheritanceName = inheritanceBuilder.inheritanceName;
        this.basicInheritance = inheritanceBuilder.basicInheritance;
        this.firstInheritance = inheritanceBuilder.firstInheritance;
        this.secondInheritance = inheritanceBuilder.secondInheritance;
        this.thirdInheritance = inheritanceBuilder.thirdInheritance;
    }

    public String getInheritanceName() {
        return inheritanceName;
    }

    public String getBasicInheritance() {
        return basicInheritance;
    }

    public String getFirstInheritance() {
        return firstInheritance;
    }

    public String getSecondInheritance() {
        return secondInheritance;
    }

    public String getThirdInheritance() {
        return thirdInheritance;
    }

    public static class InheritanceBuilder {
        private String inheritanceName;
        private String basicInheritance;
        private String firstInheritance;
        private String secondInheritance;
        private String thirdInheritance;

        public InheritanceBuilder(String inheritanceName) {
            validateMust(inheritanceName);
            this.inheritanceName = inheritanceName;
            basicInheritance = "";
            firstInheritance = "";
            secondInheritance = "";
            thirdInheritance = "";
        }

        public InheritanceBuilder basicInheritance(String basicInheritance) {
            if (!validate(basicInheritance)) {
                this.basicInheritance = "";
            } else {
                this.basicInheritance = basicInheritance;
            }
            return this;
        }

        public InheritanceBuilder firstInheritance(String firstInheritance) {
            if (!validate(firstInheritance)) {
                this.firstInheritance = "";
            } else {
                this.firstInheritance = firstInheritance;
            }
            return this;
        }

        public InheritanceBuilder secondInheritance(String secondInheritance) {
            if (!validate(secondInheritance)) {
                this.secondInheritance = "";
            } else {
                this.secondInheritance = secondInheritance;
            }
            return this;
        }

        public InheritanceBuilder thirdInheritance(String thirdInheritance) {
            if (!validate(thirdInheritance)) {
                this.thirdInheritance = "";
            } else {
                this.thirdInheritance = thirdInheritance;
            }
            return this;
        }

        public Inheritance build() {
            return new Inheritance(this);
        }

        private boolean validate(String s) {
            if (s == null || s.trim().isEmpty()) {
                return false;
            } else {
                return true;
            }
        }

        private void validateMust(String s) {
            if (s == null || s.trim().isEmpty()) {
                throw new IllegalArgumentException("传承名字有空值");
            }
        }
    }
}
