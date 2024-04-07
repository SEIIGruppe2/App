package com.example.munchkin.DrawableUtils;

import android.content.Context;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.R;

public class CardUtils {


    private CardUtils() {}


    public static int getDrawableForCard(String cardName) {
        switch (cardName) {
            case "Bogenschütze":
                return R.drawable.card;
            case "Schwertkämpfer":
                return R.drawable.card;
            case "Held":
                return R.drawable.card;
            case "Ritter":
                return R.drawable.card;

            default:
                return R.drawable.card;
        }
    }





}
