package UseCasesTests;

//import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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

        assertTrue(bm.checkMoveChecker(bd, "d2", "d3"));
    }

    @Test(timeout = 50)
    public void testPawnDouble(){
        Board bd = new Board();
        Piece pc = new Pawn("White", "d2");
        bd.addPiece(pc, "d2");

        assertTrue(bm.checkMoveChecker(bd, "d2", "d4"));

        bd.movePiece("d2", "d3");

        assertFalse(bm.checkMoveChecker(bd,"d3","d4"));
    }

    @Test(timeout = 50)
    public void testPawnTake(){
        Board bd = new Board();
        Piece pc1 = new Pawn("White", "d4");
        Piece pc2 = new Pawn("Black", "e5");
        bd.addPiece(pc1, "d4");
        bd.addPiece(pc2, "e5");

        assertTrue(bm.checkMoveChecker(bd, "d4", "e5"));
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
        assertTrue(bm.checkMoveChecker(bd, "d5", "e6"));
    }

    @Test(timeout = 50)
    public void testPromotedPawn(){
        Board bd = new Board();
        Piece pawn = new Pawn("White", "c8");
        bd.addPiece(pawn, "c8");
        ((Pawn) pawn).promote();

        assertTrue(bm.checkMoveChecker(bd,"c8","c1"));
        assertTrue(bm.checkMoveChecker(bd,"c8","f5"));
    }

    @Test(timeout = 50)
    public void testKnight(){
        Board bd = new Board();
        Piece pc = new Knight("White", "b1");
        bd.addPiece(pc, "b1");

        assertTrue(bm.checkMoveChecker(bd, "b1", "c3"));
        assertFalse(bm.checkMoveChecker(bd, "b1", "b3"));

        Piece block = new Pawn("White", "c3");
        bd.addPiece(block, "c3");

        assertFalse(bm.checkMoveChecker(bd, "b1", "c3"));
    }

    @Test(timeout = 50)
    public void testBishop(){
        Board bd = new Board();
        Piece pc = new Bishop("White", "b1");
        bd.addPiece(pc, "b1");

        assertTrue(bm.checkMoveChecker(bd, "b1", "d3"));
        assertFalse(bm.checkMoveChecker(bd, "b1", "b7"));

        Piece block = new Pawn("White", "c2");
        bd.addPiece(block, "c2");

        assertFalse(bm.checkMoveChecker(bd, "b1", "d3"));

        Piece enemy = new Pawn("Black", "c2");
        Piece removed = bd.removePiece("c2");
        bd.addPiece(enemy, "c2");

        assertFalse(bm.checkMoveChecker(bd, "b1", "d3"));
        assertTrue(bm.checkMoveChecker(bd, "b1", "c2"));
    }

    @Test(timeout = 50)
    public void testRook(){
        Board bd = new Board();
        Piece pc = new Rook("White", "c4");
        bd.addPiece(pc, "c4");

        assertTrue(bm.checkMoveChecker(bd, "c4", "c7"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "f4"));
        assertFalse(bm.checkMoveChecker(bd, "c4", "b7"));

        Piece block = new Pawn("White", "c6");
        bd.addPiece(block, "c6");

        assertFalse(bm.checkMoveChecker(bd, "c4", "c7"));

        Piece enemy = new Pawn("Black", "b4");
        bd.addPiece(enemy, "b4");

        assertFalse(bm.checkMoveChecker(bd, "c4", "a4"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "b4"));
    }

    @Test(timeout = 50)
    public void testQueen(){
        Board bd = new Board();
        Piece pc = new Queen("White", "c4");
        bd.addPiece(pc, "c4");

        assertTrue(bm.checkMoveChecker(bd, "c4", "c7"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "f4"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "e6"));
        assertFalse(bm.checkMoveChecker(bd, "c4", "b7"));

        Piece blockRook = new Pawn("White", "c6");
        bd.addPiece(blockRook, "c6");
        Piece blockBishop = new Pawn("White", "e6");
        bd.addPiece(blockBishop, "e6");

        assertFalse(bm.checkMoveChecker(bd, "c4", "c7"));
        assertFalse(bm.checkMoveChecker(bd, "c4", "f7"));

        Piece enemy1 = new Pawn("Black", "f4");
        bd.addPiece(enemy1, "f4");
        Piece enemy2 = new Pawn("Black", "b5");
        bd.addPiece(enemy1, "b5");

        assertFalse(bm.checkMoveChecker(bd, "c4", "g4"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "f4"));

        assertFalse(bm.checkMoveChecker(bd, "c4", "a6"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "b5"));
    }

    @Test(timeout = 50)
    public void testKing(){
        Board bd = new Board();
        Piece pc = new King("White", "c4");
        bd.addPiece(pc, "c4");

        assertTrue(bm.checkMoveChecker(bd, "c4", "c5"));
        assertTrue(bm.checkMoveChecker(bd, "c4", "d5"));
        assertFalse(bm.checkMoveChecker(bd, "c4", "c7"));

        Piece block = new Pawn("White", "c5");
        bd.addPiece(block, "c5");

        assertFalse(bm.checkMoveChecker(bd, "c4", "c5"));

        Piece enemy = new Pawn("Black", "d4");
        bd.addPiece(enemy, "d4");

        assertTrue(bm.checkMoveChecker(bd, "c4", "d4"));
    }

    @Test(timeout = 50)
    public void testKingCastle(){
        Board bd = new Board();
        Piece pc = new King("White", "e1");
        bd.addPiece(pc, "e1");

        Rook rookLeft = new Rook("White", "a1");
        Rook rookRight = new Rook("White", "h1");

        assertTrue(bm.checkMoveChecker(bd, "e1", "g1"));
        assertTrue(bm.checkMoveChecker(bd, "e1", "c1"));

        Piece block = new Bishop("White", "f1");
        bd.addPiece(block, "f1");

        assertFalse(bm.checkMoveChecker(bd, "e1", "g1"));

        Piece removed = bd.removePiece("f1");

        bd.movePiece("e1", "e2");
        bd.movePiece("e2", "e1");

        assertFalse(bm.checkMoveChecker(bd, "e1", "g1"));
        assertFalse(bm.checkMoveChecker(bd, "e1", "c1"));
    }

}
