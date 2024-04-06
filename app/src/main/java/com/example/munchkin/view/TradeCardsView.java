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
        ImageView[] cardViews = new ImageView[]{
                tradeCardsActivity.findViewById(R.id.card1),
                tradeCardsActivity.findViewById(R.id.card2),
                // tradeCardsActivity.findViewById(R.id.card3),
                // tradeCardsActivity.findViewById(R.id.card4),
                // tradeCardsActivity.findViewById(R.id.card5)
        };

        for (int i = 0; i < cardViews.length; i++) {
            if (i < playerCards.size()) {
                ActionCardDTO card = playerCards.get(i);
                //cardViews[i].setImageResource(CardUtils.getDrawableForCard(card));
                cardViews[i].setVisibility(View.VISIBLE);
            } else {
                cardViews[i].setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setupCardSelection() {
        ImageView[] cardViews = new ImageView[]{
                tradeCardsActivity.findViewById(R.id.card1),
                tradeCardsActivity.findViewById(R.id.card2),
                // Additional card ImageViews if present
        };

        for (int i = 0; i < cardViews.length; i++) {
            final int index = i;
            cardViews[i].setOnClickListener(v -> {
                // Highlight the selected card and dim others
                for (ImageView otherCardView : cardViews) {
                    otherCardView.setAlpha(0.5f); // Dim
                }
                v.setAlpha(1.0f); // Highlight

                // Update the selected card
                selectedCardForTrade = playerHand.getCards().get(index);

                // Optionally, enable a "Trade" button or trigger other actions
                // Example: tradeButton.setEnabled(true);
            });
        }
    }


    public void setCardDeckController(CardDeckController cardDeckController) {
        this.cardDeckController = cardDeckController;
    }


    private void performTrade() {
        if (selectedCardForTrade != null) {
            // Assuming your CardDeckController has a method to handle trading a card
            // This method could be 'tradeCardWithDeck' or 'tradeCardWithPlayer' depending on your game logic
            cardDeckController.tradeCard(selectedCardForTrade);
            selectedCardForTrade = null;
        } else {
            throw new IllegalArgumentException("Trade fehlgeschlagen");
        }
    }

    public void performPlayerTrade() {
        if (selectedCardForTrade != null && targetPlayerUsername != null) {
            cardDeckController.sendSwitchCardsPlayerMessage(selectedCardForTrade.getName(), targetPlayerUsername, "null");
            selectedCardForTrade = null;
        } else {
            throw new IllegalArgumentException("Trade fehlgeschlagen");
        }
    }

    public void updateUsernamesSpinner(ArrayList<String> usernames) {
        Activity activity = (Activity) tradeCardsActivity; // Assuming tradeCardsActivity is context
        activity.runOnUiThread(() -> {
            Spinner userSpinner = activity.findViewById(R.id.spinner); // Adjust ID as necessary
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, usernames);
            userSpinner.setAdapter(adapter);

            userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    targetPlayerUsername = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        });

    }





}

