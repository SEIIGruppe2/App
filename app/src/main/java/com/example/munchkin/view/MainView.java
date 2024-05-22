package com.example.munchkin.view;

import android.content.Intent;
import android.widget.Button;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.activity.MainActivity;
import com.example.munchkin.R;

public class MainView {

    private MainActivity mainActivity;
    private Button buttonRegister, buttonOptions, buttonExit;

    public MainView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.buttonRegister = mainActivity.findViewById(R.id.buttonRegister);
        this.buttonOptions = mainActivity.findViewById(R.id.buttonOptions);
        this.buttonExit = mainActivity.findViewById(R.id.buttonExit);
        setupUI();
    }

    private void setupUI() {
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, ConnectToServerActivity.class);
            mainActivity.startActivity(intent);
        });

        buttonOptions.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, CarddeckActivity.class);
            mainActivity.startActivity(intent);
        });

        buttonExit.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, MainGameActivity.class);
            mainActivity.startActivity(intent);
        });
    }
}