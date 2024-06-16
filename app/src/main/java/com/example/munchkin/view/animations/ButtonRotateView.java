package com.example.munchkin.view.animations;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import java.security.SecureRandom;

public class ButtonRotateView {

    private float currentRotation = 0;
    private static final SecureRandom random = new SecureRandom();

    public void rotateButton(Button button) {

        TranslateAnimation shake = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, -0.1f + random.nextFloat() * 0.2f,
                Animation.RELATIVE_TO_SELF, 0.1f + random.nextFloat() * 0.2f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
        );
        shake.setDuration(300);
        shake.setRepeatCount(3);
        shake.setRepeatMode(Animation.REVERSE);


        float nextRotation = currentRotation + 120;
        RotateAnimation rotateAnimation = new RotateAnimation(currentRotation, nextRotation,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.333f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset((long) 300 * 4); // Wait for the shake to complete

        currentRotation = nextRotation % 360;


        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(shake);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillAfter(true);


        button.startAnimation(animationSet);
    }

    public void applyCurrentRotation(Button button) {
        RotateAnimation applyRotation = new RotateAnimation(0, currentRotation,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        applyRotation.setDuration(0);
        applyRotation.setFillAfter(true);
        button.startAnimation(applyRotation);
    }

    public void resetRotation(Button button) {
        // Reset current rotation to 0
        currentRotation = 0;
        // Instantly rotate the button back to 0 degrees
        RotateAnimation resetRotate = new RotateAnimation(currentRotation, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        resetRotate.setDuration(0);
        resetRotate.setFillAfter(true);
        button.startAnimation(resetRotate);
    }

    public void setCurrentRotation(float rotation) {
        currentRotation = rotation;
    }

    public float getCurrentRotation() {
        return currentRotation;
    }
}