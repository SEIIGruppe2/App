package com.example.munchkin;

import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CarddeckActivity extends AppCompatActivity {
    LinearLayout parentlayout;
    CardView selectedCard;

    Button spielen;
    Button tauschen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carddeck);
        parentlayout= findViewById(R.id.containerforcards);
        spielen= findViewById(R.id.buttonspielen);
        tauschen = findViewById(R.id.buttontauschen);
        fillcards();
        setonclicklistenertoButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public void fillcards(){
        for(int i=0; i<=20; i++){
            CardView cards = new CardView(this);
            float dpValue = 125f;
            float dpValue2 = 200f;// Change this value to your desired dp
            float density = getResources().getDisplayMetrics().density;
            int pixelValue1 = (int) (dpValue * density);
            int pixelValue2 = (int) (dpValue2 * density);


            LinearLayout.LayoutParams layoutParamscards = new LinearLayout.LayoutParams(pixelValue1,pixelValue2);

            layoutParamscards.setMargins(16,16,16,16);
            cards.setLayoutParams(layoutParamscards);
            cards.setForeground(getResources().getDrawable(R.drawable.bg_roundrect_ripple_light_border));
            float radius = 23f;
            int pixelValueradius = (int) (radius * density);
            cards.setRadius(pixelValueradius);
            //karteninhalt
            LinearLayout karteninhalt = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParamskarteninhalt = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.MATCH_PARENT);
            karteninhalt.setOrientation(LinearLayout.VERTICAL);
            karteninhalt.setLayoutParams(layoutParamskarteninhalt);


            //kartenname
            TextView kartenname = new TextView(this);
            LinearLayout.LayoutParams layoutParamskartenname = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            float margin = 8f;
            int pixelValuemargin = (int) (margin * density);
            layoutParamskartenname.setMargins(0,pixelValuemargin,pixelValuemargin,0);
            Typeface typeface = ResourcesCompat.getFont(this, R.font.chewyregular);
            kartenname.setTypeface(typeface);
            String kartenname1 = "blauerritter1";
            int resIDkartenname = getResources().getIdentifier(kartenname1,"string",getPackageName());
            kartenname.setText(resIDkartenname);
            kartenname.setTextColor(getResources().getColor(R.color.black));
            kartenname.setTextSize(16);
            kartenname.setGravity(Gravity.CENTER);
            kartenname.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            kartenname.setLayoutParams(layoutParamskartenname);
            karteninhalt.addView(kartenname);

            ImageView kartenbild = new ImageView(this);
            float dpvalueimage = 100f;
            int pixelValueimage = (int) (dpvalueimage * density);
            LinearLayout.LayoutParams layoutParamskartenbild = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                   pixelValueimage);
            String test ="blue_knight";
            int resIDkartenbild = getResources().getIdentifier(test,"drawable",getPackageName());
            kartenbild.setImageResource(resIDkartenbild);
            kartenbild.setLayoutParams(layoutParamskartenbild);
            karteninhalt.addView(kartenbild);

            TextView kartenbeschreibung = new TextView(this);
            LinearLayout.LayoutParams layoutParamskartenbeschreibung = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.MATCH_PARENT);
            String kartenbeschreibung2 = "blauerritter2";
            int resIDkartenbeschreibung = getResources().getIdentifier(kartenbeschreibung2,"string",getPackageName());
            kartenbeschreibung.setText(resIDkartenbeschreibung);
            kartenbeschreibung.setTextColor(getResources().getColor(R.color.black));
            kartenbeschreibung.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            kartenbeschreibung.setTextSize(12);
            layoutParamskartenbeschreibung.setMargins(0,4,0,0);
            kartenbeschreibung.setTypeface(typeface);
            kartenbeschreibung.setLayoutParams(layoutParamskartenbeschreibung);
            karteninhalt.addView(kartenbeschreibung);

            cards.addView(karteninhalt);



            cards.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    oncardclick(cards);

                }
            }));

            parentlayout.addView(cards);

        }
    }
    public void oncardclick(CardView card){
        Log.d("card", "Card was selected");


        if (card == selectedCard) {
            changecardview(selectedCard, 125f,200f,16,16,12);
            selectedCard = null;
            card.setForeground(getResources().getDrawable(R.drawable.bg_roundrect_ripple_light_border));
            spielen.setVisibility(View.GONE);
            tauschen.setVisibility(View.GONE);// Setze die ausgewählte Karte zurück
        } else {

            if (selectedCard != null) {
                changecardview(selectedCard, 125f,200f,16,16,12);
                selectedCard.setForeground(getResources().getDrawable(R.drawable.bg_roundrect_ripple_light_border));
            }
            changecardview(card, 155f,250f,20,20,15);
            card.setForeground(getResources().getDrawable(R.drawable.yellowborder));
            selectedCard = card;
            spielen.setVisibility(View.VISIBLE);
            tauschen.setVisibility(View.VISIBLE);// Setze die ausgewählte Karte
        }
    }
    public void changecardview(CardView card, float layoutparam1, float layoutparam2, int margin, int textsizekartenname, int textsizekartenbeschreibung){

        float density = getResources().getDisplayMetrics().density;
        int pixelValue1 = (int) (layoutparam1 * density);
        int pixelValue2 = (int) (layoutparam2 * density);


        LinearLayout.LayoutParams layoutParamscards = new LinearLayout.LayoutParams(pixelValue1,pixelValue2);
        layoutParamscards.setMargins(margin,margin,margin,margin);
        card.setLayoutParams(layoutParamscards);
        LinearLayout karteninhalt = (LinearLayout) card.getChildAt(0);
        TextView kartenname = (TextView) karteninhalt.getChildAt(0);
        kartenname.setTextSize(textsizekartenname);
        TextView kartenbeschreibung = (TextView) karteninhalt.getChildAt(2);
        kartenbeschreibung.setTextSize(textsizekartenbeschreibung);
    }

    public void setonclicklistenertoButtons(){
        Button zurueck = findViewById(R.id.buttonzurueck1);
        zurueck.setOnClickListener(v -> {
           zurueck();
        });

        Button zugbeenden = findViewById(R.id.buttonzugbeenden);
        zugbeenden.setOnClickListener(v -> {
           zugbeenden();
        });
        Button tauschen = findViewById(R.id.buttontauschen);
        tauschen.setOnClickListener(v -> {
            tauschen("blauerritter");
        });
    }

    private void zurueck(){
        Intent intent = new Intent(this, LoadingscreenActivity.class);
        startActivity(intent);
    }

    private void zugbeenden(){

        View popupdrawable = getLayoutInflater().inflate(R.layout.popupzugbeenden, null);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupzugzuende = new PopupWindow(popupdrawable,width,height,true);
        popupzugzuende.setOutsideTouchable(false);
        popupzugzuende.setElevation(10);
        popupzugzuende.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);
        dimmwindow(popupzugzuende);
        Button ja = popupdrawable.findViewById(R.id.buttonja);
        Button nein = popupdrawable.findViewById(R.id.nein);


        ja.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoadingscreenActivity.class);
            startActivity(intent);
        });
        nein.setOnClickListener(v -> {
            popupzugzuende.dismiss();
        });
    }

    private void tauschen(String karte){

        View popupdrawable = getLayoutInflater().inflate(R.layout.popouttauschen, null);

        PopupWindow popuptauschen = new PopupWindow(popupdrawable,1750,1000,true);
        popuptauschen.setOutsideTouchable(false);
        popuptauschen.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);
        dimmwindow(popuptauschen);
        Button tauschen = popupdrawable.findViewById(R.id.buttontauschen2);
        Button zurueck = popupdrawable.findViewById(R.id.buttonzurueck2);
        TextView kartenname = popupdrawable.findViewById(R.id.kartennamepopup);
        TextView kartenbeschreibung =  popupdrawable.findViewById(R.id.kartenbeschreibungpopup);
        ImageView kartenbild = popupdrawable.findViewById(R.id.kartenbildpopup);
        String kartenname2 = karte+"1";
        int resIDkartenname = getResources().getIdentifier(kartenname2,"string",getPackageName());
        kartenname.setText(resIDkartenname);

        String kartenbeschreibung2 = karte+"2";
        int resIDkartennbeschreibung = getResources().getIdentifier(kartenbeschreibung2,"string",getPackageName());
        kartenbeschreibung.setText(resIDkartennbeschreibung);

        String kartenbild2 = "blue_knight";
        int resIDkartenbild = getResources().getIdentifier(kartenbild2,"drawable",getPackageName());
        kartenbild.setImageResource(resIDkartenbild);




        String[] options = {"Kartenstapel", "Spieler 1", "Spieler 2", "Spieler 3"};
        Spinner dropdownmenu = popupdrawable.findViewById(R.id.spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(CarddeckActivity.this, R.layout.list, options);
        dropdownmenu.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.list);

        tauschen.setOnClickListener(v -> {
            //tobedone
            Intent intent = new Intent(this, LoadingscreenActivity.class);
            startActivity(intent);
        });

        zurueck.setOnClickListener(v -> {
            popuptauschen.dismiss();
        });
    }
    private void dimmwindow(PopupWindow popup){
        View container = (View) popup.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        // add flag
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    }



