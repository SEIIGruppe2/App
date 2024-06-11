package com.example.munchkin.view;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.R;
import com.example.munchkin.game.AppState;


public class ConnectToServerView {

    private ConnectToServerActivity connectToServerActivity;
    private TextView textViewServerResponse;

    private EditText editTextUsername;

    private String username;

    public ConnectToServerView(ConnectToServerActivity connectToServerActivity) {
        this.connectToServerActivity = connectToServerActivity;
        this.textViewServerResponse = connectToServerActivity.findViewById(R.id.textViewResponse);
        this.editTextUsername = connectToServerActivity.findViewById(R.id.editTextUsername);
        setupUI();
    }

    private void setupUI() {
        Button buttonSendMsg = connectToServerActivity.findViewById(R.id.buttonConnect);
        buttonSendMsg.setOnClickListener(v -> {
            username = editTextUsername.getText().toString();
            if(!username.isEmpty() && username.length() < 21) {
                connectToServerActivity.sendMessage();
                connectToServerActivity.transitionToLoadingScreen(username);
                AppState.getInstance().setCurrentUser(username);
            }
            else{
                String txt = "Please enter a username! (< 20 Chars)";
                textViewServerResponse.setText(txt);
            }
        });

        Button buttonReconnect = connectToServerActivity.findViewById(R.id.buttonReconnect);
        buttonReconnect.setOnClickListener(v -> connectToServerActivity.reconnectToServer());
    }

    public void updateServerResponse(String message) {
        textViewServerResponse.post(() -> textViewServerResponse.setText(message));
    }

    public String getUsername(){
        return username;
    }
}
