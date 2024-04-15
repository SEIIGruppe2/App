package com.example.munchkin.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.R;

import java.util.ArrayList;
import java.util.List;

public class MainGameView {
    private MainGameActivity mainGameActivity;
    private Button buttonEndRound, buttonCards;
    private List<Button> spawnMonster = new ArrayList<>();

    // ListViews
    private ListView listActions;
    private ListView listTrophies;

    public MainGameView(MainGameActivity mainGameActivity) {
        this.mainGameActivity = mainGameActivity;
        this.buttonEndRound = mainGameActivity.findViewById(R.id.buttonEndRound);
        this.buttonCards = mainGameActivity.findViewById(R.id.buttonCards);

        // Add all spawn buttons to the list
        addSpawnButtonsToList(R.id.button_forest1_spawn1, R.id.button_forest1_spawn2, R.id.button_forest1_spawn3,
                R.id.button_archer1_spawn1, R.id.button_archer1_spawn2, R.id.button_archer1_spawn3,
                R.id.button_knight1_spawn1, R.id.button_knight1_spawn2, R.id.button_knight1_spawn3,
                R.id.button_swordsman1_spawn1, R.id.button_swordsman1_spawn2, R.id.button_swordsman1_spawn3,
                R.id.button_forest2_spawn1, R.id.button_forest2_spawn2, R.id.button_forest2_spawn3,
                R.id.button_archer2_spawn1, R.id.button_archer2_spawn2, R.id.button_archer2_spawn3,
                R.id.button_knight2_spawn1, R.id.button_knight2_spawn2, R.id.button_knight2_spawn3,
                R.id.button_swordsman2_spawn1, R.id.button_swordsman2_spawn2, R.id.button_swordsman2_spawn3,
                R.id.button_forest3_spawn1, R.id.button_forest3_spawn2, R.id.button_forest3_spawn3,
                R.id.button_archer3_spawn1, R.id.button_archer3_spawn2, R.id.button_archer3_spawn3,
                R.id.button_knight3_spawn1, R.id.button_knight3_spawn2, R.id.button_knight3_spawn3,
                R.id.button_swordsman3_spawn1, R.id.button_swordsman3_spawn2, R.id.button_swordsman3_spawn3,
                R.id.button_forest4_spawn1, R.id.button_forest4_spawn2, R.id.button_forest4_spawn3,
                R.id.button_archer4_spawn1, R.id.button_archer4_spawn2, R.id.button_archer4_spawn3,
                R.id.button_knight4_spawn1, R.id.button_knight4_spawn2, R.id.button_knight4_spawn3,
                R.id.button_swordsman4_spawn1, R.id.button_swordsman4_spawn2, R.id.button_swordsman4_spawn3);

        this.listActions = mainGameActivity.findViewById(R.id.listActions);
        this.listTrophies = mainGameActivity.findViewById(R.id.listTrophies);

        setUI();
    }

    private void addSpawnButtonsToList(int... buttonIds) {
        for (int id : buttonIds) {
            spawnMonster.add(mainGameActivity.findViewById(id));
        }
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
                for (Button button : spawnMonster) {
                    if (isButtonEmpty(button)) {
                        button.setVisibility(View.VISIBLE);
                        button.setBackground(null); // Clear existing background
                        button.setBackgroundResource(R.drawable.munchkin2); // Set monster image
                        return; // Monster spawned, exit method
                    }
                }
                // If all buttons are occupied, do nothing
                break;
            // Handle other dice roll cases if needed
        }
    }

    private int rollDice() {
        return 1; // For testing, replace with actual dice rolling logic
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
