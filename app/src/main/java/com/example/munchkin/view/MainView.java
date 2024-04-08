package com.example.munchkin.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.activity.MainActivity;
import com.example.munchkin.R;

public class MainView {

    private MainActivity mainActivity;
    private TextView textViewTitle;
    private Button buttonRegister, buttonOptions, buttonExit;

    public MainView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.textViewTitle = mainActivity.findViewById(R.id.textViewTitle);
        this.buttonRegister = mainActivity.findViewById(R.id.buttonRegister);
        this.buttonOptions = mainActivity.findViewById(R.id.buttonOptions);
        this.buttonExit = mainActivity.findViewById(R.id.buttonExit);
        setupUI();
    }

    private void setupUI() {
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, ConnectToServerActivity.class);
                mainActivity.startActivity(intent);
            }
        });

        buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, CarddeckActivity.class);
                mainActivity.startActivity(intent);
            }
        });
    }
}
