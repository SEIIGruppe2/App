package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.Player;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.GameView;

import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONException;
import org.json.JSONObject;


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
        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "PLAYER_ATTACK":
                    handlePlayerAttackMessage(jsonResponse);
                    break;
                case "MONSTER_ATTACK":
                    handlMonserAttackMessage(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/GameController");
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
    private void handlePlayerAttackMessage(JSONObject message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Spieler
    }

    private void handlMonserAttackMessage(JSONObject message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Kartenstapel
    }



}
