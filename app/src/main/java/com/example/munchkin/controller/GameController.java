package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.Player;
import com.example.munchkin.game.GameRound;
import com.example.munchkin.interfaces.DiceRollCallback;
import com.example.munchkin.interfaces.DiceRollListener;
import com.example.munchkin.interfaces.GameEventHandler;
import com.example.munchkin.model.DiceRollModel;
import com.example.munchkin.model.WebSocketClientModel;
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


    public GameController(WebSocketClientModel model, MainGameView maingameView, SpawnMonsterController spawnMonsterController) {
        super(model);
        this.maingameView = maingameView;
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
                    handleMonsterAttackMessage(jsonResponse);
                    break;
                case "REQUEST_USERNAMES":
                    handleUserName(jsonResponse);
                    break;

                case "SWITCH_CARD_PLAYER_RESPONSE":
                    handleswitchrequest(jsonResponse);
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
        if (!playerQueue.isEmpty()) {
            currentPlayer = playerQueue.poll();
            if (!diceRolledThisRound) {
                gameRound = new GameRound(currentPlayer, this);
                gameRound.start();
                diceRolledThisRound = true;
            }
            if (!isFirstRound) {
                maingameView.doDamageToTower();
                maingameView.moveMonstersInward();
            }
            maingameView.displayCurrentPlayer(currentPlayer);
            maingameView.updateRoundView(roundCounter);
            isFirstRound = false;
        } else {
            Log.e("GameController", "Player queue is empty, cannot start round");
        }
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

    public void sendPlayerAttackMessage(String monsterId, String cardTypePlayed) {
        String message = MessageFormatter.createPlayerAttackMessage(monsterId, cardTypePlayed);
        model.sendMessageToServer(message);
    }

    public void sendMonsterAttackMessage(String monsterId, String towerId) {
        String message = MessageFormatter.createMonsterAttackMessage(monsterId, towerId);
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
            maingameView.modifyTowerLifePoints(towerHp);
            Log.d("GameController", "UI should now be updated."); //not rly for other players RIP
            String attackStatus = message.getString("attackStatus");

            // Delegate UI updates to MainGameView
            //maingameView.updateMonsterAttack(monsterId, monsterHp, attackStatus);
        } catch (JSONException e) {
            Log.e("GameController", "Error parsing monster attack message", e);
        }
    }

    private void handleswitchrequest(JSONObject message) throws JSONException {
        System.out.println("Tauschanfrage erhalten");

        maingameView.tauschanfrageerhalten(message);

    }


    private void handleUserName(JSONObject jsonResponse){
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                Player player = new Player(username);
                playerQueue.add(player);
            }
            if (playerQueue.size() == REQUIRED_PLAYER_COUNT) {
                startRound();
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/GameController");
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
        diceRollModel.rollDice(result -> {
            callback.onDiceRolled(new int[]{result});
        });
    }
}