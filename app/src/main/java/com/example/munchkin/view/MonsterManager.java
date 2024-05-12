package com.example.munchkin.view;

import com.example.munchkin.DTO.MonsterDTO;

import java.util.HashMap;
import java.util.Map;

public class MonsterManager {
    private Map<Integer, MonsterDTO> activeMonsters = new HashMap<>();

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

    public int countActiveMonsters() {
        return activeMonsters.size();
    }
}