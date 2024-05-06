package com.example.munchkin.view.animations;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import java.security.SecureRandom;

public class ButtonRotateView {

    private static float currentRotation = 0;

    private static final SecureRandom random = new SecureRandom();


    public static void rotateButton(Button button) {
        float width = button.getWidth();
        float height = button.getHeight();


        float pivotX = width * 0.5f;
        float pivotY = height * (1 / 3.0f);


        TranslateAnimation shake1 = new TranslateAnimation(-10, 10, 0, 0);
        shake1.setDuration(300);
        shake1.setRepeatCount(3);
        shake1.setRepeatMode(Animation.REVERSE);


        TranslateAnimation shake2 = new TranslateAnimation(-20, 20, 0, 0);
        shake2.setDuration(200);
        shake2.setRepeatCount(2);
        shake2.setRepeatMode(Animation.REVERSE);


        TranslateAnimation shake3 = new TranslateAnimation(-5, 5, 0, 0);
        shake3.setDuration(400);
        shake3.setRepeatCount(5);
        shake3.setRepeatMode(Animation.REVERSE);


        TranslateAnimation selectedShake;
        int shakeChoice = random.nextInt(3);
        if (shakeChoice == 0) {
            selectedShake = shake1;
        } else if (shakeChoice == 1) {
            selectedShake = shake2;
        } else {
            selectedShake = shake3;
        }


        RotateAnimation rotateAnimation = new RotateAnimation(currentRotation, currentRotation + 120,
                Animation.RELATIVE_TO_SELF, pivotX / width,
                Animation.RELATIVE_TO_SELF, pivotY / height);
        currentRotation += 120;
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setStartOffset(selectedShake.getDuration() * (selectedShake.getRepeatCount() + 1));


        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(selectedShake);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillAfter(true);



        button.startAnimation(animationSet);
    }





}



