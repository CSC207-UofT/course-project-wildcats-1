package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.wildcats.ultimatechess.R;
import Interface.Database;
import Interface.User.UserRef;

// Controls user input for the Start page.
public class StartActivity extends AppCompatActivity {

    private String username, userid;

    private Button btn_singleplayer;
    private Button btn_multiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        btn_singleplayer = findViewById(R.id.btn_singleplayer);
        btn_multiplayer = findViewById(R.id.btn_multiplayer);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        userid = extras.getString("userid");

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
        UserRef userRef = new UserRef(userid);
        Database.insert(Database.Collections.USERS_MATCHMAKING, userRef, ()->{});
        Matchmaker.findOpponentAndJoinGame(userid, this::openGameActivity);
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }

}