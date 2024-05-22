package com.example.munchkin.activity;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.munchkin.R;
import com.example.munchkin.view.MainGameView;
import com.example.munchkin.view.MainView;

public class MainActivity extends AppCompatActivity {

    private MainView mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        mainView = new MainView(this);
    }

}