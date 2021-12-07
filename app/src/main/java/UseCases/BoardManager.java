package UseCases;

import Entities.*;

/**
 *
 */
public class BoardManager {

    /**
     * Check whether or not a specific move can be made from a piece at one location on the board,
     * to another different one
     *
     * @param board The Board object on which this move will attempt to take place
     * @param loc1  The origin location of a piece that will try to move, as a chess coordinate.
     * @param loc2  The location that a piece will try to move to as a chess coordinate.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkMoveChecker(Board board, String loc1, String loc2) {
        MoveChecker checker = new MoveChecker();
        return checker.checkValidMove(board, loc1, loc2);
    }

    /**
     *
     *
     * @param board The Board object on which the Checkmate will be determined.
     * @param color The Color of the for which a Checkmate will be determined.
     * @return True if the player of the last turn taken is Checkmated, otherwise False.
     */
    public boolean checkCheckmated(Board board, String color) {
        MoveChecker checker = new MoveChecker();
        return checker.checkChecked(board, color) && checker.checkNoMoves(board, color);
    }

    /**
     *
     *
     * @param board The Board object on which the Stalemate will be determined.
     * @param color The Color of the for which a Stalemate will be determined.
     * @return True if the player of the last turn taken is Stalemate, otherwise False.
     */
    public boolean checkStalemated(Board board, String color) {
        MoveChecker checker = new MoveChecker();
        return !checker.checkChecked(board, color) && checker.checkNoMoves(board, color);
    }
}