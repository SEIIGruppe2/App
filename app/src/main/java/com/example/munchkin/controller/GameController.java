package com.example.munchkin.controller;

import android.content.Intent;
import android.util.Log;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.Player;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.game.GameRound;
import com.example.munchkin.interfaces.DiceRollCallback;
import com.example.munchkin.interfaces.DiceRollListener;
import com.example.munchkin.interfaces.GameEventHandler;
import com.example.munchkin.model.DiceRollModel;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.DiceRollView;
import com.example.munchkin.view.MainGameView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedList;
import java.util.Queue;
import org.json.JSONArray;


public class GameController extends BaseController implements DiceRollListener, GameEventHandler {

    private Queue<Player> playerQueue;
    private Player currentPlayer;
    private int roundCounter = 1;
    private MainGameView maingameView;
    private GameRound gameRound;
    private boolean diceRolledThisRound = false;
    private SpawnMonsterController spawnMonsterController;
    private byte REQUIRED_PLAYER_COUNT = 4;

    private boolean isFirstRound = true;

    private MainGameActivity mainGameActivity;

    public GameController(WebSocketClientModel model, MainGameView maingameView, SpawnMonsterController spawnMonsterController,MainGameActivity mainGameActivity) {
        super(model);
        this.maingameView = maingameView;
        playerQueue = new LinkedList<>();
        this.spawnMonsterController = spawnMonsterController;
        this.mainGameActivity = mainGameActivity;
        requestUsernames();
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

                case "SWITCH_CARD_PLAYER_RESPONSE":
                    handleswitchrequest(jsonResponse);
                    break;

                case "REQUEST_ROLL":
                    handleRequestRoll(jsonResponse);
                    break;

                case "ROUND_COUNTER":
                    handleRoundCounter(jsonResponse);
                    break;

                default:
                    break;


            }
        }
         catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/GameController");
        }
    }


    public void startRound() {
        Log.d("GameController", "Current player: " +" startRoundAnfang");
        sendPlayerRollDiceMessage();
        currentPlayer = playerQueue.poll();
        maingameView.displayCurrentPlayer(currentPlayer);
        maingameView.updateRoundView(roundCounter);
        isFirstRound = false;
    }




    public void endTurn() {
        currentPlayer = playerQueue.poll();
        playerQueue.offer(currentPlayer);
        if (playerQueue.peek() == currentPlayer) {
            endRound();
        }
        maingameView.displayCurrentPlayer(currentPlayer);
        startRound();
    }

    public void endRound() {
        diceRolledThisRound = false;
        roundCounter++;
        maingameView.updateRoundView(roundCounter);
    }


    public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }


    public void sendPlayerRollDiceMessage() {
        String message = MessageFormatter.createPlayerRollDiceMessage();
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

    public void sendRequestRoundMessage() {
        String message = MessageFormatter.createRequestRoundMessage();
        model.sendMessageToServer(message);
    }


    // Methode zum Verarbeiten der empfangenen Nachrichten vom Server
    private void handlePlayerAttackMessage(JSONObject message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Spieler
    }

    private void handlMonserAttackMessage(JSONObject message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Kartenstapel
    }

    private void handleswitchrequest(JSONObject message) throws JSONException {
        System.out.println("Tauschanfrage erhalten");

        maingameView.tauschanfrageerhalten(message);

    }


    private void handleRequestRoll(JSONObject jsonResponse){
        Log.d("GameController", "RollPlayer0");
        try {
            if (playerQueue.isEmpty()) {
                Log.d("GameController", "Spielerwarteschlange ist leer. RequestRoll");
                return;
            }
            currentPlayer = playerQueue.poll();
            if (currentPlayer == null) {
                Log.d("GameController", "Kein Spieler in der Warteschlange verfügbar.");
                return;
            }
            Log.d("GameController", "RollPlayer1: " + (currentPlayer != null ? currentPlayer.getName() : "null"));
            String usernameToRoll = jsonResponse.getString("username");
            Log.d("GameController", "RollPlayer2: " + usernameToRoll);
            if (usernameToRoll.equals(currentPlayer.getName())) {
                Log.d("GameController", "InDiceRolLView mit  " + usernameToRoll);
                performeRoll();
            } else {
                Log.d("GameController", "Benutzername stimmt nicht überein.");
            }
        } catch (JSONException e) {
            Log.e("GameController", "Fehler bei der Verarbeitung der Würfelanfrage", e);
        }
    }


    private void handleRoundCounter(JSONObject jsonResponse){
        try {
            Log.d("GameController", "In HandleCounter");
            String roundString = jsonResponse.getString("round");
            int roundNumber = Integer.parseInt(roundString);
            roundCounter = roundNumber;
            maingameView.updateRoundView(roundCounter);
            Log.d("GameController", "Vor Start Round");
            startRound();
            Log.d("GameController", "Nach Start Round");
        } catch (JSONException e) {
            Log.e("GameController", "Fehler bei der Verarbeitung der Rundennummer", e);
        } catch (NumberFormatException e) {
            Log.e("GameController", "Fehler beim Konvertieren der Rundennummer", e);
        }
    }



    private void performeRoll() {
        if (!diceRolledThisRound) {
            navigateToDiceRollView();
            Log.d("GameController", "Bevor Start Round" );
            Log.d("GameController", "Nach Start Round" );
            diceRolledThisRound = true;
        }
        if (!isFirstRound) {
            maingameView.moveMonstersInward();
        }
    }

    public void navigateToDiceRollView() {
        Intent intent = new Intent(mainGameActivity, DiceRollView.class);
        mainGameActivity.startActivity(intent);
    }


    private void handleUserName(JSONObject jsonResponse){
        Log.d("GameController", jsonResponse.toString() );
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                Player player = new Player(username);
                Log.d("GameController", "Player" + player.getName() );
                playerQueue.add(player);
            }
            if (playerQueue.size() == REQUIRED_PLAYER_COUNT) {
                sendRequestRoundMessage();
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/GameController");
        }
    }


    @Override
    public void onDiceRolled(int[] results) {

        Log.d("GameRound", "Bevor SpawnMonster" );
        for (int result : results) {
            spawnMonsterController.sendMonsterSpawnMessage(Integer.toString(result));
        }

    }
    @Override
    public void requestDiceRoll(DiceRollCallback callback) {
        Log.d("GameRound", "In RequestRoll" );
        DiceRollModel diceRollModel = new DiceRollModel();
        diceRollModel.rollDice(result -> {
            callback.onDiceRolled(new int[]{result});
        });
        Log.d("GameRound", "Nach RequestRoll" );
    }



}