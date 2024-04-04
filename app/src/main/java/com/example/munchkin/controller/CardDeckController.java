package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

public class CardDeckController {



    private WebSocketClientModel model;

    public CardDeckController(WebSocketClientModel model) {
        this.model = model;
    }

    public void sendSwitchCardsPlayerMessage(String username, String abgegebeneKarte, String erhalteneKarte) {
        String message = MessageFormatter.createSwitchCardsPlayerMessage(username, abgegebeneKarte, erhalteneKarte);
        model.sendMessageToServer(message);
    }


    // Methode zum Verarbeiten der empfangenen Nachrichten vom Server
    public void handleMessage(String message) {

    }


}
