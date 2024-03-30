package com.example.munchkin.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.munchkin.ConnectToServerActivity;
import com.example.munchkin.MainActivity;
import com.example.munchkin.R;

public class MainView {

    private MainActivity mainActivity;
    private Button buttonRegister, buttonOptions, buttonQuit;

    public MainView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.buttonRegister = mainActivity.findViewById(R.id.button1);
        this.buttonOptions = mainActivity.findViewById(R.id.button2);
        this.buttonQuit = mainActivity.findViewById(R.id.button3);
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
    }
}
