package com.example.munchkin.controller;

import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.observer.ModelObserver;


public class ConnectToServerController implements ModelObserver  {

    private WebSocketClientModel model;
    private ConnectToServerView view;

    public ConnectToServerController(WebSocketClientModel model, ConnectToServerView view) {
        this.model = model;
        this.view = view;
        model.addObserver(this);
        setupController();
    }

    private void setupController() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void sendMessage() {
        model.sendMessageToServer("test message");
    }

    private void messageReceivedFromServer(String message) {
        view.updateServerResponse(message);
    }

    @Override
    public void update(String message) {
        view.updateServerResponse(message);
    }
}
