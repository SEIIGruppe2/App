package com.example.munchkin.view;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.DrawableUtils.CardUtils;
import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.R;
import com.example.munchkin.activity.TradeCardsActivity;
import com.example.munchkin.controller.CardDeckController;

import java.util.List;

public class TradeCardsView {

    private CardDeckController cardDeckController;

    private TradeCardsActivity tradeCardsActivity;

    private ActionCardDTO selectedCardForTrade;

    private PlayerHand playerHand;

    private Spinner username;
    private TextView cardname;

    public TradeCardsView(CardDeckController cardDeckController, Button buttonSwitchPlayer, Button buttonSwitchDeck, Spinner username, TextView card) {
        this.cardDeckController = cardDeckController;
        setSwitchButtonClickListenerPlayer(buttonSwitchPlayer);
        buttonSwitchDeck.setOnClickListener(v -> performTrade());
        this.username=username;
        this.cardname=card;
    }

    private void setSwitchButtonClickListenerPlayer(Button switchButton) {
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                TextView textView = (TextView)username.getSelectedView();
                String result = textView.getText().toString();
                System.out.println(result);

                String abgegebenekarteaustextview = cardname.getResources().toString();
                System.out.println(abgegebenekarteaustextview);



                // Hier werden die konkreten Werte übergeben
                String username = result;
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
            // Reset selected card after trade
            selectedCardForTrade = null;
        } else {
            throw new IllegalArgumentException("Trade fehlgeschlagen");
        }
    }


}

