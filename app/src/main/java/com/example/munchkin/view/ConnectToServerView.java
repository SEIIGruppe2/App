package com.example.munchkin.view;

import android.widget.Button;
import android.widget.TextView;

import com.example.munchkin.MainActivity;
import com.example.munchkin.R;

public class ConnectToServerView {

    private MainActivity mainActivity;
    private TextView textViewServerResponse;

    public ConnectToServerView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.textViewServerResponse = mainActivity.findViewById(R.id.responseTextView);
        setupUI();
    }

    private void setupUI() {
        Button buttonSendMsg = mainActivity.findViewById(R.id.sendbutton);
        buttonSendMsg.setOnClickListener(v -> mainActivity.sendMessage());
    }

    public void updateServerResponse(String message) {
        textViewServerResponse.setText(message);
    }

}
