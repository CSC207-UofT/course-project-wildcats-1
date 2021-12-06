package UseCasesTests;

//import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Entities.*;
import UseCases.GameManager;

public class GameManagerTest {

//    GameManager gm;
//    Board bd;
//    Piece pc1;
//    Piece pc2;
//
//    @Before
//    public void setUp(){
//        bd = new Board();
//        pc1 = new Queen("White", "d1");
//        pc2 = new Queen("Black", "d8");
//        bd.addPiece(pc1, "d1");
//        bd.addPiece(pc2, "d8");
//        gm = new GameManager(bd);
//    }

    /**
     * Test getBoard method
     */
    @Test(timeout = 50)
    public void testGetBoard(){
        Board bd = new Board();
        Piece pc1 = new Queen("White", "d1");
        Piece pc2 = new Queen("Black", "d8");
        bd.addPiece(pc1, "d1");
        bd.addPiece(pc2, "d8");
        GameManager gm = new GameManager(bd);

        Board actual = gm.getBoard();
        assertSame(bd, actual);
    }

    /**
     * Test makeMove where a piece is moved without taking a piece.
     */
    @Test(timeout = 50)
    public void testMakeMove(){
        Board bd = new Board();
        Piece pc1 = new Queen("White", "d1");
        Piece pc2 = new Queen("Black", "d8");
        bd.addPiece(pc1, "d1");
        bd.addPiece(pc2, "d8");
        GameManager gm = new GameManager(bd);

        gm.makeMove("d1", "a4");

        assertEquals(pc1, gm.getBoard().checkSquare("a4"));
        assertNull(gm.getBoard().checkSquare("d1"));
        assertEquals(pc2, gm.getBoard().checkSquare("d8"));
        assertFalse(gm.isPlayerWhiteInTurn());
        assertEquals(0, gm.getWhitePiecesOut().size());
        assertEquals(0, gm.getBlackPiecesOut().size());
    }

    /**
     * Test makeMove where a piece is moved while taking a piece.
     */
    @Test(timeout = 50)
    public void testMakeMoveTakePiece(){
        Board bd = new Board();
        Piece pc1 = new Queen("White", "d1");
        Piece pc2 = new Queen("Black", "d8");
        bd.addPiece(pc1, "d1");
        bd.addPiece(pc2, "d8");
        GameManager gm = new GameManager(bd);

        gm.makeMove("d1", "d8");

        assertEquals(pc1, gm.getBoard().checkSquare("d8"));
        assertNull(gm.getBoard().checkSquare("d1"));
        assertFalse(gm.isPlayerWhiteInTurn());
        assertEquals(0, gm.getWhitePiecesOut().size());
        assertEquals(1, gm.getBlackPiecesOut().size());
        assertSame(pc2, gm.getBlackPiecesOut().get(0));
    }
}
