package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;
import com.example.munchkin.Player.PlayerHand;
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
        System.out.println(message);
        model.sendMessageToServer(message);
    }







    @Override
    public void handleMessage(String message) {

        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "DRAW_CARD":
                    handledrawcard(jsonResponse);
                    break;
                default:
                    break;
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
            view.addtoList(karte);


        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handledrawcard/DrawCardController");
        }

    }
}