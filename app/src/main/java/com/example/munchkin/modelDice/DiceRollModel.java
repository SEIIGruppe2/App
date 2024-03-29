// DiceRollModel.java
package com.example.munchkin.modelDice;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.munchkin.R;

import java.util.Random;

public class DiceRollModel {

    private ImageView diceImage;
    private Button btnRoll;
    private TextView textView;
    private Random random = new Random();

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

    void rollDice() {
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
                    e.printStackTrace();
                }

                diceImage.post(new Runnable() {
                    @Override
                    public void run() {
                        int randomNumber = random.nextInt(4);
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
