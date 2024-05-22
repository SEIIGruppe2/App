package com.example.munchkin.view;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.munchkin.R;
import com.example.munchkin.model.DiceRollModel;
import com.example.munchkin.view.animations.ShakeDetectorView;

import java.util.ArrayList;

public class DiceRollView extends AppCompatActivity implements ShakeDetectorView.OnShakeListener {

    private ImageView diceImage;
    private Button btnRoll;
    private DiceRollModel diceRollModel;
    private ShakeDetectorView shakeDetectorView;
    private ArrayList<Integer> diceResults = new ArrayList<>();
    private int rollCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceImage = findViewById(R.id.dice_image);
        btnRoll = findViewById(R.id.btn_roll);

        diceRollModel = new DiceRollModel();


        shakeDetectorView = new ShakeDetectorView(this);
        btnRoll.setOnClickListener(v -> rollDice());
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

        diceRollModel.rollDice(randomNumber -> runOnUiThread(() -> updateUI(randomNumber)));
        }
    }

    private void updateUI(int randomNumber) {
        diceResults.add(randomNumber + 1);
        rollCount++;

        switch (randomNumber + 1) {
            case 1:
                diceImage.setImageResource(R.drawable.dice_1);
                break;
            case 2:
                diceImage.setImageResource(R.drawable.dice_2);
                break;
            case 3:
                diceImage.setImageResource(R.drawable.dice_3);
                break;
            case 4:
                diceImage.setImageResource(R.drawable.dice_4);
                break;
            default:
                diceImage.setImageResource(R.drawable.yellowborder);
                break;
        }

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
