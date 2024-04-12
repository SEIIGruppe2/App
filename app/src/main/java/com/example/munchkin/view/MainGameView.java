package com.example.munchkin.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.munchkin.MainGameActivity;
import com.example.munchkin.R;

import java.util.ArrayList;
import java.util.List;

public class MainGameView {
    private MainGameActivity mainGameActivity;
    private Button buttonEndRound, buttonCards;
    private Button b00, b01, b02, b03, b04, b05, b06, b07, b08, b09, b010, b011;

    // ListViews
    private ListView listActions;
    private ListView listTrophies;

    public MainGameView(MainGameActivity mainGameActivity) {
        this.mainGameActivity = mainGameActivity;
        this.buttonEndRound = mainGameActivity.findViewById(R.id.buttonEndRound);
        this.buttonCards = mainGameActivity.findViewById(R.id.buttonCards);

        this.b00 = mainGameActivity.findViewById(R.id.b00);
        this.b01 = mainGameActivity.findViewById(R.id.b01);
        this.b02 = mainGameActivity.findViewById(R.id.b02);
        this.b03 = mainGameActivity.findViewById(R.id.b03);
        this.b04 = mainGameActivity.findViewById(R.id.b04);
        this.b05 = mainGameActivity.findViewById(R.id.b05);
        this.b06 = mainGameActivity.findViewById(R.id.b06);
        this.b07 = mainGameActivity.findViewById(R.id.b07);
        this.b08 = mainGameActivity.findViewById(R.id.b08);
        this.b09 = mainGameActivity.findViewById(R.id.b09);
        this.b010 = mainGameActivity.findViewById(R.id.b010);
        this.b011 = mainGameActivity.findViewById(R.id.b011);

        this.listActions = mainGameActivity.findViewById(R.id.listActions);
        this.listTrophies = mainGameActivity.findViewById(R.id.listTrophies);

        setUI();
    }

    public void setUI() {

        buttonEndRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spawnMonster();
            }
        });

        buttonCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateListActions();
                updateListTrophies();
            }
        });
    }

    private void spawnMonster() {
        // Roll a dice (assuming the dice roll logic is implemented elsewhere)
        int diceRoll = rollDice();

        // Check which button to spawn the monster in based on the dice roll
        switch (diceRoll) {
            case 1:
                // Check if b00 is empty
                if (isButtonEmpty(b00)) {
                    b00.setVisibility(View.VISIBLE);
                    b00.setBackground(null); // Clear existing background
                    b00.setBackgroundResource(R.drawable.munchkin2); // Set monster image
                    return; // Monster spawned, exit method
                }
                // Check if b01 is empty
                if (isButtonEmpty(b01)) {
                    b01.setVisibility(View.VISIBLE);
                    b01.setBackground(null); // Clear existing background
                    b01.setBackgroundResource(R.drawable.munchkin2);
                    return; // Monster spawned, exit method
                }
                // Check if b02 is empty
                if (isButtonEmpty(b02)) {
                    b02.setVisibility(View.VISIBLE);
                    b02.setBackground(null); // Clear existing background
                    b02.setBackgroundResource(R.drawable.munchkin2);
                    return; // Monster spawned, exit method
                }
                // If all buttons are occupied, do nothing
                break;
            // Handle other dice roll cases if needed
        }
    }

    private int rollDice() {
        return 1;
    }

    private boolean isButtonEmpty(Button button) {
        // Check if the button background is not set (assuming empty buttons have no background)
        return button.getVisibility() == View.GONE;
    }

    private void updateListActions() {
        List<String> actionsList = new ArrayList<>();
        actionsList.add("Action 1");
        actionsList.add("Action 2");
        actionsList.add("Action 3");
        actionsList.add("Action 4");
        actionsList.add("Action 5");
        actionsList.add("Action 6");
        actionsList.add("Action 7");
        actionsList.add("Action 8");
        actionsList.add("Action 9");
        actionsList.add("Action 10");

        ArrayAdapter<String> actionsAdapter = new ArrayAdapter<>(mainGameActivity, R.layout.list_item_text, actionsList);
        listActions.setAdapter(actionsAdapter);
    }

    private void updateListTrophies() {
        List<String> trophiesList = new ArrayList<>();
        trophiesList.add("Benutzer 1: 3");
        trophiesList.add("Benutzer 2: 5");
        trophiesList.add("Benutzer 3: 1");
        trophiesList.add("Benutzer 4: 2");

        ArrayAdapter<String> trophiesAdapter = new ArrayAdapter<>(mainGameActivity, R.layout.list_item_text, trophiesList);
        listTrophies.setAdapter(trophiesAdapter);
    }
}
