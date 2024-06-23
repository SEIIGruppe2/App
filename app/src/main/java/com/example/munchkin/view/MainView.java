package com.example.munchkin.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.munchkin.activity.GameRules;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.activity.MainActivity;
import com.example.munchkin.R;

public class MainView {

    private final MainActivity mainActivity;
    private final Button buttonRegister;
    private final Button buttonOptions;
    private final Button buttonExit;


    public MainView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.buttonRegister = mainActivity.findViewById(R.id.goBack);
        this.buttonOptions = mainActivity.findViewById(R.id.buttonOptions);
        this.buttonExit = mainActivity.findViewById(R.id.buttonExit);

        setupUI();
    }

    private void setupUI() {
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, ConnectToServerActivity.class);
            mainActivity.startActivity(intent);
        });
        buttonOptions.setVisibility(View.GONE);

        buttonExit.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, GameRules.class);
            mainActivity.startActivity(intent);
        });
    }


    }
