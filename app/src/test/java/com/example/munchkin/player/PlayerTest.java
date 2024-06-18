package com.example.munchkin.player;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {

    @Test
    void testDefaultConstructor() {
        Player player = new Player();
        assertNull(player.getName(), "Name should be null for default constructor");
    }

    @Test
    void testParameterizedConstructor() {
        String testName = "Username";
        Player player = new Player(testName);
        assertEquals(testName, player.getName(), "Name should match the one set in the constructor");
    }


    @Test
    void testSetNameMethod() {
        Player player = new Player();
        player.setName("Player1");
        assertEquals("Player1", player.getName());
    }





}
