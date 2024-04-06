package com.example.munchkin.controller;

import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;



public class ConnectToServerController {

    private WebSocketClientModel model;
    private ConnectToServerView view;

    public ConnectToServerController(WebSocketClientModel model, ConnectToServerView view) {
        this.model = model;
        this.view = view;
        setupController();
    }

    private void setupController() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void reconnectToServer() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void sendMessage(String username) {
        model.sendMessageToServer(username);
    }


    private void messageReceivedFromServer(String message) {
        view.updateServerResponse(message);
    }


}
