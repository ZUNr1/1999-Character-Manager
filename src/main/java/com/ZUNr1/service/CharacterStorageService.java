package com.ZUNr1.service;

import com.ZUNr1.dao.CharactersJsonDao;
import com.ZUNr1.dao.CharactersMysqlDao;
import com.ZUNr1.manager.CharacterManage;
import com.ZUNr1.model.Characters;

import java.util.List;

public class CharacterStorageService {
    //private final CharactersJsonDao charactersJsonDao = new CharactersJsonDao();
    private final CharactersMysqlDao charactersMysqlDao = new CharactersMysqlDao();
    private final CharacterManage characterManage = new CharacterManage();
    public CharacterStorageService() {
        // 启动时自动加载现有数据
        loadAllCharacters();
    }
    public void saveCharacter(Characters character) {
        try {
            // 重复ID检查
            Characters existing = characterManage.findById(character.getId());
            if (existing != null) {
                // 更新现有角色
                characterManage.updateCharacters(character.getId(),character);
                System.out.println("更新角色: " + character.getName());
            } else {
                // 添加新角色
                characterManage.addCharacters(character);
                System.out.println("添加新角色: " + character.getName());
            }

            // 持久化到文件
            persistToFile();

        } catch (Exception e) {
            throw new RuntimeException("保存角色失败: " + e.getMessage(), e);
        }
    }
    /*public void loadAllCharacters() {
        try {
            charactersJsonDao.loadToManageOverride(characterManage);
            System.out.println("角色数据加载完成，共 " + characterManage.getTotalNumber() + " 个角色");
        } catch (Exception e) {
            System.err.println("加载角色数据失败: " + e.getMessage());
            // 不抛出异常，允许程序继续运行（可能是第一次运行，文件不存在）
        }
    }*/
    public void loadAllCharacters(){
        try {
            charactersMysqlDao.loadToManageOverride(characterManage);
            System.out.println("角色数据加载完成，共 " + characterManage.getTotalNumber() + " 个角色");
        } catch (Exception e) {
            System.err.println("加载角色数据失败: " + e.getMessage());
            // 不抛出异常，允许程序继续运行todo这里我不知道需不需要catch
        }
    }
    public boolean deleteCharacter(String id) {
        try {
            boolean success = characterManage.removeCharacters(id);
            if (success) {
                persistToFile(); // 立即保存到文件
            }
            return success;
        } catch (Exception e) {
            throw new RuntimeException("删除角色失败: " + e.getMessage(), e);
        }
    }
    public void persistToFile() {
        try {
            charactersMysqlDao.saveFromManagerOverride(characterManage);
            System.out.println("角色数据已保存到Mysql数据库");
        } catch (Exception e) {
            throw new RuntimeException("保存到Mysql失败: " + e.getMessage(), e);
        }
    }

    public String checkDuplicateCharacter(String id, String name, String enName) {
        if (id == null || id.trim().isEmpty()) {
            return "角色ID不能为空";
        }
        if (characterManage.findById(id) != null) {
            return "角色ID '" + id + "' 已存在";
        }
        if (characterManage.findByNameExact(name) != null) {
            return "角色名称 '" + name + "' 已存在";
        }
        if (enName != null && !enName.trim().isEmpty()) {
            if (characterManage.findByEnNameExact(enName) != null) {
                return "角色英文名 '" + enName + "' 已存在";
            }
        }
        return null;
    }

    public List<Characters> getAllCharacters() {
        return characterManage.findAllCharacters();
    }

    public Characters getCharacterById(String id) {
        return characterManage.findById(id);
    }

    public int getTotalCharacterCount() {
        return characterManage.getTotalNumber();
    }

    public void clearAllData() {
        characterManage.clearAll();
        persistToFile();
    }
    public CharacterManage getCharacterManage() {
        return characterManage;
    }
}
