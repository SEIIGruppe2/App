package com.example.munchkin.game;

import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.GameEventHandler;
import com.example.munchkin.model.DiceRollModel;



public class GameRound {

    private Player currentPlayer;
    private int[] diceResults;
    private GameEventHandler gameEventHandler;

    public GameRound(Player currentPlayer, GameEventHandler gameEventHandler) {
        this.currentPlayer = currentPlayer;
        this.gameEventHandler = gameEventHandler;
        diceResults = new int[3];
    }

    public void start() {
        gameEventHandler.requestDiceRoll(this::handleDiceResults);
    }


    private void handleDiceResults(int[] results) {
        this.diceResults = results;
        placeMonsters();
        // Weiter Logik nach dem Würfeln
    }


    private void placeMonsters() {


    }


}
