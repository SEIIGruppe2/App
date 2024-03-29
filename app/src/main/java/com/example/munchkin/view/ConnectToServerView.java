package com.example.munchkin.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.munchkin.MainActivity;
import com.example.munchkin.R;

public class ConnectToServerView {

    private MainActivity mainActivity;
    private TextView textViewServerResponse;

    private EditText editTextUsername;

    public ConnectToServerView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.textViewServerResponse = mainActivity.findViewById(R.id.responseTextView);
        this.editTextUsername = mainActivity.findViewById(R.id.usernameEditText);
        setupUI();

    }

    private void setupUI() {
        Button buttonSendMsg = mainActivity.findViewById(R.id.sendbutton);
        buttonSendMsg.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString();
            mainActivity.sendMessage(username);
        });


        Button buttonReconnect = mainActivity.findViewById(R.id.reConnectButton);
        buttonReconnect.setOnClickListener(v -> mainActivity.reconnectToServer());

    }

    public void updateServerResponse(String message) {
        textViewServerResponse.setText(message);
    }







}
