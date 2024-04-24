package com.example.munchkin.DTO;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TowerDTOTest {



    @Test
    public void testTowerDTOConstructionAndAccessors() {
        TowerDTO tower = new TowerDTO(1);
        assertEquals(1, tower.getLifePoints());

        tower.setLifePoints(5);
        assertEquals(5, tower.getLifePoints());
    }


    @Test
    public void testDefaultConstructor() {
        TowerDTO tower = new TowerDTO();
        assertEquals(0, tower.getLifePoints());
    }
}

