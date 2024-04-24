package com.example.munchkin.view;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.Player.Player;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;

import java.util.ArrayList;
import java.util.List;

public class MainGameView {
    private MainGameActivity mainGameActivity;
    private Button buttonEndRound, buttonCards, damage;
    private List<Button> Zone1Monster = new ArrayList<>();
    private List<Button> Zone2Monster = new ArrayList<>();
    private List<Button> Zone3Monster = new ArrayList<>();
    private List<Button> Zone4Monster = new ArrayList<>();

    private GameController gameController;

    private Button[] monsterButtons;

    private Button[] allPlayerButtons;

    // ListViews
    private ListView listActions;
    private ListView listTrophies;

    public MainGameView(MainGameActivity mainGameActivity) {
        this.mainGameActivity = mainGameActivity;
        this.buttonEndRound = mainGameActivity.findViewById(R.id.buttonEndRound);
        this.buttonCards = mainGameActivity.findViewById(R.id.buttonCards);
        this.damage = mainGameActivity.findViewById(R.id.Damage);


        this.mainGameActivity = mainGameActivity;
        allPlayerButtons = new Button[]{
                mainGameActivity.findViewById(R.id.buttonEndRound),
                mainGameActivity.findViewById(R.id.buttonCards)
                // Button entfernen damit der Spieler nichts machen kann
        };

        // Add all spawn buttons to the list
        addButtonsToZoneList(Zone1Monster,
                R.id.button_forest1_spawn1, R.id.button_forest1_spawn2, R.id.button_forest1_spawn3,
                R.id.button_archer1_spawn1, R.id.button_archer1_spawn2, R.id.button_archer1_spawn3,
                R.id.button_knight1_spawn1, R.id.button_knight1_spawn2, R.id.button_knight1_spawn3,
                R.id.button_swordsman1_spawn1, R.id.button_swordsman1_spawn2, R.id.button_swordsman1_spawn3);

        addButtonsToZoneList(Zone2Monster,
                R.id.button_forest2_spawn1, R.id.button_forest2_spawn2, R.id.button_forest2_spawn3,
                R.id.button_archer2_spawn1, R.id.button_archer2_spawn2, R.id.button_archer2_spawn3,
                R.id.button_knight2_spawn1, R.id.button_knight2_spawn2, R.id.button_knight2_spawn3,
                R.id.button_swordsman2_spawn1, R.id.button_swordsman2_spawn2, R.id.button_swordsman2_spawn3);

        addButtonsToZoneList(Zone3Monster,
                R.id.button_forest3_spawn1, R.id.button_forest3_spawn2, R.id.button_forest3_spawn3,
                R.id.button_archer3_spawn1, R.id.button_archer3_spawn2, R.id.button_archer3_spawn3,
                R.id.button_knight3_spawn1, R.id.button_knight3_spawn2, R.id.button_knight3_spawn3,
                R.id.button_swordsman3_spawn1, R.id.button_swordsman3_spawn2, R.id.button_swordsman3_spawn3);

        addButtonsToZoneList(Zone4Monster,
                R.id.button_forest4_spawn1, R.id.button_forest4_spawn2, R.id.button_forest4_spawn3,
                R.id.button_archer4_spawn1, R.id.button_archer4_spawn2, R.id.button_archer4_spawn3,
                R.id.button_knight4_spawn1, R.id.button_knight4_spawn2, R.id.button_knight4_spawn3,
                R.id.button_swordsman4_spawn1, R.id.button_swordsman4_spawn2, R.id.button_swordsman4_spawn3);

        this.listActions = mainGameActivity.findViewById(R.id.listActions);
        this.listTrophies = mainGameActivity.findViewById(R.id.listTrophies);

        setUI();
    }


