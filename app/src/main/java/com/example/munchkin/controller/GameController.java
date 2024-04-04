package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.model.WebSocketClientModel;


public class GameController extends BaseController {

    public GameController(WebSocketClientModel model) {
        super(model);
    }

    @Override
    public void handleMessage(String message) {
        if (message.contains("PLAYER_ATTACK")) {
            handlePlayerAttackMessage(message);
        } else if (message.contains("MONSTER_ATTACK")) {
            handlMonserAttackMessage(message);
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
    private void handlePlayerAttackMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Spieler
    }

    private void handlMonserAttackMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Kartenstapel
    }



}
