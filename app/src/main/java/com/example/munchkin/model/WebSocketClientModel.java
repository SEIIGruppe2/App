package com.example.munchkin.model;


import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.networking.WebSocketClient;

import com.example.munchkin.networking.WebSocketMessageHandler;
import com.example.munchkin.observer.ModelObserver;

import java.util.ArrayList;
import java.util.List;


public class WebSocketClientModel {

    private WebSocketClient networkHandler;
    private MessageRouter messageRouter; // For routing messages

    // Removed the observer list and related methods (addObserver, removeObserver)

    public void setMessageRouter(MessageRouter router) { // Sets the router
        this.messageRouter = router;
    }

    // Updated notify method to route messages directly, without looping through observers
    public void notifyObservers(String message) {
        if(messageRouter != null) {
            messageRouter.routeMessage(message); // Directly route the message
        } else {
            // Error handling if the router is null
        }
    }

    // Constructor and other existing methods remain the same

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
