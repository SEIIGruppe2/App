package com.example.munchkin.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.example.munchkin.dto.ActionCardDTO;
import com.example.munchkin.drawableutils.CardUtils;
import com.example.munchkin.messageformat.MessageRouter;
import com.example.munchkin.player.PlayerHand;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.munchkin.R;
import com.example.munchkin.controller.CardDeckController;
import com.example.munchkin.model.WebSocketClientModel;
import com.example.munchkin.view.CarddeckView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CardDeckActivity extends AppCompatActivity {

    private CardDeckController controller;
    public static int passiveMode;
    public static CardView selectedCard;
    private final String defTypeString = "string";
    private final String defTypeDrawable = "drawable";
    public List<ActionCardDTO> handCards;
    private CarddeckView view;
    private String usernameToSwitchWith;
    private PopupWindow popupSwitchCards;
    private TextView cardName;
    private ImageView cardImage;
    private TextView cardDescription;
    public static boolean switchDone = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carddeck);

        PlayerHand playerCards = new PlayerHand();
        WebSocketClientModel model = new WebSocketClientModel();

        handCards = playerCards.getCards();

        view =new CarddeckView(this);
        controller = new CardDeckController(model,view);
        MessageRouter router = new MessageRouter();
        router.registerController("SHOW_MONSTERS",controller);
        router.registerController("SWITCH_CARD_DECK_RESPONSE",controller);
        router.registerController("SWITCH_CARD_PLAYER",controller);
        router.registerController("SWITCH_CARD_PLAYER_RESPONSE",controller);
        router.registerController("REQUEST_USERNAMES_SWITCH",controller);
        model.setMessageRouter(router);
        if(passiveMode ==1){
            Bundle b = getIntent().getExtras();
            assert b != null;
            String messageFromServer = b.getString("key");
            try {
                assert messageFromServer != null;
                JSONObject message = new JSONObject(messageFromServer);
                int id = Integer.parseInt(message.getString("id"));
                String name = message.getString("name");
                int zone = Integer.parseInt(message.getString("zone"));
                ActionCardDTO cards = new ActionCardDTO(name, zone,id);
                usernameToSwitchWith = message.getString("switchedWith");
                CardDeckController.playerHand.addCard(cards);

            } catch (JSONException e) {
                throw new IllegalArgumentException(e);
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public float getDensity(){
        return getResources().getDisplayMetrics().density;
    }

    public Drawable getCardForeground(){
        return getResources().getDrawable(R.drawable.bg_roundrect_ripple_light_border);
    }

    public int getStringResource(String tag){
        return getResources().getIdentifier(tag, defTypeString,getPackageName());
    }

    public int getImageResource(String tag){
        return getResources().getIdentifier(tag,defTypeDrawable,getPackageName());
    }

    public int getBlackColour(){
        return getResources().getColor(R.color.black);
    }

    public Drawable getYellowBorder(){
        return getResources().getDrawable(R.drawable.yellowborder);
    }

    public void goBack(){
        Intent intent = new Intent(this, MainGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    /*
    //TODO: bei zugbeenden in maingame einbauen
    public void zugbeenden(){

        View drawablePopUp = getLayoutInflater().inflate(R.layout.popupzugbeenden, null);

        int width = ViewGroup.LayoutParams.WRAP_CONTENT;
        int height = ViewGroup.LayoutParams.WRAP_CONTENT;
        PopupWindow popupzugzuende = new PopupWindow(drawablePopUp,width,height,true);
        popupzugzuende.setOutsideTouchable(false);
        popupzugzuende.setElevation(10);
        popupzugzuende.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);
        dimmwindow(popupzugzuende);
        Button ja = drawablePopUp.findViewById(R.id.buttonja);
        Button nein = drawablePopUp.findViewById(R.id.nein);


        ja.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoadingscreenActivity.class);
            startActivity(intent);
        });
        nein.setOnClickListener(v -> popupzugzuende.dismiss());
    }
    */

    public void passiveSwitchCards(){
        CardView currentCard = selectedCard;
        LinearLayout getCardName = (LinearLayout) currentCard.getChildAt(0);
        TextView getTag = (TextView)  getCardName.getChildAt(2);
        String id = (String) getTag.getTag();
        sendMessage(usernameToSwitchWith,id);
        goBack();
    }

    View popupDrawable;
    TextView switchCardsText;

    @SuppressLint("SetTextI18n")
    public void switchCards(){

        controller.getactiveusers();
        LinearLayout getCardName = (LinearLayout) selectedCard.getChildAt(0);
        ImageView testGetCardName = (ImageView) getCardName.getChildAt(1);
        String resource = (String) testGetCardName.getTag();
        popupDrawable = getLayoutInflater().inflate(R.layout.popouttauschen, null);
        popupSwitchCards = new PopupWindow(popupDrawable,1750,1200,true);
        popupSwitchCards.setOutsideTouchable(false);
        popupSwitchCards.setAnimationStyle(R.anim.popup);
        popupSwitchCards.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupSwitchCards.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        popupSwitchCards.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);
        dimWindow(popupSwitchCards);
        Button switchCards = popupDrawable.findViewById(R.id.buttontauschendurchfuehren);
        Button goBack = popupDrawable.findViewById(R.id.buttonzurueck2);
        cardName = popupDrawable.findViewById(R.id.kartennamepopup);
        cardDescription =  popupDrawable.findViewById(R.id.kartenbeschreibungpopup);
        cardImage = popupDrawable.findViewById(R.id.kartenbildpopup);
        String cardName2 = resource+"1";
        int resIdCardName = getResources().getIdentifier(cardName2,defTypeString,getPackageName());
        cardName.setText(resIdCardName);

        String cardDescription2 = resource+"2";
        int resIdCardDescription = getResources().getIdentifier(cardDescription2,defTypeString,getPackageName());
        cardDescription.setText(resIdCardDescription);

        int resIdCardImage = getResources().getIdentifier(resource,defTypeDrawable,getPackageName());
        cardImage.setImageResource(resIdCardImage);

        view.usernames.add("Kartenstapel");
        Spinner dropDownMenu = popupDrawable.findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(CardDeckActivity.this, R.layout.list,view.usernames);
        dropDownMenu.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.list);

        switchCards.setOnClickListener(v -> {
            String username = dropDownMenu.getSelectedItem().toString();
            CardView currentCard = selectedCard;
            LinearLayout getCardName2 = (LinearLayout) currentCard.getChildAt(0);
            TextView getTag = (TextView)  getCardName2.getChildAt(2);
            String id = (String) getTag.getTag();
            sendMessage(username, id);
            switchCards.setVisibility(View.GONE);
            goBack.setVisibility(View.GONE);
            LinearLayout parentLayout = popupDrawable.findViewById(R.id.parentlayoutpopup);
            parentLayout.removeView(parentLayout.getChildAt(1));
            switchCardsText = popupDrawable.findViewById(R.id.tauschentext);
            switchCardsText.setText("Anfrage wurde gesendet");
            cardName.setText("");
            cardDescription.setText("");
            cardImage.setImageResource(getResources().getIdentifier("loadingimage",defTypeDrawable,getPackageName()));
        });

        goBack.setOnClickListener(v -> popupSwitchCards.dismiss());
    }

    @SuppressLint("SetTextI18n")
    public void updatePopUpWindow(){
        LinearLayout buttonContainer = popupDrawable.findViewById(R.id.buttoncontainer);
        Button neuerbutton = new Button(CardDeckActivity.this);
        LinearLayout.LayoutParams layoutParamsCardsContent = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, // Breite
                ViewGroup.LayoutParams.WRAP_CONTENT);

        switchCardsText.setText("Du hast folgende Karte erhalten");
        String[] handCards = CardUtils.getresources(this.handCards);
        String newCard = handCards[handCards.length-1];
        String newCardName= newCard+"1";
        cardName.setText(getResources().getIdentifier(newCardName,defTypeString,getPackageName()));

        String newCardDescription = newCard+"2";
        cardDescription.setText(getResources().getIdentifier(newCardDescription,defTypeString,getPackageName()));
        cardImage.setImageResource(getResources().getIdentifier(newCard,defTypeDrawable,getPackageName()));

        neuerbutton.setLayoutParams(layoutParamsCardsContent);
        neuerbutton.setText("ok");
        neuerbutton.setBackgroundResource(R.drawable.rippleeffect);
        neuerbutton.setBackgroundTintList(ContextCompat.getColorStateList(CardDeckActivity.this, R.color.yellow));
        buttonContainer.addView(neuerbutton);
        Typeface typeface = ResourcesCompat.getFont(CardDeckActivity.this, R.font.chewyregular);
        neuerbutton.setTypeface(typeface);
        neuerbutton.setOnClickListener(v -> {

            CardDeckActivity.switchDone = true;
            findViewById(R.id.buttontauschen).setVisibility(View.GONE);
            findViewById(R.id.buttonspielen).setVisibility(View.GONE);
            popupSwitchCards.dismiss();
            view.updatenachtauschen();
        });
    }

    private void dimWindow(PopupWindow popup){
        View container = (View) popup.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    private void sendMessage(String username, String id){
        if(passiveMode ==1) {
            controller.switchcardMeassagepassive(username,id);
        }
        else{
            controller.switchcardMeassage(username, id);
        }
    }

    public void playCard(){
        CardView currentCard = selectedCard;
        LinearLayout getCardName = (LinearLayout) currentCard.getChildAt(0);
        TextView getTag = (TextView)  getCardName.getChildAt(2);
        String id = (String) getTag.getTag();
        controller.showMonsterMessage(id);
    }
}




