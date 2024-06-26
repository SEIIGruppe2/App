package com.example.munchkin.messageformat;



import android.util.Log;

import com.example.munchkin.controller.BaseController;
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
            BaseController controller = messageTypeToControllerMap.get(messageType);


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
