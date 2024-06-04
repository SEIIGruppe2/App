package com.example.munchkin.activity;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.munchkin.R;
import com.example.munchkin.view.MainGameView;
import com.example.munchkin.view.MainView;
import com.example.munchkin.view.Options;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MainView mainView;
    MediaPlayer mediaPlayer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);


        // Load the initial view

        setContentView(R.layout.activity_main);
        loadView(new Options());

        // Example of switching views on some action, like button click
        findViewById(R.id.buttonOptions).setOnClickListener(view -> {
            // Replace the current view with another view
            loadView(new Options());
        });
        mainView = new MainView(this);
        mediaPlayer1 = MediaPlayer.create(this, R.raw.munchkinpanic);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setLegacyStreamType(AudioAttributes.USAGE_MEDIA)
                .build();
        mediaPlayer1.setAudioAttributes(audioAttributes);




    }

    public void startOptions(){
        setContentView(R.layout.options);
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