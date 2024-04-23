package com.example.munchkin.activity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.view.MainGameView;
import com.example.munchkin.view.ZoomDetectorView;

public class MainGameActivity extends AppCompatActivity {

    private MainGameView mainGameView;
    private ZoomDetectorView zoomDetectorView;
    private ScaleGestureDetector scaleGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);

        mainGameView = new MainGameView(this);
        View mainView = findViewById(R.id.game);
        zoomDetectorView = new ZoomDetectorView(this, mainView);
        scaleGestureDetector = new ScaleGestureDetector(this, zoomDetectorView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        zoomDetectorView.onTouchEvent(event);
        return true;
    }

}
