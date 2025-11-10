package com.ZUNr1.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ZUNr1.model.Characters;
import com.ZUNr1.model.Skills;
import com.ZUNr1.model.Attributes;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;

public class CharacterManage {
    //处理数据存放数据的类
    private final Map<String,Characters> charactersMap;
    //这个Map是实现id与角色对应
    private final List<Characters> charactersList;
    //这个是方便实现遍历，所有需要遍历读取的都用这个list
    private final Map<Gender,List<Characters>> genderIndex;
    private final Map<Afflatus,List<Characters>> afflatusIndex;
    private final Map<DamageType,List<Characters>> damageTypeIndex;
    private final Map<Integer,List<Characters>> rarityIndex;
    //这四个是实现对应类型的查询，注意键值对的值是list，放的是对应类型的所有角色
    private final Map<String,List<Characters>> termIndex;
    public CharacterManage(){
        //要初始化，new初始化就是让其是空集合而不是空指针
        this.charactersMap = new HashMap<>();
        this.charactersList = new ArrayList<>();
        this.afflatusIndex = new HashMap<>();
        this.damageTypeIndex = new HashMap<>();
        this.rarityIndex = new HashMap<>();
        this.genderIndex = new HashMap<>();
        this.termIndex = new HashMap<>();
    }
    public void addCharacters(Characters  characters){
        if (characters == null) {
            //先验证角色的有效性
            throw new IllegalArgumentException("角色不能为null");
        }
        charactersMap.put(characters.getId(), characters);
        charactersList.add(characters);
        addToIndex(afflatusIndex,characters.getAfflatus(),characters);
        addToIndex(damageTypeIndex,characters.getDamageType(),characters);
        addToIndex(rarityIndex,characters.getRarity(),characters);
        addToIndex(genderIndex,characters.getGender(),characters);
        List<String> usedTerm = characters.getUsedTerm();
        //同样是防御性，这里要检查空
        if (usedTerm != null){
            for (String term : usedTerm){
                if (term != null && !term.trim().isEmpty()){
                    addToIndex(termIndex,term.trim(),characters);
                }
            }
        }

        System.out.println("已添加"+ characters.getName() + "角色");

    }
    private <K> void addToIndex(Map<K,List<Characters>> index,K key,Characters characters){
        //首先，这是个泛型，一次性供多个使用
        if (key != null){//一定要记得先检查传入的是不是空的指针
            if (!index.containsKey(key)){
                index.put(key,new ArrayList<>());
                //这是检查，如果不存在就新开一个集合
            }
            List<Characters> list = index.get(key);
            //创建list接收从Map中get到的集合，因为前面不存在就会创建，所有避免返回空指针
            list.add(characters);
            //直接修改
        }
    }
    public boolean removeCharacters(String id){
        //使用Boolean看成不成功
        Characters characters = charactersMap.remove(id);
        if (characters == null){
            System.out.println("未找到对应角色");
            return false;
        }
        charactersList.remove(characters);
        removeToIndex(afflatusIndex,characters.getAfflatus(),characters);
        removeToIndex(damageTypeIndex,characters.getDamageType(),characters);
        removeToIndex(rarityIndex,characters.getRarity(),characters);
        removeToIndex(genderIndex,characters.getGender(),characters);
        List<String> usedTerm = characters.getUsedTerm();
        if (usedTerm != null){
            for (String term : usedTerm){
                if (term != null && !term.trim().isEmpty()){
                    removeToIndex(termIndex,term.trim(),characters);
                }
            }
        }
        System.out.println("已删除角色: " + characters.getName());
        return true;

    }
    private <K> void removeToIndex(Map<K,List<Characters>> index,K key,Characters characters){
        if (key != null && index.containsKey(key)){
            List<Characters> list = index.get(key);
            list.remove(characters);
            if (list.isEmpty()){
                index.remove(key);
                //list如果是空的，就删了，节省空间
            }
        }
    }

    public void clearAll(){
        //清空所有内容
        charactersMap.clear();
        charactersList.clear();
        afflatusIndex.clear();
        damageTypeIndex.clear();
        rarityIndex.clear();
        genderIndex.clear();
        termIndex.clear();
    }


