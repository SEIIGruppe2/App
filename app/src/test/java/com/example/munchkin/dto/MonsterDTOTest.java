package com.example.munchkin.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MonsterDTOTest {

    @Test
    void testMonsterDTOFunctions() {
        MonsterDTO monster = new MonsterDTO("Goblin", 4, 2, 1, 1);
        assertEquals("Goblin", monster.getName());
        assertEquals(4, monster.getZone());
        assertEquals(2, monster.getRing());
        assertEquals(1, monster.getLifePoints());
        assertEquals(1, monster.getId());

        monster.setLifePoints(3);
        assertEquals(3, monster.getLifePoints());

        monster.setRing(3);
        assertEquals(3, monster.getRing());
    }

    @Test
    void testDefaultConstructor() {
        MonsterDTO monster = new MonsterDTO();
        assertNull(monster.getName());
        assertEquals(0, monster.getZone());
        assertEquals(0, monster.getRing());
        assertEquals(0, monster.getLifePoints());
    }
}
