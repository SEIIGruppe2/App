package com.example.munchkin.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;


public class TradeCardsActivity extends AppCompatActivity {

    private CardDeckController cardDeckController;
    private WebSocketClientModel model = new WebSocketClientModel();
    private TradeCardsView tradeCardsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tauschen_test_layer);

        /*MessageRouter router = new MessageRouter();
        cardDeckController = new CardDeckController(model, tradeCardsView);

        // Now that cardDeckController is initialized, we can create tradeCardsView
        tradeCardsView = new TradeCardsView(cardDeckController, findViewById(R.id.tauschen_btn_deck), findViewById(R.id.tauschen_btn_player));

        cardDeckController.setTradeCardsView(tradeCardsView);
        cardDeckController.requestUsernames();

        // Spinner setup remains mostly the same, assuming updateCardImageView is implemented correctly
        setupSpinner();

        // Setup router and model
        router.registerController("SWITCH_CARDS_DECK", cardDeckController);
        router.registerController("SWITCH_CARDS_PLAYER", cardDeckController);
        model.setMessageRouter(router);
    }

    private void setupSpinner() {
        Spinner cardSpinner = findViewById(R.id.cardSelectionSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getCardNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(adapter);

        //soll mit activity übergeben werden
        String kartedesc = "blauerritter";

        TextView kartenname = findViewById(R.id.kartennamepopup);
        TextView kartenbeschreibung =  findViewById(R.id.kartenbeschreibungpopup);
        ImageView kartenbild = findViewById(R.id.kartenbildpopup);
        String kartenname2 = kartedesc+"1";
        int resIDkartenname = getResources().getIdentifier(kartenname2,"string",getPackageName());
        kartenname.setText(resIDkartenname);

        String kartenbeschreibung2 = kartedesc+"2";
        int resIDkartennbeschreibung = getResources().getIdentifier(kartenbeschreibung2,"string",getPackageName());
        kartenbeschreibung.setText(resIDkartennbeschreibung);

        String kartenbild2 = kartedesc;
        int resIDkartenbild = getResources().getIdentifier(kartenbild2,"drawable",getPackageName());
        kartenbild.setImageResource(resIDkartenbild);


        String[] options = {"Kartenstapel", "Spieler 1", "Spieler 2", "Spieler 3"};
        Spinner dropdownmenu = findViewById(R.id.spinner1);


        //ArrayAdapter<String> adapter = new ArrayAdapter<>(TradeCardsActivity.this, R.layout.list, options);
        //dropdownmenu.setAdapter(adapter);
        //adapter.setDropDownViewResource(R.layout.list);




        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Implement card selection logic for trade
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private List<String> getCardNames() {

        return new ArrayList<>();*/
    }


}