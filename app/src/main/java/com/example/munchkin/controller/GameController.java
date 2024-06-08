package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.Player;

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
import java.util.Queue;
import org.json.JSONArray;
import com.example.munchkin.game.AppState;



public class GameController extends BaseController implements DiceRollListener, GameEventHandler {

    private static boolean endConditionCheckedThisRound = false;
    public String currentPlayerp;
    private static String roundCounter="0";
    private MainGameView maingameView;
    private boolean diceRolledThisRound = false;
    public boolean cheatMode = false;
    private SpawnMonsterController spawnMonsterController;
    private MainGameActivity mainGameActivity;
    private static String clientplayerUsername = AppState.getInstance().getCurrentUser();
    public static HashMap<String, Integer> usernamesWithPoints = new HashMap<>();

    private static  List<String> playerusernames = new ArrayList<>();

    public GameController(WebSocketClientModel model, MainGameView maingameView, SpawnMonsterController spawnMonsterController,MainGameActivity mainGameActivity) {
        super(model);
        this.maingameView = maingameView;
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
                    handleswitchrequest(jsonResponse);
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
            maingameView.updateMonsterList(monsterId, lifepoints);

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

        maingameView.disablePlayerAction();
        diceRolledThisRound=false;

        Log.d("GameController4", "End of turn for: test2 "+roundCounter);
        sendEndTurnMessage(roundCounter);

    }

    public void cheatMode() {
        cheatMode=!cheatMode;
        sendCheatMessage(String.valueOf(cheatMode));
    }


    public void sendEndTurnMessage(String currentturn) {
        String message = MessageFormatter.createEndTurnMessage(currentturn);
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


    public void sendEndGameMessage(String hasWinner){
        String message = MessageFormatter.createEndGameMessage(hasWinner);
        model.sendMessageToServer(message);
    }

   /* private String findPlayerWithMostTrophies() {
        int maxPoints = 0;
        String winner = "";
        List<String> trophiesList = maingameView.getTrophiesList();
        for (String entry : trophiesList) {
            String[] parts = entry.split(": ");
            String username = parts[0];
            int points = Integer.parseInt(parts[1]);

            if (points > maxPoints) {
                maxPoints = points;
                winner = username;
            }
        }
        return winner;
    }

    */

    public String findPlayerWithMostTrophies(){

        String winner = " ";

        for(int i = 0; i < usernamesWithPoints.size()-1; i++ ){

            if(usernamesWithPoints.get(playerusernames.get(i)) > usernamesWithPoints.get(playerusernames.get(i+1))){
                winner = playerusernames.get(i);
            }
            else{
                winner = playerusernames.get(i+1);
            }
        }
        return winner;
    }


    public void checkEndCondition(int towerhealth) {
        int round = Integer.parseInt(roundCounter);
        if (round >= 14) {
            sendEndGameMessage("true");
        } else {
            checkTowerHealth(towerhealth);
        }
    }

    public void checkTowerHealth(int towerhealth) {
        if (towerhealth == 0) {
            sendEndGameMessage("false");
        }
    }

    private void handleEndGameMessage(JSONObject message) {

        try {
            Log.d("InEndGameMessage", message.toString());
            String hasWinner = message.getString("hasWinner");
            if (hasWinner.equals("true")) {
                String winner = findPlayerWithMostTrophies();
                if(clientplayerUsername.equals(winner))
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
            maingameView.modifyTowerLifePoints(towerHp);



            Log.d("GameController6", "UI should now be updated.");
            maingameView.updateMonsterHealth(monsterId, monsterHp);

            if (!endConditionCheckedThisRound) {
                checkEndCondition(towerHp);
                endConditionCheckedThisRound = true;
            }

        } catch (JSONException e) {
            Log.e("GameController7", "Error parsing monster attack message", e);
        }
    }

    private void handlePlayerTrophies(JSONObject jsonResponse) {
        Log.d("Trophies", "Trophies should update now");
        try{
            String playerName = jsonResponse.getString("playerName");
            int points = jsonResponse.getInt("points");
            maingameView.updateListTrophies(playerName, points);
        } catch (JSONException e) {
            Log.e("GameController", "Error parsing trophies message", e);
        }
    }

    public void sendPlayerTrophiesRequest() {
        String message = MessageFormatter.createPlayerTrophiesRequestMessage();
        model.sendMessageToServer(message);
    }

    private void handleswitchrequest(JSONObject message) throws JSONException {
        maingameView.tauschanfrageerhalten(message);

    }

    private void performeRoll() {
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
                playerusernames.add(i, username);
                Player player = new Player(username);
                playerQueue.add(player);
                usernamesWithPoints.put(username, 0); // Initialize points to 0
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
            endConditionCheckedThisRound = false;
            Log.d("inHandleCurrentPlayer", "endConditionCheckedThisRound = false;");
            String currentPlayerUsername = jsonResponse.getString("currentPlayer");
            currentPlayerp= currentPlayerUsername;

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
        return currentPlayerp != null && currentPlayerp.equals(clientplayerUsername);
    }

    public void sendAccusationMessage(String cheaterName) {
        String accusatorName = currentPlayerp;
        String message = MessageFormatter.createAccusationMessage(cheaterName, accusatorName);
        model.sendMessageToServer(message);
    }

}