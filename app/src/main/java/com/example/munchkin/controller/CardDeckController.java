package com.example.munchkin.controller;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.CarddeckView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardDeckController extends BaseController {

    WebSocketClientModel websocket;
    public PlayerHand playerHand;
    private CarddeckView carddeckView;



    private CarddeckActivity carddeckActivity;

    public CardDeckController(WebSocketClientModel model, CarddeckView carddeckView) {
        super(model);
        websocket=model;
        this.playerHand = new PlayerHand();
        this.carddeckView = carddeckView;
    }

    public void switchcardMeassage(String username, String id) {

        ActionCardDTO toremove = new ActionCardDTO();
        for(ActionCardDTO a: playerHand.getCards()){
            if(a.getId() == Integer.parseInt(id)){
                toremove=a;
            }
        }
        playerHand.removeCard(toremove);

        if(username.equals("Kartenstapel")) {
            String message = MessageFormatter.createSwitchCardsDeckMessage(id);
            websocket.sendMessageToServer(message);
        }
        else{

                String message = MessageFormatter.createSwitchCardsPlayerMessage(username, "null", id);
                websocket.sendMessageToServer(message);
            }


    }

    public void switchcardMeassagepassive(String text, String id) {

        ActionCardDTO toremove = new ActionCardDTO();
        for(ActionCardDTO a: playerHand.getCards()){
            if(a.getId() == Integer.parseInt(id)){
                toremove=a;
            }
        }
        playerHand.removeCard(toremove);
            String message = MessageFormatter.createSwitchCardsPlayerMessage(text,"null",id);
            websocket.sendMessageToServer(message);

    }
    public void getactiveusers(){
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }

    @Override
    public void handleMessage(String message) {
        try {
            System.out.println("Carddeckcontroller message received");
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "SWITCH_CARD_PLAYER_RESPONSE":
                    updatehanddeck(jsonResponse);
                    break;
                case "SWITCH_CARD_DECK_RESPONSE":
                    updatehanddeck(jsonResponse);
                    break;
                case "REQUEST_USERNAMES":
                    handleUserName(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/CardDeckController");
        }
    }

    private void updatehanddeck(JSONObject jsonResponse) {

        System.out.println("Update Handdeck checked");

        try{

            int id = Integer.parseInt(jsonResponse.getString("id"));
            String name = jsonResponse.getString("name");
            int zone = Integer.parseInt(jsonResponse.getString("zone"));
            ActionCardDTO karte = new ActionCardDTO(name, zone,id);
            System.out.println(karte.getName());
            playerHand.addCard(karte);
            carddeckView.updatescreen();

        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei updatehanddeck/Cardddeckcontroller");
        }

    }
    private void handleUserName(JSONObject jsonResponse){
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            ArrayList<String> usernamesList = new ArrayList<>();
            for (int i = 0; i < usernamesArray.length(); i++) {
                usernamesList.add(usernamesArray.getString(i));

            }
            carddeckView.usernames = usernamesList;


        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/CardDeckController");
        }
    }




}



