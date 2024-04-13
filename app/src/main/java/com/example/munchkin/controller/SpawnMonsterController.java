package com.example.munchkin.controller;

import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.GameView;

import org.json.JSONException;
import org.json.JSONObject;

public class SpawnMonsterController extends BaseController {


    private WebSocketClientModel model;
    private GameView gameView;

    public SpawnMonsterController(WebSocketClientModel model,GameView gameView) {
        super(model);
        this.model = model;
        this.gameView = gameView;
    }


    @Override
    public void handleMessage(String message) {
        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "SPAWN_MONSTER":
                    handleSpawnMonsterMessage(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/GameController");
        }
    }


    public void sendMonsterSpawnMessage(String zone) {
        String message = MessageFormatter.createSpawnMonsterMessage(zone);
        model.sendMessageToServer(message);
    }


    private void handleSpawnMonsterMessage(JSONObject message ){

        try {
            String monsterName = message.getString("name");
            int monsterZone = message.getInt("zone");
            int monsterId = message.getInt("id");
            int ring = message.getInt("ring");
            int lifePoints = message.getInt("lifePoints");

            MonsterDTO monster = new MonsterDTO(monsterName, monsterZone, ring, lifePoints, monsterId);

            gameView.displayMonster(monster, monsterZone);
        } catch (JSONException e) {
            e.printStackTrace(); // Hier besser geeignete Fehlerbehandlung
        }

    }



}
