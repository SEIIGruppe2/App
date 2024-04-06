package com.example.munchkin.activity;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

import java.util.ArrayList;
import java.util.List;

public class TradeCardsActivity extends AppCompatActivity {

    private CardDeckController cardDeckController;
    private WebSocketClientModel model = new WebSocketClientModel();
    private TradeCardsView tradeCardsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tauschen_test_layer);

        MessageRouter router = new MessageRouter();
        cardDeckController = new CardDeckController(model, tradeCardsView);

        // Now that cardDeckController is initialized, we can create tradeCardsView
        tradeCardsView = new TradeCardsView(cardDeckController, findViewById(R.id.tauschen_btn_deck), findViewById(R.id.tauschen_btn_player));

        cardDeckController.setTradeCardsView(tradeCardsView);
        cardDeckController.requestUsernames();

        // Spinner setup remains mostly the same, assuming updateCardImageView is implemented correctly
        setupSpinner();

        // Setup router and model
        router.registerController("SWITCH_CARDS_DECK", cardDeckController);
        router.registerController("SWITCH_CARDS_PLAYER", cardDeckController);
        model.setMessageRouter(router);
    }

    private void setupSpinner() {
        Spinner cardSpinner = findViewById(R.id.cardSelectionSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCardNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(adapter);

        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Implement card selection logic for trade
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private List<String> getCardNames() {

        return new ArrayList<>();
    }


}