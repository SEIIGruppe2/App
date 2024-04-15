package com.example.munchkin.view;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.activity.GameActivity;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.model.DiceRollModel;
import java.util.ArrayList;

public class DiceRollView extends AppCompatActivity implements ShakeDetectorView.OnShakeListener {

    private ImageView diceImage;
    private Button btnRoll;
    private TextView textView;

    private DiceRollModel diceRollModel;
    private ShakeDetectorView shakeDetectorView;
    private ArrayList<Integer> diceResults = new ArrayList<>();
    private int rollCount = 0;
    private GameController gameController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceImage = findViewById(R.id.dice_image);
        btnRoll = findViewById(R.id.btn_roll);
        textView = findViewById(R.id.text_view);

        diceRollModel = new DiceRollModel();


        shakeDetectorView = new ShakeDetectorView(this);
        btnRoll.setOnClickListener(v -> rollDice());
    }


    public void setGameController(GameController controller) {
        this.gameController = controller;
    }

    @Override
    protected void onResume() {
        super.onResume();
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(shakeDetectorView, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.unregisterListener(shakeDetectorView);
        super.onPause();
    }


    private void rollDice() {
        if (rollCount < 3) {
        btnRoll.setEnabled(false);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        diceImage.startAnimation(animation);

        diceRollModel.rollDice(new com.example.munchkin.model.DiceRollModel.DiceRollCallback() {
            @Override
            public void onDiceRolled(final int randomNumber) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(randomNumber);
                    }
                 });
                }
            });
        }
    }

    private void updateUI(int randomNumber) {
        diceResults.add(randomNumber);
        rollCount++;

        if (rollCount == 3) {
            Intent returnIntent = new Intent();
            returnIntent.putIntegerArrayListExtra("diceResults", diceResults);
            setResult(RESULT_OK, returnIntent);
            finish();
        } else {
            btnRoll.postDelayed(() -> btnRoll.setEnabled(true), 1000);
        }

    }



    @Override
    public void onShake() {
        rollDice();
    }





}
