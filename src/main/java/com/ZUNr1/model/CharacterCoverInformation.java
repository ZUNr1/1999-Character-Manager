package com.ZUNr1.model;

public class CharacterCoverInformation {
    private String characterIntroduction;
    private String characterSize;
    private String characterFragrance;
    private String characterDetailedAfflatus;

    public CharacterCoverInformation(){}
    private CharacterCoverInformation(CharacterCoverInformationBuilder builder) {
        this.characterIntroduction = builder.characterIntroduction;
        this.characterSize = builder.characterSize;
        this.characterFragrance = builder.characterFragrance;
        this.characterDetailedAfflatus = builder.characterDetailedAfflatus;
    }

    public String getCharacterIntroduction() {
        return characterIntroduction;
    }
    public String getCharacterSize() {
        return characterSize;
    }
    public String getCharacterFragrance() {
        return characterFragrance;
    }
    public String getCharacterDetailedAfflatus() {
        return characterDetailedAfflatus;
    }

    public static class CharacterCoverInformationBuilder {
        private String characterIntroduction;
        private String characterSize;
        private String characterFragrance;
        private String characterDetailedAfflatus;

        public CharacterCoverInformationBuilder() {
            this.characterIntroduction = "";
            this.characterSize = "";
            this.characterFragrance = "";
            this.characterDetailedAfflatus = "";
        }

        public CharacterCoverInformationBuilder characterIntroduction(String introduction) {
            validate(introduction, "角色介绍");
            this.characterIntroduction = introduction;
            return this;
        }

        public CharacterCoverInformationBuilder characterSize(String size) {
            validate(size, "角色尺寸");
            this.characterSize = size;
            return this;
        }

        public CharacterCoverInformationBuilder characterFragrance(String fragrance) {
            validate(fragrance, "角色香调");
            this.characterFragrance = fragrance;
            return this;
        }

        public CharacterCoverInformationBuilder characterDetailedAfflatus(String detailedAfflatus) {
            validate(detailedAfflatus, "详细灵感");
            this.characterDetailedAfflatus = detailedAfflatus;
            return this;
        }

        public CharacterCoverInformation build() {
            return new CharacterCoverInformation(this);
        }

        private void validate(String s, String fieldName) {
            if (s == null || s.trim().isEmpty()) {
                throw new IllegalArgumentException(fieldName + "不能为空");
            }
        }
    }

}