package com.example.munchkin.controller;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
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
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.DiceRollView;
import com.example.munchkin.view.MainGameView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONArray;
import com.example.munchkin.game.AppState;


public class GameController extends BaseController implements DiceRollListener, GameEventHandler {

    private static Queue<Player> playerQueue;
    private Player currentPlayer;
    private static String roundCounter="0";
    private MainGameView maingameView;
    private GameRound gameRound;
    private boolean diceRolledThisRound = false;
    private SpawnMonsterController spawnMonsterController;
    private byte REQUIRED_PLAYER_COUNT = 4;

    private boolean isFirstRound = true;

    private MainGameActivity mainGameActivity;

    private static String clientplayerUsername = AppState.getInstance().getCurrentUser();

    private static Player clientplayer;

    private static List<String> playerusernames;
    private int turnCount = 0;

    public GameController(WebSocketClientModel model, MainGameView maingameView, SpawnMonsterController spawnMonsterController,MainGameActivity mainGameActivity) {
        super(model);
        this.maingameView = maingameView;
        playerQueue = new LinkedList<>();
        this.spawnMonsterController = spawnMonsterController;
        this.mainGameActivity = mainGameActivity;


        startRound();

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
                    handleMonsterAttackMessage(jsonResponse);
                    break;
                /*case "REQUEST_USERNAMES":
                    handleUserName(jsonResponse);
                    break;*/

                case "SWITCH_CARD_PLAYER_RESPONSE":
                    handleswitchrequest(jsonResponse);
                    break;

                case "REQUEST_ROLL":
                    //handleRequestRoll(jsonResponse);
                    break;

                case "ROUND_COUNTER":
                    //handleRoundCounter(jsonResponse);
                    break;

                case "CURRENT_PLAYER":
                    Log.d("CurrentPlayer", "im handler");
                    handleCurrentPlayer(jsonResponse);
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
        Log.d("GameController", "Current player: " +" startRoundAnfang" + roundCounter);
        sendEndTurnMessage(roundCounter);

    }




    public void endTurn() {
        Log.d("GameController", "End of turn for: test " );

        maingameView.disablePlayerAction();
        diceRolledThisRound=false;

        Log.d("GameController", "End of turn for: test2 "+roundCounter);
        sendEndTurnMessage(roundCounter);

    }

//TODO: Überprüfen ob es noch benötigt wird
/*

    public void endRound() {
        Log.d("GameController", "Ending round " + roundCounter);
        isFirstRound = false;
        diceRolledThisRound = false;
        sendRequestRoundMessage();
        transitionToNextPlayer();
        startRound();
    }
*/

