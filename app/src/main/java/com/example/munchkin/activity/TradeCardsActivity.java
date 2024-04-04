package com.example.munchkin.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.TradeCardsView;

public class TradeCardsActivity extends AppCompatActivity {

    private CardDeckController cardDeckController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayer_for_game_board);

        WebSocketClientModel model = new WebSocketClientModel();
        cardDeckController = new CardDeckController(model);

        Button switchButton = findViewById(R.id.tauschen_btn);
        TradeCardsView view = new TradeCardsView(cardDeckController, switchButton);
    }
}