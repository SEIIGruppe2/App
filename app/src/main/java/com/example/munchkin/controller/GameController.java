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

    private String clientplayerUsername = AppState.getInstance().getCurrentUser();

    private Player clientplayer;

    private static List<String> playerusernames;
    private int turnCount = 0;

    public GameController(WebSocketClientModel model, MainGameView maingameView, SpawnMonsterController spawnMonsterController,MainGameActivity mainGameActivity) {
        super(model);
        this.maingameView = maingameView;
        playerQueue = new LinkedList<>();
        this.spawnMonsterController = spawnMonsterController;
        this.mainGameActivity = mainGameActivity;
        playerusernames = new ArrayList<>();

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
        Log.d("GameController", "Current player: " +" startRoundAnfang");
        sendPlayerRollDiceMessage();
        currentPlayer = playerQueue.peek();
        maingameView.displayCurrentPlayer(currentPlayer.getName());
        if (currentPlayer.getName().equals(clientplayerUsername)) {
            maingameView.enablePlayerAction();
        } else {
            maingameView.disablePlayerAction();
        }
    }




    public void endTurn() {
        Log.d("GameController", "End of turn for: " + currentPlayer.getName());
        sendEndTurnMessage();

        currentPlayer = playerQueue.poll();
        playerQueue.offer(currentPlayer);

        if ((turnCount+1) == 4) {
            endRound();
            Log.d("TurnCount", "EndRound");
        }
        else {
            transitionToNextPlayer();
        }
        if (currentPlayer.getName().equals(clientplayerUsername)) {
            maingameView.disablePlayerAction();
        }
    }




    public void endRound() {
        Log.d("GameController", "Ending round " + roundCounter);
        isFirstRound = false;
        diceRolledThisRound = false;
        sendRequestRoundMessage();
        transitionToNextPlayer();
    }


    public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }


    public void sendEndTurnMessage() {
        String message = MessageFormatter.createEndTurnMessage();
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


    private void transitionToNextPlayer() {
        currentPlayer = playerQueue.peek();
        maingameView.displayCurrentPlayer(currentPlayer.getName());
        if (currentPlayer.getName().equals(clientplayerUsername)) {
            maingameView.enablePlayerAction();
        } else {
            maingameView.disablePlayerAction();
        }
    }


    private void handleRequestRoll(JSONObject jsonResponse){
        Log.d("GameController", jsonResponse.toString());
        try {
            if (currentPlayer == null) {
                Log.d("GameController", "Kein Spieler in der Warteschlange verfügbar.");
                return;
            }
            String usernameToRoll = jsonResponse.getString("username");
            Log.d("GameController", "Angeforderter Spieler zum Würfeln: " + usernameToRoll);
            Log.d("GameController", "Aktueller Client-Benutzername: " + clientplayerUsername);
            if (clientplayerUsername.equals(usernameToRoll)) {
                Log.d("GameController", "Client ist dran zu würfeln.");
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
            maingameView.moveMonstersInward();


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
            mainGameActivity.requestRoll();
            diceRolledThisRound = true;
        }

    }



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
    }

    private void handleCurrentPlayer(JSONObject jsonResponse) throws JSONException {

        Log.d("handleCurrentPlayer", "Anfang");
        try {
            String currentPlayerIndex = jsonResponse.getString("index");
            int currentPlayerIndexInt = Integer.parseInt(currentPlayerIndex);



            turnCount = jsonResponse.getInt("turnCount");
            Log.d("TurnCount", "tun" + turnCount);


            if (currentPlayerIndexInt >= playerusernames.size()) {
                Log.d("handleCurrentPlayer", "Index außerhalb des Bereichs: " + currentPlayerIndexInt);
                return;
            }

            String currentPlayerUsername = playerusernames.get(currentPlayerIndexInt);
            Log.d("handleCurrentPlayer", "Aktueller Spieler: " + currentPlayerUsername);


            mainGameActivity.runOnUiThread(() -> maingameView.displayCurrentPlayer(currentPlayerUsername));

            if (currentPlayerUsername.equals(clientplayerUsername)) {
                Log.d("handleCurrentPlayer", "Aktueller Client ist am Zug");
                mainGameActivity.runOnUiThread(() -> maingameView.enablePlayerAction());
            } else {
                Log.d("handleCurrentPlayer", "Ein anderer Spieler ist am Zug");
                mainGameActivity.runOnUiThread(() -> maingameView.disablePlayerAction());
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