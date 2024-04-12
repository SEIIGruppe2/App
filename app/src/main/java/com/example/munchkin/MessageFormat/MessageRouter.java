package com.example.munchkin.MessageFormat;



import android.util.Log;

import com.example.munchkin.controller.BaseController;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.networking.WebSocketMessageHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MessageRouter {

    private static Map<String, BaseController> messageTypeToControllerMap = new HashMap<>();

    public void registerController(String messageType, BaseController controller) {
        messageTypeToControllerMap.put(messageType, controller);


    }

    public static void routeMessage(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String messageType = jsonMessage.getString("type");
            messageTypeToControllerMap.forEach((key, value) -> System.out.println(key + " -> " + value));
            BaseController controller = messageTypeToControllerMap.get(messageType);
            System.out.println("Route message to" + messageType);


            if (controller != null) {
                controller.handleMessage(message);
            }
            else
            {
                Log.e("Network","Message not routable");
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei routeMessage");
        }
    }

}
