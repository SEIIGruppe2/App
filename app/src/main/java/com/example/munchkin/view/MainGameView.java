package com.example.munchkin.view;


import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.PopupWindow;



import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.DTO.MonsterDTO;
import com.example.munchkin.Player.Player;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.view.animations.ButtonRotateView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainGameView {
    private MainGameActivity mainGameActivity;
    private Button buttonEndRound, buttonCards, damage,startGame, endRund;
    private List<Button> Zone1Monster = new ArrayList<>();
    private List<Button> Zone2Monster = new ArrayList<>();
    private List<Button> Zone3Monster = new ArrayList<>();
    private List<Button> Zone4Monster = new ArrayList<>();

    private GameController gameController;

    private Button[] monsterButtons;

    private List<ArrayList<MonsterDTO>> monsterZones;

    private Button[] allPlayerButtons;

    // ListViews
    private ListView listActions;
    private ListView listTrophies;

    public MainGameView(MainGameActivity mainGameActivity) {

        this.mainGameActivity = mainGameActivity;
        this.buttonEndRound = mainGameActivity.findViewById(R.id.buttonEndRound);
        this.buttonCards = mainGameActivity.findViewById(R.id.buttonCards);
        this.damage = mainGameActivity.findViewById(R.id.Damage);
        //Testbuttons
        this.startGame = mainGameActivity.findViewById(R.id.Startround);
        this.endRund = mainGameActivity.findViewById(R.id.Endround);
        //Ende Testbuttons

        this.monsterZones = new ArrayList<ArrayList<MonsterDTO>>();
        initializeMonsterZones();

        this.mainGameActivity = mainGameActivity;
        allPlayerButtons = new Button[]{
                mainGameActivity.findViewById(R.id.buttonEndRound),
                mainGameActivity.findViewById(R.id.buttonCards)
                // Button entfernen damit der Spieler nichts machen kann
        };

        buttonEndRound.setTag("EndRound");
        buttonCards.setTag("Cards");

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


    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    private void addButtonsToZoneList(List<Button> zoneList, int... buttonIds) {
        for (int id : buttonIds) {
            zoneList.add(mainGameActivity.findViewById(id));
        }
    }

    public void setUI() {

        mainGameActivity.runOnUiThread(this::disablePlayerAction);

        buttonEndRound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gameController.endTurn();
            }
        });

        buttonCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainGameActivity.sendMessage();
                mainGameActivity.transitionToCardDeckscreen();
                updateListActions();
                updateListTrophies();
                mainGameActivity.gehezukarten();
            }
        });


        //START: Testing animation when monster takes hit
        damage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //spawnMonster(1);
                ButtonRotateView.rotateButton(damage);
            }
        });
        //End: Testing animation when monster takes hit



        //Für tests

        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameController.startRound(); //gameController.startRound();  // Startet die Runde manuell für Testzwecke
            }
        });



        endRund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gameController.endRound();  // Endet die Runde manuell für Testzwecke
            }
        });

    }


    public void addtoList(ActionCardDTO karte){
        mainGameActivity.addcardtolist(karte);
    }


    public void displayCurrentPlayer(String currentPlayer) {

        mainGameActivity.runOnUiThread(() -> {
        TextView currentPlayerTextView = mainGameActivity.findViewById(R.id.Spieler);
        currentPlayerTextView.setText("Spieler: " + (currentPlayer != null ? currentPlayer : "Unbekannt"));

        });
    }

    public void enablePlayerAction() {
        mainGameActivity.runOnUiThread(() -> {
        for (Button button : allPlayerButtons) {
            button.setVisibility(View.VISIBLE);
            button.setAlpha(1.0f);  // Enable and highlight buttons for the current player
        }
        });
    }


    public void disablePlayerAction() {
        mainGameActivity.runOnUiThread(() -> {
            for (Button button : allPlayerButtons) {
                button.setVisibility(View.GONE);
                button.setAlpha(0.5f); // Half opaque
            }
        });
    }



    public void updateRoundView(int round) {
        mainGameActivity.runOnUiThread(() -> {
            TextView roundView = mainGameActivity.findViewById(R.id.textViewRound);
            roundView.setText("Runde: " + round);
        });
    }


    public void spawnMonster(int monsterzone) {

        mainGameActivity.runOnUiThread(() -> {
            switch (monsterzone) {
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

            }
        });
    }

    // Method to spawn monster in a specific zone
    private void spawnMonsterInZone(List<Button> zoneButtons) {

        mainGameActivity.runOnUiThread(() -> {
        for (Button button : zoneButtons) {
            if (isButtonEmpty(button)) {
                button.setVisibility(View.VISIBLE);
                button.setBackground(null); // Clear existing background
                button.setBackgroundResource(R.drawable.monster_bullrog); // Set monster image
                return; // Monster spawned, exit method
            }
        }
        // If all buttons in the zone are occupied, do nothing

        });
    }
    //END: Spawn Monsters


    //START: Remove Monsters
    private void removeVisibleMonster() {
        removeVisibleMonsterById(R.id.button_knight1_spawn2);
    }

    private void removeVisibleMonsterById(int buttonId) {

        mainGameActivity.runOnUiThread(() -> {

        Button button = mainGameActivity.findViewById(buttonId);
        if (isButtonFull(button)) {
            button.setVisibility(View.GONE);
        }


        });
    }


    public void moveMonstersInward() {

        mainGameActivity.runOnUiThread(() -> {


        List<List<Button>> zones = Arrays.asList(Zone1Monster, Zone2Monster, Zone3Monster, Zone4Monster);
        for (List<Button> zone : zones) {
            for (int i = 9; i >= 3; i -= 3) { // Beginnend beim ersten Button des zweiten Rings (Archer), zurück zum ersten Button des ersten Rings (Forest)
                for (int j = 0; j < 3; j++) {
                    if (i + j - 3 >= 0 && i + j < zone.size()) {
                        Button outer = zone.get(i + j - 3);
                        Button inner = zone.get(i + j);
                        if (isButtonFull(outer)) {
                            Drawable background = outer.getBackground(); // Sichern des Hintergrundes
                            inner.setBackground(background);
                            inner.setVisibility(View.VISIBLE);
                            outer.setVisibility(View.GONE);
                            outer.setBackground(null);
                        }
                    }
                }
            }
        }


        });
    }

    private boolean isButtonFull(Button button) {

        // Check if the button background is not set (assuming empty buttons have no background)
        return button.getVisibility() == View.VISIBLE && button.getBackground() != null;
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

    private void initializeMonsterZones() {
        for (int i = 0; i < 4; i++) {
            monsterZones.add(new ArrayList<MonsterDTO>());
        }
    }


    public void tauschanfrageerhalten(JSONObject message) throws JSONException {

        int id = Integer.parseInt(message.getString("id"));
        String name = message.getString("name");
        int zone = Integer.parseInt(message.getString("zone"));
        ActionCardDTO karte = new ActionCardDTO(name, zone,id);
        System.out.println(karte.getName());
        String username = message.getString("switchedWith");
        /*playerhand.addCard(karte);*/

        View popupdrawable = mainGameActivity.getLayoutInflater().inflate(R.layout.popuptauschenanfrage, null);

        mainGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                PopupWindow popuptauschanfrage = new PopupWindow(popupdrawable,width,height,true);
                popuptauschanfrage.setOutsideTouchable(false);
                popuptauschanfrage.setElevation(10);
                popuptauschanfrage.showAtLocation(mainGameActivity.getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);

                mainGameActivity.dimmwindow(popuptauschanfrage);
                Button ok = popupdrawable.findViewById(R.id.ok);

                ok.setOnClickListener(v -> {
                    popuptauschanfrage.dismiss();
                    mainGameActivity.gehezuhandkarten(message);
                });
            }
        });



    }

}
