package com.example.munchkin.controller;

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
            switch (messageType) {
                case "LOBBY_ASSIGNED":
                    handleLobbyAssignedMessage(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/LoadingController ");
        }
    }



    private void handleLobbyAssignedMessage(JSONObject jsonResponse) {


    }




}
