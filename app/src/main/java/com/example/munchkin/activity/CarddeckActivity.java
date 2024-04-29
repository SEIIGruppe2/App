package com.example.munchkin.activity;

import android.content.Context;
import android.content.Intent;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.munchkin.DTO.ActionCardDTO;
import com.example.munchkin.DrawableUtils.CardUtils;
import com.example.munchkin.MessageFormat.MessageRouter;
import com.example.munchkin.Player.PlayerHand;

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

public class CarddeckActivity extends AppCompatActivity {


    private CardDeckController controller;

    public static int passivmode;

    public CardView selectedCard;

    Button spielen;
    Button tauschen;
    PlayerHand spielerkarten;

    public List<ActionCardDTO> handkarten;
    CarddeckView view;

    String messagfromserver;
    String usernametoswitchwith;

    PopupWindow popuptauschen;
    TextView kartenname;

    ImageView kartenbild;
    TextView kartenbeschreibung;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_carddeck);

        spielerkarten = new PlayerHand();
        MessageRouter router = new MessageRouter();
        WebSocketClientModel model = new WebSocketClientModel();



        model.setMessageRouter(router);
        handkarten= spielerkarten.getCards();



        view =new CarddeckView(this);
        controller = new CardDeckController(model,view);
        router.registerController("SWITCH_CARD_DECK_RESPONSE",controller);
        router.registerController("SWITCH_CARD_PLAYER",controller);
        router.registerController("SWITCH_CARD_PLAYER_RESPONSE",controller);
        router.registerController("REQUEST_USERNAMES",controller);
        if(passivmode==1){
            Bundle b = getIntent().getExtras();
            messagfromserver = b.getString("key");
            try {
                JSONObject message = new JSONObject(messagfromserver);
                int id = Integer.parseInt(message.getString("id"));
                String name = message.getString("name");
                int zone = Integer.parseInt(message.getString("zone"));
                ActionCardDTO karte = new ActionCardDTO(name, zone,id);
                usernametoswitchwith = message.getString("switchedWith");
                controller.playerHand.addCard(karte);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    public float getdensity(){
        return getResources().getDisplayMetrics().density;
    }

    public Drawable getcardforeground(){
        return getResources().getDrawable(R.drawable.bg_roundrect_ripple_light_border);
    }

    public int getstringressource(String tag){
        return getResources().getIdentifier(tag,"string",getPackageName());
    }

    public int getimageressource(String tag){
        return getResources().getIdentifier(tag,"drawable",getPackageName());

    }

    public int getblackcolour(){
        return getResources().getColor(R.color.black);
    }

    public Drawable getyellowborder(){
        return getResources().getDrawable(R.drawable.yellowborder);
    }




    public void zurueck(){
        Intent intent = new Intent(this, MainGameActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void zugbeenden(){

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

    public void passivetauschen(){
        CardView currentcard = selectedCard;
        LinearLayout getkardname = (LinearLayout) currentcard.getChildAt(0);
        TextView gettag = (TextView)  getkardname.getChildAt(2);
        String id = (String) gettag.getTag();
        sendmessage(usernametoswitchwith,id);


        zurueck();

        //username von absender, idauslesen

    }
    View popupdrawable;
    TextView tauschentext;
    public void tauschen(){


        controller.getactiveusers();

        LinearLayout getkartenname = (LinearLayout) selectedCard.getChildAt(0);
        ImageView testgetcardname = (ImageView) getkartenname.getChildAt(1);
        String ressource = (String) testgetcardname.getTag();
        popupdrawable = getLayoutInflater().inflate(R.layout.popouttauschen, null);
        popuptauschen = new PopupWindow(popupdrawable,1750,1200,true);
        popuptauschen.setOutsideTouchable(false);
        popuptauschen.setAnimationStyle(R.anim.popup);
        popuptauschen.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER,0,0);
        dimmwindow(popuptauschen);
        Button tauschen = popupdrawable.findViewById(R.id.buttontauschendurchfuehren);
        Button zurueck = popupdrawable.findViewById(R.id.buttonzurueck2);
        kartenname = popupdrawable.findViewById(R.id.kartennamepopup);
        kartenbeschreibung =  popupdrawable.findViewById(R.id.kartenbeschreibungpopup);
        kartenbild = popupdrawable.findViewById(R.id.kartenbildpopup);
        String kartenname2 = ressource+"1";
        int resIDkartenname = getResources().getIdentifier(kartenname2,"string",getPackageName());
        kartenname.setText(resIDkartenname);

        String kartenbeschreibung2 = ressource+"2";
        int resIDkartennbeschreibung = getResources().getIdentifier(kartenbeschreibung2,"string",getPackageName());
        kartenbeschreibung.setText(resIDkartennbeschreibung);

        String kartenbild2 = ressource;
        int resIDkartenbild = getResources().getIdentifier(kartenbild2,"drawable",getPackageName());
        kartenbild.setImageResource(resIDkartenbild);



        view.usernames.add("Kartenstapel");

        Spinner dropdownmenu = popupdrawable.findViewById(R.id.spinner1);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(CarddeckActivity.this, R.layout.list,view.usernames);
        dropdownmenu.setAdapter(adapter);
        adapter.setDropDownViewResource(R.layout.list);


        tauschen.setOnClickListener(v -> {

            String username = dropdownmenu.getSelectedItem().toString();
            CardView currentcard = selectedCard;
            LinearLayout getkardname = (LinearLayout) currentcard.getChildAt(0);
            TextView gettag = (TextView)  getkardname.getChildAt(2);
            String id = (String) gettag.getTag();
            sendmessage(username, id);
            Handler handler = new Handler();
            tauschen.setVisibility(View.GONE);
            zurueck.setVisibility(View.GONE);
            LinearLayout parentlayout = popupdrawable.findViewById(R.id.parentlayoutpopup);
            parentlayout.removeView(parentlayout.getChildAt(1));
            tauschentext = popupdrawable.findViewById(R.id.tauschentext);
            tauschentext.setText("Anfrage wurde gesendet");
            kartenname.setText("");
            kartenbeschreibung.setText("");
            kartenbild.setImageResource(getResources().getIdentifier("loadingimage","drawable",getPackageName()));
            // Post a delayed Runnable to the Handler
            /*handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    LinearLayout buttoncontainer = popupdrawable.findViewById(R.id.buttoncontainer);
                    Button neuerbutton = new Button(CarddeckActivity.this);
                    LinearLayout.LayoutParams layoutParamskarteninhalt = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, // Breite
                            ViewGroup.LayoutParams.WRAP_CONTENT);

                    tauschentext.setText("Du hast folgende Karte erhalten");
                    String[] handcards = CardUtils.getresources(handkarten);
                    String neuekarte = handcards[handcards.length-1];
                    String neuerkartenname= neuekarte+"1";
                    getResources().getIdentifier(kartenname2,"string",getPackageName());
                    kartenname.setText(getResources().getIdentifier(neuerkartenname,"string",getPackageName()));

                    String neuekartenbeschreibung = neuekarte+"2";
                    kartenbeschreibung.setText(getResources().getIdentifier(neuekartenbeschreibung,"string",getPackageName()));

                    kartenbild.setImageResource(getResources().getIdentifier(neuekarte,"drawable",getPackageName()));

                    neuerbutton.setLayoutParams(layoutParamskarteninhalt);
                    neuerbutton.setText("ok");
                    neuerbutton.setBackgroundResource(R.drawable.rippleeffect);
                    neuerbutton.setBackgroundTintList(ContextCompat.getColorStateList(CarddeckActivity.this, R.color.yellow));
                    buttoncontainer.addView(neuerbutton);
                    Typeface typeface = ResourcesCompat.getFont(CarddeckActivity.this, R.font.chewyregular);
                    neuerbutton.setTypeface(typeface);
                    neuerbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popuptauschen.dismiss();
                            view.updatenachtauschen();
                        }
                    });

                }
            }, 3000);*/



           //hier ist zuende
        });

        zurueck.setOnClickListener(v -> {
            popuptauschen.dismiss();
        });
    }

    public void updatepopupwindow(){

        LinearLayout buttoncontainer = popupdrawable.findViewById(R.id.buttoncontainer);
        Button neuerbutton = new Button(CarddeckActivity.this);
        LinearLayout.LayoutParams layoutParamskarteninhalt = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT, // Breite
                ViewGroup.LayoutParams.WRAP_CONTENT);

        tauschentext.setText("Du hast folgende Karte erhalten");
        String[] handcards = CardUtils.getresources(handkarten);
        String neuekarte = handcards[handcards.length-1];
        String neuerkartenname= neuekarte+"1";
        kartenname.setText(getResources().getIdentifier(neuerkartenname,"string",getPackageName()));

        String neuekartenbeschreibung = neuekarte+"2";
        kartenbeschreibung.setText(getResources().getIdentifier(neuekartenbeschreibung,"string",getPackageName()));

        kartenbild.setImageResource(getResources().getIdentifier(neuekarte,"drawable",getPackageName()));

        neuerbutton.setLayoutParams(layoutParamskarteninhalt);
        neuerbutton.setText("ok");
        neuerbutton.setBackgroundResource(R.drawable.rippleeffect);
        neuerbutton.setBackgroundTintList(ContextCompat.getColorStateList(CarddeckActivity.this, R.color.yellow));
        buttoncontainer.addView(neuerbutton);
        Typeface typeface = ResourcesCompat.getFont(CarddeckActivity.this, R.font.chewyregular);
        neuerbutton.setTypeface(typeface);
        neuerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popuptauschen.dismiss();
                view.updatenachtauschen();
            }
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
    private void sendmessage(String username, String id){
        if(passivmode==1) {
            controller.switchcardMeassagepassive(username,id);
        }
        else{
            controller.switchcardMeassage(username, id);
        }
    }


    }




