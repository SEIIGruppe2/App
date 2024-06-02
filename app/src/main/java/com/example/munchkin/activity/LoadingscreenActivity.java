package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.LoadingController;
import com.example.munchkin.game.AppState;
import com.example.munchkin.model.WebSocketClientModel;

public class LoadingscreenActivity extends AppCompatActivity {


    private LoadingController loadingController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loadingscreen);

        MessageRouter router = new MessageRouter();

        WebSocketClientModel model = new WebSocketClientModel();

        loadingController = new LoadingController(model, this);


        router.registerController("REGISTER_USERNAME",loadingController);
        model.setMessageRouter(router);




        loadingController.registerUserMessage(AppState.getInstance().getCurrentUser());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }

    public void startlobby(){

        Intent intent = new Intent(this, LobbyActivity.class);
        startActivity(intent);

    }

    public void goBackToUsername(){
        Intent intent = new Intent(this, ConnectToServerActivity.class);
        startActivity(intent);
    }
}