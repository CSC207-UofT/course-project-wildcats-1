package EntitiesTests;

//import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Entities.*;

public class PawnTest {

    @Test(timeout = 50)
    public void testUnpromotedPawn(){
        Pawn pawn = new Pawn("White", "c2");

        assertTrue(pawn.getUnmoved());
        assertEquals("c2", pawn.getLocation());
        assertFalse(pawn.getMovedTwice());
        assertFalse(pawn.isPromoted());
        assertFalse(pawn.isEliminated());

        pawn.move("c4");
        pawn.movedTwice();

        assertTrue(pawn.getMovedTwice());
        assertFalse(pawn.getUnmoved());

        pawn.clearMovedTwice();

        assertFalse(pawn.getMovedTwice());
    }

    @Test(timeout = 50)
    public void testPromotedPawn(){
        Pawn pawn = new Pawn("White", "c8");
        pawn.promote();

        assertTrue(pawn.isPromoted());
        assertFalse(pawn.getMovedTwice());

        pawn.move("c1");

        assertFalse(pawn.getUnmoved());
    }
}