    private void addButtonsToZoneList(List<Button> zoneList, int... buttonIds) {
        for (int id : buttonIds) {
            zoneList.add(mainGameActivity.findViewById(id));
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
                mainGameActivity.sendMessage();
                mainGameActivity.transitionToCardDeckscreen();
                updateListActions();
                updateListTrophies();
            }
        });


        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeVisibleMonster();
            }
        });
    }



    public void addtoList(ActionCardDTO karte){
        mainGameActivity.addcardtolist(karte);
    }


    public void displayMonster(MonsterDTO monster, int position) {
        if (position >= 0 && position < monsterButtons.length) {
            Button button = monsterButtons[position];
            button.setVisibility(View.VISIBLE);
            button.setBackground(mainGameActivity.getDrawable(R.drawable.munchkin2));
        }
    }


    public void displayCurrentPlayer(Player currentPlayer) {
        // Beispieltextfeld oder Label in der UI, das den Namen des aktuellen Spielers anzeigt
        TextView currentPlayerTextView = mainGameActivity.findViewById(R.id.Spieler);
        currentPlayerTextView.setText("Spieler: " + currentPlayer.getName());

        // Optional: UI-Elemente für andere Spieler deaktivieren oder visuell ändern
        for (Button button : allPlayerButtons) {
            if (!button.getTag().equals(currentPlayer.getName())) {
                button.setEnabled(false);
                button.setAlpha(0.5f);
            } else {
                button.setEnabled(true);
                button.setAlpha(1.0f);
            }
        }
    }


    public void updateRoundView(int round) {
        TextView roundView = mainGameActivity.findViewById(R.id.textViewRound);
        roundView.setText("Runde: " + round);
    }


    private void spawnMonster() {
        // Roll a dice (assuming the dice roll logic is implemented elsewhere)
        int diceRoll = rollDice();

        // Check which zone to spawn the monster in based on the dice roll
        switch (diceRoll) {
            case 1:
                spawnMonsterInZone(Zone1Monster);
                break;
            case 2:
                spawnMonsterInZone(Zone2Monster);
                break;
            case 3:
                spawnMonsterInZone(Zone3Monster);
                break;
            case 4:
                spawnMonsterInZone(Zone4Monster);
                break;
            // Handle other cases if needed
        }
    }

    // Method to spawn monster in a specific zone
    private void spawnMonsterInZone(List<Button> zoneButtons) {
        for (Button button : zoneButtons) {
            if (isButtonEmpty(button)) {
                button.setVisibility(View.VISIBLE);
                button.setBackground(null); // Clear existing background
                button.setBackgroundResource(R.drawable.munchkin2); // Set monster image
                return; // Monster spawned, exit method
            }
        }
        // If all buttons in the zone are occupied, do nothing
    }
    //END: Spawn Monsters

    private int rollDice() {
        return 1;
    }


    private void removeVisibleMonster() {
        removeVisibleMonsterById(R.id.button_knight1_spawn2);
    }

    private void removeVisibleMonsterById(int buttonId) {
        Button button = mainGameActivity.findViewById(buttonId);
        if (isButtonFull(button)) {
            button.setVisibility(View.GONE);
        }
    }


    private boolean isButtonFull(Button button) {
        // Check if the button background is not set (assuming empty buttons have no background)
        return button.getVisibility() == View.VISIBLE;
    }



    public void updateMonsters(List<List<MonsterDTO>> monsterRings) {
        // Angenommen, wir haben ein Array von Button Arrays, jedes repräsentiert einen Ring
        //Button[][] ringButtons = ...; // Muss initialisiert werden mit den tatsächlichen UI-Buttons

        for (int ring = 0; ring < monsterRings.size(); ring++) {
            List<MonsterDTO> ringMonsters = monsterRings.get(ring);
            for (int pos = 0; pos < ringMonsters.size(); pos++) {
                MonsterDTO monster = ringMonsters.get(pos);
                //   Button button = ringButtons[ring][pos];
                //  button.setBackground(R.drawable.dice_4); // Update das Button-Bild
            }
        }
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
