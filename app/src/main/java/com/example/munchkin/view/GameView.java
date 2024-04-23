package com.example.munchkin.view;

import android.view.View;
import android.widget.Button;

import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.R;
import com.example.munchkin.activity.GameActivity;
import com.example.munchkin.controller.GameController;

public class GameView {
    private GameController gameController;
    private GameActivity gameActivity;
    private Button[] monsterButtons;



    public GameView(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        this.monsterButtons = new Button[] {
                gameActivity.findViewById(R.id.button_forest1_spawn1),
                gameActivity.findViewById(R.id.button_forest1_spawn2),

        };
    }

    public void displayMonster(MonsterDTO monster, int position) {
        if (position >= 0 && position < monsterButtons.length) {
            Button button = monsterButtons[position];
            button.setVisibility(View.VISIBLE);
            button.setBackground(gameActivity.getDrawable(R.drawable.munchkin2));
        }
    }




    private void setupUI() {
      //  Button playerAttackButton = // initialize button
              /*  playerAttackButton.setOnClickListener(v -> {
                    String monsterId = // get monster ID
                            String cardTypePlayed = // get card type
                            gameController.handlePlayerAttack(monsterId, cardTypePlayed);
                });

        Button monsterAttackButton = // initialize button
                monsterAttackButton.setOnClickListener(v -> {
                    String monsterId = // get monster ID
                            gameController.handleMonsterAttack(monsterId);
                });


               */
    }
}
