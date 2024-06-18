package com.example.munchkin.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.munchkin.messageformat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.MainGameView;
import com.example.munchkin.view.animations.ZoomDetectorView;
import org.json.JSONObject;
import com.example.munchkin.dto.ActionCardDTO;
import com.example.munchkin.player.PlayerHand;
import com.example.munchkin.controller.DrawCardController;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.munchkin.controller.SpawnMonsterController;
import com.example.munchkin.view.DiceRollView;
import java.util.ArrayList;


public class MainGameActivity extends AppCompatActivity {

    private ZoomDetectorView zoomDetectorView;
    private ScaleGestureDetector scaleGestureDetector;

    private GameController gameController;

    private ArrayList<Integer> diceResults = new ArrayList<>();

    private ActivityResultLauncher<Intent> diceRollLauncher;

    private DrawCardController drawCardController;

    private PlayerHand handkarten;


    public static ArrayList<String>  monsterList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);



        setupControllers();
        setupDiceRollLauncher();


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.game), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        zoomDetectorView.onTouchEvent(event);
        return true;
    }

    private void setupControllers() {
        WebSocketClientModel model = new WebSocketClientModel();
        View mainView = findViewById(R.id.game);
        zoomDetectorView = new ZoomDetectorView(this, mainView);
        scaleGestureDetector = new ScaleGestureDetector(this, zoomDetectorView);


        MainGameView mainGameView = new MainGameView(this);
        SpawnMonsterController spawnMonsterController = new SpawnMonsterController(model, mainGameView);
        gameController = new GameController(model, mainGameView, spawnMonsterController,this);
        drawCardController = new DrawCardController(model, mainGameView);

        mainGameView.setGameController(gameController);

        this.handkarten = new PlayerHand();

        MessageRouter router = new MessageRouter();
        router.registerController("PLAYER_ATTACK", gameController);
        router.registerController("MONSTER_ATTACK", gameController);
        router.registerController("SPAWN_MONSTER", spawnMonsterController);
        router.registerController("REQUEST_USERNAMES", gameController);
        router.registerController("DRAW_CARD", drawCardController);
        router.registerController("SWITCH_CARD_PLAYER_RESPONSE1", gameController);
        router.registerController("REQUEST_ROLL", gameController);
        router.registerController("ROUND_COUNTER", gameController);
        router.registerController("CURRENT_PLAYER", gameController);
        router.registerController("CARD_ATTACK_MONSTER", gameController);
        router.registerController("CHEAT_MODE", gameController);
        router.registerController("END_GAME", gameController);
        router.registerController("PLAYER_TROPHIES", gameController);
        router.registerController("ACCUSATION_MSG", gameController);
        model.setMessageRouter(router);

    }




    public void sendMessage() {
        drawCardController.drawMeassage();
    }

    public void addcardtolist(ActionCardDTO karte){
        handkarten.addCard(karte);
    }

    public void transitionToCardDeckscreen() {
        Intent intent = new Intent(this, CarddeckActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }



    public void dimmwindow(PopupWindow popup){
        View container = (View) popup.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        // add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
    public void gehezuhandkarten(JSONObject messagefromserver){
        Bundle b = new Bundle();
        b.putString("key", messagefromserver.toString()); //Your id
        //
        Intent handcards = new Intent(this, CarddeckActivity.class);
        handcards.putExtras(b);
        CarddeckActivity.passivmode=1;
        startActivity(handcards);

    }


    public void requestRoll() {
        Intent intent = new Intent(this, DiceRollView.class);
        diceRollLauncher.launch(intent);
    }

    public void sendCardAttackMonsterMessage(String monsterId, String cardID){
        gameController.cardAttackMonsterMessage(monsterId,cardID);
    }

    public void sendPlayerTrophiesRequest(){
        gameController.sendPlayerTrophiesRequest();
    }



    private void setupDiceRollLauncher() {
        diceRollLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<Integer> newResults = result.getData().getIntegerArrayListExtra("diceResults");
                        if (newResults != null) {
                            diceResults.addAll(newResults);
                            gameController.onDiceRolled(newResults.stream().mapToInt(i->i).toArray());
                        }
                    }
                }
        );
    }

    public void navigateToWinScreen(String winner) {
        runOnUiThread(() -> {
        Intent winIntent = new Intent(this, WinActivity.class);
        winIntent.putExtra("winnerName", winner);
        startActivity(winIntent);
        finish();
        });
    }

    public void navigateToLoseScreen(String winner) {
        runOnUiThread(() -> {
        Intent  loseIntent = new Intent(this, LoseActivity.class);
        loseIntent.putExtra("winnerName", winner);
        startActivity( loseIntent);
        finish();
        });
    }

    public void navigateToAllLoseScreen() {
        runOnUiThread(() -> {
            Intent allloseIntent = new Intent(this, AllLoseActivity.class);
            startActivity(allloseIntent);
            finish();
        });

    }


}