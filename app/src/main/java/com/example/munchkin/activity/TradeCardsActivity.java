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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayer_for_game_board);

        WebSocketClientModel model = new WebSocketClientModel();
        MessageRouter router = new MessageRouter();


        cardDeckController = new CardDeckController(model);


        Button switchButtonPlayer = findViewById(R.id.tauschen_btn_player);
        Button switchButtonDeck = findViewById(R.id.tauschen_btn_deck);
        TradeCardsView view = new TradeCardsView(cardDeckController, switchButtonPlayer,switchButtonDeck);

        router.registerController("SWITCH_CARDS_PLAYER", cardDeckController);
        router.registerController("SWITCH_CARDS_DECK", cardDeckController);

        model.setMessageRouter(router);
    }
}