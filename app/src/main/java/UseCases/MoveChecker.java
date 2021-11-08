package UseCases;

public class MoveChecker {

    private final String[] LETTER_COORDINATES =
            new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
    
    /*public boolean checkValidPawnMove(Board board, Pawn pawn, String loc) {
        // integer of the difference between the column you're at, from the column you're going to.
        int colDiff = Arrays.asList(LETTER_COORDINATES).indexOf(loc.substring(0,1)) -
                Arrays.asList(LETTER_COORDINATES).indexOf(pawn.location.substring(0,1));
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(pawn.getLocation().substring(1));

        if (colDiff == 0 && rowDiff == 1 && pawn.color.equals("white") &&
                board.checkSquareEmpty(loc)) {
            return true;
        } else if (colDiff == 0 && rowDiff == 2 && pawn.color.equals("white") &&
                pawn.firstMove && board.checkSquareEmpty(loc)) {
            return true;
        } else if (colDiff == 0 && rowDiff == -1 && pawn.color.equals("black") &&
                board.checkSquareEmpty(loc)) {
            return true;
        } else if (colDiff == 0 && rowDiff == -2 && pawn.color.equals("black") &&
                pawn.firstMove && board.checkSquareEmpty(loc)) {
            return true;
            //  checking en passant
        } else if (Math.abs(colDiff) == 1 && rowDiff == 1 && pawn.color.equals("white") &&
                pawn.movedTwo && board.checkSquareEmpty(loc)) {
            String enemy_loc = loc.charAt(0) + pawn.location.substring(1);
            Piece poss_pawn = board.checkSquare(enemy_loc);
            return (poss_pawn != null && poss_pawn.color.equals("black") &&
                    poss_pawn instanceof Pawn);
        } else if (Math.abs(colDiff) == 1 && rowDiff == -1 && pawn.color.equals("black") &&
                pawn.movedTwo && board.checkSquareEmpty(loc)) {
            String enemy_loc = loc.charAt(0) + pawn.location.substring(1);
            Piece poss_pawn = board.checkSquare(enemy_loc);
            return (poss_pawn != null && poss_pawn.color.equals("white") &&
                    poss_pawn instanceof Pawn);
        }
        return false;
    }*/

}
