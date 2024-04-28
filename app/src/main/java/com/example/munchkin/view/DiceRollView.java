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

public class DiceRollView extends AppCompatActivity implements ShakeDetectorView.OnShakeListener {

    private ImageView diceImage;
    private Button btnRoll;
    private TextView textView;
    private com.example.munchkin.model.DiceRollModel diceRollModel;
    private ShakeDetectorView shakeDetectorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceImage = findViewById(R.id.dice_image);
        btnRoll = findViewById(R.id.btn_roll);
        //textView = findViewById(R.id.textViewDice);

        diceRollModel = new com.example.munchkin.model.DiceRollModel();

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        shakeDetectorView = new ShakeDetectorView(this);
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

    private void updateUI(int randomNumber) {
        diceImage.setImageResource(R.drawable.dice_1 + randomNumber);
        textView.setText(String.valueOf(randomNumber + 1));

        btnRoll.setEnabled(true);

        sendResultToGameView(randomNumber); //Vielleicht anpassen auf +1
    }

    @Override
    public void onShake() {
        rollDice();
    }


    private void sendResultToGameView(int diceResult) {
        String zone = convertDiceResultToZone(diceResult);
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("diceResult", zone);
        startActivity(intent);
    }

    private String convertDiceResultToZone(int diceResult) {
        // Mapping der Würfelzahl zur Zone (als String)
        return "Zone" + diceResult;
    }




}
