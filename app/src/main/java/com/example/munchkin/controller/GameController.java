package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.messageformat.MessageFormatter;
import com.example.munchkin.player.Player;

import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.MainGameActivity;

import com.example.munchkin.interfaces.DiceRollCallback;
import com.example.munchkin.interfaces.DiceRollListener;
import com.example.munchkin.interfaces.GameEventHandler;
import com.example.munchkin.model.DiceRollModel;
import com.example.munchkin.model.WebSocketClientModel;

import com.example.munchkin.view.MainGameView;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import org.json.JSONArray;
import com.example.munchkin.game.AppState;



public class GameController extends BaseController implements DiceRollListener, GameEventHandler {

    private static boolean gameEnded = false;
    public String currentPlayer;
    private static String roundCounter="0";
    private final MainGameView mainGameView;
    private boolean diceRolledThisRound = false;
    private boolean cheatMode = false;
    private final SpawnMonsterController spawnMonsterController;
    private final MainGameActivity mainGameActivity;
    private static final String CLIENT_PLAYER_USERNAME = AppState.getInstance().getCurrentUser();
    public static HashMap<String, Integer> usernamesWithPoints = new HashMap<>();

    private static final List<String> PLAYER_USERNAMES = new ArrayList<>();

    public GameController(WebSocketClientModel model, MainGameView mainGameView, SpawnMonsterController spawnMonsterController, MainGameActivity mainGameActivity) {
        super(model);
        this.mainGameView = mainGameView;
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
                case "MONSTER_ATTACK":
                    handleMonsterAttackMessage(jsonResponse);
                    break;
                case "SWITCH_CARD_PLAYER_RESPONSE1":
                    handleSwitchRequest(jsonResponse);
                    break;
                case "CURRENT_PLAYER":
                    handleCurrentPlayer(jsonResponse);
                    break;
                case "CARD_ATTACK_MONSTER":
                    handleCardAttackMonster(jsonResponse);
                    break;
                case "END_GAME":
                    handleEndGameMessage(jsonResponse);
                    break;
                case "PLAYER_TROPHIES":
                    handlePlayerTrophies(jsonResponse);
                    break;
                default:
                    break;


            }
        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/GameController");
        }
    }

    public void handleCardAttackMonster(JSONObject jsonObject){
        try{
            String monsterId = jsonObject.getString("monsterid");
            int lifepoints = jsonObject.getInt("lifepoints");
            mainGameView.updateMonsterList(monsterId, lifepoints);

        } catch (JSONException e) {
            Log.e("GameController1", "Error parsing monster attack message", e);
        }
    }


    public void startRound() {
        Log.d("GameController2", "Current player: " +" startRoundAnfang" + roundCounter);
        sendEndTurnMessage(roundCounter);

    }


    public void endTurn() {
        Log.d("GameController3", "End of turn for: test " );

        mainGameView.disablePlayerAction();
        diceRolledThisRound=false;

        Log.d("GameController4", "End of turn for: test2 "+roundCounter);
        sendEndTurnMessage(roundCounter);

    }

    public void cheatModeMethod() {
        cheatMode=!cheatMode;
        sendCheatMessage(String.valueOf(cheatMode));
    }


    public void sendEndTurnMessage(String currentTurn) {
        String message = MessageFormatter.createEndTurnMessage(currentTurn);
        model.sendMessageToServer(message);
    }

    public void sendCheatMessage(String cheatMode) {
        String message = MessageFormatter.createCheaterMessage(cheatMode);
        model.sendMessageToServer(message);
    }


    public void sendMonsterAttackMessage(String monsterId, String towerId) {
      String message = MessageFormatter.createMonsterAttackMessage(monsterId, towerId);
        model.sendMessageToServer(message);
    }

    public void cardAttackMonsterMessage(String monsterId, String cardId){
        String message = MessageFormatter.createCardAttackMonsterMessage(monsterId, cardId);
        model.sendMessageToServer(message);
    }


    public void sendEndGameMessage(String hasWinner) {
        if (!gameEnded) {
            gameEnded = true;
            String message = MessageFormatter.createEndGameMessage(hasWinner);
            model.sendMessageToServer(message);
            Log.d("Nach send message an Server", message);
        }
    }


    public String findPlayerWithMostTrophies() {
        String winner = "";
        int maxPoints = -1;

        for (Map.Entry<String, Integer> entry : usernamesWithPoints.entrySet()) {
            if (entry == null || entry.getValue() == null) {
                continue;
            }

            if (entry.getValue() > maxPoints) {
                maxPoints = entry.getValue();
                winner = entry.getKey();
            }
        }

        return winner;
    }



    public void checkEndCondition(int towerHealth) {
        if (gameEnded) {
            return;
        }
        if (towerHealth == 0) {
            checkTowerHealth(towerHealth);
        }
        else
        {
            int round = Integer.parseInt(roundCounter);
            if (round >= 12) {
                sendEndGameMessage("true");
            }
        }
    }

    public void checkTowerHealth(int towerHealth) {
        if (towerHealth == 0) {
            sendEndGameMessage("false");
        }
    }

    private void handleEndGameMessage(JSONObject message) {

        try {
            Log.d("InEndGameMessage", message.toString());
            String hasWinner = message.getString("hasWinner");
            if (hasWinner.equals("true")) {
                String winner = findPlayerWithMostTrophies();
                if(CLIENT_PLAYER_USERNAME.equals(winner))
                    mainGameActivity.navigateToWinScreen(winner);
                else{
                    Log.d("InLoseZweig", message.toString());
                    mainGameActivity.navigateToLoseScreen(winner);
                }
            } else {
                Log.d("InAllLoseZweig", message.toString());
                mainGameActivity.navigateToAllLoseScreen();
            }
        } catch (JSONException e) {
            Log.e("GameController", "Error in ENDGAME-Handler", e);
        }
    }


    private void handleMonsterAttackMessage(JSONObject message) {
        Log.d("MonsterAttack", "Handling monster attack message: " + message);
        try {
            String monsterId = message.getString("monsterId");
            int monsterHp = message.getInt("monsterHp");
            int towerHp = message.getInt("towerHp");
            Log.d("GameController5", "Tower HP received: " + towerHp);
            mainGameView.modifyTowerLifePoints(towerHp);




            Log.d("GameController6", "UI should now be updated.");
            mainGameView.updateMonsterHealth(monsterId, monsterHp);


            checkEndCondition(towerHp);

        } catch (JSONException e) {
            Log.e("GameController7", "Error parsing monster attack message", e);
        }
    }

    private void handlePlayerTrophies(JSONObject jsonResponse) {
        Log.d("Trophies", "Trophies should update now");
        try{
            String playerName = jsonResponse.getString("playerName");
            int points = jsonResponse.getInt("points");
            usernamesWithPoints.put(playerName, points);
            mainGameView.updateListTrophies(playerName, points);
            Log.d("Player Points", playerName + points);
        } catch (JSONException e) {
            Log.e("GameController", "Error parsing trophies message", e);
        }
    }

    public void sendPlayerTrophiesRequest() {
        String message = MessageFormatter.createPlayerTrophiesRequestMessage();
        model.sendMessageToServer(message);
    }

    private void handleSwitchRequest(JSONObject message) throws JSONException {
        mainGameView.tauschanfrageErhalten(message);

    }

    private void performRoll() {
        if(!diceRolledThisRound) {
            mainGameActivity.requestRoll();
            diceRolledThisRound=true;
        }
    }

    public static void setUsernames(JSONObject jsonResponse){

        Queue<Player> playerQueue = new LinkedList<>();
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                PLAYER_USERNAMES.add(i, username);
                Player player = new Player(username);
                playerQueue.add(player);
                usernamesWithPoints.put(username, 0);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei setUsernames/GameController");
        }
    }

    private void handleCurrentPlayer(JSONObject jsonResponse) throws JSONException {
        String handleCurrentPlayerString = "handleCurrentPlayer";
        Log.d(handleCurrentPlayerString, "Anfang");


        try {
            roundCounter = jsonResponse.getString("turnCount");
            Log.d("inHandleCurrentPlayer", "endConditionCheckedThisRound = false;");
            String currentPlayerUsername = jsonResponse.getString("currentPlayer");
            currentPlayer = currentPlayerUsername;

            mainGameActivity.runOnUiThread(() -> mainGameView.displayCurrentPlayer(currentPlayerUsername));
            mainGameView.updateRoundView(Integer.parseInt(roundCounter));
            if(currentPlayerUsername.equals(CLIENT_PLAYER_USERNAME) && mainGameView.isMonsterInAttackZone()) {
                mainGameView.doDamageToTower();
            }
            mainGameView.moveMonstersInward();


            if (currentPlayerUsername.equals(CLIENT_PLAYER_USERNAME)) {
                mainGameView.enablePlayerAction();
                CarddeckActivity.switchDone = false;
                mainGameActivity.runOnUiThread(mainGameActivity::sendMessage);
                performRoll();
            } else {
                mainGameView.disablePlayerAction();
            }


        } catch (Exception e) {
            Log.e(handleCurrentPlayerString, "Fehler beim Verarbeiten der aktuellen Spielerdaten", e);
        }

    }

    @Override
    public void onDiceRolled(int[] results) {
        for (int result : results) {
            spawnMonsterController.sendMonsterSpawnMessage(Integer.toString(result));
        }

    }
    @Override
    public void requestDiceRoll(DiceRollCallback callback) {
        DiceRollModel diceRollModel = new DiceRollModel();
        diceRollModel.rollDice(result -> callback.onDiceRolled(new int[]{result}));
    }

    public boolean currentPlayer(){
        return currentPlayer != null && currentPlayer.equals(CLIENT_PLAYER_USERNAME);
    }

    public void sendAccusationMessage(String cheaterName) {
        String accusatoryName = currentPlayer;
        String message = MessageFormatter.createAccusationMessage(cheaterName, accusatoryName);
        model.sendMessageToServer(message);
    }

}