package com.example.munchkin;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Carddeck extends AppCompatActivity {
    LinearLayout parentlayout;
    CardView selectedCard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carddeck);
        parentlayout= findViewById(R.id.containterforcards);
        //fillcards();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void fillcards(){
        for(int i=0; i<=5; i++){

            CardView cards = new CardView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            cards.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oncardclick(cards);
                    //buttonsvisible machen
                    //eventuell alte ausgewählte karte entleuchten
                }
            }));

            layoutParams.setMargins(20, 10, 20, 0);
            // Setze die Layoutparameter für die ImageView
            cards.setLayoutParams(layoutParams);
            // Setze das Bild für die ImageView
            cards.setBackgroundResource(R.drawable.card); // Ändere 'your_image' zu deinem Bild
            // Füge die ImageView zum LinearLayout hinzu
            parentlayout.addView(cards);

        }
    }
    public void oncardclick(CardView card){
        Log.d("card", "Card was selected");
        if (card == selectedCard) {
            // Wenn ja, entferne die Markierung
            card.setBackgroundColor(Color.TRANSPARENT); // Setze die Hintergrundfarbe auf transparent
            selectedCard = null; // Setze die ausgewählte Karte zurück
        } else {
            // Wenn nein, markiere die Karte
            if (selectedCard != null) {
                // Entferne die Markierung von der vorher ausgewählten Karte, wenn eine vorhanden ist
                selectedCard.setBackgroundColor(Color.TRANSPARENT);

            }
            // Markiere die ausgewählte Karte
            card.setBackgroundColor(Color.RED); // Ändere die Hintergrundfarbe nach deinen Wünschen
            selectedCard = card; // Setze die ausgewählte Karte
        }
    }

    }

