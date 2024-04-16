package com.example.munchkin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.controller.DrawCardController;
import com.example.munchkin.view.MainGameView;
import android.content.Intent;
import android.widget.Button;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.controller.SpawnMonsterController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.DiceRollView;

import java.util.ArrayList;


public class MainGameActivity extends AppCompatActivity {


    private GameController gameController;
    private SpawnMonsterController spawnMonsterController;
    private MainGameView maingameView;

    private ArrayList<Integer> diceResults = new ArrayList<>();

    private ActivityResultLauncher<Intent> diceRollLauncher;

    private DrawCardController drawCardController;

    private PlayerHand handkarten;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);

        setupControllers();
        setupDiceRollLauncher();
        requestRoll();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button endRoundButton = findViewById(R.id.buttonEndRound);
        endRoundButton.setOnClickListener(v -> gameController.endTurn());
    }

    private void setupControllers() {
        WebSocketClientModel model = new WebSocketClientModel();
        maingameView = new MainGameView(this);
        spawnMonsterController = new SpawnMonsterController(model, maingameView);
        gameController = new GameController(model, maingameView,spawnMonsterController);
        drawCardController = new DrawCardController(model,maingameView);


        this.handkarten=new PlayerHand();

        MessageRouter router = new MessageRouter();
        router.registerController("PLAYER_ATTACK", gameController);
        router.registerController("MONSTER_ATTACK", gameController);
        router.registerController("SPAWN_MONSTER", spawnMonsterController);
        router.registerController("REQUEST_USERNAMES", gameController);
        router.registerController("DRAW_CARD",drawCardController);
        model.setMessageRouter(router);


        gameController.requestUsernames();

    }


    public void sendMessage() {
        drawCardController.drawMeassage();
    }

    public void addcardtolist(ActionCardDTO karte){
        handkarten.addCard(karte);
        System.out.println("---- add to list"+handkarten.getCards().size());

    }

    public void transitionToCardDeckscreen() {
        Intent intent = new Intent(MainGameActivity.this, CarddeckActivity.class);
        startActivity(intent);
    }


    private void processDiceResults() {
        for (int zone : diceResults) {
            spawnMonsterController.sendMonsterSpawnMessage("Zone" + zone);
        }
        diceResults.clear();
    }

    private void requestRoll() {
        Intent intent = new Intent(this, DiceRollView.class);
        diceRollLauncher.launch(intent);
    }

    private void setupDiceRollLauncher() {
        diceRollLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<Integer> newResults = result.getData().getIntegerArrayListExtra("diceResults");
                        if (newResults != null) {
                            diceResults.addAll(newResults);
                            gameController.onDiceRolled(newResults.stream().mapToInt(i->i).toArray());
                        }
                    }
                }
        );
    }


}
