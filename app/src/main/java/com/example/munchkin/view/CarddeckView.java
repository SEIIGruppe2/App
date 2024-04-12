package com.example.munchkin.view;

import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.example.munchkin.DrawableUtils.CardUtils;
import com.example.munchkin.R;
import com.example.munchkin.activity.CarddeckActivity;
import com.example.munchkin.activity.DrawActivity;
import com.example.munchkin.controller.CardDeckController;

public class CarddeckView {

    LinearLayout parentlayout;
    Button spielen;
    Button tauschen;

    Button zurueck;

    Button zugbeenden;

    private CarddeckActivity carddeckActivity;

    private CardDeckController cardDeckController;


    public CarddeckView(CarddeckActivity carddeckActivity){
        this.carddeckActivity=carddeckActivity;

        setupUI();

    }

    private void setupUI(){

        parentlayout= carddeckActivity.findViewById(R.id.containerforcards);

        zugbeenden = carddeckActivity.findViewById(R.id.buttonzugbeenden);
        zugbeenden.setOnClickListener(v -> {
            carddeckActivity.zugbeenden();
        });

        spielen= carddeckActivity.findViewById(R.id.buttonspielen);


        tauschen = carddeckActivity.findViewById(R.id.buttontauschen);
        tauschen.setOnClickListener(v -> {

            carddeckActivity.tauschen();
        });

        zurueck = carddeckActivity.findViewById(R.id.buttonzurueck1);

        zurueck.setOnClickListener(v -> {
            carddeckActivity.zurueck();
        });
        String[] handcards = CardUtils.getresources(carddeckActivity.handkarten);




        fillcards(handcards);



    }

    public void updatenachtauschen(){

        String[] handcrads = CardUtils.getresources(carddeckActivity.handkarten);
        for(String a:handcrads){
            System.out.println(a);
        }
        parentlayout.removeAllViews();

       fillcards(handcrads);


    }



    public void fillcards(String[] handcards){
        for(int i=0; i<handcards.length; i++){

            String filler = handcards[i];
            CardView cards = new CardView(carddeckActivity);
            float dpValue = 125f;
            float dpValue2 = 200f;// Change this value to your desired dp
            float density = carddeckActivity.getdensity();
            int pixelValue1 = (int) (dpValue * density);
            int pixelValue2 = (int) (dpValue2 * density);


            LinearLayout.LayoutParams layoutParamscards = new LinearLayout.LayoutParams(pixelValue1,pixelValue2);

            layoutParamscards.setMargins(16,16,16,16);
            cards.setLayoutParams(layoutParamscards);
            cards.setForeground(carddeckActivity.getcardforeground());
            float radius = 23f;
            int pixelValueradius = (int) (radius * density);
            cards.setRadius(pixelValueradius);
            //karteninhalt
            LinearLayout karteninhalt = new LinearLayout(carddeckActivity);
            LinearLayout.LayoutParams layoutParamskarteninhalt = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.MATCH_PARENT);
            karteninhalt.setOrientation(LinearLayout.VERTICAL);
            karteninhalt.setLayoutParams(layoutParamskarteninhalt);


            //kartenname
            TextView kartenname = new TextView(carddeckActivity);
            LinearLayout.LayoutParams layoutParamskartenname = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            float margin = 8f;
            int pixelValuemargin = (int) (margin * density);
            layoutParamskartenname.setMargins(0,pixelValuemargin,pixelValuemargin,0);
            Typeface typeface = ResourcesCompat.getFont(carddeckActivity, R.font.chewyregular);
            kartenname.setTypeface(typeface);

            String kartenname1 = filler+"1";


            kartenname.setText(carddeckActivity.getstringressource(kartenname1));
            kartenname.setTextColor(carddeckActivity.getblackcolour());
            kartenname.setTextSize(16);
            kartenname.setGravity(Gravity.CENTER);
            kartenname.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            kartenname.setLayoutParams(layoutParamskartenname);
            karteninhalt.addView(kartenname);

            ImageView kartenbild = new ImageView(carddeckActivity);
            float dpvalueimage = 100f;
            int pixelValueimage = (int) (dpvalueimage * density);
            LinearLayout.LayoutParams layoutParamskartenbild = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    pixelValueimage);
            kartenbild.setImageResource(carddeckActivity.getimageressource(filler));
            String id = String.valueOf(carddeckActivity.handkarten.get(i).getId());
            kartenbild.setTag(filler);
            kartenbild.setLayoutParams(layoutParamskartenbild);
            karteninhalt.addView(kartenbild);

            TextView kartenbeschreibung = new TextView(carddeckActivity);
            LinearLayout.LayoutParams layoutParamskartenbeschreibung = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, // Breite
                    ViewGroup.LayoutParams.MATCH_PARENT);

            String kartenbeschreibung2 = filler+"2";
            kartenbeschreibung.setText(carddeckActivity.getstringressource(kartenbeschreibung2));
            kartenbeschreibung.setTextColor(carddeckActivity.getblackcolour());
            kartenbeschreibung.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            kartenbeschreibung.setTag(id);
            kartenbeschreibung.setTextSize(12);
            layoutParamskartenbeschreibung.setMargins(3,4,3,0);
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


        if (card == carddeckActivity.selectedCard) {
            changecardview(carddeckActivity.selectedCard, 125f,200f,16,16,12);
            carddeckActivity.selectedCard = null;
            card.setForeground(carddeckActivity.getcardforeground());
            spielen.setVisibility(View.GONE);
            tauschen.setVisibility(View.GONE);// Setze die ausgewählte Karte zurück
        } else {

            if (carddeckActivity.selectedCard != null) {
                changecardview(carddeckActivity.selectedCard, 125f,200f,16,16,12);
                carddeckActivity.selectedCard.setForeground(carddeckActivity.getcardforeground());
            }
            changecardview(card, 155f,250f,20,20,15);
            card.setForeground(carddeckActivity.getyellowborder());
            carddeckActivity.selectedCard = card;
            spielen.setVisibility(View.VISIBLE);
            tauschen.setVisibility(View.VISIBLE);// Setze die ausgewählte Karte
        }
    }

    public void changecardview(CardView card, float layoutparam1, float layoutparam2, int margin, int textsizekartenname, int textsizekartenbeschreibung){

        float density = carddeckActivity.getdensity();
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

}
