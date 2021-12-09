package Controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wildcats.ultimatechess.R;


public class EndGameActivity extends AppCompatActivity {
    TextView text;
    View background;
    private String username;
    private String userid;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind xml containing the GUI.
        setContentView(R.layout.activity_end);

        text = findViewById(R.id.Info);
        background = findViewById(R.id.background);
        background.setOnTouchListener(touchListener);

        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        userid = extras.getString("userid");
        boolean stale = extras.getBoolean("stale");
        String winner = extras.getString("winner");

        if (stale){
            text.setText("Stalemate \nClick anywhere on the screen to start a new game.");
        }
        else{
            text.setText(winner + " won the game \nClick anywhere on the screen to start a new game.");
        }




    }
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent me) {
            openStartActivity(username, userid);
            return true;
        }
    };


    // Closes the end screen and open the start page.
    private void openStartActivity(String username, String id) {
        // An "intent" allows us to pass variables between activities.
        // Here we are passing the username and id (they will come in handy in the other activities).
        Intent intent = new Intent(this, StartActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", id);
        startActivity(intent);
    }

}
