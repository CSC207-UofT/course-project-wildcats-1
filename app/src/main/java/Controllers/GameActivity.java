package Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.wildcats.ultimatechess.R;

import Interfaces.*;

import UseCases.*;

import android.view.View;


// Controls user input for the Game page.
public class GameActivity extends AppCompatActivity {

    private TextView txt_playerWhite, txt_playerBlack;
    private String username, userid;
    private View boardView;
    private float[] touchLoc = new float[2];

    private ImageView pawn;
    private TableLayout board;
    private PieceLayoutManager layoutManager;
    private GameManager gameManager;
    private GameBuildDirector gameBuilder;
    private BoardUpdator boardUpdator = new BoardUpdator();
    private MoveBuffer moveBuffer;
    private String tempMove;
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

        pawn = findViewById(R.id.a2);

        layoutManager = new PieceLayoutManager(this);

        gameBuilder = new GameBuildDirector(new NormalGameBuilder());
        gameBuilder.Construct();

        gameManager = gameBuilder.getGame();

        boardManager = new BoardManager();

        moveBuffer = new MoveBuffer(this);

        //initialize white's clock timer
        CharSequence charSequenceWhite = new StringBuffer(GameManager.initializeClockWhite());
        final TextView helloTextViewWhite = (TextView) findViewById(R.id.clockViewWhite);
        helloTextViewWhite.setText(charSequenceWhite);

        //initialize black's clock timer
        CharSequence charSequenceBlack = new StringBuffer(GameManager.initializeClockBlack());
        final TextView helloTextViewBlack = (TextView) findViewById(R.id.clockViewBlack);
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
            String coordinates = boardCoordConvert(touchLoc[0], touchLoc[1], true);
            System.out.println("COORDINATES: " + coordinates);
            tempMove = moveBuffer.addClick(coordinates, gameManager.getBoard());
            layoutManager.setClicked(moveBuffer);
            System.out.println(tempMove);
            if (tempMove!=null){
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
                final TextView helloTextView = (TextView) findViewById(R.id.clockViewWhite);
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
                final TextView helloTextView = (TextView) findViewById(R.id.clockViewBlack);
                helloTextView.setText(charSequence);
            }





            boardUpdator.updateBoard(gameManager.getBoard(), layoutManager);

            // color to be checked against
            String color;
            if (gameManager.isPlayerWhiteInTurn()) {
                color = "White";
            } else {
                color = "Black";
            }

            if (boardManager.checkCheckmated(gameManager.getBoard(), color)) {
                //TODO Checkmate endgame method
            } else if (boardManager.checkStalemated(gameManager.getBoard(), color)) {
                //TODO Stalemate endgame method
            }

            // TODO Provide all documentation and comments in this method
            Database.fetch(Database.Collections.MOVES, (moves) -> {
                if (moves.size() > moveNumber) {
                    Move lastMove = (Move) moves.get(0);
                    int largestNumber = 0;
                    for (Document doc : moves) {
                        Move move  = (Move)doc;
                        if (move.getNumber() > largestNumber) {
                            largestNumber = move.getNumber();
                            lastMove = move;
                        }
                    }
                    String currSpot = String.valueOf(lastMove.getCode().charAt(0)) +
                            lastMove.getCode().charAt(1);
                    String newSpot = String.valueOf(lastMove.getCode().charAt(2)) +
                            lastMove.getCode().charAt(3);
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
     * @param isWhite true if the board is being viewed from white side, false otherwise
     */
    private String boardCoordConvert(float x, float y, boolean isWhite){
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