package com.ZUNr1.model;

public class CharacterStory {
    private String firstStoryName;
    private String secondStoryName;
    private String thirdStoryName;
    private String firstStoryDescribe;
    private String secondStoryDescribe;
    private String thirdStoryDescribe;
    public CharacterStory(){}

    private CharacterStory(CharacterStoryBuilder builder) {
        this.firstStoryName = builder.firstStoryName;
        this.secondStoryName = builder.secondStoryName;
        this.thirdStoryName = builder.thirdStoryName;
        this.firstStoryDescribe = builder.firstStoryDescribe;
        this.secondStoryDescribe = builder.secondStoryDescribe;
        this.thirdStoryDescribe = builder.thirdStoryDescribe;
    }

    public String getFirstStoryName() {
        return firstStoryName;
    }

    public String getSecondStoryName() {
        return secondStoryName;
    }

    public String getThirdStoryName() {
        return thirdStoryName;
    }

    public String getFirstStoryDescribe() {
        return firstStoryDescribe;
    }

    public String getSecondStoryDescribe() {
        return secondStoryDescribe;
    }

    public String getThirdStoryDescribe() {
        return thirdStoryDescribe;
    }

    public static class CharacterStoryBuilder {
        private String firstStoryName;
        private String secondStoryName;
        private String thirdStoryName;
        private String firstStoryDescribe;
        private String secondStoryDescribe;
        private String thirdStoryDescribe;

        public CharacterStoryBuilder(String firstStoryName, String secondStoryName, String thirdStoryName) {
            validate(firstStoryName, "第一个文化名称");
            validate(secondStoryName, "第二个文化名称");
            validate(thirdStoryName, "第三个文化名称");

            this.firstStoryName = firstStoryName;
            this.secondStoryName = secondStoryName;
            this.thirdStoryName = thirdStoryName;

            // 初始化默认值
            this.firstStoryDescribe = "";
            this.secondStoryDescribe = "";
            this.thirdStoryDescribe = "";
        }

        public CharacterStoryBuilder firstStoryDescribe(String describe) {
            validate(describe, "第一个文化描述");
            this.firstStoryDescribe = describe;
            return this;
        }

        public CharacterStoryBuilder secondStoryDescribe(String describe) {
            validate(describe, "第二个文化描述");
            this.secondStoryDescribe = describe;
            return this;
        }

        public CharacterStoryBuilder thirdStoryDescribe(String describe) {
            validate(describe, "第三个文化描述");
            this.thirdStoryDescribe = describe;
            return this;
        }

        public CharacterStory build() {
            validate(firstStoryDescribe, "第一个文化描述");
            validate(secondStoryDescribe, "第二个文化描述");
            validate(thirdStoryDescribe, "第三个文化描述");

            return new CharacterStory(this);
        }

        private void validate(String s, String fieldName) {
            if (s == null || s.trim().isEmpty()) {
                throw new IllegalArgumentException(fieldName + "不能为空");
            }
        }
    }
}