package UseCasesTests;

//import org.junit.Before;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import android.provider.Settings;

import Entities.*;
import UseCases.*;

public class BoardManagerTest {

    BoardManager bm = new BoardManager();
//    Board bd;
//
//    /**
//     * Sets up test cases by using the GameBuilder to construct a normal chess board.
//     */
//    @Before
//    public void setUp(){
//        GameBuilder gb = new NormalGameBuilder();
//        GameBuildDirector gbd = new GameBuildDirector(gb);
//        gbd.Construct();
//        GameManager gm = gbd.getGame();
//        bd = gm.getBoard();
//    }

    @Test(timeout = 50)
    public void testPawnSingle(){
        Board bd = new Board();
        Piece pc = new Pawn("White", "d2");
        bd.addPiece(pc, "d2");

        assertTrue(bm.checkValidMove(bd, "d2", "d3"));
    }

    @Test(timeout = 50)
    public void testPawnDouble(){
        Board bd = new Board();
        Piece pc = new Pawn("White", "d2");
        bd.addPiece(pc, "d2");

        assertTrue(bm.checkValidMove(bd, "d2", "d4"));

        bd.movePiece("d2", "d3");

        assertFalse(bm.checkValidMove(bd,"d3","d4"));
    }

    @Test(timeout = 50)
    public void testPawnEnPassent(){
        Board bd = new Board();
        Piece pc1 = new Pawn("White", "d2");
        Piece pc2 = new Pawn("Black", "e7");
        bd.addPiece(pc1, "d2");
        bd.addPiece(pc2, "e7");
        bd.movePiece("d2", "d5");

        bd.movePiece("e7", "e5");
        assertTrue(bm.checkValidMove(bd, "d5", "e6"));
    }
}
