package com.example.munchkin.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.controller.SpawnMonsterController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.networking.WebSocketMessageHandler;
import com.example.munchkin.view.GameView;

public class GameActivity extends AppCompatActivity {

    private GameController gameController;
    private SpawnMonsterController spawnMonsterController;
    private GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game); // Make sure to use the correct layout file

        String zone = getIntent().getStringExtra("diceResult");

        WebSocketClientModel model = new WebSocketClientModel();
        gameController = new GameController(model);
        gameView = new GameView(this);
        spawnMonsterController = new SpawnMonsterController(model,gameView);
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
        router.registerController("SPAWN_MONSTER", spawnMonsterController);


        model.setMessageRouter(router);

        if (zone != null) {
            spawnMonsterController.sendMonsterSpawnMessage(zone);
        }

    }

}
