package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MainGameActivity;
import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.R;
import com.example.munchkin.controller.DrawCardController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.DrawView;
import com.example.munchkin.view.MainGameView;

import java.io.Serializable;

public class DrawActivity extends AppCompatActivity {

    /*
    private DrawCardController controller;
    private MainGameView mainGameView;

    private MainGameActivity mainGameActivity;

    private PlayerHand handkarten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_draw);
        this.handkarten=new PlayerHand();


        MessageRouter router = new MessageRouter();
        WebSocketClientModel model = new WebSocketClientModel();
        mainGameView = new MainGameView(mainGameActivity);

        controller = new DrawCardController(model, mainGameView);


        router.registerController("DRAW_CARD",controller);
        model.setMessageRouter(router);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void sendMessage() {

        controller.drawMeassage();

    }

    public void addcardtolist(ActionCardDTO karte){
        handkarten.addCard(karte);
        System.out.println("---- add to list"+handkarten.getCards().size());

    }

    public void transitionToCardDeckscreen() {

        Intent intent = new Intent(DrawActivity.this, CarddeckActivity.class);
        startActivity(intent);
    }
*/

}