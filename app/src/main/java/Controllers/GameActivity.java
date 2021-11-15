package Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import com.wildcats.ultimatechess.R;
import Interfaces.Database;
import UseCases.BoardManager;
import UseCases.BoardUpdator;
import UseCases.GameBuildDirector;
import UseCases.GameManager;
import UseCases.MoveBuffer;
import UseCases.NormalGameBuilder;

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

        moveBuffer = new MoveBuffer(this);

        boardManager.checkValidMove(gameManager.getBoard(), "a2", "a3");

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

            }
            tempMove = moveBuffer.addClick(boardCordConvert(touchLoc[0], touchLoc[1], true),
                    gameManager.getBoard());
            System.out.println(tempMove);
            System.out.println("hiii");
            if (tempMove!=null){
                System.out.println("helloooo");
                System.out.println(boardManager.checkValidMove(gameManager.getBoard(), tempMove.substring(0,2), tempMove.substring(1)));
                gameManager.makeMove(tempMove.substring(0,2), tempMove.substring(1));
            }
            return false;
        }


    };


    private void gameLoop() {
        Database.fetch(Database.Collections.MOVES, docs -> {

            System.out.println("=== GAME LOOP ===");
            boardUpdator.updateBoard(gameManager.getBoard(), layoutManager);


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