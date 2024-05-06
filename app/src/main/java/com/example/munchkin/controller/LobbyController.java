package com.example.munchkin.controller;

import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.CarddeckView;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.LobbyView;

import org.json.JSONException;
import org.json.JSONObject;

public class LobbyController extends BaseController{

    private WebSocketClientModel model;
    private LobbyView view;

    public LobbyController(WebSocketClientModel model, LobbyView lobbyView) {
        super(model);
        this.model= model;

        this.view=lobbyView;

    }


    @Override
    public void handleMessage(String message) {
        try {
        JSONObject jsonResponse = new JSONObject(message);
        String messageType = jsonResponse.getString("type");
        switch (messageType) {
            case "REQUEST_USERNAMES":
                handleRegisterUsernameMessage(jsonResponse);
                break;

            default:
                break;
        }
        }
     catch(JSONException e){
            throw new IllegalArgumentException("Fehler bei handleMessage/LobbyController");
        }
    }

    public void handleRegisterUsernameMessage(JSONObject jsonObject){

    }






}
