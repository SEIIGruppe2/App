package com.example.munchkin.controller;

import com.example.munchkin.dto.ActionCardDTO;
import com.example.munchkin.messageformat.MessageFormatter;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.MainGameView;

import org.json.JSONException;
import org.json.JSONObject;

public class DrawCardController extends BaseController{

    private final WebSocketClientModel websocket;
    private final MainGameView mainGameView;



    public DrawCardController(WebSocketClientModel model,  MainGameView mainGameView) {
        super(model);
        this.websocket = model;
        this.mainGameView = mainGameView;


    }



    public void drawMessage() {
        String message = MessageFormatter.createDrawCardMessage();
        websocket.sendMessageToServer(message);
    }





    @Override
    public void handleMessage(String message) {

        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            if (messageType.equals("DRAW_CARD")) {
                handleDrawCard(jsonResponse);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/DrawCardController");
        }
    }



    private void handleDrawCard(JSONObject jsonResponse) {


        try{

            int id = Integer.parseInt(jsonResponse.getString("id"));
            String name = jsonResponse.getString("name");
            int zone = Integer.parseInt(jsonResponse.getString("zone"));
            ActionCardDTO card = new ActionCardDTO(name, zone,id);
            mainGameView.addToList(card);


        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handledrawcard/DrawCardController");
        }

    }
}
