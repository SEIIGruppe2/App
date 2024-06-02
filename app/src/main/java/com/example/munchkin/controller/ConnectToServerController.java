package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.game.AppState;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;
import org.json.JSONException;
import org.json.JSONObject;



public class ConnectToServerController extends BaseController {

    private WebSocketClientModel model;
    private ConnectToServerActivity connectToServerActivity;


    public ConnectToServerController(WebSocketClientModel model, ConnectToServerActivity connectToServerActivity) {
        super(model);
        this.model = model;
        this.connectToServerActivity=connectToServerActivity;

        //setupController();
    }

   public void setupController() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void reconnectToServer() {
        model.connectToServer(this::messageReceivedFromServer);
    }

    public void registerUserMessage(String username) {
        String message = MessageFormatter.registerUserMessage(username);
        model.sendMessageToServer(message);
    }



    private void messageReceivedFromServer(String message) {

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
                case "WAITING_FOR_PLAYERS":
                    handleWaitingForPlayersMessage(jsonResponse);
                    break;

                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/ConnectToServerController");
        }
    }


    private void handleLobbyAssignedMessage(JSONObject jsonResponse) {

        try {
            String type = jsonResponse.getString("type");
            Log.d(type, "Lobby assigned in Connect to server");


        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei der Verarbeitung der LobbyActivity-Zuweisungsnachricht", e);
        }

    }

    private void handleWaitingForPlayersMessage(JSONObject jsonResponse) {
        try {
            String type = jsonResponse.getString("type");
            String content = jsonResponse.getString("content");
            Log.d(type, content);
            connectToServerActivity.runOnUiThread(()->connectToServerActivity.transitionToLoadingScreen(AppState.getInstance().getCurrentUser()));
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei der Verarbeitung der LobbyActivity-Zuweisungsnachricht", e);
        }

    }

}
