package com.example.munchkin.player;

import com.example.munchkin.dto.ActionCardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class PlayerHandTest {

    private PlayerHand playerHand;
    private ActionCardDTO card1, card2;

    @BeforeEach
    protected void setUp() {
        playerHand = new PlayerHand();
        card1 = new ActionCardDTO();
        card2 = new ActionCardDTO();
        playerHand.addCard(card1);
        playerHand.addCard(card2);
    }

    @Test
    void testAddCard() {
        ActionCardDTO newCard = new ActionCardDTO();
        playerHand.addCard(newCard);
        assertTrue(playerHand.getCards().contains(newCard));
    }

    @Test
    void testRemoveCard() {
        playerHand.removeCard(card1);
        assertFalse(playerHand.getCards().contains(card1));
    }

    @Test
    void testGetCards() {
        List<ActionCardDTO> cards = playerHand.getCards();
        assertEquals(2, cards.size());
    }
}