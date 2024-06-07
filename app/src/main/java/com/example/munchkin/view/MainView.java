package com.example.munchkin.view;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.Switch;


import com.example.munchkin.activity.GameRules;
import com.example.munchkin.activity.MainGameActivity;

import com.example.munchkin.activity.ConnectToServerActivity;
import com.example.munchkin.activity.MainActivity;
import com.example.munchkin.R;

public class MainView {

    private MainActivity mainActivity;
    private Button buttonRegister, buttonOptions, buttonExit;

    private Switch switchMusic;
    MediaPlayer mediaPlayer;


    public MainView(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.buttonRegister = mainActivity.findViewById(R.id.goBack);
        this.buttonOptions = mainActivity.findViewById(R.id.buttonOptions);
        this.buttonExit = mainActivity.findViewById(R.id.buttonExit);

        mediaPlayer = new MediaPlayer();
        setupUI();
    }

    private void setupUI() {
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, ConnectToServerActivity.class);
            mainActivity.startActivity(intent);
        });

        buttonOptions.setOnClickListener(v -> {

            mainActivity.startOptions();
            setupOptions();



        });

        buttonExit.setOnClickListener(v -> {
            Intent intent = new Intent(mainActivity, GameRules.class);
            mainActivity.startActivity(intent);
        });
    }

    private  void setupOptions(){

        /*this.switchMusic = mainActivity.findViewById(R.id.music);
        Button backToMenu = mainActivity.findViewById(R.id.backMainMenu);
        backToMenu.setOnClickListener(v -> {
            mainActivity.setContentView(R.layout.activity_main);

        });
        switchMusic.setOnClickListener(v -> {
            boolean isChecked = switchMusic.isChecked();
            if(isChecked){
                Log.d("Music starts","musi");

                mainActivity.startmusic();


            }else{
                Log.d("Music stops","musi");
                MediaPlayer mediaPlayer1 = MediaPlayer.create(mainActivity, R.raw.munchkinpanic);
                mediaPlayer1.start();

            }

        });
*/
    }
}