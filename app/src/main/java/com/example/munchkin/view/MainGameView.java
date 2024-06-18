package com.example.munchkin.view;

import static com.example.munchkin.controller.GameController.usernamesWithPoints;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.PopupWindow;
import androidx.cardview.widget.CardView;
import com.example.munchkin.dto.ActionCardDTO;
import com.example.munchkin.dto.MonsterDTO;
import com.example.munchkin.dto.TowerDTO;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.MainGameActivity;
import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
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
    private static MainGameActivity mainGameActivity;
    private final Button buttonAccuseCheater;
    private final Button buttonCards;
    private final Button buttonEndRound;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private final Switch switchCheatMode;
    private static final List<Button> zone1monster = new ArrayList<>();
    private static final List<Button> zone2monster = new ArrayList<>();
    private static final List<Button> zone3monster = new ArrayList<>();
    private static final List<Button> zone4monster = new ArrayList<>();
    static MonsterManager monsterManager = new MonsterManager();
    private GameController gameController;
    private final List<ArrayList<MonsterDTO>> monsterZones;
    private final Button[] allPlayerButtons;
    private final Button towerButton;
    private final Map<Button, ButtonRotateView> buttonRotateViews = new HashMap<>();
    private final HashMap<String, Integer> trophiesMap = new HashMap<>();
    private static final String mainGameViewString = "MainGameView";


    private final List<String> trophiesList = new ArrayList<>();
    ArrayAdapter<String> trophiesAdapter;

    public MainGameView(MainGameActivity mainGameActivity) {

        this.buttonEndRound = mainGameActivity.findViewById(R.id.buttonEndRound);
        this.buttonCards = mainGameActivity.findViewById(R.id.buttonCards);
        this.switchCheatMode = mainGameActivity.findViewById(R.id.switchCheatMode);
        this.buttonAccuseCheater = mainGameActivity.findViewById(R.id.buttonAccuseCheater);


        this.monsterZones = new ArrayList<>();
        initializeMonsterZones();

        allPlayerButtons = new Button[]{
                buttonEndRound,
                buttonCards,
                switchCheatMode,
                buttonAccuseCheater
        };

        buttonEndRound.setTag("EndRound");
        buttonCards.setTag("Cards");
        switchCheatMode.setTag("Cheating");
        buttonAccuseCheater.setTag("Accuse");


        addButtonsToZoneList(zone1monster,
                R.id.button_forest1_spawn1, R.id.button_forest1_spawn2, R.id.button_forest1_spawn3,
                R.id.button_archer1_spawn1, R.id.button_archer1_spawn2, R.id.button_archer1_spawn3,
                R.id.button_knight1_spawn1, R.id.button_knight1_spawn2, R.id.button_knight1_spawn3,
                R.id.button_swordsman1_spawn1, R.id.button_swordsman1_spawn2, R.id.button_swordsman1_spawn3);

        addButtonsToZoneList(zone2monster,
                R.id.button_forest2_spawn1, R.id.button_forest2_spawn2, R.id.button_forest2_spawn3,
                R.id.button_archer2_spawn1, R.id.button_archer2_spawn2, R.id.button_archer2_spawn3,
                R.id.button_knight2_spawn1, R.id.button_knight2_spawn2, R.id.button_knight2_spawn3,
                R.id.button_swordsman2_spawn1, R.id.button_swordsman2_spawn2, R.id.button_swordsman2_spawn3);

        addButtonsToZoneList(zone3monster,
                R.id.button_forest3_spawn1, R.id.button_forest3_spawn2, R.id.button_forest3_spawn3,
                R.id.button_archer3_spawn1, R.id.button_archer3_spawn2, R.id.button_archer3_spawn3,
                R.id.button_knight3_spawn1, R.id.button_knight3_spawn2, R.id.button_knight3_spawn3,
                R.id.button_swordsman3_spawn1, R.id.button_swordsman3_spawn2, R.id.button_swordsman3_spawn3);

        addButtonsToZoneList(zone4monster,
                R.id.button_forest4_spawn1, R.id.button_forest4_spawn2, R.id.button_forest4_spawn3,
                R.id.button_archer4_spawn1, R.id.button_archer4_spawn2, R.id.button_archer4_spawn3,
                R.id.button_knight4_spawn1, R.id.button_knight4_spawn2, R.id.button_knight4_spawn3,
                R.id.button_swordsman4_spawn1, R.id.button_swordsman4_spawn2, R.id.button_swordsman4_spawn3);

        towerButton = mainGameActivity.findViewById(R.id.tower);
        initializeTower();
        setupRotate(Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster));

        ListView listTrophies = mainGameActivity.findViewById(R.id.listTrophies);
        if (listTrophies == null) {
            Log.e(mainGameViewString, "listTrophies is null");
            return;
        }
        this.trophiesAdapter = new ArrayAdapter<>(mainGameActivity, R.layout.list_item_text, trophiesList);
        listTrophies.setAdapter(trophiesAdapter);

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
        initializeUsernamesWithPoints(usernamesWithPoints);
        mainGameActivity.runOnUiThread(this::disablePlayerAction);

        buttonEndRound.setOnClickListener(v -> gameController.endTurn());

        switchCheatMode.setOnClickListener(v -> gameController.cheatModeMethod());

        buttonAccuseCheater.setOnClickListener(v -> showAccusePopup());

        buttonCards.setOnClickListener(v -> mainGameActivity.transitionToCardDeckScreen());
    }


    public void addToList(ActionCardDTO card){
        mainGameActivity.addCardToList(card);
    }


    public void displayCurrentPlayer(String currentPlayer) {
        mainGameActivity.runOnUiThread(() -> {
            TextView currentPlayerTextView = mainGameActivity.findViewById(R.id.Spieler);
            String playerName = currentPlayer != null ? currentPlayer : mainGameActivity.getString(R.string.unknown_player);
            String playerText = mainGameActivity.getString(R.string.current_player, playerName);
            currentPlayerTextView.setText(playerText);
        });
    }

    public void enablePlayerAction() {
        mainGameActivity.runOnUiThread(() -> {
            for (Button button : allPlayerButtons) {
                button.setVisibility(View.VISIBLE);
                button.setAlpha(1.0f);
            }
        });
    }


    public void disablePlayerAction() {
        mainGameActivity.runOnUiThread(() -> {
            for (Button button : allPlayerButtons) {
                button.setVisibility(View.GONE);
                button.setAlpha(0.5f);
            }
        });
    }



    public void updateRoundView(int round) {
        mainGameActivity.runOnUiThread(() -> {
            TextView roundView = mainGameActivity.findViewById(R.id.textViewRound);
            String roundText = mainGameActivity.getString(R.string.round_text, round);
            roundView.setText(roundText);
        });
    }


    public void spawnMonster(MonsterDTO monster) {
        int monsterZone = monster.getZone();
        mainGameActivity.runOnUiThread(() -> {
            Log.d("SpawnMonster", "Spawnmonstermethode ausgeführt");
            switch (monsterZone) {
                case 1:
                    spawnMonsterInZone(zone1monster, monster);
                    break;
                case 2:
                    spawnMonsterInZone(zone2monster, monster);
                    break;
                case 3:
                    spawnMonsterInZone(zone3monster, monster);
                    break;
                case 4:
                    spawnMonsterInZone(zone4monster, monster);
                    break;
                default:

                    break;

            }
        });
    }


    private void spawnMonsterInZone(List<Button> zoneButtons, MonsterDTO monster) {
        mainGameActivity.runOnUiThread(() -> {
            Log.d("SpawnMonster", "Spawnmonsterinzone ausgeführt");
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


        });
    }



    public void doDamageToTower() {
        final String TAG = "GameDebug";
        Log.d(TAG, "Initial active monster count: " + monsterManager.countActiveMonsters());

        mainGameActivity.runOnUiThread(() -> {
            List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);
            for (List<Button> zone : zones) {
                for (Button button : zone) {
                    if (isButtonSwordsman(button) && button.getVisibility() == View.VISIBLE && button.getTag() instanceof MonsterDTO) {
                        MonsterDTO monster = (MonsterDTO) button.getTag();
                        gameController.sendMonsterAttackMessage(String.valueOf(monster.getId()), "0");
                        Log.d(TAG, "Sending attack message for Monster ID: " + monster.getId());
                    }
                }
            }
            Log.d(TAG, "Final active monster count: " + monsterManager.countActiveMonsters());
        });
    }


    public void setupRotate(List<List<Button>> zones) {
        for (List<Button> zone : zones) {
            for (Button button : zone) {
                buttonRotateViews.put(button, new ButtonRotateView());
            }
        }
    }

    public void updateMonsterHealth(String monsterId, int newLifePoints) {
        mainGameActivity.runOnUiThread(() -> {
            List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);
            for (List<Button> zone : zones) {
                updateHealthInZone(zone, monsterId, newLifePoints);
            }
        });
    }

    private void updateHealthInZone(List<Button> zone, String monsterId, int newLifePoints) {
        for (Button button : zone) {
            MonsterDTO monster = getMonsterFromButton(button, monsterId);
            if (monster != null) {
                manageMonsterHealth(button, monster, newLifePoints);
                break;
            }
        }
    }

    private MonsterDTO getMonsterFromButton(Button button, String monsterId) {
        MonsterDTO monster = (MonsterDTO) button.getTag();
        return (monster != null && String.valueOf(monster.getId()).equals(monsterId)) ? monster : null;
    }

    private void manageMonsterHealth(Button button, MonsterDTO monster, int newLifePoints) {
        ButtonRotateView rotateView = buttonRotateViews.get(button);
        if (newLifePoints <= 0) {
            handleMonsterDeath(button, monster, rotateView);
        } else {
            updateMonster(button, monster, newLifePoints, rotateView);
        }
    }

    private void handleMonsterDeath(Button button, MonsterDTO monster, ButtonRotateView rotateView) {
        removeMonsterVisuals(button);

        if (rotateView != null) {
            rotateView.resetRotation(button);
        }
        monsterManager.removeMonster(String.valueOf(monster.getId()));
        Log.d(mainGameViewString, "Monster " + monster.getId() + " is dead and removed.");
    }

    private void updateMonster(Button button, MonsterDTO monster, int newLifePoints, ButtonRotateView rotateView) {
        monster.setLifePoints(newLifePoints);
        monsterManager.updateMonster(monster.getId(), newLifePoints);
        if (rotateView != null) {
            rotateView.rotateButton(button);
        }
        Log.d(mainGameViewString, "Updated Monster " + monster.getId() + " HP to " + newLifePoints);
    }

    private void removeMonsterVisuals(Button button){
        button.setVisibility(View.GONE);
        button.setTag(null);
        button.setBackground(null);
    }



    public boolean isMonsterInAttackZone() {
        List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);
        for (List<Button> zone : zones) {
            for (Button button : zone) {
                if (isButtonSwordsman(button) && isButtonFull(button)) {
                    return true;
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
        TowerDTO tower = new TowerDTO(10, 0);
        towerButton.setTag(tower);
        towerButton.setBackgroundResource(R.drawable.tower);
        updateTowerDisplay();
    }

    public void updateTowerDisplay() {
        TowerDTO tower = (TowerDTO) towerButton.getTag();
        String hpText = mainGameActivity.getString(R.string.button_tower_hp, tower.getLifePoints());
        towerButton.setText(hpText);
    }

    public void modifyTowerLifePoints(int lifeChange) {
        mainGameActivity.runOnUiThread(() -> {
            TowerDTO tower = (TowerDTO) towerButton.getTag();
            tower.setLifePoints(lifeChange);
            updateTowerDisplay();
        });
    }

    public void moveMonstersInward() {
        mainGameActivity.runOnUiThread(() -> {
            List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);
            for (List<Button> zone : zones) {
                moveZoneMonstersInward(zone);
            }
        });
    }

    private void moveZoneMonstersInward(List<Button> zone) {
        for (int i = 9; i >= 3; i -= 3) {
            moveMonstersInSegment(zone, i);
        }
    }

    private void moveMonstersInSegment(List<Button> zone, int i) {
        for (int j = 0; j < 3; j++) {
            if (i + j < zone.size()) {
                Button outer = zone.get(i + j - 3);
                if (isButtonMonster(outer)) {
                    moveMonsterInward(zone, outer, i + j);
                }
            }
        }
    }

    private void moveMonsterInward(List<Button> zone, Button outer, int startIdx) {
        MonsterDTO monster = (MonsterDTO) outer.getTag();
        for (int k = 0; k < 3; k++) {
            if (startIdx + k < zone.size()) {
                Button inner = zone.get(startIdx + k);
                if (isButtonEmpty(inner)) {
                    transferMonster(outer, inner, monster);
                    break;
                }
            }
        }
    }

    private boolean isButtonMonster(Button button) {
        return button.getVisibility() == View.VISIBLE && button.getTag() instanceof MonsterDTO;
    }

    private void transferMonster(Button outer, Button inner, MonsterDTO monster) {
        Drawable background = outer.getBackground();
        removeMonsterVisuals(outer);

        inner.setBackground(background);
        inner.setVisibility(View.VISIBLE);
        inner.setTag(monster);

        applyRotationOnMove(outer, inner);
    }

    private void applyRotationOnMove(Button outer, Button inner) {
        ButtonRotateView outerRotateView = buttonRotateViews.get(outer);
        if (outerRotateView != null) {
            float currentRotation = outerRotateView.getCurrentRotation();
            outerRotateView.resetRotation(outer); // Reset outer rotation

            // Set the same rotation to the inner button
            ButtonRotateView innerRotateView = buttonRotateViews.get(inner);
            if (innerRotateView != null) {
                innerRotateView.setCurrentRotation(currentRotation);
                innerRotateView.applyCurrentRotation(inner);
            }
        }
    }


    private boolean isButtonFull(Button button) {
        return button.getVisibility() == View.VISIBLE && button.getBackground() != null;
    }


    private boolean isButtonEmpty(Button button) {
        return button.getVisibility() == View.GONE;
    }

    public void initializeUsernamesWithPoints(Map<String, Integer> usernamesWithPoints) {
        for (Map.Entry<String, Integer> entry : usernamesWithPoints.entrySet()) {
            updateListTrophies(entry.getKey(), entry.getValue());
        }
    }
    public void updateListTrophies(String username, int points) {
        Log.d(mainGameViewString, "Updating list trophies for user: " + username + " with points: " + points);
        mainGameActivity.runOnUiThread(() -> {
            trophiesMap.put(username, points);
            refreshTrophiesList();
        });
    }

    private void refreshTrophiesList() {
        Log.d(mainGameViewString, "Refreshing trophies list");
        trophiesList.clear(); // Clear the current list
        for (Map.Entry<String, Integer> entry : trophiesMap.entrySet()) {
            trophiesList.add(entry.getKey() + ": " + entry.getValue());
        }
        trophiesAdapter.notifyDataSetChanged();
        Log.d(mainGameViewString, "Trophies list updated");
    }
    private void initializeMonsterZones() {
        for (int i = 0; i < 4; i++) {
            monsterZones.add(new ArrayList<>());
        }
    }
    public static void showMonster(){
        disableForMonsters();
        List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);
        for (List<Button> zone : zones) {

            for(Button b:zone){

                if (b.getTag()!=null) {
                    MonsterDTO monster = (MonsterDTO) b.getTag();
                    int tagFromMonster = monster.getId();


                    if(checkIfItsInList(tagFromMonster)) {

                        b.setOnClickListener(v -> {

                            mainGameActivity.findViewById(R.id.stop).setVisibility(View.GONE);
                            mainGameActivity.sendCardAttackMonsterMessage(String.valueOf(tagFromMonster), removeCardFromHandCards());
                            mainGameActivity.sendPlayerTrophiesRequest();
                        });
                    }else{
                        b.setBackgroundResource(0);
                    }}


            }
        }
    }

    public static String removeCardFromHandCards(){
        CardView currentCard = CarddeckActivity.selectedCard;
        LinearLayout getCardName = (LinearLayout) currentCard.getChildAt(0);
        TextView getTag = (TextView)  getCardName.getChildAt(2);
        String cardId = (String) getTag.getTag();
        ActionCardDTO toRemove = new ActionCardDTO();
        for(ActionCardDTO a:  CardDeckController.playerHand.getCards()){
            if(a.getId() == Integer.parseInt(cardId)){
                toRemove=a;
            }
        }
        CardDeckController.playerHand.removeCard(toRemove);
        return cardId;
    }


    private static void disableForMonsters(){
        mainGameActivity.findViewById(R.id.buttonEndRound).setVisibility(View.GONE);
        mainGameActivity.findViewById(R.id.buttonCards).setVisibility(View.GONE);
        mainGameActivity.findViewById(R.id.stop).setVisibility(View.VISIBLE);
        mainGameActivity.findViewById(R.id.stop).setOnClickListener(v -> {


            showAllMonsters();
            MainGameActivity.monsterList=new ArrayList<>();
            mainGameActivity.transitionToCardDeckScreen();
            enableForMonsters();

        });

    }

    private static void enableForMonsters(){
        mainGameActivity.findViewById(R.id.buttonEndRound).setVisibility(View.VISIBLE);
        mainGameActivity.findViewById(R.id.buttonCards).setVisibility(View.VISIBLE);
        mainGameActivity.findViewById(R.id.stop).setVisibility(View.GONE);

    }
    private static boolean checkIfItsInList(int id) {

        for (String m : MainGameActivity.monsterList) {
            if (id == Integer.parseInt(m)) {
                return true;
            }
        }
        return false;
    }




    public void tauschanfrageErhalten(JSONObject message) throws JSONException {

        int id = Integer.parseInt(message.getString("id"));
        String name = message.getString("name");
        int zone = Integer.parseInt(message.getString("zone"));
        ActionCardDTO karte = new ActionCardDTO(name, zone,id);
        Log.d("Karte in tauschenAnfrageErhalten",karte.getName());
        View popupDrawable = mainGameActivity.getLayoutInflater().inflate(R.layout.popuptauschenanfrage, null);


        mainGameActivity.runOnUiThread(() -> {
            int width = ViewGroup.LayoutParams.WRAP_CONTENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            PopupWindow popupTauschanfrage = new PopupWindow(popupDrawable,width,height,true);
            popupTauschanfrage.setOutsideTouchable(false);
            popupTauschanfrage.setElevation(10);
            popupTauschanfrage.showAtLocation(mainGameActivity.getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);

            mainGameActivity.dimWindow(popupTauschanfrage);
            Button ok = popupDrawable.findViewById(R.id.ok);

            ok.setOnClickListener(v -> {
                popupTauschanfrage.dismiss();
                mainGameActivity.goToHandCards(message);
            });
        });

    }
    public static void showAllMonsters() {
        List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);

        for (Map.Entry<Integer, MonsterDTO> entry : monsterManager.activeMonsters.entrySet()) {
            Log.d("showAllMonsters", entry.getKey() + "/" + entry.getValue());
        }
        for (List<Button> zone : zones) {

            for (Button b : zone) {
                if(b.getTag()!=null){
                    b.setOnClickListener(null);

                    MonsterDTO currentM= (MonsterDTO) b.getTag();

                    switch (currentM.getName()){
                        case "Schleim":
                            b.setBackgroundResource(R.drawable.monster_slime);
                            break;
                        case "Sphinx":
                            b.setBackgroundResource(R.drawable.monster_sphinx);
                            break;
                        case "Bullrog":
                            b.setBackgroundResource(R.drawable.monster_bullrog);
                            break;
                        default:
                            Log.d("Error in spawnMonsterInZone", "Kein passendes Monster");
                    }

                }
            }
        }
    }
    public void updateMonsterList(String monsterId, int lifepoints){

        mainGameActivity.runOnUiThread(() -> {
            updateMonsterHealth(monsterId, lifepoints);
            updateGameView();
        });
    }

    private void updateGameView(){
        if(gameController.currentPlayer()){
            enableForMonsters();
            showAllMonsters();
        }
    }
    public void updateMonstersView(String monsterId) {
        List<List<Button>> zones = Arrays.asList(zone1monster, zone2monster, zone3monster, zone4monster);
        for (List<Button> zone : zones) {

            for (Button b : zone) {

                if (b.getTag() != null) {
                    MonsterDTO monster = (MonsterDTO) b.getTag();
                    int tagFromMonster = monster.getId();
                    if (tagFromMonster == Integer.parseInt(monsterId)) {
                        b.setTag(null);
                        b.setBackgroundResource(0);
                        b.setVisibility(View.GONE);
                    }

                }
            }
        }
    }


    private void showAccusePopup() {
        View popupView = mainGameActivity.getLayoutInflater().inflate(R.layout.popup_accuse_cheater, null);

        PopupWindow accusePopup = new PopupWindow(popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        Spinner spinnerPlayers = popupView.findViewById(R.id.accuseSpinner);
        Button buttonAccuse = popupView.findViewById(R.id.buttonAccuseCheater);
        Button buttonCancel = popupView.findViewById(R.id.buttonCancel);

        List<String> players = new ArrayList<>(usernamesWithPoints.keySet());
        players.remove(gameController.currentPlayer);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(mainGameActivity, android.R.layout.simple_spinner_item, players);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayers.setAdapter(adapter);

        buttonAccuse.setOnClickListener(v -> {
            String selectedPlayer = (String) spinnerPlayers.getSelectedItem();
            gameController.sendAccusationMessage(selectedPlayer);
            accusePopup.dismiss();
        });

        buttonCancel.setOnClickListener(v -> accusePopup.dismiss());

        accusePopup.setOutsideTouchable(false);
        accusePopup.setElevation(10);
        accusePopup.showAtLocation(mainGameActivity.getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);
    }


}