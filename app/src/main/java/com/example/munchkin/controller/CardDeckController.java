package com.example.munchkin.controller;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.game.AppState;
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
        String message = MessageFormatter.createUsernameForSwitchRequestMessage();
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


    /*public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }

    private void updateCardsListWithNewCard(ActionCardDTO newCard) {
        playerHand.addCard(newCard);

        tradeCardsView.displayPlayerCards(playerHand.getCards());
        tradeCardsView.setupCardSelection();
    }

    public void tradeCardDeck(ActionCardDTO card) {

        int id = card.getId();
        String idAsString = Integer.toString(id);

        sendSwitchCardsDeckMessage(idAsString);
    }*/

    public void showMonsterMessage(String id){
        String message = MessageFormatter.createShowMonsterMessage(id);
        websocket.sendMessageToServer(message);
    }

    private void handleShowMonsters(JSONObject jsonObject){

        try{

            JSONArray monsterIdArray = jsonObject.getJSONArray("monstersid");
            ArrayList<String>  monsterList = new ArrayList<>();
            for (int i = 0; i < monsterIdArray.length(); i++) {
                monsterList.add(monsterIdArray.getString(i));

                System.out.println("handleshowmoonster"+monsterIdArray.getString(i));
            }
            MainGameActivity.monsterList = monsterList;
            carddeckView.startMonsterAttack();
        }
        catch (JSONException e) {
        throw new IllegalArgumentException("Fehler bei handleShowMonsters/Cardddeckcontroller");
    }
    //TODO: zeige die monster abhängig von der id an
    }

}



