package com.example.munchkin.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.MainGameView;

public class MainGameActivity extends AppCompatActivity {

    private MainGameView mainGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);

        mainGameView = new MainGameView(this);
    }
}
