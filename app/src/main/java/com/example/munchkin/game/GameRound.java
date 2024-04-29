package com.example.munchkin.game;

import android.widget.Button;

import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;
import com.example.munchkin.model.DiceRollModel;

import java.util.ArrayList;
import java.util.List;


public class GameRound {

    private Player currentPlayer;
    private int[] diceResults;
    private GameEventHandler gameEventHandler;
    private List<List<MonsterDTO>> monsterRings;
    private boolean isFirstRound = true;


    public GameRound(Player currentPlayer, GameEventHandler gameEventHandler) {
        this.currentPlayer = currentPlayer;
        this.gameEventHandler = gameEventHandler;
        diceResults = new int[3];

    }



    public void start() {
        gameEventHandler.requestDiceRoll(this::handleDiceResults);
    }


    private void handleDiceResults(int[] results) {

    }



    private void moveMonstersInward() {
        for (int i = monsterRings.size() - 1; i > 0; i--) {
            monsterRings.get(i).addAll(monsterRings.get(i - 1));
            monsterRings.get(i - 1).clear();
        }
    }

}
