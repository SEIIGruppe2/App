package com.example.munchkin.view;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.DrawableUtils.CardUtils;
import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.R;
import com.example.munchkin.activity.TradeCardsActivity;
import com.example.munchkin.controller.CardDeckController;

import java.util.ArrayList;
import java.util.List;

public class TradeCardsView {

    private CardDeckController cardDeckController;

    private TradeCardsActivity tradeCardsActivity;

    private ActionCardDTO selectedCardForTrade;

    private PlayerHand playerHand;

    private String targetPlayerUsername;

    public TradeCardsView(CardDeckController cardDeckController, Button buttonSwitchPlayer, Button buttonSwitchDeck) {
        this.cardDeckController = cardDeckController;
        buttonSwitchPlayer.setOnClickListener(v -> performPlayerTrade());
        buttonSwitchDeck.setOnClickListener(v -> performTrade());
    }


    public void displayPlayerCards(List<ActionCardDTO> playerCards) {
        Spinner cardSelector = tradeCardsActivity.findViewById(R.id.cardSelectionSpinner);
        ArrayList<String> cardNames = new ArrayList<>();
        for (ActionCardDTO card : playerCards) {
            cardNames.add(card.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(tradeCardsActivity, android.R.layout.simple_spinner_dropdown_item, cardNames);
        cardSelector.setAdapter(adapter);

        cardSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCardName = cardNames.get(position);
                updateCardImageView(selectedCardName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ImageView cardImageView = tradeCardsActivity.findViewById(R.id.kartenbildpopup);
                cardImageView.setImageResource(R.drawable.card);
            }
        });
    }


    public void setupCardSelection() {
        // Let's assume you have a Spinner or some form of selector for card names
        Spinner cardSelector = tradeCardsActivity.findViewById(R.id.cardSelectionSpinner);
        ArrayList<String> cardNames = new ArrayList<>();
        for (ActionCardDTO card : playerHand.getCards()) {
            cardNames.add(card.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(tradeCardsActivity, android.R.layout.simple_spinner_dropdown_item, cardNames);
        cardSelector.setAdapter(adapter);

        cardSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCardName = (String) parent.getItemAtPosition(position);
                for (ActionCardDTO card : playerHand.getCards()) {
                    if (card.getName().equals(selectedCardName)) {
                        selectedCardForTrade = card;
                        updateCardImageView(card.getName());
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCardForTrade = null;
            }
        });
    }


    public void updateCardImageView(String cardName) {
        ImageView cardImageView = tradeCardsActivity.findViewById(R.id.kartenbildpopup);
        int resourceId = CardUtils.getDrawableForCard(cardName); // This method should return a drawable resource ID based on the card name
        cardImageView.setImageResource(resourceId);
    }

    public void setCardDeckController(CardDeckController cardDeckController) {
        this.cardDeckController = cardDeckController;
    }


    private void performTrade() {
        if (selectedCardForTrade != null) {
            // Assuming your CardDeckController has a method to handle trading a card

            cardDeckController.tradeCardDeck(selectedCardForTrade);
            selectedCardForTrade = null;
        } else {
            throw new IllegalArgumentException("Trade fehlgeschlagen");
        }
    }

    public void performPlayerTrade() {
        if (selectedCardForTrade != null && targetPlayerUsername != null)
        {
            int id = selectedCardForTrade.getId();
            String idAsString = String.valueOf(id);

            cardDeckController.sendSwitchCardsPlayerMessage(targetPlayerUsername, idAsString, "null");
            selectedCardForTrade = null;
        } else {
            throw new IllegalArgumentException("Trade fehlgeschlagen");
        }
    }

    public void updateUsernamesSpinner(ArrayList<String> usernames) {
        Spinner userSpinner = tradeCardsActivity.findViewById(R.id.userspinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(tradeCardsActivity, android.R.layout.simple_spinner_dropdown_item, usernames);
        userSpinner.setAdapter(adapter);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetPlayerUsername = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


}

