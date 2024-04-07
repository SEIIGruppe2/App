package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.DrawView;

import org.json.JSONException;
import org.json.JSONObject;

public class DrawCardController extends BaseController{

    private WebSocketClientModel model;
    private DrawView view;


    public DrawCardController(WebSocketClientModel model, DrawView view) {
        super(model);
        this.model = model;
        this.view = view;

    }



    public void drawMeassage() {
        String message = MessageFormatter.createDrawCardMessage();
        model.sendMessageToServer(message);
    }




    //TODO: warten auf antwort vom server - überprüfe ob wir das brauchen??
    private void messageReceivedFromServer(String message) {

    }

    @Override
    public void handleMessage(String message) {
        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "DRAW_CARD":
                    handleDrawCards(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/DrawCardController");
        }
    }



    private void handleDrawCards(JSONObject jsonResponse) {
        //TODO gehört noch abgeändert die json Response muss umgewandelt werden


        String accepted = "accepted";
        try{
            String serverResponse = jsonResponse.getString("response");

            if(serverResponse.equals(accepted)){
                view.updateServerResponse(serverResponse);
            }
            else {
                throw new IllegalArgumentException("User konnte nicht erstellt werden");
            }
        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleRegisterUsernameMessage/DrawCardController");
        }

    }
}
