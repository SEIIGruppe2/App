package com.example.munchkin.MessageFormat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MessageFormatterTest {

    @Test
    protected void testCreatePlayerAttackMessage() {
        String result = MessageFormatter.createPlayerAttackMessage("123", "Fireball");
        assertEquals("{\"type\":\"PLAYER_ATTACK\",\"monsterid\":\"123\",\"cardTypePlayed\":\"Fireball\"}", result);
    }

    @Test
    protected void testCreateMonsterAttackMessage() {
        String result = MessageFormatter.createMonsterAttackMessage("456");
        assertEquals("{\"type\":\"MONSTER_ATTACK\",\"monsterid\":\"456\"}", result);
    }

    @Test
    protected void testCreateSwitchCardsDeckMessage() {
        String result = MessageFormatter.createSwitchCardsDeckMessage("789");
        assertEquals("{\"type\":\"SWITCH_CARD_DECK\",\"cardid\":\"789\"}", result);
    }

    @Test
    protected void testCreateSwitchCardsPlayerMessage() {
        String result = MessageFormatter.createSwitchCardsPlayerMessage("Alice", "Sword", "Shield");
        assertEquals("{\"type\":\"SWITCH_CARD_PLAYER\",\"switchedWith\":\"Alice\",\"cardGiven\":\"Sword\",\"cardGivenP\":\"Shield\"}", result);
    }

    @Test
    protected void testCreateDrawCardMessage() {
        String result = MessageFormatter.createDrawCardMessage();
        assertEquals("{\"type\":\"DRAW_CARD\"}", result);
    }

    @Test
    protected void testRegisterUserMessage() {
        String result = MessageFormatter.registerUserMessage("Bob");
        assertEquals("{\"type\":\"REGISTER_USERNAME\",\"username\":\"Bob\"}", result);
    }

    @Test
    protected void testCreateUsernameRequestMessage() {
        String result = MessageFormatter.createUsernameRequestMessage();
        assertEquals("{\"type\":\"REQUEST_USERNAMES\"}", result);
    }

    @Test
    protected void testCreateSpawnMonsterMessage() {
        String result = MessageFormatter.createSpawnMonsterMessage("North");
        assertEquals("{\"type\":\"SPAWN_MONSTER\",\"zone\":\"North\"}", result);
    }
}