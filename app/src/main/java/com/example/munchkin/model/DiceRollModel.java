package com.example.munchkin.model;

import java.security.SecureRandom;

public class DiceRollModel {

    private SecureRandom secureRandom = new SecureRandom();

    public interface DiceRollCallback {
        void onDiceRolled(int randomNumber);
    }

    public void rollDice(final DiceRollCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                final int randomNumber = secureRandom.nextInt(4);

                callback.onDiceRolled(randomNumber);
            }
        }).start();
    }
}
