package com.example.munchkin.view;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.munchkin.R;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.activity.DrawActivity;

public class DrawView {

    private DrawActivity drawActivity;
    private TextView textViewServerResponse;

    private EditText editTextUsername;
    public DrawView(DrawActivity drawActivity){
        this.drawActivity=drawActivity;

        setupUI();

    }

    private void setupUI() {
        Button buttonSendMsg = drawActivity.findViewById(R.id.playgame);
        buttonSendMsg.setOnClickListener(v -> {
            drawActivity.sendMessage();
        });



    }

    //todo antwort vom server
    public void updateServerResponse(String message) {
        textViewServerResponse.post(new Runnable() {
            @Override
            public void run() {
                textViewServerResponse.setText(message);
            }
        });
    }

}
