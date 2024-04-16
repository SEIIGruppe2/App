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

import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.LoadingscreenActivity;
import com.example.munchkin.view.ConnectToServerView;
import com.example.munchkin.view.MainGameView;

public class MainGameActivity extends AppCompatActivity {

    private MainGameView mainGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_game);

        mainGameView = new MainGameView(this);
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
    public void gehezuhandkarten(){
        Intent handkarten = new Intent(this, CarddeckActivity.class);
        CarddeckActivity.passivmode=1;
        startActivity(handkarten);

    }
}
