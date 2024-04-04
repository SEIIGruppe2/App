package com.example.munchkin.controller;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.MessageFormat.MessageFormatter;

import com.example.munchkin.Player.PlayerHand;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

import org.json.JSONException;
import org.json.JSONObject;

public class CardDeckController extends BaseController {


    private PlayerHand playerHand;
    private TradeCardsView tradeCardsView;

    public CardDeckController(WebSocketClientModel model, TradeCardsView tradeCardsView) {
        super(model);
        this.playerHand = new PlayerHand();
        this.tradeCardsView = tradeCardsView;
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
                default:
                    // Handle other message types or log an unsupported message
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace(); // Consider more sophisticated error handling
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

    private void handleCardSwitchResponse(JSONObject jsonResponse) {
        try {
            String newCardName = jsonResponse.getString("newCardName");
            int newCardZone = jsonResponse.getInt("newCardZone");
            ActionCardDTO newCard = new ActionCardDTO(newCardName, newCardZone);
            updateCardsListWithNewCard(newCard);
        } catch (JSONException e) {
            e.printStackTrace(); // Consider more sophisticated error handling
        }
    }

    private void updateCardsListWithNewCard(ActionCardDTO newCard) {
        playerHand.addCard(newCard); // Update the player's hand
        // UI updates should be done on the main thread. Ensure this method is called from the UI thread.
        tradeCardsView.displayPlayerCards(playerHand.getCards());
        tradeCardsView.setupCardSelection();
    }

}



