package com.example.munchkin.activity;

import androidx.appcompat.app.AppCompatActivity;

public class Obsolete3 extends AppCompatActivity {

    /*
    private DrawCardController controller;
    private MainGameView mainGameView;

    private MainGameActivity mainGameActivity;

    private PlayerHand handkarten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_draw);
        this.handkarten=new PlayerHand();


        MessageRouter router = new MessageRouter();
        WebSocketClientModel model = new WebSocketClientModel();
        mainGameView = new MainGameView(mainGameActivity);

        controller = new DrawCardController(model, mainGameView);


        router.registerController("DRAW_CARD",controller);
        model.setMessageRouter(router);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void sendMessage() {

        controller.drawMeassage();

    }

    public void addcardtolist(ActionCardDTO karte){
        handkarten.addCard(karte);
        System.out.println("---- add to list"+handkarten.getCards().size());

    }

    public void transitionToCardDeckscreen() {

        Intent intent = new Intent(DrawActivity.this, CarddeckActivity.class);
        startActivity(intent);
    }
*/

}