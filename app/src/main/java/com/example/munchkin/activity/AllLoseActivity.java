package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.networking.WebSocketClient;

public class AllLoseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_lose_screen);

        Button homeButton = findViewById(R.id.homeButtonAllLose);

        WebSocketClient webSocketClient = WebSocketClient.getINSTANCE(new WebSocketClientModel());
        webSocketClient.closeWebSocket();

        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });







    }
}
