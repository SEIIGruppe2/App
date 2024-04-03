package com.example.munchkin.view;

import android.view.View;
import android.widget.Button;

import com.example.munchkin.controller.CardDeckController;

public class TradeCardsView {

    private CardDeckController cardDeckController;

    public TradeCardsView(CardDeckController cardDeckController, Button buttonSwitch) {
        this.cardDeckController = cardDeckController;
        setSwitchButtonClickListener(buttonSwitch);
    }

    private void setSwitchButtonClickListener(Button switchButton) {
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier werden die konkreten Werte übergeben
                onSwitchButtonClicked("username", "abgegebeneKarte", "erhalteneKarte");
            }
        });
    }

    private void onSwitchButtonClicked(String username, String abgegebeneKarte, String erhalteneKarte) {
        // Nachricht an den Controller senden
        cardDeckController.sendSwitchCardsPlayerMessage(username, abgegebeneKarte, erhalteneKarte);
    }

}
