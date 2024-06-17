package com.example.munchkin.controller;


import android.util.Log;
import com.example.munchkin.messageformat.MessageFormatter;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.activity.LoadingscreenActivity;
import com.example.munchkin.model.WebSocketClientModel;
import org.json.JSONException;
import org.json.JSONObject;

public class LoadingController extends BaseController {

    LoadingscreenActivity loadingscreenActivity;
    public LoadingController(WebSocketClientModel model, LoadingscreenActivity loadingscreenActivity) {
        super(model);
        this.loadingscreenActivity=loadingscreenActivity;
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
                case "REGISTER_USERNAME":
                    handleRegisterUsernameMessage(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/LoadingController ");
        }
    }

    public void registerUserMessage(String username) {
        String message = MessageFormatter.registerUserMessage(username);
        model.sendMessageToServer(message);
    }

    private void handleRegisterUsernameMessage(JSONObject jsonResponse) {

        String accepted = "accepted";
        try{
            String serverResponse = jsonResponse.getString("response");

            if(serverResponse.equals(accepted)){
                ConnectToServerActivity.usernameaccepted=true;
                loadingscreenActivity.runOnUiThread(() -> loadingscreenActivity.startlobby());;
            }
            else {
                ConnectToServerActivity.usernameaccepted=false;
                loadingscreenActivity.runOnUiThread(() -> loadingscreenActivity.goBackToUsername());;
            }
        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleRegisterUsernameMessage/ConnectToServerController");
        }

    }

    private void handleLobbyAssignedMessage(JSONObject jsonResponse) {
        Log.d("Lobby wurde Assigned", jsonResponse.toString());
    }

}
