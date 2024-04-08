package com.example.munchkin.networking;

import android.util.Log;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


import com.example.munchkin.model.WebSocketClientModel;

public class WebSocketClient {

    /**
     * localhost from the Android emulator is reachable as 10.0.2.2
     * https://developer.android.com/studio/run/emulator-networking
     */

    private final String WEBSOCKET_URI = "ws://10.0.2.2:8080/game";

    private WebSocket webSocket;

    private WebSocketClientModel model;

    private static WebSocketClient INSTANCE;


    private WebSocketClient(WebSocketClientModel model) {
        this.model = model;
    }

    public static WebSocketClient getINSTANCE(WebSocketClientModel model){

        if(INSTANCE == null){
            INSTANCE = new WebSocketClient(model);
        }
        return INSTANCE;
    }

    public void connectToServer(WebSocketMessageHandler<String> messageHandler) {
        if (messageHandler == null)
            throw new IllegalArgumentException("messageHandler is required");

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(WEBSOCKET_URI)
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                Log.d("Network", "connected");
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {

                model.notifyObservers(text);
            }

            @Override
            public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason) {
                // WebSocket connection closed
            }

            @Override
            public void onFailure(@NonNull WebSocket webSocket,@NonNull Throwable t, Response response) {
                Log.d("Network", "connection failure");
            }
        });
    }

    public void sendMessageToServer(String msg) {
        System.out.println("WebSocketClient"+msg);
        webSocket.send(msg);
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            webSocket.close(1000, "Closing");
        } finally {
            super.finalize();
        }
    }

    // Simple method to demonstrate unit testing and test coverage with sonarcloud
    public static String concatenateStrings(String first, String second) {
        return first + " " + second;
    }



}
