package com.example.munchkin.controller;

import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.MainGameView;

import org.json.JSONException;
import org.json.JSONObject;

public class SpawnMonsterController extends BaseController {


    private WebSocketClientModel model;
    private MainGameView maingameView;

    public SpawnMonsterController(WebSocketClientModel model, MainGameView maingameView) {
        super(model);
        this.model = model;
        this.maingameView = maingameView;
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

            maingameView.displayMonster(monster, monsterZone);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei der Erstellung des Monsters anhand der Informationen/SpawnMonsterController");
        }

    }



}
