package com.example.munchkin.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;

public class SensorModel implements SensorEventListener {

    private AppCompatActivity activity;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final float SHAKE_THRESHOLD = 10.0f;
    private long lastShakeTime = 0;
    private DiceRollModel diceRollModel;

    public SensorModel(AppCompatActivity activity, DiceRollModel diceRollModel) {
        this.activity = activity;
        this.diceRollModel = diceRollModel;

        sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    public void registerListener() {
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void unregisterListener() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastShakeTime) > 1000) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
                if (acceleration > SHAKE_THRESHOLD) {
                    lastShakeTime = currentTime;
                    diceRollModel.rollDice();
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }
}
