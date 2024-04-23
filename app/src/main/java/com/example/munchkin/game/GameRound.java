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
        this.monsterRings = new ArrayList<>();
        diceResults = new int[3];
        initializeRings();
    }

    private void initializeRings() {
        for (int i = 0; i < 4; i++) {
            monsterRings.add(new ArrayList<>());
        }
    }


    public void start() {
        gameEventHandler.requestDiceRoll(this::handleDiceResults);
    }


    private void handleDiceResults(int[] results) {
        this.diceResults = results;
        if (!isFirstRound) {
            moveMonstersInward();
        }
        placeMonsters(results);
        isFirstRound = false;
        // Weiter Logik nach dem Würfeln
    }


    private void placeMonsters(int[] results) {
        List<MonsterDTO> newMonsters = new ArrayList<>();
        for (int result : results) {
            if (result >= 0 && result < 12) {
                newMonsters.add(new MonsterDTO()); // Erstellen und Hinzufügen neuer Monster
            }
        }
        monsterRings.get(0).addAll(newMonsters);
    }



    private void moveMonstersInward() {
        for (int i = monsterRings.size() - 1; i > 0; i--) {
            monsterRings.get(i).addAll(monsterRings.get(i - 1));
            monsterRings.get(i - 1).clear();
        }
    }

}
