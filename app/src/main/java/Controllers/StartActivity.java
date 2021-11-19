package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.wildcats.ultimatechess.R;
import Interfaces.Database;
import Interfaces.User.UserRef;

// Controls user input for the Start page.
public class StartActivity extends AppCompatActivity {

    private String username, userid;

    private Button btn_singleplayer;
    private Button btn_multiplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind xml containing the GUI.
        setContentView(R.layout.activity_start);

        // Bind Java variables to xml elements.
        btn_singleplayer = findViewById(R.id.btn_singleplayer);
        btn_multiplayer = findViewById(R.id.btn_multiplayer);

        // Get the username and id from the intent created in "LoginActivity".
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        userid = extras.getString("userid");

        // Run onSinglePlayerClicked() when the single player button is clicked.
        btn_singleplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onSinglePlayerClicked(); }
        });

        // Run onMultiplayerClicked() when the multiplayer button is clicked.
        btn_multiplayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { onMultiplayerClicked(); }
        });
    }

    private void onSinglePlayerClicked() {

    }

    private void onMultiplayerClicked() {

        // Add the user to the database collection storing the users matchmaking.
        UserRef userRef = new UserRef(userid);
        Database.insert(Database.Collections.USERS_MATCHMAKING, userRef, ()->{});

        // Use the Matchmaker class to find and opponent and join a game.
        // Once a game is found, open game activity.
        Matchmaker.findOpponentAndJoinGame(userid, this::openGameActivity);
    }

    private void openGameActivity() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", userid);
        startActivity(intent);
    }

}