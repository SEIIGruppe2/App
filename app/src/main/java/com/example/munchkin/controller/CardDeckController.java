package com.example.munchkin.controller;

import com.example.munchkin.MessageFormat.MessageFormatter;

public class CardDeckController {

    private ConnectToServerController serverController;

    public CardDeckController(ConnectToServerController serverController) {
        this.serverController = serverController;
    }

    public void sendSwitchCardsPlayerMessage(String username, String abgegebeneKarte, String erhalteneKarte) {
        String message = MessageFormatter.createSwitchCardsPlayerMessage(username, abgegebeneKarte, erhalteneKarte);
        serverController.sendMessage(message);
    }


    // Methode zum Verarbeiten der empfangenen Nachrichten vom Server
    public void handleMessage(String message) {
        // Hier wird dann die view angesprochen um den layer etc. zu verändern
    }

    //Beispiel IMPL
    /*
    // Beispiel: Nachricht parsen und basierend darauf die View ändern
    try {
        JSONObject jsonMessage = new JSONObject(message);
        String messageType = jsonMessage.getString("type");

        // Beispiel: Wenn die Nachricht den Typ "UPDATE_VIEW" hat, ändere die View entsprechend
        if (messageType.equals("UPDATE_VIEW")) {
            // Ändern Sie hier die View entsprechend Ihren Anforderungen
            // Zum Beispiel können Sie eine Methode in der View aufrufen, um sie zu aktualisieren
            view.updateView();
        }
    } catch (JSONException e) {
        e.printStackTrace();
    }
        */
}
