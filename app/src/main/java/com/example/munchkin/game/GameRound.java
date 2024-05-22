package com.example.munchkin.game;

import android.util.Log;
import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;



public class GameRound {

    private GameEventHandler gameEventHandler;

    public GameRound(Player currentPlayer, GameEventHandler gameEventHandler) {
        this.gameEventHandler = gameEventHandler;
    }


    public void start() {
        gameEventHandler.requestDiceRoll(this::handleDiceResults);
    }


    private void handleDiceResults(int[] results) {
    }

}
