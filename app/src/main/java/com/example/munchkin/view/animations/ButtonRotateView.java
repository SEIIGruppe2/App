package com.example.munchkin.view.animations;

import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import java.security.SecureRandom;

public class ButtonRotateView {

    private float currentRotation = 0; // This tracks the intended rotation in degrees.
    private static final SecureRandom random = new SecureRandom();

    public void rotateButton(Button button) {
        float width = button.getWidth();
        float height = button.getHeight();

        // Create the shake animation
        TranslateAnimation shake = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, -0.1f + random.nextFloat() * 0.2f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.1f + random.nextFloat() * 0.2f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0f
        );
        shake.setDuration(300);
        shake.setRepeatCount(3);
        shake.setRepeatMode(Animation.REVERSE);

        // Create the rotation animation
        float nextRotation = currentRotation + 120;
        RotateAnimation rotateAnimation = new RotateAnimation(currentRotation, nextRotation,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.333f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setStartOffset(300 * 4); // Wait for the shake to complete

        // Update the current rotation state
        currentRotation = nextRotation % 360;

        // Combine both animations into an animation set
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(shake);
        animationSet.addAnimation(rotateAnimation);
        animationSet.setFillAfter(true); // Ensures the button remains in the state post-animation

        // Start the combined animation
        button.startAnimation(animationSet);
    }

    public void resetRotation() {
        currentRotation = 0;
    }
}