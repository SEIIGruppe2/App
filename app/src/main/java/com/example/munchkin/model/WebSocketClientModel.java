package com.example.munchkin.model;


import com.example.munchkin.networking.WebSocketClient;

import com.example.munchkin.networking.WebSocketMessageHandler;
import com.example.munchkin.observer.ModelObserver;

import java.util.ArrayList;
import java.util.List;


public class WebSocketClientModel {

    private WebSocketClient networkHandler;


    private List<ModelObserver> observers = new ArrayList<>();

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(String message) {
        for (ModelObserver observer : observers) {
            observer.update(message);
        }
    }

    public WebSocketClientModel() {
        networkHandler = new WebSocketClient(this);
    }

    public void connectToServer(WebSocketMessageHandler<String> messageHandler) {
        networkHandler.connectToServer(messageHandler);
    }

    public void sendMessageToServer(String msg) {
        networkHandler.sendMessageToServer(msg);
    }


}
