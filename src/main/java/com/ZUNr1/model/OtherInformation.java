package com.ZUNr1.model;

import java.util.ArrayList;
import java.util.List;

public class OtherInformation {
    private CharacterCoverInformation characterCoverInformation;
    private List<String> dressName;
    private List<CharacterItems> characterItems;
    private CharacterStory characterStory;

    public OtherInformation(){}
    private OtherInformation(OtherInformationBuilder builder) {
        this.characterCoverInformation = builder.characterCoverInformation;
        this.dressName = new ArrayList<>(builder.dressName); // 防御性拷贝
        this.characterItems = new ArrayList<>(builder.characterItems); // 防御性拷贝
        this.characterStory = builder.characterStory;
    }

    public CharacterCoverInformation getCharacterCoverInformation() {
        return characterCoverInformation;
    }

    public List<String> getDressName() {
        return new ArrayList<>(dressName); // 防御性拷贝
    }

    public List<CharacterItems> getCharacterItems() {
        return new ArrayList<>(characterItems); // 防御性拷贝
    }

    public CharacterStory getCharacterStory() {
        return characterStory;
    }

    public static class OtherInformationBuilder {
        private CharacterCoverInformation characterCoverInformation;
        private List<String> dressName;
        private List<CharacterItems> characterItems;
        private CharacterStory characterStory;

        public OtherInformationBuilder() {
            this.characterCoverInformation = null;
            this.dressName = new ArrayList<>();
            this.characterItems = new ArrayList<>();
            this.characterStory = null;
        }

        public OtherInformationBuilder characterCoverInformation(CharacterCoverInformation coverInfo) {
            this.characterCoverInformation = coverInfo;
            return this;
        }
        public OtherInformationBuilder dressName(List<String> dressName) {
            if (dressName != null) {
                this.dressName = new ArrayList<>(dressName); // 防御性拷贝
            }
            return this;
        }
        public OtherInformationBuilder addDressName(String dress) {
            if (dress != null && !dress.trim().isEmpty()) {
                this.dressName.add(dress.trim());
            }
            return this;
        }

        public OtherInformationBuilder characterItems(List<CharacterItems> items) {
            if (items != null) {
                this.characterItems = new ArrayList<>(items); // 防御性拷贝
            }
            return this;
        }

        public OtherInformationBuilder addCharacterItem(CharacterItems item) {
            if (item != null) {
                this.characterItems.add(item);
            }
            return this;
        }

        public OtherInformationBuilder characterStory(CharacterStory story) {
            this.characterStory = story;
            return this;
        }

        public OtherInformation build() {
            return new OtherInformation(this);
        }
    }

    // 实用方法
    public boolean hasOtherInformation() {
        return characterCoverInformation != null ||
                !dressName.isEmpty() ||
                !characterItems.isEmpty() ||
                characterStory != null;
    }

    public boolean hasDressNames() {
        return !dressName.isEmpty();
    }

    public boolean hasCharacterItems() {
        return !characterItems.isEmpty();
    }

    public boolean hasCoverInformation() {
        return characterCoverInformation != null;
    }

    public boolean hasCharacterStory() {
        return characterStory != null;
    }

}