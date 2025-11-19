package com.ZUNr1.service;

import com.ZUNr1.enums.SkillType;
import com.ZUNr1.model.*;
import com.ZUNr1.ui.service.CharacterFormData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CharacterConverter {
    public static Characters convertToCharacter(CharacterFormData formData) {
        Characters.CharactersBuilder builder = new Characters.CharactersBuilder(
                formData.getId(),
                formData.getName(),
                formData.getEnName(),
                formData.getGender(),
                formData.isCustom(),
                formData.getCreator()
        );
        builder.afflatus(formData.getAfflatus())
                .damageType(formData.getDamageType())
                .rarity(formData.getRarity())
                .attributes(createAttributes(formData))
                .skills(createSkills(formData))
                .inheritance(createInheritance(formData))
                .portrait(createPortrait(formData))
                .usedTerm(createUsedTerm(formData))
                .euphoria(createEuphoria(formData))
                .otherInformation(createOtherInformation(formData));
        return builder.build();
    }
    private static Attributes createAttributes(CharacterFormData formData) {
        return new Attributes(
                formData.getHealth(),
                formData.getAttack(),
                formData.getRealityDefense(),
                formData.getMentalDefense(),
                formData.getTechnique()
        );
    }
    private static Inheritance createInheritance(CharacterFormData formData) {
        return new Inheritance.InheritanceBuilder(formData.getInheritanceName())
                .basicInheritance(formData.getBasicInheritance())
                .firstInheritance(formData.getFirstInheritance())
                .secondInheritance(formData.getSecondInheritance())
                .thirdInheritance(formData.getThirdInheritance())
                .build();
    }
    private static Portrait createPortrait(CharacterFormData formData) {
        return new Portrait.PortraitBuilder(formData.getPortraitDescribe())
                .firstPortrait(formData.getFirstPortrait())
                .secondPortrait(formData.getSecondPortrait())
                .thirdPortrait(formData.getThirdPortrait())
                .fourthPortrait(formData.getFourthPortrait())
                .fifthPortrait(formData.getFifthPortrait())
                .build();
    }
    private static List<Euphoria> createEuphoria(CharacterFormData formData) {
        List<Euphoria> euphoriaList = new ArrayList<>();

        if (formData.getEuphoriaNames() != null && formData.getEuphoriaDescribes() != null) {
            // 遍历所有狂想条目
            for (String euphoriaId : formData.getEuphoriaNames().keySet()) {
                String euphoriaName = formData.getEuphoriaNames().get(euphoriaId);

                if (euphoriaName != null && !euphoriaName.trim().isEmpty()) {
                    // 创建Euphoria对象
                    Euphoria euphoria = createSingleEuphoria(formData, euphoriaId, euphoriaName);
                    if (euphoria != null) {
                        euphoriaList.add(euphoria);
                    }
                }
            }
        }
        return euphoriaList;
    }
    private static Euphoria createSingleEuphoria(CharacterFormData formData, String euphoriaId, String euphoriaName) {
        try {
            Euphoria.EuphoriaBuilder builder = new Euphoria.EuphoriaBuilder(euphoriaName);

            // 设置属性加成
            Map<String, Integer> attributesMap = formData.getEuphoriaAttributes().get(euphoriaId);
            if (attributesMap != null) {
                Attributes attributes = new Attributes(
                        attributesMap.getOrDefault("health", 0),
                        attributesMap.getOrDefault("attack", 0),
                        attributesMap.getOrDefault("realityDefense", 0),
                        attributesMap.getOrDefault("mentalDefense", 0),
                        attributesMap.getOrDefault("technique", 0)
                );
                builder.euphoriaAttributesAddition(attributes);
            }

            // 设置各级狂想描述
            Map<String, String> describeMap = formData.getEuphoriaDescribes().get(euphoriaId);
            if (describeMap != null) {
                if (describeMap.containsKey("first") && !describeMap.get("first").trim().isEmpty()) {
                    builder.firstEuphoria(describeMap.get("first"));
                }
                if (describeMap.containsKey("second") && !describeMap.get("second").trim().isEmpty()) {
                    builder.secondEuphoria(describeMap.get("second"));
                }
                if (describeMap.containsKey("third") && !describeMap.get("third").trim().isEmpty()) {
                    builder.thirdEuphoria(describeMap.get("third"));
                }
                if (describeMap.containsKey("fourth") && !describeMap.get("fourth").trim().isEmpty()) {
                    builder.fourthEuphoria(describeMap.get("fourth"));
                }
            }

            return builder.build();

        } catch (Exception e) {
            System.err.println("创建狂想失败: " + euphoriaName + ", 错误: " + e.getMessage());
            return null;
        }
    }
    private static Skills createSkills(CharacterFormData formData) {
        Skills.SkillsBuilder builder = new Skills.SkillsBuilder();

        // 创建三个固定技能
        if (formData.getSkillNames() != null) {
            // 神秘术I
            if (formData.getSkillNames().containsKey("神秘术I")) {
                Skills.SkillDetail skill1 = createSkillDetail("神秘术I", formData);
                if (skill1 != null) {
                    builder.arcaneSkill1(skill1);
                }
            }

            // 神秘术II
            if (formData.getSkillNames().containsKey("神秘术II")) {
                Skills.SkillDetail skill2 = createSkillDetail("神秘术II", formData);
                if (skill2 != null) {
                    builder.arcaneSkill2(skill2);
                }
            }

            // 至终的仪式
            if (formData.getSkillNames().containsKey("至终的仪式")) {
                Skills.SkillDetail skill3 = createSkillDetail("至终的仪式", formData);
                if (skill3 != null) {
                    builder.arcaneSkill3(skill3);
                }
            }
        }
        if (formData.getSkillNames() != null) {
            for (Map.Entry<String, String> entry : formData.getSkillNames().entrySet()) {
                String skillKey = entry.getKey();
                if (skillKey.startsWith("额外神秘术_")) {
                    Skills.SkillDetail extraSkill = createSkillDetail(skillKey, formData);
                    if (extraSkill != null) {
                        builder.addExtraSkills(extraSkill);
                    }
                }
            }
        }
        return builder.build();
    }
    private static Skills.SkillDetail createSkillDetail(String skillKey, CharacterFormData formData) {
        try {
            String skillName = formData.getSkillNames().get(skillKey);
            if (skillName == null || skillName.trim().isEmpty()) {
                return null;
            }

            Skills.SkillDetail.SkillDetailBuilder skillBuilder =
                    new Skills.SkillDetail.SkillDetailBuilder(skillName);

            // 添加三个等级的技能
            Map<String, String> describeMap = formData.getSkillDescribes().get(skillKey);
            Map<String, String> storyMap = formData.getSkillStories().get(skillKey);
            Map<String, SkillType> typeMap = formData.getSkillTypes().get(skillKey);

            if (describeMap != null && describeMap.containsKey("一星牌") &&
                    storyMap != null && storyMap.containsKey("一星牌") &&
                    typeMap != null && typeMap.containsKey("一星牌")) {

                skillBuilder.addSkillInLevel(
                        1,
                        describeMap.get("一星牌"),
                        storyMap.get("一星牌"),
                        typeMap.get("一星牌")
                );
            }
            if (describeMap != null && describeMap.containsKey("二星牌") &&
                    storyMap != null && storyMap.containsKey("二星牌") &&
                    typeMap != null && typeMap.containsKey("二星牌")) {

                skillBuilder.addSkillInLevel(
                        2,
                        describeMap.get("二星牌"),
                        storyMap.get("二星牌"),
                        typeMap.get("二星牌")
                );
            }
            if (describeMap != null && describeMap.containsKey("三星牌") &&
                    storyMap != null && storyMap.containsKey("三星牌") &&
                    typeMap != null && typeMap.containsKey("三星牌")) {

                skillBuilder.addSkillInLevel(
                        3,
                        describeMap.get("三星牌"),
                        storyMap.get("三星牌"),
                        typeMap.get("三星牌")
                );
            }

            return skillBuilder.build();

        } catch (Exception e) {
            System.err.println("创建技能失败: " + skillKey + ", 错误: " + e.getMessage());
            return null;
        }
    }
    private static List<String> createUsedTerm(CharacterFormData formData) {
        List<String> usedTermList = new ArrayList<>();

        if (formData.getUsedTerms() != null && !formData.getUsedTerms().isEmpty()) {
            for (Map.Entry<String, String> entry : formData.getUsedTerms().entrySet()) {
                String termName = entry.getKey();
                String termDescription = entry.getValue();

                // 只有当术语名称不为空时才添加到列表
                if (termName != null && !termName.trim().isEmpty()) {
                    usedTermList.add(termName.trim());

                    // 这里可以同时将术语添加到全局词典中
                    // 但Characters类只存储名称，不存储描述
                    /*todo 完成全局术语词典，在这里录入术语到全局*/
                }
            }
        }

        return usedTermList;
    }
    private static OtherInformation createOtherInformation(CharacterFormData formData) {
        OtherInformation.OtherInformationBuilder builder = new OtherInformation.OtherInformationBuilder();

        // 1. 封面信息处理
        CharacterCoverInformation coverInfo = createCharacterCoverInformation(formData);
        if (coverInfo != null) {
            builder.characterCoverInformation(coverInfo);
        }

        // 2. 服装名称处理
        if (formData.getDressNames() != null && !formData.getDressNames().isEmpty()) {
            List<String> dressNameList = new ArrayList<>(formData.getDressNames().values());
            builder.dressName(dressNameList);
        }

        // 3. 角色单品处理
        List<CharacterItems> characterItems = createCharacterItems(formData);
        if (!characterItems.isEmpty()) {
            builder.characterItems(characterItems);
        }

        // 4. 角色故事处理
        CharacterStory characterStory = createCharacterStory(formData);
        if (characterStory != null) {
            builder.characterStory(characterStory);
        }

        return builder.build();
    }

    private static CharacterCoverInformation createCharacterCoverInformation(CharacterFormData formData) {
        try {
            CharacterCoverInformation.CharacterCoverInformationBuilder builder =
                    new CharacterCoverInformation.CharacterCoverInformationBuilder();

            if (formData.getIntroduction() != null && !formData.getIntroduction().trim().isEmpty()) {
                builder.characterIntroduction(formData.getIntroduction());
            }
            if (formData.getSize() != null && !formData.getSize().trim().isEmpty()) {
                builder.characterSize(formData.getSize());
            }
            if (formData.getFragrance() != null && !formData.getFragrance().trim().isEmpty()) {
                builder.characterFragrance(formData.getFragrance());
            }
            if (formData.getDetailedAfflatus() != null && !formData.getDetailedAfflatus().trim().isEmpty()) {
                builder.characterDetailedAfflatus(formData.getDetailedAfflatus());
            }

            return builder.build();

        } catch (Exception e) {
            System.err.println("创建封面信息失败: " + e.getMessage());
            return null;
        }
    }

    private static List<CharacterItems> createCharacterItems(CharacterFormData formData) {
        List<CharacterItems> itemsList = new ArrayList<>();

        if (formData.getCharacterItems() != null) {
            for (Map.Entry<String, Map<String, String>> entry : formData.getCharacterItems().entrySet()) {
                String itemId = entry.getKey();
                Map<String, String> itemData = entry.getValue();

                try {
                    CharacterItems item = createSingleCharacterItem(itemData);
                    if (item != null) {
                        itemsList.add(item);
                    }
                } catch (Exception e) {
                    System.err.println("创建角色单品失败 " + itemId + ": " + e.getMessage());
                }
            }
        }

        return itemsList;
    }

    private static CharacterItems createSingleCharacterItem(Map<String, String> itemData) {
        // 提取必要字段
        String dressName = itemData.get("dressNameArea");
        String firstItemName = itemData.get("firstItemNameArea");
        String secondItemName = itemData.get("secondItemNameArea");
        String thirdItemName = itemData.get("thirdItemNameArea");

        // 验证必要字段
        if (dressName == null || firstItemName == null || secondItemName == null || thirdItemName == null) {
            throw new IllegalArgumentException("单品信息不完整");
        }

        CharacterItems.CharacterItemsBuilder builder = new CharacterItems.CharacterItemsBuilder(
                dressName, firstItemName, secondItemName, thirdItemName
        );

        // 设置描述
        if (itemData.containsKey("firstItemDescribeArea")) {
            builder.firstItemDescribe(itemData.get("firstItemDescribeArea"));
        }
        if (itemData.containsKey("secondItemDescribeArea")) {
            builder.secondItemDescribe(itemData.get("secondItemDescribeArea"));
        }
        if (itemData.containsKey("thirdItemDescribeArea")) {
            builder.thirdItemDescribe(itemData.get("thirdItemDescribeArea"));
        }

        // 设置价格
        if (itemData.containsKey("firstItemPriceArea")) {
            builder.firstItemPrice(itemData.get("firstItemPriceArea"));
        }
        if (itemData.containsKey("secondItemPriceArea")) {
            builder.secondItemPrice(itemData.get("secondItemPriceArea"));
        }
        if (itemData.containsKey("thirdItemPriceArea")) {
            builder.thirdItemPrice(itemData.get("thirdItemPriceArea"));
        }

        return builder.build();
    }

    private static CharacterStory createCharacterStory(CharacterFormData formData) {
        try {
            // 从FormData中提取故事名称
            String firstStoryName = formData.getStoryNames() != null ?
                    formData.getStoryNames().get("first") : "";
            String secondStoryName = formData.getStoryNames() != null ?
                    formData.getStoryNames().get("second") : "";
            String thirdStoryName = formData.getStoryNames() != null ?
                    formData.getStoryNames().get("third") : "";

            // 验证必要字段
            if (firstStoryName == null || firstStoryName.trim().isEmpty() ||
                    secondStoryName == null || secondStoryName.trim().isEmpty() ||
                    thirdStoryName == null || thirdStoryName.trim().isEmpty()) {
                return null;
            }

            CharacterStory.CharacterStoryBuilder builder = new CharacterStory.CharacterStoryBuilder(
                    firstStoryName, secondStoryName, thirdStoryName
            );

            // 设置故事描述
            if (formData.getStoryDescribes() != null) {
                if (formData.getStoryDescribes().containsKey("first")) {
                    builder.firstStoryDescribe(formData.getStoryDescribes().get("first"));
                }
                if (formData.getStoryDescribes().containsKey("second")) {
                    builder.secondStoryDescribe(formData.getStoryDescribes().get("second"));
                }
                if (formData.getStoryDescribes().containsKey("third")) {
                    builder.thirdStoryDescribe(formData.getStoryDescribes().get("third"));
                }
            }

            return builder.build();

        } catch (Exception e) {
            System.err.println("创建角色故事失败: " + e.getMessage());
            return null;
        }
    }
}