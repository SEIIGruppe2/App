package com.example.munchkin.activity;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.TradeCardsView;

public class TradeCardsActivity extends AppCompatActivity {

    private CardDeckController cardDeckController;

    private TradeCardsView tradeCardsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popouttauschen);
        WebSocketClientModel model = new WebSocketClientModel();
        MessageRouter router = new MessageRouter();


        Button switchButtonPlayer = findViewById(R.id.buttontauschendeck);
        Button switchButtonDeck = findViewById(R.id.buttonzurueck2);
        Spinner spinner = findViewById(R.id.spinner1);
        TextView karte = findViewById(R.id.kartennamepopup);

        tradeCardsView = new TradeCardsView(null, switchButtonPlayer, switchButtonDeck,spinner,karte); // Temporarily passing null


        cardDeckController = new CardDeckController(model, tradeCardsView);

        tradeCardsView.setCardDeckController(cardDeckController);

        // Setup router and model
        router.registerController("SWITCH_CARDS_DECK", cardDeckController);
        router.registerController("SWITCH_CARDS_PLAYER", cardDeckController);
        model.setMessageRouter(router);


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


        ArrayAdapter<String> adapter = new ArrayAdapter<>(TradeCardsActivity.this, R.layout.list, options);
        dropdownmenu.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.list);




    }


}