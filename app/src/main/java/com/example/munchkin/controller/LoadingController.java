package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.model.WebSocketClientModel;

import org.json.JSONException;
import org.json.JSONObject;

public class LoadingController extends BaseController {


    public LoadingController(WebSocketClientModel model) {
        super(model);
    }


    @Override
    public void handleMessage(String message) {
        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            if (messageType.equals("LOBBY_ASSIGNED")) {
                handleLobbyAssignedMessage(jsonResponse);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/LoadingController ");
        }
    }



    private void handleLobbyAssignedMessage(JSONObject jsonResponse) {
        Log.d("Lobby wurde Assigned", jsonResponse.toString());
    }

}
