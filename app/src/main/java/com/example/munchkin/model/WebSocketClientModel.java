package com.example.munchkin.model;


import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.networking.WebSocketClient;

import com.example.munchkin.networking.WebSocketMessageHandler;


import java.util.ArrayList;
import java.util.List;


public class WebSocketClientModel {

    private WebSocketClient networkHandler;
    private MessageRouter messageRouter;


    public void setMessageRouter(MessageRouter router) { // Sets the router
        this.messageRouter = router;
    }

    public void notifyObservers(String message) {
        if(messageRouter != null) {
            messageRouter.routeMessage(message);
        } else {
            throw new IllegalStateException("MessageRouter not initialized. Cannot route messages without a valid MessageRouter.");
        }
    }


    public WebSocketClientModel() {
        networkHandler = WebSocketClient.getINSTANCE(this);
    }

    public void connectToServer(WebSocketMessageHandler<String> messageHandler) {
        networkHandler.connectToServer(messageHandler);
    }

    public void sendMessageToServer(String msg) {
        networkHandler.sendMessageToServer(msg);
    }



}