    public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }


    public void sendEndTurnMessage(String currentturn) {
        String message = MessageFormatter.createEndTurnMessage(currentturn);
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

    public void sendMonsterAttackMessage(String monsterId, String towerId) {
        String message = MessageFormatter.createMonsterAttackMessage(monsterId, towerId);
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

    private void handleMonsterAttackMessage(JSONObject message) {
        Log.d("MonsterAttack", "Handling monster attack message: " + message);
        try {
            String monsterId = message.getString("monsterId");
            //String towerId = message.getString("towerId");
            int monsterHp = message.getInt("monsterHp");
            int towerHp = message.getInt("towerHp");
            Log.d("GameController", "Tower HP received: " + towerHp);
            // Update tower HP
            maingameView.modifyTowerLifePoints(towerHp);
            Log.d("GameController", "UI should now be updated.");
            String attackStatus = message.getString("attackStatus");
            // Update monster HP
            maingameView.updateMonsterHealth(monsterId, monsterHp);
            //maingameView.moveMonstersInward();
        } catch (JSONException e) {
            Log.e("GameController", "Error parsing monster attack message", e);
        }
    }

    private void handleswitchrequest(JSONObject message) throws JSONException {
        System.out.println("Tauschanfrage erhalten");

        maingameView.tauschanfrageerhalten(message);

    }


    private void transitionToNextPlayer() {
        currentPlayer = playerQueue.peek();
        maingameView.displayCurrentPlayer(currentPlayer.getName());
        if (currentPlayer.getName().equals(clientplayerUsername)) {
            maingameView.enablePlayerAction();
        } else {
            maingameView.disablePlayerAction();
        }
    }

    //TODO: check ob noch gebraucht wird
    /*
    private void handleRequestRoll(JSONObject jsonResponse){
        Log.d("GameController", jsonResponse.toString());
        try {
            if (currentPlayer == null) {
                Log.d("GameController", "Kein Spieler in der Warteschlange verfügbar.");
                return;
            }
            Log.d("GameController", "RollPlayer1: " + (currentPlayer != null ? currentPlayer.getName() : "null"));
            String usernameToRoll = jsonResponse.getString("username");
            Log.d("GameController", "RollPlayer2: " + clientplayerUsername);
            if (clientplayerUsername.equals(usernameToRoll)) {
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

            //roundCounter = roundNumber;
            //maingameView.updateRoundView(roundCounter);
            maingameView.moveMonstersInward();

        } catch (JSONException e) {
            Log.e("GameController", "Fehler bei der Verarbeitung der Rundennummer", e);
        } catch (NumberFormatException e) {
            Log.e("GameController", "Fehler beim Konvertieren der Rundennummer", e);
        }
    }
*/


    private void performeRoll() {
        Log.d("PerformeRoll", "Würfel methode wird ausgelöst " );
        if(!diceRolledThisRound) {
            mainGameActivity.requestRoll();
            diceRolledThisRound=true;
        }
    }

    public static void setUsernames(JSONObject jsonResponse){
        playerusernames = new ArrayList<>();
        playerQueue = new LinkedList<>();
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                playerusernames.add(i, username);
                Player player = new Player(username);
                playerQueue.add(player);
                if (username.equals(clientplayerUsername)) {
                    //TODO: clientplayer wird nie verwendet?
                    clientplayer = player;
                }
            }


        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei setUsernames/GameController");
        }
    }

    //TODO: evtl. Methode entfernen
/*
    private void handleUserName(JSONObject jsonResponse){
        Log.d("GameController", jsonResponse.toString() );
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                Log.d("User", "User"  + username);

                playerusernames.add(i,username);
                Player player = new Player(username);
                Log.d("GameController", "Player" + player.getName() );
                playerQueue.add(player);
                if(username.equals(clientplayerUsername)){
                    clientplayer = player;
                }

            }
            if (playerQueue.size() == REQUIRED_PLAYER_COUNT) {
                sendRequestRoundMessage();
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/GameController");
        }
    }*/

    private void handleCurrentPlayer(JSONObject jsonResponse) throws JSONException {

        Log.d("handleCurrentPlayer", "Anfang");

        try {

            roundCounter = jsonResponse.getString("turnCount");
            Log.d("TurnCount", "tun" + turnCount);

            String currentPlayerUsername = jsonResponse.getString("currentPlayer");
            Log.d("handleCurrentPlayer", "Aktueller Spieler: " + currentPlayerUsername);


            mainGameActivity.runOnUiThread(() -> maingameView.displayCurrentPlayer(currentPlayerUsername));
            maingameView.updateRoundView(Integer.parseInt(roundCounter));
            if(currentPlayerUsername.equals(clientplayerUsername) && maingameView.isMonsterInAttackZone()) {
                maingameView.doDamageToTower();
            }
            maingameView.moveMonstersInward();

            if (currentPlayerUsername.equals(clientplayerUsername)) {
                maingameView.enablePlayerAction();
                performeRoll();
            } else {
                maingameView.disablePlayerAction();
            }

        } catch (Exception e) {
            Log.e("handleCurrentPlayer", "Fehler beim Verarbeiten der aktuellen Spielerdaten", e);
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