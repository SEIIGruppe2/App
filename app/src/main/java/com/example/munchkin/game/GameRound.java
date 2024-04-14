package com.example.munchkin.game;

import com.example.munchkin.Player.Player;

public class GameRound {

    private Player currentPlayer;
    private int[] diceResults;

    public GameRound(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        diceResults = new int[3];
    }

    public void start() {
        rollDice();
        placeMonsters();

    }

    private void rollDice() {

    }

    private void placeMonsters() {



    }


}
