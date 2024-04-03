package com.example.munchkin.MessageFormat;



import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.controller.ConnectToServerController;

import org.json.JSONException;
import org.json.JSONObject;

public class MessageRouter {

    private ConnectToServerController serverController;
    private CardDeckController cardDeckController;
    // Fügen Sie hier weitere Controller hinzu, falls erforderlich

    public MessageRouter(ConnectToServerController serverController, CardDeckController cardDeckController) {
        this.serverController = serverController;
        this.cardDeckController = cardDeckController;
        // Initialisieren Sie hier weitere Controller, falls erforderlich
    }

    public void routeMessage(String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String messageType = jsonMessage.getString("type");

            // Entscheiden, welcher Controller die Nachricht verarbeiten soll
            switch (messageType) {
                case "PLAYER_ATTACK":

                    break;
                case "MONSTER_ATTACK":

                    break;
                case "DRAW_CARD":

                    break;
                case "SWITCH_CARDS_DECK":

                    break;
                case "SWITCH_CARDS_PLAYER":
                    cardDeckController.handleMessage(message);
                    break;

                default:

                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
