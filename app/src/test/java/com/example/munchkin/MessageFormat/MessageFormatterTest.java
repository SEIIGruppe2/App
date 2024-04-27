package com.example.munchkin.MessageFormat;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageFormatterTest {


    @Test
    public void testCreatePlayerAttackMessage() {
        String result = MessageFormatter.createPlayerAttackMessage("123", "Fireball");
        assertEquals("{\"type\":\"PLAYER_ATTACK\",\"monsterid\":\"123\",\"cardTypePlayed\":\"Fireball\"}", result);
    }



    @Test
    public void testCreateMonsterAttackMessage() {
        String result = MessageFormatter.createMonsterAttackMessage("456");
        assertEquals("{\"type\":\"MONSTER_ATTACK\",\"monsterid\":\"456\"}", result);
    }



    @Test
    public void testCreateSwitchCardsDeckMessage() {
        String result = MessageFormatter.createSwitchCardsDeckMessage("789");
        assertEquals("{\"type\":\"SWITCH_CARD_DECK\",\"cardid\":\"789\"}", result);
    }


    @Test
    public void testCreateSwitchCardsPlayerMessage() {
        String result = MessageFormatter.createSwitchCardsPlayerMessage("Alice", "Sword", "Shield");
        assertEquals("{\"type\":\"SWITCH_CARDS_PLAYER\",\"switchedWith\":\"Alice\",\"cardGiven\":\"Sword\",\"cardGotten\":\"Shield\"}", result);
    }



    @Test
    public void testCreateDrawCardMessage() {
        String result = MessageFormatter.createDrawCardMessage();
        assertEquals("{\"type\":\"DRAW_CARD\"}", result);
    }



    @Test
    public void testRegisterUserMessage() {
        String result = MessageFormatter.registerUserMessage("Bob");
        assertEquals("{\"type\":\"REGISTER_USERNAME\",\"username\":\"Bob\"}", result);
    }


    @Test
    public void testCreateUsernameRequestMessage() {
        String result = MessageFormatter.createUsernameRequestMessage();
        assertEquals("{\"type\":\"REQUEST_USERNAMES\"}", result);
    }



    @Test
    public void testCreateSpawnMonsterMessage() {
        String result = MessageFormatter.createSpawnMonsterMessage("North");
        assertEquals("{\"type\":\"SPAWN_MONSTER\",\"zone\":\"North\"}", result);
    }

}


