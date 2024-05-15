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
        String result = MessageFormatter.createMonsterAttackMessage("1", "1");
        assertEquals("{\"type\":\"MONSTER_ATTACK\",\"monsterid\":\"1\",\"towerid\":\"1\"}", result);
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

    @Test
    protected void testCreatePlayerRollDiceMessage() {
        String result = MessageFormatter.createPlayerRollDiceMessage();
        assertEquals("{\"type\":\"PLAYER_ROLL_DICE\"}", result);
    }

    @Test
    protected void testCreateRequestRoundMessage() {
        String result = MessageFormatter.createRequestRoundMessage();
        assertEquals("{\"type\":\"ROUND_COUNTER\"}", result);
    }

    @Test
    protected void createEndTurnMessage() {
        String currentturn = "Player1";
        String result = MessageFormatter.createEndTurnMessage(currentturn);
        assertEquals("{\"type\":\"END_TURN\",\"turn\":\"Player1\"}", result);
    }


}