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

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.GameController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.MainGameView;
import com.example.munchkin.view.ZoomDetectorView;

import org.json.JSONObject;


import com.example.munchkin.R;
import com.example.munchkin.view.MainGameView;
import com.example.munchkin.view.ZoomDetectorView;

public class MainGameActivity extends AppCompatActivity {

    private MainGameView mainGameView;
    private ZoomDetectorView zoomDetectorView;
    private ScaleGestureDetector scaleGestureDetector;

    private GameController gameController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);

        mainGameView = new MainGameView(this);
        View mainView = findViewById(R.id.game);
        zoomDetectorView = new ZoomDetectorView(this, mainView);
        scaleGestureDetector = new ScaleGestureDetector(this, zoomDetectorView);


        WebSocketClientModel model = new WebSocketClientModel();

        MessageRouter router = new MessageRouter();

        gameController = new GameController(model,mainGameView);
        router.registerController("PLAYER_ATTACK", gameController);
        router.registerController("MONSTER_ATTACK", gameController);
        router.registerController("SWITCH_CARD_PLAYER_RESPONSE", gameController);


        model.setMessageRouter(router);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        zoomDetectorView.onTouchEvent(event);
        return true;
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
