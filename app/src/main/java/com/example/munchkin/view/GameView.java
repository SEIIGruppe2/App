package com.example.munchkin.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.Player.Player;
import com.example.munchkin.R;
import com.example.munchkin.activity.GameActivity;
import com.example.munchkin.controller.GameController;

public class GameView {
    private GameController gameController;
    private GameActivity gameActivity;
    private Button[] monsterButtons;

    private Button[] allPlayerButtons;

    public GameView(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        allPlayerButtons = new Button[]{
                gameActivity.findViewById(R.id.buttonEndRound),
                gameActivity.findViewById(R.id.buttonCards)
                // Button entfernen damit der Spieler nichts machen kann
        };

        this.monsterButtons = new Button[] {
                gameActivity.findViewById(R.id.b00),
                gameActivity.findViewById(R.id.b01),

        };
    }




    public void displayMonster(MonsterDTO monster, int position) {
        if (position >= 0 && position < monsterButtons.length) {
            Button button = monsterButtons[position];
            button.setVisibility(View.VISIBLE);
            button.setBackground(gameActivity.getDrawable(R.drawable.munchkin2));
        }
    }


    public void displayCurrentPlayer(Player currentPlayer) {
        // Beispieltextfeld oder Label in der UI, das den Namen des aktuellen Spielers anzeigt
        TextView currentPlayerTextView = gameActivity.findViewById(R.id.Spieler);
        currentPlayerTextView.setText("Spieler: " + currentPlayer.getName());

        // Optional: UI-Elemente für andere Spieler deaktivieren oder visuell ändern
        for (Button button : allPlayerButtons) {
            if (!button.getTag().equals(currentPlayer.getName())) {
                button.setEnabled(false);
                button.setAlpha(0.5f);
            } else {
                button.setEnabled(true);
                button.setAlpha(1.0f);
            }
        }
    }

    public void updateRoundView(int round) {
        TextView roundView = gameActivity.findViewById(R.id.textViewRound);
        roundView.setText("Runde: " + round);
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
