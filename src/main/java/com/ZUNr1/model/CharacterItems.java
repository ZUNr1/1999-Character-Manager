package com.ZUNr1.model;

public class CharacterItems {
    private String dressName;
    private String firstItemName;
    private String secondItemName;
    private String thirdItemName;
    private String firstItemDescribe;
    private String secondItemDescribe;
    private String thirdItemDescribe;
    private String firstItemPrice;
    private String secondItemPrice;
    private String thirdItemPrice;

    public CharacterItems(){}
    // 私有构造器
    private CharacterItems(CharacterItemsBuilder builder) {
        this.dressName = builder.dressName;
        this.firstItemName = builder.firstItemName;
        this.secondItemName = builder.secondItemName;
        this.thirdItemName = builder.thirdItemName;
        this.firstItemDescribe = builder.firstItemDescribe;
        this.secondItemDescribe = builder.secondItemDescribe;
        this.thirdItemDescribe = builder.thirdItemDescribe;
        this.firstItemPrice = builder.firstItemPrice;
        this.secondItemPrice = builder.secondItemPrice;
        this.thirdItemPrice = builder.thirdItemPrice;
    }

    public String getDressName() {
        return dressName;
    }

    public String getFirstItemName() {
        return firstItemName;
    }

    public String getSecondItemName() {
        return secondItemName;
    }

    public String getThirdItemName() {
        return thirdItemName;
    }

    public String getFirstItemDescribe() {
        return firstItemDescribe;
    }

    public String getSecondItemDescribe() {
        return secondItemDescribe;
    }

    public String getThirdItemDescribe() {
        return thirdItemDescribe;
    }

    public String getFirstItemPrice() {
        return firstItemPrice;
    }

    public String getSecondItemPrice() {
        return secondItemPrice;
    }

    public String getThirdItemPrice() {
        return thirdItemPrice;
    }

    public static class CharacterItemsBuilder {
        private String dressName;
        private String firstItemName;
        private String secondItemName;
        private String thirdItemName;
        private String firstItemDescribe;
        private String secondItemDescribe;
        private String thirdItemDescribe;
        private String firstItemPrice;
        private String secondItemPrice;
        private String thirdItemPrice;

        public CharacterItemsBuilder(String dressName,String firstItemName, String secondItemName, String thirdItemName) {
            validate(dressName,"所属装扮名称");
            validate(firstItemName, "第一个单品名称");
            validate(secondItemName, "第二个单品名称");
            validate(thirdItemName, "第三个单品名称");
            this.dressName = dressName;
            this.firstItemName = firstItemName;
            this.secondItemName = secondItemName;
            this.thirdItemName = thirdItemName;

            this.firstItemDescribe = "";
            this.secondItemDescribe = "";
            this.thirdItemDescribe = "";
            this.firstItemPrice = "";
            this.secondItemPrice = "";
            this.thirdItemPrice = "";
        }

        // 单品描述设置方法
        public CharacterItemsBuilder firstItemDescribe(String describe) {
            validate(describe, "第一个单品描述");
            this.firstItemDescribe = describe;
            return this;
        }

        public CharacterItemsBuilder secondItemDescribe(String describe) {
            validate(describe, "第二个单品描述");
            this.secondItemDescribe = describe;
            return this;
        }

        public CharacterItemsBuilder thirdItemDescribe(String describe) {
            validate(describe, "第三个单品描述");
            this.thirdItemDescribe = describe;
            return this;
        }

        // 单品价格设置方法
        public CharacterItemsBuilder firstItemPrice(String price) {
            validate(price, "第一个单品价格");
            this.firstItemPrice = price;
            return this;
        }

        public CharacterItemsBuilder secondItemPrice(String price) {
            validate(price, "第二个单品价格");
            this.secondItemPrice = price;
            return this;
        }

        public CharacterItemsBuilder thirdItemPrice(String price) {
            validate(price, "第三个单品价格");
            this.thirdItemPrice = price;
            return this;
        }

        public CharacterItemsBuilder allItemsPrice(String firstPrice, String secondPrice, String thirdPrice) {
            return firstItemPrice(firstPrice)
                    .secondItemPrice(secondPrice)
                    .thirdItemPrice(thirdPrice);
        }

        public CharacterItems build() {
            validate(dressName,"所属装扮名称");
            validate(firstItemDescribe, "第一个单品描述");
            validate(secondItemDescribe, "第二个单品描述");
            validate(thirdItemDescribe, "第三个单品描述");
            validate(firstItemPrice, "第一个单品价格");
            validate(secondItemPrice, "第二个单品价格");
            validate(thirdItemPrice, "第三个单品价格");

            return new CharacterItems(this);
        }

        private void validate(String s, String fieldName) {
            if (s == null || s.trim().isEmpty()) {
                throw new IllegalArgumentException(fieldName + "不能为空");
            }
        }
    }
}
