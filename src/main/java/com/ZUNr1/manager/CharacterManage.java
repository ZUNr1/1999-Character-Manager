package com.ZUNr1.manager;

import com.ZUNr1.enums.Afflatus;
import com.ZUNr1.enums.DamageType;
import com.ZUNr1.enums.Gender;
import com.ZUNr1.model.Characters;

import java.util.*;
import java.util.stream.Collectors;

public class CharacterManage {
    //处理数据存放数据的类
    private final Map<String, Characters> charactersMap;
    //这个Map是实现id与角色对应
    private final List<Characters> charactersList;
    //这个是方便实现遍历，所有需要遍历读取的都用这个list
    private final Map<Gender, List<Characters>> genderIndex;
    private final Map<Afflatus, List<Characters>> afflatusIndex;
    private final Map<DamageType, List<Characters>> damageTypeIndex;
    private final Map<Integer, List<Characters>> rarityIndex;
    //这四个是实现对应类型的查询，注意键值对的值是list，放的是对应类型的所有角色
    private final Map<String, List<Characters>> termIndex;

    public CharacterManage() {
        //要初始化，new初始化就是让其是空集合而不是空指针
        this.charactersMap = new HashMap<>();
        this.charactersList = new ArrayList<>();
        this.afflatusIndex = new HashMap<>();
        this.damageTypeIndex = new HashMap<>();
        this.rarityIndex = new HashMap<>();
        this.genderIndex = new HashMap<>();
        this.termIndex = new HashMap<>();
    }

    public void addCharacters(Characters characters) {
        if (characters == null) {
            //先验证角色的有效性
            throw new IllegalArgumentException("角色不能为null");
        }
        charactersMap.put(characters.getId(), characters);
        charactersList.add(characters);
        addToIndex(afflatusIndex, characters.getAfflatus(), characters);
        addToIndex(damageTypeIndex, characters.getDamageType(), characters);
        addToIndex(rarityIndex, characters.getRarity(), characters);
        addToIndex(genderIndex, characters.getGender(), characters);
        List<String> usedTerm = characters.getUsedTerm();
        //同样是防御性，这里要检查空
        if (usedTerm != null) {
            for (String term : usedTerm) {
                if (term != null && !term.trim().isEmpty()) {
                    addToIndex(termIndex, term.trim(), characters);
                }
            }
        }

        System.out.println("已添加" + characters.getName() + "角色");

    }

    private <K> void addToIndex(Map<K, List<Characters>> index, K key, Characters characters) {
        //首先，这是个泛型，一次性供多个使用
        if (key != null) {//一定要记得先检查传入的是不是空的指针
            if (!index.containsKey(key)) {
                index.put(key, new ArrayList<>());
                //这是检查，如果不存在就新开一个集合
            }
            List<Characters> list = index.get(key);
            //创建list接收从Map中get到的集合，因为前面不存在就会创建，所有避免返回空指针
            list.add(characters);
            //直接修改
        }
    }

    public boolean removeCharacters(String id) {
        //使用Boolean看成不成功
        Characters characters = charactersMap.remove(id);
        if (characters == null) {
            System.out.println("未找到对应角色");
            return false;
        }
        charactersList.remove(characters);
        removeToIndex(afflatusIndex, characters.getAfflatus(), characters);
        removeToIndex(damageTypeIndex, characters.getDamageType(), characters);
        removeToIndex(rarityIndex, characters.getRarity(), characters);
        removeToIndex(genderIndex, characters.getGender(), characters);
        List<String> usedTerm = characters.getUsedTerm();
        if (usedTerm != null) {
            for (String term : usedTerm) {
                if (term != null && !term.trim().isEmpty()) {
                    removeToIndex(termIndex, term.trim(), characters);
                }
            }
        }
        System.out.println("已删除角色: " + characters.getName());
        return true;

    }

