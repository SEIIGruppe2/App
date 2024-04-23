package com.example.munchkin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.LoadingscreenActivity;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.MainGameView;

import org.json.JSONObject;

public class MainGameActivity extends AppCompatActivity {

    private MainGameView mainGameView;
    private GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);
        WebSocketClientModel model = new WebSocketClientModel();

        MessageRouter router = new MessageRouter();

        mainGameView = new MainGameView(this);
        gameController = new GameController(model,mainGameView);
        router.registerController("PLAYER_ATTACK", gameController);
        router.registerController("MONSTER_ATTACK", gameController);
        router.registerController("SWITCH_CARD_PLAYER_RESPONSE", gameController);


        model.setMessageRouter(router);
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
        Intent handkarten = new Intent(this, CarddeckActivity.class);
        handkarten.putExtras(b);
        CarddeckActivity.passivmode=1;
        startActivity(handkarten);

    }

    public void gehezukarten(){
        Intent intent = new Intent(this, CarddeckActivity.class);
        startActivity(intent);
    }
}
