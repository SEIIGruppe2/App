package com.example.munchkin.view;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import com.example.munchkin.ConnectToServerActivity;
import com.example.munchkin.MainActivity;
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
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainActivity, ConnectToServerActivity.class);
                mainActivity.startActivity(intent);
            }
        });
    }
}