package com.example.munchkin.Player;

import com.example.munchkin.dto.ActionCardDTO;

import java.util.ArrayList;
import java.util.List;

public class PlayerHand {

    private static List<ActionCardDTO> cards= new ArrayList<>();


    public void addCard(ActionCardDTO card) {
        cards.add(card);
    }

    public void removeCard(ActionCardDTO card) {
        cards.remove(card);
    }

    public List<ActionCardDTO> getCards() {
        return cards;
    }

}
