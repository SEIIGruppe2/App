package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.Player;
import com.example.munchkin.interfaces.DiceRollListener;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.GameView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class GameController extends BaseController implements DiceRollListener {

    private Queue<Player> playerQueue; // Warteschlange für die Spieler
    private Player currentPlayer;
    private int roundCounter = 1;
    private GameView gameView;

    private SpawnMonsterController spawnMonsterController;


    public GameController(WebSocketClientModel model, GameView gameView, SpawnMonsterController spawnMonsterController) {
        super(model);
        this.gameView = gameView;
        playerQueue = new LinkedList<>();
        this.spawnMonsterController = spawnMonsterController;
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
                case "REQUEST_USERNAMES":
                    handleUserName(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/GameController");
        }
    }


    public void startRound() {
        System.out.println("Runde " + roundCounter + " beginnt."); //Muss ersetzt werden durch pop-up
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




    public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
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



    private void handleUserName(JSONObject jsonResponse){
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                Player player = new Player(username);
                playerQueue.add(player);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/GameController");
        }
    }

    @Override
    public void onDiceRolled(int[] results) {

        for (int result : results) {
            spawnMonsterController.sendMonsterSpawnMessage("Zone" + result);
        }

    }
}
