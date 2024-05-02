package com.example.munchkin.interfaces;

import com.example.munchkin.model.DiceRollModel;

public interface GameEventHandler {
    void requestDiceRoll(DiceRollCallback callback);
}
