package com.example.munchkin.controller;

import android.util.Log;

import com.example.munchkin.dto.ActionCardDTO;
import com.example.munchkin.messageformat.MessageFormatter;

import com.example.munchkin.player.PlayerHand;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.CardDeckView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardDeckController extends BaseController {

    WebSocketClientModel websocket;
    public static PlayerHand playerHand = new PlayerHand();
    private static CardDeckView carddeckView;


    public CardDeckController(WebSocketClientModel model, CardDeckView carddeckView) {
        super(model);
        websocket=model;
        CardDeckController.carddeckView = carddeckView;
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

                String message = MessageFormatter.createSwitchCardsPlayerMessage(username, id, "null");
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
        String message = MessageFormatter.createUsernameForSwitchRequestMessage();
        model.sendMessageToServer(message);
    }

    @Override
    public void handleMessage(String message) {
        try {
            Log.d("handleMassage", "CardDeckController message received");
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "SWITCH_CARD_PLAYER_RESPONSE":
                    updatehanddeck(jsonResponse);
                    break;
                case "SWITCH_CARD_DECK_RESPONSE":
                    updatehanddeck(jsonResponse);
                    break;
                case "REQUEST_USERNAMES_SWITCH":
                    handleUserName(jsonResponse);
                    break;
                case "SHOW_MONSTERS":
                    handleShowMonsters(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleMessage/CardDeckController");
        }
    }

    private void updatehanddeck(JSONObject jsonResponse) {



        try{

            int id = Integer.parseInt(jsonResponse.getString("id"));
            String name = jsonResponse.getString("name");
            int zone = Integer.parseInt(jsonResponse.getString("zone"));
            ActionCardDTO karte = new ActionCardDTO(name, zone,id);
            playerHand.addCard(karte);
            carddeckView.updateScreen();

        }
        catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei updatehanddeck/Cardddeckcontroller");
        }

    }
    private void handleUserName(JSONObject jsonResponse) {
        try {
            Log.e("Handleusername", "test");
            JSONArray usernamesArray = jsonResponse.getJSONArray("usernames");
            ArrayList<String> usernamesList = new ArrayList<>();
            if (!usernamesArray.getString(0).equals("no users found")) {
                for (int i = 0; i < usernamesArray.length(); i++) {
                    usernamesList.add(usernamesArray.getString(i));
                }
            }
            carddeckView.setUsernames(usernamesList);
        } catch (JSONException e) {
            throw new IllegalArgumentException("Fehler bei handleUserName/CardDeckController");
        }
    }


    public void showMonsterMessage(String id){
        String message = MessageFormatter.createShowMonsterMessage(id);
        websocket.sendMessageToServer(message);
    }

    private static void handleShowMonsters(JSONObject jsonObject){

        try{

            JSONArray monsterIdArray = jsonObject.getJSONArray("monstersid");
            ArrayList<String>  monsterList = new ArrayList<>();
            for (int i = 0; i < monsterIdArray.length(); i++) {
                monsterList.add(monsterIdArray.getString(i));

            }
            MainGameActivity.setMonsterList(monsterList);
            carddeckView.startMonsterAttack();
        }
        catch (JSONException e) {
        throw new IllegalArgumentException("Fehler bei handleShowMonsters/Cardddeckcontroller");
    }

    }

}



