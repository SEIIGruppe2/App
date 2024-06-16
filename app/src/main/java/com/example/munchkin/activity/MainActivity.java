package com.example.munchkin.activity;


import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.munchkin.R;
import com.example.munchkin.view.MainView;

public class MainActivity extends AppCompatActivity {

    private MainView mainView;
    MediaPlayer mediaPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main); mainView = new MainView(this);
        findViewById(R.id.buttonOptions).setOnClickListener(view -> {

        });
    }

    public void startOptions(){
        //setContentView(R.layout.options);
    }

    public void startmusic(){
        // Initialize the MediaPlayer with the correct audio attributes
        mediaPlayer1.start();
    }



    private void loadView(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null) // This allows the back button to navigate between views
                .commit();
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