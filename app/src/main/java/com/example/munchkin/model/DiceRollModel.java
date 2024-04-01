package com.example.munchkin.model;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.munchkin.R;

import java.security.SecureRandom;

public class DiceRollModel {

    private ImageView diceImage;
    private Button btnRoll;
    private TextView textView;
    private SecureRandom secureRandom = new SecureRandom();

    public DiceRollModel(ImageView diceImage, Button btnRoll, TextView textView) {
        this.diceImage = diceImage;
        this.btnRoll = btnRoll;
        this.textView = textView;

        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });
    }

    private boolean rolling = false;

    public void rollDice() {
        if (rolling) {
            return;
        }
        rolling = true;
        btnRoll.setEnabled(false);

        Animation animation = AnimationUtils.loadAnimation(diceImage.getContext(), R.anim.rotate);
        diceImage.startAnimation(animation);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                diceImage.post(new Runnable() {
                    @Override
                    public void run() {
                        int randomNumber = secureRandom.nextInt(4);
                        diceImage.setImageResource(R.drawable.dice_1 + randomNumber);
                        textView.setText(String.valueOf(randomNumber + 1));

                        rolling = false;
                        btnRoll.setEnabled(true);
                    }
                });
            }
        }).start();
    }
}
