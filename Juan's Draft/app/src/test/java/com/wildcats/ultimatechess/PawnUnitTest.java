package com.wildcats.ultimatechess;

import org.junit.Test;
import static org.junit.Assert.*;

public class PawnUnitTest {

    @Test
    public void checkPossibleMovesUnmoved() {
        Pawn testPawn = Pawn("White", "c2");
        String[] actual = testPawn.getPossibleMoves();

        String[] expected = new String[2];
        expected[0] = "c3";
        expected[1] = "c4";

        assertEquals(actual, expected);
    }
}