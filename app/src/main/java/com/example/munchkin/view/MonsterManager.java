package com.example.munchkin.view;

import com.example.munchkin.DTO.MonsterDTO;

import java.util.HashMap;
import java.util.Map;

public class MonsterManager {
    Map<Integer, MonsterDTO> activeMonsters = new HashMap<>();

    public void registerMonster(MonsterDTO monster, int buttonId) {
        activeMonsters.put(buttonId, monster);
    }

    public void updateMonster(int buttonId, int newLifePoints) {
        MonsterDTO monster = activeMonsters.get(buttonId);
        if (monster != null) {
            monster.setLifePoints(newLifePoints);
            if (newLifePoints <= 0) {
                activeMonsters.remove(buttonId);  // Remove monster if dead
            }
        }
    }
    public void removeMonster(String monsterId) {
        activeMonsters.remove(monsterId);
    }

    public int countActiveMonsters() {
        return activeMonsters.size();
    }
}