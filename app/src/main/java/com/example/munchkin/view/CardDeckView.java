package com.example.munchkin.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import com.example.munchkin.drawableutils.CardUtils;
import com.example.munchkin.R;
import com.example.munchkin.activity.CardDeckActivity;
import com.example.munchkin.activity.MainGameActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardDeckView {

    LinearLayout parentLayout;
    Button buttonPlay;
    Button buttonSwitchCards;
    Button buttonGoBack;

    private CardDeckActivity cardDeckActivity;
    public List<String> usernames = new ArrayList<>(Arrays.asList("Buenos Aires", "Córdoba", "La Plata"));

    public CardDeckView(CardDeckActivity cardDeckActivity){
        this.cardDeckActivity = cardDeckActivity;
            setupUI();
    }

    private void setupUI(){

        parentLayout = cardDeckActivity.findViewById(R.id.containerforcards);

        buttonPlay = cardDeckActivity.findViewById(R.id.buttonspielen);
        buttonPlay.setOnClickListener(v -> cardDeckActivity.playCard());

        buttonSwitchCards = cardDeckActivity.findViewById(R.id.buttontauschen);
        buttonSwitchCards.setOnClickListener(v -> cardDeckActivity.switchCards());

        buttonGoBack = cardDeckActivity.findViewById(R.id.buttonzurueck1);
        if(CardDeckActivity.passiveMode ==1){
            buttonGoBack.setVisibility(View.GONE);
        }
        else {
            buttonGoBack.setOnClickListener(v -> cardDeckActivity.goBack());
        }
        String[] handCards = CardUtils.getresources(cardDeckActivity.handCards);
        fillCards(handCards);
    }

    public void updateAfterSwitchCards(){
        String[] handCards = CardUtils.getresources(cardDeckActivity.handCards);
        for(String a:handCards){
            Log.d("updatenachTauschen", a);
        }
        parentLayout.removeAllViews();
       fillCards(handCards);
    }

    public void fillCards(String[] handCards){
        for(int i=0; i<handCards.length; i++){

            String filler = handCards[i];
            CardView cards = new CardView(cardDeckActivity);
            float dpValue = 125f;
            float dpValue2 = 200f;
            float density = cardDeckActivity.getDensity();
            int pixelValue1 = (int) (dpValue * density);
            int pixelValue2 = (int) (dpValue2 * density);

            LinearLayout.LayoutParams layoutParamsCards = new LinearLayout.LayoutParams(pixelValue1,pixelValue2);

            layoutParamsCards.setMargins(16,16,16,16);
            cards.setLayoutParams(layoutParamsCards);
            cards.setForeground(cardDeckActivity.getCardForeground());
            float radius = 23f;
            int pixelValueRadius = (int) (radius * density);
            cards.setRadius(pixelValueRadius);
            LinearLayout cardDescription = new LinearLayout(cardDeckActivity);
            LinearLayout.LayoutParams layoutParamsCardDescription = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            cardDescription.setOrientation(LinearLayout.VERTICAL);
            cardDescription.setLayoutParams(layoutParamsCardDescription);

            TextView cardName = new TextView(cardDeckActivity);
            LinearLayout.LayoutParams layoutParamsCardName = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            float margin = 8f;
            int pixelValueMargin = (int) (margin * density);
            layoutParamsCardName.setMargins(0,pixelValueMargin,pixelValueMargin,0);
            Typeface typeface = ResourcesCompat.getFont(cardDeckActivity, R.font.chewyregular);
            cardName.setTypeface(typeface);

            String cardName1 = filler+"1";

            cardName.setText(cardDeckActivity.getStringResource(cardName1));
            cardName.setTextColor(cardDeckActivity.getBlackColour());
            cardName.setTextSize(16);
            cardName.setGravity(Gravity.CENTER);
            cardName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cardName.setLayoutParams(layoutParamsCardName);
            cardDescription.addView(cardName);

            ImageView cardImage = new ImageView(cardDeckActivity);
            float dpValueImage = 100f;
            int pixelValueImage = (int) (dpValueImage * density);
            LinearLayout.LayoutParams layoutParamsCardImage = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                    pixelValueImage);
            cardImage.setImageResource(cardDeckActivity.getImageResource(filler));
            String id = String.valueOf(cardDeckActivity.handCards.get(i).getId());
            cardImage.setTag(filler);
            cardImage.setLayoutParams(layoutParamsCardImage);
            cardDescription.addView(cardImage);

            TextView cardsDescription = new TextView(cardDeckActivity);
            LinearLayout.LayoutParams layoutParamsCardsDescription = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            String cardsDescription2 = filler+"2";
            cardsDescription.setText(cardDeckActivity.getStringResource(cardsDescription2));
            cardsDescription.setTextColor(cardDeckActivity.getBlackColour());
            cardsDescription.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            cardsDescription.setTag(id);
            cardsDescription.setTextSize(12);
            layoutParamsCardsDescription.setMargins(3,4,3,0);
            cardsDescription.setTypeface(typeface);
            cardsDescription.setLayoutParams(layoutParamsCardsDescription);
            cardDescription.addView(cardsDescription);

            cards.addView(cardDescription);

            cards.setOnClickListener((v -> {
                if(CardDeckActivity.passiveMode == 1){
                    onCardClickPassive(cards);
                }else{
                    onCardClick(cards);
                }

            }));

            parentLayout.addView(cards);

        }
    }

    public void onCardClickPassive(CardView card){

        if (card == CardDeckActivity.selectedCard) {
            changeCardView(CardDeckActivity.selectedCard, 125f,200f,16,16,12);
            CardDeckActivity.selectedCard = null;
            card.setForeground(cardDeckActivity.getCardForeground());
            buttonSwitchCards.setVisibility(View.GONE);
        } else {

            if (CardDeckActivity.selectedCard != null) {
                changeCardView(CardDeckActivity.selectedCard, 125f,200f,16,16,12);
                CardDeckActivity.selectedCard.setForeground(cardDeckActivity.getCardForeground());
            }
            changeCardView(card, 155f,250f,20,20,15);
            card.setForeground(cardDeckActivity.getYellowBorder());
            CardDeckActivity.selectedCard = card;
            buttonSwitchCards.setOnClickListener((v -> cardDeckActivity.passiveSwitchCards()));
            buttonSwitchCards.setVisibility(View.VISIBLE);
        }
    }

    public void onCardClick(CardView card){

        if (card == CardDeckActivity.selectedCard) {
            changeCardView(CardDeckActivity.selectedCard, 125f,200f,16,16,12);
            CardDeckActivity.selectedCard = null;
            card.setForeground(cardDeckActivity.getCardForeground());
            buttonPlay.setVisibility(View.GONE);
            buttonSwitchCards.setVisibility(View.GONE);
        } else {

            if (CardDeckActivity.selectedCard != null) {
                changeCardView(CardDeckActivity.selectedCard, 125f,200f,16,16,12);
                CardDeckActivity.selectedCard.setForeground(cardDeckActivity.getCardForeground());
            }
            changeCardView(card, 155f,250f,20,20,15);
            card.setForeground(cardDeckActivity.getYellowBorder());
            CardDeckActivity.selectedCard = card;
            buttonPlay.setVisibility(View.VISIBLE);
            if(!cardDeckActivity.switchDone){
            buttonSwitchCards.setVisibility(View.VISIBLE);}
        }
    }

    public void changeCardView(CardView card, float layoutParam1, float layoutParam2, int margin, int textSizeCardName, int textSizeCardDescription){

        float density = cardDeckActivity.getDensity();
        int pixelValue1 = (int) (layoutParam1 * density);
        int pixelValue2 = (int) (layoutParam2 * density);

        LinearLayout.LayoutParams layoutParamsCards = new LinearLayout.LayoutParams(pixelValue1,pixelValue2);
        layoutParamsCards.setMargins(margin,margin,margin,margin);
        card.setLayoutParams(layoutParamsCards);
        LinearLayout cardDescription = (LinearLayout) card.getChildAt(0);
        TextView cardName = (TextView) cardDescription.getChildAt(0);
        cardName.setTextSize(textSizeCardName);
        TextView cardsDescription = (TextView) cardDescription.getChildAt(2);
        cardsDescription.setTextSize(textSizeCardDescription);
    }

    public void updateScreen() {
        cardDeckActivity.runOnUiThread(() -> cardDeckActivity.updatePopUpWindow());
    }

    public void startMonsterAttack(){

        cardDeckActivity.runOnUiThread(new Runnable() {

                final View popUpDrawable = cardDeckActivity.getLayoutInflater().inflate(R.layout.popuptauschenanfrage, null);

                    @Override
                    public void run() {
                        TextView textForPopUP= popUpDrawable.findViewById(R.id.textpopup);
                        textForPopUP.setText(R.string.showMonsters);
                        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
                        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        PopupWindow popUpShowMonster = new PopupWindow(popUpDrawable,width,height,true);
                        popUpShowMonster.setOutsideTouchable(false);
                        popUpShowMonster.setElevation(10);

                        popUpShowMonster.showAtLocation(cardDeckActivity.getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);

                        Button ok = popUpDrawable.findViewById(R.id.ok);

                        ok.setOnClickListener(v -> {
                            MainGameView.showMonster();
                            Intent intent = new Intent(cardDeckActivity, MainGameActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            cardDeckActivity.startActivity(intent);

                            popUpShowMonster.dismiss();

                        });
                    }
                });
            }
}

