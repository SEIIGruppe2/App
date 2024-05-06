package com.example.munchkin.controller;


import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.model.WebSocketClientModel;

import com.example.munchkin.view.LobbyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LobbyController extends BaseController{

    private WebSocketClientModel model;
    private LobbyView view;

    public static String[] usernames;

    public LobbyController(WebSocketClientModel model, LobbyView lobbyView) {
        super(model);
        this.model= model;

        this.view=lobbyView;
        usernames=new String[4];

    }


    @Override
    public void handleMessage(String message) {
        try {
        JSONObject jsonResponse = new JSONObject(message);
        String messageType = jsonResponse.getString("type");
        switch (messageType) {
            case "REQUEST_USERNAMES":
                handleRequestUsernameMessage(jsonResponse);
                break;

            default:
                break;
        }
        }
     catch(JSONException e){
            throw new IllegalArgumentException("Fehler bei handleMessage/LobbyController");
        }
    }

    public void handleRequestUsernameMessage(JSONObject jsonObject){
        try {
            JSONArray usernamesArray = jsonObject.getJSONArray("usernames");
            for (int i = 0; i < usernamesArray.length(); i++) {
                String username = usernamesArray.getString(i);
                usernames[i]=username;

            }

            view.updateUserList(usernamesArray.length());
        }catch(JSONException e){
                throw new RuntimeException(e);
            }
        }


            public void requestUsernames(){
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }






}