package com.example.munchkin.view;


import android.graphics.drawable.Drawable;
import android.util.Log;
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
import com.example.munchkin.DTO.TowerDTO;
import com.example.munchkin.Player.Player;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.view.animations.ButtonRotateView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainGameView {
    private MainGameActivity mainGameActivity;
    private Button buttonEndRound, buttonCards, damage,startGame, endRund;
    private List<Button> Zone1Monster = new ArrayList<>();
    private List<Button> Zone2Monster = new ArrayList<>();
    private List<Button> Zone3Monster = new ArrayList<>();
    private List<Button> Zone4Monster = new ArrayList<>();
    MonsterManager monsterManager = new MonsterManager();
    private GameController gameController;

    private Button[] monsterButtons;

    private List<ArrayList<MonsterDTO>> monsterZones;

    private Button[] allPlayerButtons;

    // ListViews
    private ListView listActions;
    private ListView listTrophies;
    private Button towerButton;
    private TowerDTO tower;
    private Map<Button, ButtonRotateView> buttonRotateViews = new HashMap<>();

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

        towerButton = mainGameActivity.findViewById(R.id.tower); // Assume the button ID is 'tower'
        initializeTower();
        setupRotate(Arrays.asList(Zone1Monster, Zone2Monster, Zone3Monster, Zone4Monster));

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
                //ButtonRotateView.rotateButton(damage);
                //gameController.sendMonsterAttackMessage("1","1");
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


    //START: SpawnMonster
    public void spawnMonster(MonsterDTO monster) {
        int monsterZone = monster.getZone();
        mainGameActivity.runOnUiThread(() -> {
            switch (monsterZone) {
                case 1:
                    spawnMonsterInZone(Zone1Monster, monster);
                    break;
                case 2:
                    spawnMonsterInZone(Zone2Monster, monster);
                    break;
                case 3:
                    spawnMonsterInZone(Zone3Monster, monster);
                    break;
                case 4:
                    spawnMonsterInZone(Zone4Monster, monster);
                    break;
                default:

                    break;

            }
        });
    }


    private void spawnMonsterInZone(List<Button> zoneButtons, MonsterDTO monster) {
        mainGameActivity.runOnUiThread(() -> {
            for (Button button : zoneButtons) {
                if (isButtonEmpty(button)) {
                    button.setVisibility(View.VISIBLE);
                    button.setTag(monster);
                    monsterManager.registerMonster(monster, button.getId());

                    button.setBackground(null); // Clear existing background

                    switch (monster.getName()){
                        case "Schleim":
                            button.setBackgroundResource(R.drawable.monster_slime);
                            break;
                        case "Sphinx":
                            button.setBackgroundResource(R.drawable.monster_sphinx);
                            break;
                        case "Bullrog":
                            button.setBackgroundResource(R.drawable.monster_bullrog);
                            break;
                        default:
                            Log.d("Error in spawnMonsterInZone", "Kein passendes Monster");
                    }
                    return;
                }
            }
            // If all buttons in the zone are occupied, do nothing

        });
    }
    //END: SpawnMonster

    //START: DoDamageToTower
    public void doDamageToTower() {
        final String TAG = "GameDebug";
        Log.d(TAG, "Initial active monster count: " + monsterManager.countActiveMonsters());

        mainGameActivity.runOnUiThread(() -> {
            // List of zones containing swordsman buttons
            List<List<Button>> zones = Arrays.asList(Zone1Monster, Zone2Monster, Zone3Monster, Zone4Monster);
            for (List<Button> zone : zones) {
                for (Button button : zone) {
                    if (isButtonSwordsman(button) && button.getVisibility() == View.VISIBLE && button.getTag() instanceof MonsterDTO) {
                        MonsterDTO monster = (MonsterDTO) button.getTag();
                        // Notify the server about the attack
                        gameController.sendMonsterAttackMessage(String.valueOf(monster.getId()), "0");
                        Log.d(TAG, "Sending attack message for Monster ID: " + monster.getId());
                    }
                }
            }
            Log.d(TAG, "Final active monster count: " + monsterManager.countActiveMonsters());
        });
    }
    //END: DoDamageToTower

    public void setupRotate(List<List<Button>> zones) {
        for (List<Button> zone : zones) {
            for (Button button : zone) {
                buttonRotateViews.put(button, new ButtonRotateView());
            }
        }
    }

    public void updateMonsterHealth(String monsterId, int newLifePoints) {
        mainGameActivity.runOnUiThread(() -> {
            for (List<Button> zone : Arrays.asList(Zone1Monster, Zone2Monster, Zone3Monster, Zone4Monster)) {
                for (Button button : zone) {
                    MonsterDTO monster = (MonsterDTO) button.getTag();
                    if (monster != null && String.valueOf(monster.getId()).equals(monsterId)) {
                        ButtonRotateView rotateView = buttonRotateViews.get(button);
                        if (newLifePoints <= 0) {
                            button.setVisibility(View.GONE);
                            button.setTag(null);
                            button.setBackground(null);

                            if (rotateView != null) {
                                rotateView.resetRotation();
                            }
                            monsterManager.removeMonster(monsterId);
                            Log.d("MainGameView", "Monster " + monsterId + " is dead and removed.");
                        } else {
                            monster.setLifePoints(newLifePoints);
                            monsterManager.updateMonster(monster.getId(), newLifePoints);
                            if (rotateView != null) {
                                rotateView.rotateButton(button);
                            }
                            Log.d("MainGameView", "Updated Monster " + monsterId + " HP to " + newLifePoints);
                        }
                        break;
                    }
                }
            }
        });
    }



    public boolean isMonsterInAttackZone() {
        List<List<Button>> zones = Arrays.asList(Zone1Monster, Zone2Monster, Zone3Monster, Zone4Monster);
        for (List<Button> zone : zones) {
            for (Button button : zone) {
                if (isButtonSwordsman(button) && isButtonFull(button)) {
                    return true; // If any swordsman button is occupied, return true
                }
            }
        }
        return false;
    }

    private boolean isButtonSwordsman(Button button) {
        Integer buttonId = button.getId();
        List<Integer> swordsmanButtonIds = Arrays.asList(
                R.id.button_swordsman1_spawn1, R.id.button_swordsman1_spawn2, R.id.button_swordsman1_spawn3,
                R.id.button_swordsman2_spawn1, R.id.button_swordsman2_spawn2, R.id.button_swordsman2_spawn3,
                R.id.button_swordsman3_spawn1, R.id.button_swordsman3_spawn2, R.id.button_swordsman3_spawn3,
                R.id.button_swordsman4_spawn1, R.id.button_swordsman4_spawn2, R.id.button_swordsman4_spawn3
        );


        return swordsmanButtonIds.contains(buttonId);
    }


    //START: TowerImpl
    private void initializeTower() {
        tower = new TowerDTO(10, 0);
        towerButton.setTag(tower);
        updateTowerDisplay();
    }

    public void updateTowerDisplay() {
        TowerDTO tower = (TowerDTO) towerButton.getTag();
        towerButton.setText("Tower HP: " + tower.getLifePoints());
    }

    public void modifyTowerLifePoints(int lifeChange) {
        mainGameActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TowerDTO tower = (TowerDTO) towerButton.getTag();
                tower.setLifePoints(lifeChange);
                updateTowerDisplay();
            }
        });
    }
    //END: TowerImpl


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
    //END: Remove Monsters


    public void moveMonstersInward() {

        mainGameActivity.runOnUiThread(() -> {

        Log.d("MoveMonsters", "Starting to move monsters inward.");
        List<List<Button>> zones = Arrays.asList(Zone1Monster, Zone2Monster, Zone3Monster, Zone4Monster);
        for (List<Button> zone : zones) {
            for (int i = zone.size() - 1; i >= 3; i--) {
                Button outer = zone.get(i - 3);
                Button inner = zone.get(i);
                if (outer.getVisibility() == View.VISIBLE && outer.getTag() instanceof MonsterDTO) {
                    MonsterDTO monster = (MonsterDTO) outer.getTag();
                    if (isButtonEmpty(inner)) {
                        inner.setTag(monster);
                        inner.setVisibility(View.VISIBLE);
                        Drawable background = outer.getBackground();
                        inner.setBackground(background);

                        outer.setVisibility(View.GONE);
                        outer.setTag(null);
                        outer.setBackground(null);

                        Log.d("MoveMonsters", "Moved monster from " + (i - 3) + " to " + i);
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
        //hier versuchen mit post

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
