package com.example.munchkin.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.R;



public class ConnectToServerView {

    private ConnectToServerActivity connectToServerActivity;
    private TextView textViewServerResponse;

    private EditText editTextUsername;

    public ConnectToServerView(ConnectToServerActivity connectToServerActivity) {
        this.connectToServerActivity = connectToServerActivity;
        this.textViewServerResponse = connectToServerActivity.findViewById(R.id.textViewResponse);
        this.editTextUsername = connectToServerActivity.findViewById(R.id.editTextUsername);
        setupUI();
    }

    private void setupUI() {
        Button buttonSendMsg = connectToServerActivity.findViewById(R.id.buttonConnect);
        buttonSendMsg.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            connectToServerActivity.sendMessage(username);
        });


        Button buttonReconnect = connectToServerActivity.findViewById(R.id.buttonReconnect);
        buttonReconnect.setOnClickListener(v -> connectToServerActivity.reconnectToServer());

    }

    public void updateServerResponse(String message) {
        textViewServerResponse.setText(message);
    }







}
