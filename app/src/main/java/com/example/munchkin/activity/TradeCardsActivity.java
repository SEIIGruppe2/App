package com.example.munchkin.activity;


import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

public class TradeCardsActivity extends AppCompatActivity {

    private CardDeckController cardDeckController;

    private TradeCardsView tradeCardsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayer_for_game_board);

        WebSocketClientModel model = new WebSocketClientModel();
        MessageRouter router = new MessageRouter();


        Button switchButtonPlayer = findViewById(R.id.tauschen_btn_player);
        Button switchButtonDeck = findViewById(R.id.tauschen_btn_deck);
        tradeCardsView = new TradeCardsView(null, switchButtonPlayer, switchButtonDeck); // Temporarily passing null


        cardDeckController = new CardDeckController(model, tradeCardsView);

        tradeCardsView.setCardDeckController(cardDeckController);

        // Setup router and model
        router.registerController("MONSTER_ATTACK", cardDeckController);
        router.registerController("PLAYER_ATTACK", cardDeckController);
        model.setMessageRouter(router);
    }



}