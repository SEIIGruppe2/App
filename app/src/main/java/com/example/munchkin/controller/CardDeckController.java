package com.example.munchkin.controller;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.activity.TradeCardsActivity;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardDeckController extends BaseController {


    private PlayerHand playerHand;
    private TradeCardsView tradeCardsView;

    private TradeCardsActivity tradeCardsActivity;

    public CardDeckController(WebSocketClientModel model, TradeCardsView tradeCardsView) {
        super(model);
        this.playerHand = new PlayerHand();
        this.tradeCardsView = tradeCardsView;
    }


    public void setTradeCardsView(TradeCardsView view) {
        this.tradeCardsView = view;
    }

    @Override
    public void handleMessage(String message) {
        try {
            JSONObject jsonResponse = new JSONObject(message);
            String messageType = jsonResponse.getString("type");
            switch (messageType) {
                case "SWITCH_CARDS_PLAYER_RESPONSE":
                case "SWITCH_CARDS_DECK_RESPONSE":
                    handleCardSwitchResponse(jsonResponse);
                    break;
                case "REQUEST_USERNAMES":
                    handleUserName(jsonResponse);
                    break;
                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendSwitchCardsPlayerMessage(String username, String givenCard, String receivedCard) {
        String message = MessageFormatter.createSwitchCardsPlayerMessage(username, givenCard, receivedCard);
        model.sendMessageToServer(message);
    }

    public void sendSwitchCardsDeckMessage(String card) {
        String message = MessageFormatter.createSwitchCardsDeckMessage(card);
        model.sendMessageToServer(message);
    }

    public void requestUsernames() {
        String message = MessageFormatter.createUsernameRequestMessage();
        model.sendMessageToServer(message);
    }



    private void handleUserName(JSONObject jsonResponse){
        try {
            JSONArray usernamesArray = jsonResponse.getJSONArray("Usernames");
            ArrayList<String> usernamesList = new ArrayList<>();
            for (int i = 0; i < usernamesArray.length(); i++) {
                usernamesList.add(usernamesArray.getString(i));
            }

            tradeCardsActivity.runOnUiThread(() -> {
                tradeCardsView.updateUsernamesSpinner(usernamesList);
            });
        } catch (JSONException e) {
            e.printStackTrace(); // Handle error
        }
    }



    private void handleCardSwitchResponse(JSONObject jsonResponse) {
        try {
            String newCardName = jsonResponse.getString("CardName");
            int newCardZone = jsonResponse.getInt("CardZone");
            int cardID = jsonResponse.getInt("ID");
            ActionCardDTO newCard = new ActionCardDTO(newCardName, newCardZone,cardID);
            updateCardsListWithNewCard(newCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    }



}



