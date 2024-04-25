package com.example.munchkin.controller;

import android.widget.Button;

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
            int monsterId = message.getInt("id");
            int monsterZone = message.getInt("zone");
            int ring = message.getInt("ring");
            String monsterName = message.getString("name");
            int lifePoints = message.getInt("lifepoints");

            MonsterDTO monster = new MonsterDTO(monsterName, monsterZone, ring, lifePoints, monsterId);

            maingameView.spawnMonster(monster.getZone());

        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei der Erstellung des Monsters anhand der Informationen/SpawnMonsterController");
        }

    }



}
