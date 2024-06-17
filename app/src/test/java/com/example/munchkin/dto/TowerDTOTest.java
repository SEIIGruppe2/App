package com.example.munchkin.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TowerDTOTest {

    @Test
    protected void testTowerDTOConstructionAndAccessors() {
        TowerDTO tower = new TowerDTO(1,0);
        assertEquals(1, tower.getLifePoints());

        tower.setLifePoints(5);
        assertEquals(5, tower.getLifePoints());
    }

    @Test
    protected void testDefaultConstructor() {
        TowerDTO tower = new TowerDTO();
        assertEquals(0, tower.getLifePoints());
    }
}