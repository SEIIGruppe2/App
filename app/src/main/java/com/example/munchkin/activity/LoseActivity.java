package com.example.munchkin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.munchkin.R;

public class LoseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lose_screen);


        String winnerName = getIntent().getStringExtra("winnerName");


        TextView textViewWin = findViewById(R.id.textViewLose);
        textViewWin.setText("Oh nein, " + winnerName + " hat das Spiel gewonnen! Du hast verloren!");

        Button homeButton = findViewById(R.id.homeButtonLose);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}