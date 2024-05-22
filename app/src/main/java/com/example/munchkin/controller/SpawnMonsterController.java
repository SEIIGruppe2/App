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
            if (messageType.equals("SPAWN_MONSTER")) {
                handleSpawnMonsterMessage(jsonResponse);
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
            JSONObject monsterJson = message.getJSONObject("monster");
            int monsterId = monsterJson.getInt("id");
            int monsterZone = monsterJson.getInt("zone");
            int ring = monsterJson.getInt("ring");
            String monsterName = monsterJson.getString("name");
            int lifePoints = monsterJson.getInt("lifepoints");

            MonsterDTO monster = new MonsterDTO(monsterName, monsterZone, ring, lifePoints, monsterId);
            maingameView.spawnMonster(monster);

        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei der Erstellung des Monsters anhand der Informationen/SpawnMonsterController");
        }
    }



}
