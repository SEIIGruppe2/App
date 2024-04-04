package com.example.munchkin.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.networking.WebSocketMessageHandler;

public class GameActivity extends AppCompatActivity implements WebSocketMessageHandler<String> {

    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayer_for_game_board); // Make sure to use the correct layout file

        WebSocketClientModel model = new WebSocketClientModel();
        gameController = new GameController(model);
        MessageRouter router = new MessageRouter();
        /*

        Button buttonPlayerAttack = findViewById(R.id.buttonPlayerAttack);
        Button buttonMonsterAttack = findViewById(R.id.buttonMonsterAttack);

        buttonPlayerAttack.setOnClickListener(v -> {
            // Assuming you have a way to determine the monsterId and cardTypePlayed
            String monsterId = "someMonsterId";
            String cardTypePlayed = "someCardType";
            gameController.sendPlayerAttackMessage(monsterId, cardTypePlayed);
        });

        buttonMonsterAttack.setOnClickListener(v -> {
            String monsterId = "someMonsterId";
            gameController.sendMonsterAttackMessage(monsterId);
        });



         */
        router.registerController("PLAYER_ATTACK", gameController);
        router.registerController("MONSTER_ATTACK", gameController);


        model.setMessageRouter(router);
    }

    @Override
    public void onMessageReceived(String message) {

        runOnUiThread(() -> {
            // Update your UI elements based on the received message
            // This might include showing the new card received from a card exchange
        });
    }
}
