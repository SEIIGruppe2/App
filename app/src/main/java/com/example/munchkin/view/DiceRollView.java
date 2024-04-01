package com.example.munchkin.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.R;
import com.example.munchkin.model.DiceRollModel;
import com.example.munchkin.model.SensorModel;

public class DiceRollView extends AppCompatActivity {

    private ImageView diceImage;
    private Button btnRoll;
    private TextView textView;
    private DiceRollModel diceRollModel;

    private SensorModel sensorModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_roll);

        diceImage = findViewById(R.id.dice_image);
        btnRoll = findViewById(R.id.btn_roll);
        textView = findViewById(R.id.text_view);


        diceRollModel = new DiceRollModel(diceImage, btnRoll, textView);
        sensorModel = new SensorModel(this, diceRollModel);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diceRollModel.rollDice();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorModel.registerListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorModel.unregisterListener();
    }
}