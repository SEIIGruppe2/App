package com.example.munchkin.view;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.munchkin.R;
import com.example.munchkin.activity.LobbyActivity;
import com.example.munchkin.controller.LobbyController;


public class LobbyView {

    LobbyActivity lobbyActivity;
    TextView[] textViewArray = new TextView[4];
    public LobbyView( LobbyActivity lobbyActivity){
        this.lobbyActivity= lobbyActivity;

        setupUI();
    }
    public void setupUI(){
        Button spielStarten= lobbyActivity.findViewById(R.id.buttonSpielStarten);
        spielStarten.setOnClickListener(v -> {
            lobbyActivity.goToMainGame();

        });
       spielStarten.setVisibility(View.GONE);
        textViewArray[0]=lobbyActivity.findViewById(R.id.Player1);
        textViewArray[1]=lobbyActivity.findViewById(R.id.Player2);
        textViewArray[2]=lobbyActivity.findViewById(R.id.Player3);
        textViewArray[3]=lobbyActivity.findViewById(R.id.Player4);

    }
    public void updateUserList(int arraylength){
        for(int i=0; i< LobbyController.usernames.length;i++){
            if(LobbyController.usernames[i].length()>0){
            textViewArray[i].setText("Spieler "+String.valueOf(i+1)+": "+LobbyController.usernames[i]);
        }else{
                textViewArray[i].setText("Spieler "+String.valueOf(i+1)+": ");
            }
        }
        if(arraylength==4){
            Button spielStarten= lobbyActivity.findViewById(R.id.buttonSpielStarten);
            spielStarten.setVisibility(View.GONE);
        }
    }
}
