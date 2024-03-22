package com.example.munchkin.networking;

public interface WebSocketMessageHandler<T> {
    void onMessageReceived(T message);

}
