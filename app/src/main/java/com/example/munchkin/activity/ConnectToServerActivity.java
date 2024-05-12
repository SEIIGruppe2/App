package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;

public class ConnectToServerActivity extends AppCompatActivity {

    private ConnectToServerController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connect_to_server);

        MessageRouter router = new MessageRouter();

        ConnectToServerView view = new ConnectToServerView(this);
        WebSocketClientModel model = new WebSocketClientModel();
        controller = new ConnectToServerController(model, view);


        router.registerController("REGISTER_USERNAME",controller);
        router.registerController("LOBBY_ASSIGNED",controller);
        router.registerController("WAITING_FOR_PLAYERS",controller);

        model.setMessageRouter(router);
    }

    public void connectToServer(){
        //controller.setupController();
    }


    public void sendMessage(String username) {

        controller.registerUserMessage(username);

    }

    public void reconnectToServer() {
        controller.reconnectToServer();
    }



    public void transitionToLoadingScreen(String username) {
        //TODO: change to LobbyActivity activity
        Intent intent = new Intent(ConnectToServerActivity.this, LobbyActivity.class);
        startActivity(intent);
    }

}




