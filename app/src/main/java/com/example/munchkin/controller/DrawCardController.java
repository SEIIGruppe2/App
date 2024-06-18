package com.example.munchkin.controller;

import com.example.munchkin.dto.ActionCardDTO;
import com.example.munchkin.messageformat.MessageFormatter;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.MainGameView;

import org.json.JSONException;
import org.json.JSONObject;

public class DrawCardController extends BaseController{

    private WebSocketClientModel websocket;
    private  MainGameView MainGameview;



    public DrawCardController(WebSocketClientModel model,  MainGameView MainGameview) {
        super(model);
        this.websocket = model;
        this.MainGameview = MainGameview;


    }



    public void drawMeassage() {
        String message = MessageFormatter.createDrawCardMessage();
        websocket.sendMessageToServer(message);
    }





    @Override
    public void handleMessage(String message) {

        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            if (messageType.equals("DRAW_CARD")) {
                handledrawcard(jsonResponse);
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/DrawCardController");
        }
    }



    private void handledrawcard(JSONObject jsonResponse) {


        try{

            int id = Integer.parseInt(jsonResponse.getString("id"));
            String name = jsonResponse.getString("name");
            int zone = Integer.parseInt(jsonResponse.getString("zone"));
            ActionCardDTO karte = new ActionCardDTO(name, zone,id);
            MainGameview.addtoList(karte);


        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handledrawcard/DrawCardController");
        }

    }
}
