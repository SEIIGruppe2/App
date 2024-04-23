package com.example.munchkin.Player;

import com.example.munchkin.DTO.ActionCardDTO;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class PlayerHandTest {
    private PlayerHand playerHand;
    private ActionCardDTO card1, card2;


    @BeforeEach
    public void setUp() {
        playerHand = new PlayerHand();
        card1 = new ActionCardDTO();
        card2 = new ActionCardDTO();
        playerHand.addCard(card1);
        playerHand.addCard(card2);
    }


    @Test
    public void testAddCard() {
        ActionCardDTO newCard = new ActionCardDTO();
        playerHand.addCard(newCard);
        assertTrue(playerHand.getCards().contains(newCard));
    }


    @Test
    public void testRemoveCard() {
        playerHand.removeCard(card1);
        assertFalse(playerHand.getCards().contains(card1));
    }


    @Test
    public void testGetCards() {
        List<ActionCardDTO> cards = playerHand.getCards();
        assertEquals(2, cards.size());
    }
}


