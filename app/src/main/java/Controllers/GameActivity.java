package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.wildcats.ultimatechess.R;
import Interface.Database;

// Controls user input for the Game page.
public class GameActivity extends AppCompatActivity {

    private TextView txt_playerWhite, txt_playerBlack;
    private String username, userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        gameLoop();

    }

    private void gameLoop() {
        Database.fetch(Database.Collections.MOVES, docs -> {

            System.out.println("=== GAME LOOP ===");

            // The game loop will check every second for new moves.
            // A new move is committed as the following:
            // Database.insert(Database.Collections.MOVES, new Move(...), ()->{});

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() { gameLoop(); }
            }, 1000);
        });
    }

}