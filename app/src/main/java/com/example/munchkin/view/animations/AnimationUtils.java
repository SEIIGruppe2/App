package com.example.munchkin.view.animations;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

public class AnimationUtils {

    public static void startBlinkingAnimation(Button button) {
        AlphaAnimation blinkAnimation = new AlphaAnimation(1.0f, 0.7f);
        blinkAnimation.setDuration(1000);
        blinkAnimation.setInterpolator(new LinearInterpolator());
        blinkAnimation.setRepeatCount(Animation.INFINITE);
        blinkAnimation.setRepeatMode(Animation.REVERSE);
        button.startAnimation(blinkAnimation);
    }

    public static void stopBlinkingAnimation(Button button) {
        button.clearAnimation();
    }
}