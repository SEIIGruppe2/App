package com.example.munchkin;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;

public class ConnectToServerActivity extends AppCompatActivity {

    private ConnectToServerController controller;

    private String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connect_to_server);



        ConnectToServerView view = new ConnectToServerView(this);
        WebSocketClientModel model = new WebSocketClientModel();
        controller = new ConnectToServerController(model, view);
    }


    public void sendMessage(String username) {
        controller.sendMessage(username);
    }

    public void reconnectToServer() {
        controller.reconnectToServer();
    }

}