    private <K> void removeToIndex(Map<K, List<Characters>> index, K key, Characters characters) {
        if (key != null && index.containsKey(key)) {
            List<Characters> list = index.get(key);
            list.remove(characters);
            if (list.isEmpty()) {
                index.remove(key);
                //list如果是空的，就删了，节省空间
            }
        }
    }
    public boolean updateCharacters(String id, Characters updatedCharacter) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("角色ID不能为空");
        }
        if (updatedCharacter == null) {
            throw new IllegalArgumentException("更新角色信息不能为null");
        }
        // 检查ID是否存在于系统中
        Characters existingCharacter = charactersMap.get(id);
        if (existingCharacter == null) {
            System.out.println("未找到ID为 " + id + " 的角色");
            return false;
        }
        // 检查新ID是否与现有ID冲突（如果ID被修改）
        String newId = updatedCharacter.getId();
        if (!id.equals(newId) && charactersMap.containsKey(newId)) {
            System.out.println("ID " + newId + " 已存在，无法更新");
            return false;
        }
        // 先移除旧的角色信息
        removeCharacters(id);
        // 添加更新后的角色信息
        addCharacters(updatedCharacter);
        System.out.println("已成功更新角色: " + updatedCharacter.getName());
        return true;
    }

    public void clearAll() {
        //清空所有内容
        charactersMap.clear();
        charactersList.clear();
        afflatusIndex.clear();
        damageTypeIndex.clear();
        rarityIndex.clear();
        genderIndex.clear();
        termIndex.clear();
    }


    public Characters findById(String id) {
        return charactersMap.get(id);
    }

    public List<Characters> findByName(String name) {
        if (name == null || name.trim().isEmpty()){
            return new ArrayList<>();
        }
        String newName = name.trim().toLowerCase();
        List<Characters> list = charactersList.stream()
                .filter(character -> character.getName().trim().toLowerCase().contains(newName))
                .toList();
        return list;
    }
    public List<Characters> findByEnName(String enName){
        if (enName == null || enName.trim().isEmpty()){
            return new ArrayList<>();
        }
        String newName = enName.trim().toLowerCase();
        List<Characters> list = charactersList.stream()
                .filter(character -> character.getEnName().trim().toLowerCase().contains(newName))
                .toList();
        //toList是java16新加的方法，类似于获取不可变对象.collect(Collectors.toUnmodifiableList());，更加简洁，注意不能修改
        return list;
    }
    public Characters findByNameExact(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String searchName = name.trim().toLowerCase();
        Optional<Characters> optional = charactersList.stream()
                .filter(character ->
                        character.getName().trim().toLowerCase().equals(searchName))
                .findFirst();
        return optional.orElse(null);
        //orElse相当于optional.isPresent() ? optional.get() : null;
        // 就是先看optional的存不存在，不存在就返回指定默认值(类型还是相同的)
    }

    public Characters findByEnNameExact(String enName) {
        //用于输入的适合检查角色名是否存在
        if (enName == null || enName.trim().isEmpty()) {
            return null;
        }
        String searchEnName = enName.trim().toLowerCase();
        Optional<Characters> optional = charactersList.stream()
                .filter(character ->
                        character.getEnName().trim().toLowerCase().equals(searchEnName))
                .findFirst();
        return optional.orElse(null);

    }

    public List<Characters> findByAfflatus(Afflatus afflatus) {
        return afflatusIndex.getOrDefault(afflatus, new ArrayList<>());
        //我们要想，要是找不到就抛出异常是不好的，对用户体验不好，不如返回空List
    }

    public List<Characters> findByDamageType(DamageType damageType) {
        return damageTypeIndex.getOrDefault(damageType, new ArrayList<>());
    }

    public List<Characters> findByGender(Gender gender) {
        return genderIndex.getOrDefault(gender, new ArrayList<>());
    }

    public List<Characters> findByRarity(int rarity) {
        return rarityIndex.getOrDefault(rarity, new ArrayList<>());
    }

    public List<Characters> findByTerm(String term) {
        if (term == null || term.trim().isEmpty()) {
            return new ArrayList<>();
            //同样，这样处理，不要抛出异常，不要对用户太严格
            //保持设计一致性很重要，建议所有查询方法都采用"宽容"的设计哲学！
        }
        return termIndex.getOrDefault(term.trim(), new ArrayList<>());
    }

    public List<Characters> findAllCharacters() {
        return new ArrayList<>(charactersList);
    }

    public int getTotalNumber() {
        return charactersList.size();
    }

    public int getRarityNumber(int rarity) {
        if (rarity < 2 || rarity > 6) {
            throw new IllegalArgumentException("星级必须在2到6之间");
        }
        return rarityIndex.getOrDefault(rarity, new ArrayList<>()).size();
    }

    public int getAfflatusNumber(Afflatus afflatus) {
        return afflatusIndex.getOrDefault(afflatus, new ArrayList<>()).size();
    }

    public int getGenderNumber(Gender gender) {
        return genderIndex.getOrDefault(gender, new ArrayList<>()).size();
    }

    public int getDamageTypeNumber(DamageType damageType) {
        return damageTypeIndex.getOrDefault(damageType, new ArrayList<>()).size();
    }

    public int getTermCharactersNumber(String term) {
        if (term == null || term.trim().isEmpty()) {
            return 0;
        }
        return termIndex.getOrDefault(term.trim(), new ArrayList<>()).size();
    }

    public Map<Integer, Integer> getRarityStatistics() {
        if (charactersList.size() < 1000){
            //小数据量用传统循环，大数据量用stream流
            //根据稀有度分类，返回所有稀有度对应的角色数量
            Map<Integer, Integer> map = new HashMap<>();
            for (Characters characters : charactersList) {
                int rarity = characters.getRarity();
                map.put(rarity, map.getOrDefault(rarity, 0) + 1);
                //getOrDefault方法，后面两个值，表示使用左边的参数，如果空就返回后面的参数，如果不空就正常get
                //这里就是实现了从0开始数数
            }
            return map;
        }else {
            Map<Integer,Integer> map = charactersList.stream()
                    .collect(Collectors.groupingBy(
                            Characters::getRarity,
                            Collectors.collectingAndThen(
                                    Collectors.counting(),
                                    Long::intValue
                            )
                    ));
            //虽然没什么必要，但是作为练习还是写一下流的形式吧，
            //Collectors.collectingAndThen可以添加多个，一个是counting统计数据，另一个把long变为int
            return map;
        }
    }

    public Map<Afflatus, Integer> getAfflatusStatistics() {
        Map<Afflatus, Integer> map = new HashMap<>();
        for (Characters characters : charactersList) {
            Afflatus afflatus = characters.getAfflatus();
            map.put(afflatus, map.getOrDefault(afflatus, 0) + 1);
        }
        return map;
    }

    public Map<DamageType, Integer> getDamageTypeStatistics() {
        Map<DamageType, Integer> map = new HashMap<>();
        for (Characters characters : charactersList) {
            DamageType damageType = characters.getDamageType();
            map.put(damageType, map.getOrDefault(damageType, 0) + 1);
        }
        return map;
    }

    public Map<Gender, Integer> getGenderStatistics() {
        Map<Gender, Integer> map = new HashMap<>();
        for (Characters characters : charactersList) {
            Gender gender = characters.getGender();
            map.put(gender, map.getOrDefault(gender, 0) + 1);
        }
        return map;
    }


}
