package com.example.munchkin.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TowerDTOTest {

    @Test
    void testTowerDTOConstructionAndAccessors() {
        TowerDTO tower = new TowerDTO(1,0);
        assertEquals(1, tower.getLifePoints());

        tower.setLifePoints(5);
        assertEquals(5, tower.getLifePoints());
    }

    @Test
    void testDefaultConstructor() {
        TowerDTO tower = new TowerDTO();
        assertEquals(0, tower.getLifePoints());
    }
}