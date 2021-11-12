package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import com.wildcats.ultimatechess.R;
import Interfaces.Database;
import android.view.View;


// Controls user input for the Game page.
public class GameActivity extends AppCompatActivity {

    private TextView txt_playerWhite, txt_playerBlack;
    private String username, userid;
    private View boardView;
    private float[] touchLoc = new float[2];

    private ImageView pawn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind xml containing the GUI.
        setContentView(R.layout.activity_game);

        // find the 8x8 board on the screen
        boardView = findViewById(R.id.img_board);
        boardView.setOnTouchListener(touchListener);

        pawn = findViewById(R.id.img_pawnW1);


        gameLoop();


    }
    // Method to define what is done when a location on the board is touched/clicked
    // In this case, it will set touch location indexes to match where on the board was touched.
    // (0,0) is the top left of the board.
    View.OnTouchListener touchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View v, MotionEvent me){
            if (me.getActionMasked() == MotionEvent.ACTION_DOWN){
                touchLoc[0] = me.getX();
                touchLoc[1] = me.getY();
                pawn.setImageResource(R.drawable.bishop_black);
            }
            System.out.println(boardCordConvert(touchLoc[0], touchLoc[1], true));
            return false;
        }


    };


    private void gameLoop() {
        Database.fetch(Database.Collections.MOVES, docs -> {

            System.out.println("=== GAME LOOP ===");



            // The game loop will check every second for new moves.
            // A new move is committed as the following:
            // Database.insert(Database.Collections.MOVES, new Move(...), ()->{});

            // Run this method recursively every 1000ms (1s).
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() { gameLoop(); }
            }, 1000);
        });
    }
    /**
     * Returns the chess coordinate of a given x,y value on the board view
     *
     * @param x the x-coordinate of click
     * @param y the y-coordinate of click
     * @param isWhite true if the board is being viewed from white side, false otherwise
     */
    private String boardCordConvert(float x, float y, boolean isWhite){
        float boardSize = boardView.getHeight();
        float squareSize = boardSize/8;
        final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        final char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8'};
        char letter, num;

        if (isWhite){
            letter = letters[(int)(x/squareSize)];
            num = nums[7-((int)(y/squareSize))];
        }
        else{
            letter = letters[7-((int)(x/squareSize))];
            num = nums[(int)(y/squareSize)];
        }
        return ""+letter+num;
    }

}