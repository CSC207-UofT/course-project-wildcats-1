package UseCasesTests;

//import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Entities.*;
import UseCases.GameManager;

public class GameManagerTest {

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

    @Test(timeout = 50)
    public void testMakeCastling(){
        Board bd = new Board();
        Piece king = new King("White", "e1");
        Piece rook = new Rook("White", "h1");
        bd.addPiece(king, "e1");
        bd.addPiece(rook, "h1");
        GameManager gm = new GameManager(bd);

        gm.makeMove("e1","g1");
        assertEquals(king, gm.getBoard().checkSquare("g1"));
        assertNull(gm.getBoard().checkSquare("e1"));
        assertEquals(rook, gm.getBoard().checkSquare("f1"));
        assertNull(gm.getBoard().checkSquare("h1"));
        assertFalse(gm.isPlayerWhiteInTurn());

    }

    @Test(timeout = 50)
    public void testMakeEnPassent(){
        Board bd = new Board();
        Piece whitePawn = new Pawn("White", "c2");
        Piece blackPawn = new Pawn("Black", "d4");
        bd.addPiece(whitePawn, "c2");
        bd.addPiece(blackPawn, "d4");
        GameManager gm = new GameManager(bd);

        gm.makeMove("c2","c4");

        assertFalse(gm.isPlayerWhiteInTurn());

        gm.makeMove("d4", "c3");

        assertEquals(blackPawn, gm.getBoard().checkSquare("c3"));
        assertNull(gm.getBoard().checkSquare("d4"));
        assertNull(gm.getBoard().checkSquare("c4"));
        assertNull(gm.getBoard().checkSquare("c2"));
        assertTrue(gm.isPlayerWhiteInTurn());
        assertEquals(whitePawn, gm.getWhitePiecesOut().get(0));
        assertEquals(1, gm.getWhitePiecesOut().size());

    }
}
