package com.example.munchkin.MessageFormat;



import com.example.munchkin.controller.BaseController;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.networking.WebSocketMessageHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessageRouter {

    private Map<String, BaseController> messageTypeToControllerMap = new HashMap<>();

    public void registerController(String messageType, BaseController controller) {
        messageTypeToControllerMap.put(messageType, controller);
    }

    public void routeMessage(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String messageType = jsonMessage.getString("type");
            BaseController controller = messageTypeToControllerMap.get(messageType);
            if (controller != null) {
                controller.handleMessage(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
