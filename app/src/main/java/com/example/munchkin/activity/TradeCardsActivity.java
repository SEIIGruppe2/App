package com.example.munchkin.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.controller.ConnectToServerController;

public class TradeCardsActivity extends AppCompatActivity {

    private CardDeckController cardDeckController;
    private ConnectToServerController serverController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayer_for_game_board);

        // Initialisierung der Controller
        cardDeckController = new CardDeckController(serverController);

        // Finden des Buttons
        Button switchButton = findViewById(R.id.tauschen_btn);

        // Hinzufügen des Click Listeners zum Button
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hier werden die konkreten Werte übergeben
                String username = "username";
                String abgegebeneKarte = "abgegebeneKarte";
                String erhalteneKarte = "erhalteneKarte";

                // Nachricht an den Controller senden
                cardDeckController.sendSwitchCardsPlayerMessage(username, abgegebeneKarte, erhalteneKarte);
            }
        });
    }
}