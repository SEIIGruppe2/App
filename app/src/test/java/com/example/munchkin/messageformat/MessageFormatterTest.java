package com.example.munchkin.messageformat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


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
    void testCreateSwitchCardsDeckMessage() {
        String result = MessageFormatter.createSwitchCardsDeckMessage("789");
        assertEquals("{\"type\":\"SWITCH_CARD_DECK\",\"cardid\":\"789\"}", result);
    }

    @Test
     void testCreateSwitchCardsPlayerMessage() {
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

    @Test
     void createShowMonsterMessage() {
        String cardid = "1";
        String result = MessageFormatter.createShowMonsterMessage(cardid);
        assertEquals("{\"type\":\"SHOW_MONSTERS\",\"cardid\":\"1\"}", result);
    }

    @Test
    void createCardAttackMonsterMessage() {
        String cardid = "1";
        String monsterid = "2";
        String result = MessageFormatter.createCardAttackMonsterMessage(monsterid,cardid);
        assertEquals("{\"type\":\"CARD_ATTACK_MONSTER\",\"monsterid\":\"2\",\"cardid\":\"1\"}", result);
    }

    @Test
    void testCreateUsernameForSwitchRequestMessage() {
        String result = MessageFormatter.createUsernameForSwitchRequestMessage();
        assertEquals("{\"type\":\"REQUEST_USERNAMES_SWITCH\"}", result);
    }

    @Test
    void testPrivateConstructor() {
        try {
            invokePrivateConstructor();
            fail("Expected UnsupportedOperationException to be thrown");
        } catch (InvocationTargetException e) {
            assertTrue(e.getCause() instanceof UnsupportedOperationException, "Expected cause to be UnsupportedOperationException");
        } catch (ReflectiveOperationException e) {
            fail("Unexpected exception type thrown: " + e);
        }
    }

    private void invokePrivateConstructor() throws ReflectiveOperationException {
        Constructor<MessageFormatter> constructor = MessageFormatter.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }


    @Test
    void testCreateAccusationMessage() {
        String result = MessageFormatter.createAccusationMessage("Cheater", "Accusator");
        assertEquals("{\"type\":\"ACCUSATION_MSG\",\"cheaterName\":\"Cheater\",\"accusatorName\":\"Accusator\"}", result);
    }

    @Test
    void testCreateCheaterMessage() {
        String result = MessageFormatter.createCheaterMessage("ON");
        assertEquals("{\"type\":\"CHEAT_MODE\",\"cheatMode\":\"ON\"}", result);
    }

    @Test
    void testCreatePlayerTrophiesRequestMessage() {
        String result = MessageFormatter.createPlayerTrophiesRequestMessage();
        assertEquals("{\"type\":\"PLAYER_TROPHIES\"}", result);
    }

    @Test
    void testCreateEndGameMessage() {
        String result = MessageFormatter.createEndGameMessage("Player1");
        assertEquals("{\"type\":\"END_GAME\",\"hasWinner\":\"Player1\"}", result);
    }


}


