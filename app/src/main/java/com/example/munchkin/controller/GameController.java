package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.MainGameView;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.JSONException;
import org.json.JSONObject;


public class GameController extends BaseController {

    MainGameView gameView;
    private MainGameActivity mainGameActivity;

    public GameController(WebSocketClientModel model, MainGameView gameView) {
        super(model);
        this.gameView=gameView;
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
                case "SWITCH_CARD_PLAYER_RESPONSE":
                    handleswitchrequest(jsonResponse);
                    break;
                default:
                    break;


            }
        }catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/GameController");
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

    private void handleswitchrequest(JSONObject message) throws JSONException {
        System.out.println("Tauschanfrage erhalten");

        gameView.tauschanfrageerhalten(message);

    }


}
