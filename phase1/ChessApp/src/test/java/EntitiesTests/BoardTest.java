package EntitiesTests;

//import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Entities.*;

public class BoardTest {

    /**
     * Test addPiece, checkSquare, removePiece methods
     */
    @Test(timeout = 50)
    public void testAddRemoveCheckPiece(){
        Board bd = new Board();
        Piece pc = new King("White", "d5");
        bd.addPiece(pc, "d5");

        assertEquals(pc, bd.checkSquare("d5"));
        assertSame(pc, bd.checkSquare("d5"));
        assertNull(bd.checkSquare("a1"));

        Piece actual = bd.removePiece("d5");

        assertEquals(pc, actual);
        assertNull(bd.checkSquare("d5"));
    }

    /**
     * Test movePiece
     */
    @Test(timeout = 50)
    public void testMovePiece(){
        Board bd = new Board();
        Piece pc1 = new Queen("White", "d1");
        Piece pc2 = new Queen("Black", "d8");
        bd.addPiece(pc1, "d1");
        bd.addPiece(pc2, "d8");
        bd.movePiece("d1", "a4");

        assertNull(bd.checkSquare("d1"));
        assertSame(pc1, bd.checkSquare("a4"));
        assertSame(pc2, bd.checkSquare("d8"));
    }

    @Test(timeout = 50)
    public void testCheckSquareEmpty(){
        Board bd = new Board();

        assertTrue(bd.checkSquareEmpty("d5"));

        Piece pc = new King("White", "d5");
        bd.addPiece(pc, "d5");

        assertTrue(bd.checkSquareEmpty("h8"));
        assertFalse(bd.checkSquareEmpty("d5"));
    }

}
