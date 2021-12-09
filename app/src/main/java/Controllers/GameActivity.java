package Controllers;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wildcats.ultimatechess.R;

import Interfaces.Database;
import Interfaces.Document;
import Interfaces.Move;
import UseCases.BoardManager;
import UseCases.BoardUpdator;
import UseCases.GameBuildDirector;
import UseCases.GameManager;
import UseCases.MoveBuffer;
import UseCases.NormalGameBuilder;


// Controls user input for the Game page.
public class GameActivity extends AppCompatActivity {

    private String username, userid;
    private View boardView;
    private final float[] touchLoc = new float[2];

    private PieceLayoutManager layoutManager;
    private GameManager gameManager;
    private final BoardUpdator boardUpdator = new BoardUpdator();
    private MoveBuffer moveBuffer;
    private BoardManager boardManager = new BoardManager();

    private int moveNumber = 0;
    private int trackHoursBlack = 0;
    private int trackMinsBlack = 0;
    private int trackSecsBlack = 0;
    private int trackHoursWhite = 0;
    private int trackMinsWhite = 0;
    private int trackSecsWhite = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Bind xml containing the GUI.
        setContentView(R.layout.activity_game);

        // find the 8x8 board on the screen
        boardView = findViewById(R.id.img_board);
        boardView.setOnTouchListener(touchListener);

        // Get the username and id from the intent created in "LoginActivity".
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        userid = extras.getString("userid");

        layoutManager = new PieceLayoutManager(this);

        GameBuildDirector gameBuilder = new GameBuildDirector(new NormalGameBuilder());
        gameBuilder.Construct();

        gameManager = gameBuilder.getGame();

        boardManager = new BoardManager();

        moveBuffer = new MoveBuffer(this);

        //initialize white's clock timer
        CharSequence charSequenceWhite = new StringBuffer(GameManager.initializeClockWhite());
        final TextView helloTextViewWhite = findViewById(R.id.clockViewWhite);
        helloTextViewWhite.setText(charSequenceWhite);

        //initialize black's clock timer
        CharSequence charSequenceBlack = new StringBuffer(GameManager.initializeClockBlack());
        final TextView helloTextViewBlack = findViewById(R.id.clockViewBlack);
        helloTextViewBlack.setText(charSequenceBlack);


        gameLoop();


    }
    // Method to define what is done when a location on the board is touched/clicked
    // In this case, it will set touch location indexes to match where on the board was touched.
    // (0,0) is the top left of the board.
    View.OnTouchListener touchListener = new View.OnTouchListener(){
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent me){
            if (me.getActionMasked() == MotionEvent.ACTION_DOWN){
                touchLoc[0] = me.getX();
                touchLoc[1] = me.getY();

            }
            String coordinates = boardCoordConvert(touchLoc[0], touchLoc[1]);
            System.out.println("COORDINATES: " + coordinates);
            String tempMove = moveBuffer.addClick(coordinates, gameManager.getBoard());
            layoutManager.setClicked(moveBuffer);
            System.out.println(tempMove);
            if (tempMove !=null){
                System.out.println("VALID");
                gameManager.makeMove(tempMove.substring(0,2), tempMove.substring(2));
            }
            return false;
        }
    };


    private void gameLoop() {
        Database.fetch(Database.Collections.MOVES, docs -> {

            System.out.println("=== GAME LOOP ===");

            // logic to keep clock up to date
            //white player turn
            if(gameManager.isPlayerWhiteInTurn()){
                trackSecsWhite += 1;

                if (trackSecsWhite == 60) {
                    trackSecsWhite = 0;
                    trackMinsWhite += 1;
                }
                if (trackMinsWhite == 60) {
                    trackMinsWhite = 0;
                    trackHoursWhite += 1;
                }
                //convert data to a string and store
                String timeToDisplayWhite = GameManager.clockUpdatorWhite(trackHoursWhite,
                        trackMinsWhite, trackSecsWhite);
                //update display
                CharSequence charSequence = new StringBuffer(timeToDisplayWhite);
                final TextView helloTextView = findViewById(R.id.clockViewWhite);
                helloTextView.setText(charSequence);
                //black player turn
            } else {
                trackSecsBlack += 1;

                if (trackSecsBlack == 60) {
                    trackSecsBlack = 0;
                    trackMinsBlack += 1;
                }
                if (trackMinsBlack == 60) {
                    trackMinsBlack = 0;
                    trackHoursBlack += 1;
                }
                //convert data to a string and store
                String timeToDisplay = GameManager.clockUpdatorBlack(trackHoursBlack, trackMinsBlack, trackSecsBlack);
                //update display
                CharSequence charSequence = new StringBuffer(timeToDisplay);
                final TextView helloTextView = findViewById(R.id.clockViewBlack);
                helloTextView.setText(charSequence);
            }

            boardUpdator.updateBoard(gameManager.getBoard(), layoutManager);

            // color to be checked against
            String color;
            String winner;
            if (gameManager.isPlayerWhiteInTurn()) {
                color = "White";
                winner = "Black";
            } else {
                color = "Black";
                winner = "White";
            }

            if (boardManager.checkCheckmated(gameManager.getBoard(), color)) {
                openEndGameActivity(winner, false);
            }
            else if (boardManager.checkStalemated(gameManager.getBoard(), color)) {
                openEndGameActivity(winner, true);
            }

            // Fetch the database for new moves.
            Database.fetch(Database.Collections.MOVES, (moves) -> {
                // When the size of 'moves' is larger than 'moveNumber',
                // it means that one of the users has performed a move and both players need to
                // replicate it on their devices.
                if (moves.size() > moveNumber) {
                    Move lastMove = (Move) moves.get(0);
                    int largestNumber = 0;
                    // We need to find the last move which is the one with the largest 'number'.
                    for (Document doc : moves) {
                        Move move  = (Move)doc;
                        if (move.getNumber() > largestNumber) {
                            largestNumber = move.getNumber();
                            lastMove = move;
                        }
                    }
                    // Get currSpot and newSpot using the move's code.
                    String currSpot = String.valueOf(lastMove.getCode().charAt(0)) +
                            lastMove.getCode().charAt(1);
                    String newSpot = String.valueOf(lastMove.getCode().charAt(2)) +
                            lastMove.getCode().charAt(3);
                    // Use the GameManager to register the new move.
                    gameManager.makeMove(currSpot, newSpot);
                    moveNumber++;
                }

                // Run this method recursively every 1000ms (1s).
                final Handler handler = new Handler();
                handler.postDelayed(this::gameLoop, 1000);
            });
        });
    }
    /**
     * Returns the chess coordinate of a given x,y value on the board view
     *
     * @param x the x-coordinate of click
     * @param y the y-coordinate of click
     */
    private String boardCoordConvert(float x, float y){
        float boardSize = boardView.getHeight();
        float squareSize = boardSize/8;
        final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
        final char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8'};
        char letter, num;

        letter = letters[(int)(x/squareSize)];
        num = nums[7-((int)(y/squareSize))];

        return ""+letter+num;
    }

    /**
     * Opens the end gane screen.
     *
     * @param winningColor the color of the winning player
     * @param stale True if game was a draw, otherwise false
     *
     */
    private void openEndGameActivity(String winningColor, boolean stale){
        Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra("winner", winningColor);
        intent.putExtra("stale", stale);
        intent.putExtra("username", username);
        intent.putExtra("userid", userid);
        startActivity(intent);
        finish();
    }

}