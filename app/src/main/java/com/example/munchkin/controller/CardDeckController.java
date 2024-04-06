package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

public class CardDeckController extends BaseController {


    public CardDeckController(WebSocketClientModel model) {
        super(model);
    }

    @Override
    public void handleMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten, die spezifisch für den Kartenstapel sind
        if (message.contains("SWITCH_CARDS_PLAYER")) {
            handlePlayerMessage(message);
        } else if (message.contains("SWITCH_CARDS_DECK")) {
            handleDeckMessage(message);
        }
    }


    public void sendSwitchCardsPlayerMessage(String username, String abgegebeneKarte, String erhalteneKarte) {
        String message = MessageFormatter.createSwitchCardsPlayerMessage(username, abgegebeneKarte, erhalteneKarte);
        model.sendMessageToServer(message);
    }


    public void sendSwitchCardsDeckMessage(String card) {
        String message = MessageFormatter.createSwitchCardsDeckMessage(card);
        model.sendMessageToServer(message);
    }




    // Methode zum Verarbeiten der empfangenen Nachrichten vom Server
    private void handlePlayerMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Spieler
    }

    private void handleDeckMessage(String message) {
        // Implementiere die Logik zum Verarbeiten der Nachrichten für den Kartenstapel
    }
}



