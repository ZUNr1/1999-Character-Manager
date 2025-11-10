package com.ZUNr1.dao;

import com.ZUNr1.manager.CharacterManage;
import com.ZUNr1.model.Characters;
import com.ZUNr1.util.JsonDataLoader;
import com.ZUNr1.util.JsonDataPersister;

import java.util.ArrayList;
import java.util.List;

public class CharactersDao {
    //DAO层是专门负责所有数据与文件交换的层，我们实现json与java互换就在这里实现
    //我们先高搞从json加载，我们想，要加载进Manage，是不是先对Manage原有的数据清除，不然冲突了
    //是不是都要clearAll？不知道，但是做两手准备比较好
    public void loadToManageOverride(CharacterManage manage){
        //这方法实现完全覆盖
        loadToManage(manage,true);
    }
    public void loadToManageIncremental(CharacterManage manage){
        //这方法实现部分添加，不用每次都清空再从头添加，浪费性能
        loadToManage(manage,false);
    }
    private void loadToManage(CharacterManage manage,boolean isClearAll) {
        //通过一个Boolean参数来判断要不要清空
        List<JsonDataLoader.CharactersJson> jsonList = JsonDataLoader.loadCharacters();
        //先创建list接收工具类读取到的角色的json格式的原始数据
        if (isClearAll){
            manage.clearAll();
        }
        int loadCount = 0;
        //添加加载角色数变量，告诉用户新加载了多少角色
        for (JsonDataLoader.CharactersJson json : jsonList){
            //遍历
            if (json == null || manage.findById(json.id) != null){
                continue;
                //如果有这个角色，就跳过
            }
            Characters character = convertFromJson(json);
            //这个方法把json格式的对象转为Characters角色
            if (character != null){
                manage.addCharacters(character);
                loadCount++;
            }
        }
        System.out.println("成功加载 " + loadCount + " 个角色");  // 输出结果
    }
    private Characters convertFromJson(JsonDataLoader.CharactersJson json){
        //把json格式的对象转为Characters角色
        Characters.CharactersBuilder charactersBuilder =
                new Characters.CharactersBuilder(json.id, json.name, json.gender, json.level);
        charactersBuilder.afflatus(json.afflatus).
                attributes(json.attributes).
                damageType(json.damageType).
                rarity(json.rarity).
                inheritance(json.inheritance).
                portrait(json.portrait).
                skills(json.skills).
                usedTerm(json.usedTerm).
                otherInformation(json.otherInformation);
        return charactersBuilder.build();
        //builder的好处就这样体现了

    }
    public void saveFromManage(CharacterManage manage){
        //实现保存，输入到json文件中
        List<Characters> charactersList = manage.findAllCharacters();
        //这里已经在Manage里面排除了null可能性，获得所有character对象的list
        List<JsonDataLoader.CharactersJson> jsonList = convertFromManage(charactersList);
        //转为json格式的对象
        JsonDataPersister.saveCharacters(jsonList);
        //使用工具类方法写入到文件
        System.out.println("成功保存" + jsonList.size() + "个角色到json文件");
    }
    private List<JsonDataLoader.CharactersJson> convertFromManage(List<Characters> charactersList){
        List<JsonDataLoader.CharactersJson> charactersJsonList = new ArrayList<>();
        for (Characters character : charactersList){
            JsonDataLoader.CharactersJson json = new JsonDataLoader.CharactersJson();
            json.id = character.getId();
            json.name = character.getName();
            json.gender = character.getGender();
            json.level = character.getLevel();
            json.afflatus = character.getAfflatus();
            json.attributes = character.getAttributes();
            json.damageType = character.getDamageType();
            json.inheritance = character.getInheritance();
            json.portrait = character.getPortrait();
            json.rarity = character.getRarity();
            json.skills = character.getSkills();
            json.usedTerm = character.getUsedTerm();
            json.otherInformation = character.getOtherInformation();
            charactersJsonList.add(json);
        }
        return charactersJsonList;
    }
}
