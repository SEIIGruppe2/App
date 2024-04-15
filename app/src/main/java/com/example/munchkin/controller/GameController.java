package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.Player;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.GameView;

import java.util.LinkedList;
import java.util.Queue;


public class GameController extends BaseController {

    private Queue<Player> playerQueue; // Warteschlange für die Spieler
    private Player currentPlayer;
    private int roundCounter = 1;
    private GameView gameView;

    public GameController(WebSocketClientModel model, GameView gameView, LinkedList<Player> players) {
        super(model);
        this.gameView = gameView;
        this.playerQueue = new LinkedList<>(players);
    }

    @Override
    public void handleMessage(String message) {
        if (message.contains("PLAYER_ATTACK")) {
            handlePlayerAttackMessage(message);
        } else if (message.contains("MONSTER_ATTACK")) {
            handlMonserAttackMessage(message);
        }
    }


    public void startRound() {
        System.out.println("Runde " + roundCounter + " beginnt.");
        currentPlayer = playerQueue.poll();
        playerQueue.offer(currentPlayer);
        gameView.displayCurrentPlayer(currentPlayer);
        // Hier könnte ein Event oder Call zur DiceRollView initiiert werden, um das Würfeln zu starten
    }

    public void endTurn() {
        if (playerQueue.peek() == currentPlayer) {
            roundCounter++;
            startRound();
        } else {
            currentPlayer = playerQueue.poll();
            playerQueue.offer(currentPlayer);
            gameView.displayCurrentPlayer(currentPlayer);
        }
    }




    public void sendPlayerAttackMessage(String monsterId, String cardTypePlayed) {
        String message = MessageFormatter.createPlayerAttackMessage(monsterId, cardTypePlayed);
        model.sendMessageToServer(message);
    }

    public void sendMonsterAttackMessage(String monsterId) {
        String message = MessageFormatter.createMonsterAttackMessage(monsterId);
        model.sendMessageToServer(message);
    }



    // Methode zum Verarbeiten der empfangenen Nachrichten vom Server
    private void handlePlayerAttackMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Spieler
    }

    private void handlMonserAttackMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Kartenstapel
    }



}
