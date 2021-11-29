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
    public void testPawnTake(){
        Board bd = new Board();
        Piece pc1 = new Pawn("White", "d4");
        Piece pc2 = new Pawn("Black", "e5");
        bd.addPiece(pc1, "d4");
        bd.addPiece(pc2, "e5");

        assertTrue(bm.checkValidMove(bd, "d4", "e5"));
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

    @Test(timeout = 50)
    public void testKnight(){
        Board bd = new Board();
        Piece pc = new Knight("White", "b1");
        bd.addPiece(pc, "b1");

        assertTrue(bm.checkValidMove(bd, "b1", "c3"));
        assertFalse(bm.checkValidMove(bd, "b1", "b3"));

        Piece block = new Pawn("White", "c3");
        bd.addPiece(block, "c3");

        assertFalse(bm.checkValidMove(bd, "b1", "c3"));
    }

}