    public Characters findById(String id){
        return charactersMap.get(id);
    }
    public List<Characters> findByName(String name){
        //这里要实现模糊搜素，找到符合的所有掘金
        if (name == null || name.trim().isEmpty()){
            //检查传入值，对于String，一般看是不是空指针或者去掉空格还是空的
            return new ArrayList<>();//return的也不能是null啊
        }
        List<Characters> list = new ArrayList<>();
        String newName = name.toLowerCase().trim();
        //把名字变成小写的，不看大小写
        for (Characters characters : charactersList){
            String newCharactersName = characters.getName().toLowerCase().trim();
            //统一变小写去空格
            if (newCharactersName.contains(newName)){
                //只要contains就行
                list.add(characters);
            }
        }
        return list;
    }
    public List<Characters> findByAfflatus(Afflatus afflatus){
        return afflatusIndex.getOrDefault(afflatus,new ArrayList<>());
        //我们要想，要是找不到就抛出异常是不好的，对用户体验不好，不如返回空List
    }
    public List<Characters> findByDamageType(DamageType damageType){
        return damageTypeIndex.getOrDefault(damageType,new ArrayList<>());
    }
    public List<Characters> findByGender(Gender gender){
        return genderIndex.getOrDefault(gender,new ArrayList<>());
    }
    public List<Characters> findByRarity(int rarity){
        return rarityIndex.getOrDefault(rarity,new ArrayList<>());
    }
    public List<Characters> findByTerm(String term) {
        if (term == null || term.trim().isEmpty()) {
            return new ArrayList<>();
            //同样，这样处理，不要抛出异常，不要对用户太严格
            //保持设计一致性很重要，建议所有查询方法都采用"宽容"的设计哲学！
        }
        return termIndex.getOrDefault(term.trim(), new ArrayList<>());
    }
    public List<Characters> findAllCharacters(){
        return new ArrayList<>(charactersList);
    }
    public int getTotalNumber(){
        return charactersList.size();
    }
    public int getRarityNumber(int rarity){
        if (rarity < 2 || rarity > 6){
            throw new IllegalArgumentException("星级必须在2到6之间");
        }
        return rarityIndex.getOrDefault(rarity,new ArrayList<>()).size();
    }
    public int getAfflatusNumber(Afflatus afflatus){
        return afflatusIndex.getOrDefault(afflatus,new ArrayList<>()).size();
    }
    public int getGenderNumber(Gender gender){
        return genderIndex.getOrDefault(gender,new ArrayList<>()).size();
    }
    public int getDamageTypeNumber(DamageType damageType){
        return damageTypeIndex.getOrDefault(damageType,new ArrayList<>()).size();
    }
    public int getTermCharactersNumber(String term){
        if (term == null || term.trim().isEmpty()) {
            return 0;
        }
        return termIndex.getOrDefault(term.trim(), new ArrayList<>()).size();
    }
    public Map<Integer,Integer> getRarityStatistics(){
        //根据稀有度分类，返回所有稀有度对应的角色数量
        Map<Integer,Integer> map = new HashMap<>();
        for (Characters characters : charactersList){
            int rarity = characters.getRarity();
            map.put(rarity,map.getOrDefault(rarity,0) + 1);
            //getOrDefault方法，后面两个值，表示使用左边的参数，如果空就返回后面的参数，如果不空就正常get
            //这里就是实现了从0开始数数
        }
        return map;
    }
    public Map<Afflatus,Integer> getAfflatusStatistics(){
        Map<Afflatus,Integer> map = new HashMap<>();
        for (Characters characters : charactersList){
            Afflatus afflatus = characters.getAfflatus();
            map.put(afflatus,map.getOrDefault(afflatus,0) + 1);
        }
        return map;
    }
    public Map<DamageType,Integer> getDamageTypeStatistics(){
        Map<DamageType,Integer> map = new HashMap<>();
        for (Characters characters : charactersList){
            DamageType damageType = characters.getDamageType();
            map.put(damageType,map.getOrDefault(damageType,0) + 1);
        }
        return map;
    }
    public Map<Gender,Integer> getGenderStatistics(){
        Map<Gender,Integer> map = new HashMap<>();
        for (Characters characters : charactersList){
            Gender gender = characters.getGender();
            map.put(gender,map.getOrDefault(gender,0) + 1);
        }
        return map;
    }


}
