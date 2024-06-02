package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.ConnectToServerController;
import com.example.munchkin.controller.LobbyController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.LobbyView;

public class LobbyActivity extends AppCompatActivity {
    LobbyController controller;
    LobbyView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lobby);

        MessageRouter router = new MessageRouter();

        view = new LobbyView(this);
        WebSocketClientModel model = new WebSocketClientModel();
        controller = new LobbyController(model, view);


        router.registerController("REQUEST_USERNAMES",controller);
        requestUsernames();


        model.setMessageRouter(router);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void goToMainGame(){
        Intent intent = new Intent(LobbyActivity.this, MainGameActivity.class);
        startActivity(intent);
    }

    private void requestUsernames(){
        controller.requestUsernames();
    }

    public void updateevrything(int arraylength){



    }


}