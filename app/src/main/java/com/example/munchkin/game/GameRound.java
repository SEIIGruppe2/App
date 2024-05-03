package com.example.munchkin.game;

import android.util.Log;

import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;

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
        Log.d("GameRound", "BevorRequestRoll" );
        gameEventHandler.requestDiceRoll(this::handleDiceResults);
    }


    private void handleDiceResults(int[] results) {

    }

}
