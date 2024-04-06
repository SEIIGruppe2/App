package com.example.munchkin.view;

import android.view.View;
import android.widget.Button;

import com.example.munchkin.controller.CardDeckController;

public class TradeCardsView {

    private CardDeckController cardDeckController;

    public TradeCardsView(CardDeckController cardDeckController, Button buttonSwitchPlayer, Button buttonSwitchDeck){
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





}
