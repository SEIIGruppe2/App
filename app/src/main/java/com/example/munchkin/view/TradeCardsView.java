package com.example.munchkin.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.DrawableUtils.CardUtils;
import com.example.munchkin.R;
import com.example.munchkin.activity.TradeCardsActivity;
import com.example.munchkin.controller.CardDeckController;

import java.util.List;

public class TradeCardsView {

    private CardDeckController cardDeckController;

    private TradeCardsActivity tradeCardsActivity;

    private ActionCardDTO selectedCardForTrade;

    public TradeCardsView(CardDeckController cardDeckController, Button buttonSwitchPlayer, Button buttonSwitchDeck) {
        this.cardDeckController = cardDeckController;
        setSwitchButtonClickListenerPlayer(buttonSwitchPlayer);
        setSwitchButtonClickListenerDeck(buttonSwitchDeck);
    }

    private void setSwitchButtonClickListenerPlayer(Button switchButton) {
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier werden die konkreten Werte übergeben
                String username = "username";
                String abgegebeneKarte = "abgegebeneKarte";
                String erhalteneKarte = "erhalteneKarte";
                onSwitchButtonClickedPlayer(username, abgegebeneKarte, erhalteneKarte);
            }
        });
    }

    private void onSwitchButtonClickedPlayer(String username, String abgegebeneKarte, String erhalteneKarte) {
        // Nachricht an den Controller senden
        cardDeckController.sendSwitchCardsPlayerMessage(username, abgegebeneKarte, erhalteneKarte);
    }


    private void setSwitchButtonClickListenerDeck(Button switchButton) {
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier werden die konkreten Werte übergeben
                String card = "Ritter 1";

                onSwitchButtonClickedDeck(card);
            }
        });
    }

    private void onSwitchButtonClickedDeck(String card) {
        cardDeckController.sendSwitchCardsDeckMessage(card);
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
        ImageView card1 = tradeCardsActivity.findViewById(R.id.card1);
        ImageView card2 = tradeCardsActivity.findViewById(R.id.card2);
        // Obtain references to other card ImageViews similarly

        ImageView[] cardViews = new ImageView[]{card1, card2 /*, other card ImageViews */};

        for (ImageView cardView : cardViews) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Highlight the selected card
                    for (ImageView cv : cardViews) {
                        cv.setAlpha(0.5f); // Dim other cards to visually indicate they are not selected
                    }
                    v.setAlpha(1.0f); // Highlight the selected card

                    // Perform actions based on the selected card

                }
            });
        }

    }


    public void setCardDeckController(CardDeckController cardDeckController) {
        this.cardDeckController = cardDeckController;
    }


}

