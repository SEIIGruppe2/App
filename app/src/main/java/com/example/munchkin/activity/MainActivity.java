package com.example.munchkin.activity;


import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.munchkin.R;
import com.example.munchkin.view.MainView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        MainView mainView = new MainView(this);
        findViewById(R.id.buttonOptions).setOnClickListener(view -> {

        });
    }




    @Override
    protected void onDestroy() {
        if (mediaPlayer1 != null) {
            mediaPlayer1.release();
            mediaPlayer1 = null;
        }
        super.onDestroy();
    }

}