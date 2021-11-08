package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.wildcats.ultimatechess.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class StartActivity extends AppCompatActivity {

    private String username, userid;

    private Button btn_singleplayer;
    private Button btn_multiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        Intent intent = new Intent(this, GameActivity.class);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        userid = extras.getString("userid");

        btn_singleplayer = findViewById(R.id.btn_singleplayer);
        btn_multiplayer = findViewById(R.id.btn_multiplayer);

        btn_singleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onSinglelayerClicked(); }
        });

        btn_multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onMultiplayerClicked(); }
        });
    }

    private void onSinglelayerClicked() {

    }

    private void onMultiplayerClicked() {

        CollectionReference fbUsersMatchmaking
                = FirebaseFirestore.getInstance().collection("users_matchmaking");
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("user_id", userid);
        fbUsersMatchmaking.add(data);

        findOpponentAndJoinGame();
    }

    private void findOpponentAndJoinGame() {

        CollectionReference fbGames = FirebaseFirestore.getInstance().collection("games");
        fbGames.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                boolean userInGame = false;
                for (DocumentSnapshot game : snapshot.getDocuments()) {
                    if (game.getString("player_black_id").equals(userid)) {
                        userInGame = true;
                    }
                }
                if (userInGame) {
                    openGameActivity();
                }
                else {
                    CollectionReference usersMatchmaking
                            = FirebaseFirestore.getInstance().collection("users_matchmaking");
                    usersMatchmaking.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot snapshot) {
                            for (DocumentSnapshot user : snapshot.getDocuments()) {
                                String opponentId = user.getString("user_id");
                                if (!opponentId.equals(userid)) {
                                    CollectionReference fbGames
                                            = FirebaseFirestore.getInstance().collection("games");
                                    Map<String, Object> data = new HashMap<String, Object>();
                                    data.put("player_white_id", userid);
                                    data.put("player_black_id", opponentId);
                                    deleteUserMatchmaking(userid);
                                    deleteUserMatchmaking(opponentId);
                                    fbGames.add(data);
                                    openGameActivity();
                                    return;
                                }
                            }
                            int min = 1000;
                            int max = 10000;
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    findOpponentAndJoinGame();
                                }
                            }, ThreadLocalRandom.current().nextInt(min, max + 1));
                        }
                    });
                }
            }
        });
    }

    private void deleteUserMatchmaking(String userid) {
        CollectionReference usersMatchmaking
                = FirebaseFirestore.getInstance().collection("users_matchmaking");
        usersMatchmaking.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot snapshot) {
                for (DocumentSnapshot user : snapshot.getDocuments()) {
                    if (user.getString("user_id").equals(userid)) {
                        FirebaseFirestore.getInstance().document("users_matchmaking/" + user.getId()).delete();
                    }
                }
            }
        });
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }

}