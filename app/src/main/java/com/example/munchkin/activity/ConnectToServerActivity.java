package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.munchkin.messageformat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;

public class ConnectToServerActivity extends AppCompatActivity {

    private ConnectToServerController controller;

    private static boolean usernameAccepted = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_connect_to_server);

        MessageRouter router = new MessageRouter();

        ConnectToServerView view = new ConnectToServerView(this);
        WebSocketClientModel model = new WebSocketClientModel();
        controller = new ConnectToServerController(model, this);
        if(!usernameAccepted) {
            view.updateServerResponse("Username bereits vergeben. Wähle einen anderen usernamen");
        }
        router.registerController("LOBBY_ASSIGNED",controller);
        router.registerController("WAITING_FOR_PLAYERS",controller);

        model.setMessageRouter(router);
    }

    public void sendMessage() {

        controller.setupController();

    }

    public void reconnectToServer() {
        controller.reconnectToServer();
    }



    public void transitionToLoadingScreen(String username) {

        Intent intent = new Intent(ConnectToServerActivity.this, LoadingScreenActivity.class);
        Bundle usernameintent = new Bundle();
        usernameintent.putString("username",username);
        startActivity(intent, usernameintent);
    }

    public static void setUsernameAccepted(boolean usernameAccepted) {
        ConnectToServerActivity.usernameAccepted = usernameAccepted;
    }
}




