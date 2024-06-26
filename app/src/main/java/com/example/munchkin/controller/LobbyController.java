package com.example.munchkin.controller;


import com.example.munchkin.messageformat.MessageFormatter;

import com.example.munchkin.model.WebSocketClientModel;

import com.example.munchkin.view.LobbyView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LobbyController extends BaseController{

    private WebSocketClientModel clientModel;
    private LobbyView lobbyView;

    private static String[] usernames;

    public LobbyController(WebSocketClientModel model, LobbyView lobbyView) {
        super(model);
        this.clientModel = model;

        this.lobbyView =lobbyView;
        usernames=new String[4];

    }


    @Override
    public void handleMessage(String message) {
        try {
        JSONObject jsonResponse = new JSONObject(message);
        String messageType = jsonResponse.getString("type");
            if (messageType.equals("REQUEST_USERNAMES")) {
                handleRequestUsernameMessage(jsonResponse);
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
                getUsernames()[i]=username;

            }
            if(usernamesArray.length()==4){
                GameController.setUsernames(jsonObject);
            }
            lobbyView.updateUserList(usernamesArray.length());



        }catch(JSONException e){
                throw new IllegalArgumentException(e);
            }
        }

        public void requestUsernames(){
        String message = MessageFormatter.createUsernameRequestMessage();
        clientModel.sendMessageToServer(message);
    }

    public static String[] getUsernames() {
        return usernames;
    }


}