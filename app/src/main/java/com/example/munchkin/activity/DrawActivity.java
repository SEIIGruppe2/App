package com.example.munchkin.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.DrawCardController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.DrawView;

public class DrawActivity extends AppCompatActivity {
    private DrawCardController controller;
    private DrawView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_draw);


        MessageRouter router = new MessageRouter();
        WebSocketClientModel model = new WebSocketClientModel();
        view = new DrawView(this);

        controller = new DrawCardController(model, view);


        router.registerController("DRAW_CARD",controller);
        model.setMessageRouter(router);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void sendMessage() {

        controller.drawMeassage();

    }

}