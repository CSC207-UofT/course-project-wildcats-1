package UseCases;

import java.util.Arrays;

import Entities.Board;
import Entities.King;
import Entities.Knight;
import Entities.Pawn;
import Entities.Piece;
import Entities.Bishop;
import Entities.Queen;
import Entities.Rook;

public class BoardManager {


    /**
     * Letters representing the chess board columns.
     */
    private final String[] COLUMNS =
            new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};
    /**
     * Integers representing the chess board rows.
     */
    private final int[] ROWS =
            new int[]{1, 2, 3, 4, 5, 6, 7, 8};


    /**
     * Check whether or not a specific move can be made from a piece at one location on the board,
     * to another different one
     *
     * @param board The Board object on which this move will attempt to take place
     * @param loc1  The origin location of a piece that will try to move, as a chess coordinate.
     * @param loc2  The location that a piece will try to move to as a chess coordinate.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidMove(Board board, String loc1, String loc2) {
        Piece piece = board.checkSquare(loc1);
        if (piece instanceof Pawn) {
            return checkValidPawnMove(board, (Pawn) piece, loc2);
        } else if (piece instanceof Bishop) {
            return checkValidBishopMove(board, piece, loc2);
        } else if (piece instanceof Rook) {
            return checkValidRookMove(board, piece, loc2);
        } else if (piece instanceof Knight) {
            return checkValidKnightMove(board, (Knight) piece, loc2);
        } else if (piece instanceof Queen) {
            return checkValidQueenMove(board, (Queen) piece, loc2);
        } else if (piece instanceof King) {
            return checkValidKingMove(board, (King) piece, loc2);
        }
        return !board.checkSquareEmpty(loc1);
    }



    /**
     * Check whether or not a Pawn can make a move to a specific location on the board.
     *
     * @param board The Board object on which this move will attempt to take place
     * @param pawn The Pawn object that is trying to move.
     * @param loc The string location in chess-coordinates, to which the pawn is trying to move to.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidPawnMove(Board board, Pawn pawn, String loc) {
        //TODO: check both spaces available are in front of two unit moves

        // integer of the difference between the column you're at, from the column you're going to.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0, 1)) -
                Arrays.asList(COLUMNS).indexOf(pawn.getLocation().substring(0, 1));

        // The difference between the row you're at, from the row you're going to.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(pawn.getLocation().substring(1));

        // True if only the pawn is white
        boolean whiteTurn = pawn.getColor().equals("white");

        // True if only the pawn is black
        boolean blackTurn = pawn.getColor().equals("black");


        // move 1 forward
        if (colDiff == 0 && rowDiff == 1 && whiteTurn && board.checkSquareEmpty(loc)) {
            return true;
        }
        // move 2 forward
        else if (colDiff == 0 && rowDiff == 2 && whiteTurn && pawn.getPlayStatus() && board.checkSquareEmpty(loc)) {
            return true;
        }
        // move 1 forward
        else if (colDiff == 0 && rowDiff == -1 && blackTurn &&
                board.checkSquareEmpty(loc)) {
            return true;
        }
        // move 2 forward
        else if (colDiff == 0 && rowDiff == -2 && blackTurn&&
                pawn.getPlayStatus() && board.checkSquareEmpty(loc)) {
            return true;
        }
        //  checking en passant & attack
        else if (Math.abs(colDiff) == 1 && rowDiff == 1 && whiteTurn) {
            boolean empty = board.checkSquareEmpty(loc);

            // en passant
            if (empty) {
                String enemy_loc = loc.charAt(0) + pawn.getLocation().substring(1);
                Piece poss_pawn = board.checkSquare(enemy_loc);
                return (poss_pawn != null && poss_pawn.getColor().equals("black") &&
                        poss_pawn instanceof Pawn && ((Pawn) poss_pawn).getMovedTwice());
            }
            // attack
            else {
                Piece poss_enemy = board.checkSquare(loc);
                return (poss_enemy.getColor().equals("black"));
            }
        }
        //  checking attack & en passant
        else if (Math.abs(colDiff) == 1 && rowDiff == -1 && blackTurn) {
            boolean empty = board.checkSquareEmpty(loc);

            // en passant
            if (empty) {
                String enemy_loc = loc.charAt(0) + pawn.getLocation().substring(1);
                Piece poss_pawn = board.checkSquare(enemy_loc);
                return (poss_pawn != null && poss_pawn.getColor().equals("white") &&
                        poss_pawn instanceof Pawn && ((Pawn) poss_pawn).getMovedTwice());
            }
            // attack
            else {
                Piece poss_enemy = board.checkSquare(loc);
                return (poss_enemy.getColor().equals("white"));
            }

        } else {
            return false;
        }
    }

    /**
     * Check whether or not a Knight can make a move to a specific location on the board.
     *
     * @param board The Board object on which this move will take place
     * @param knight The Knight object that is trying to move.
     * @param loc The string location in chess-coordinates, to which the pawn is trying to move to.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidKnightMove(Board board, Knight knight, String loc) {
        // The difference between the row you're going to, and the row you're at.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(knight.getLocation().substring(1));

        // The difference between the column you're going to, and the column you're at.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0,1)) -
                Arrays.asList(COLUMNS).indexOf(knight.getLocation().substring(0,1));

        String enemyColour = null;
        if (knight.getColor().equals("white")) {
            enemyColour = "black";
        } else if (knight.getColor().equals("black")) {
            enemyColour = "white";

        }
        // When the move attempted is L shaped from the origin
        if ((Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 1) ||
                (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 2)) {
            return board.checkSquareEmpty(loc) ||
                    board.checkSquare(loc).getColor().equals(enemyColour);
        } else {
            return false;
        }
    }

    /**
     * Check whether or not a Bishop can make a move to a specific location on the board.
     *
     * @param board The Board object on which this move will take place
     * @param piece The Bishop/Queen object that is trying to move.
     * @param loc The string location in chess-coordinates, to which the pawn is trying to move to.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidBishopMove(Board board, Piece piece, String loc) {
        // The difference between the row you're going to, and the row you're at.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(piece.getLocation().substring(1));

        // The difference between the column you're going to, and the column you're at.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0,1)) -
                Arrays.asList(COLUMNS).indexOf(piece.getLocation().substring(0,1));

        String enemyColour = null;
        if (piece.getColor().equals("white")) {
            enemyColour = "black";
        } else if (piece.getColor().equals("black")) {
            enemyColour = "white";
        }

        // When the horizontal & vertical shift are equal in magnitude.
        if (Math.abs(colDiff) == Math.abs(rowDiff)) {
            int col =
                    Arrays.asList(COLUMNS).indexOf(piece.getLocation().substring(0,1));
            int row = Integer.parseInt(piece.getLocation().substring(1));

            boolean emptySquare;
            // check every horizontal square in front of the Bishop until the destination loc.
            for (int i = 1; i <= Math.abs(rowDiff); i++) {
                if (rowDiff > 0) {
                    row += 1;
                } else if (rowDiff < 0) {
                    row -= 1;
                }
                if (colDiff > 0) {
                    colDiff += 1;
                } else if (colDiff < 0) {
                    colDiff -= 1;
                }
                String squareID = COLUMNS[col] + row;
                emptySquare = board.checkSquareEmpty(squareID);
                // stopped because there is a piece in the way of path
                if (!emptySquare && !squareID.equals(loc)) {
                    return false;
                }
                // attempting an attack at target location
                else if (!emptySquare) {
                    return board.checkSquare(loc).getColor().equals(enemyColour);
                }
            }
            return true;
        } else {
            return false;
        }


    }

    /**
     * Check whether or not a Rook can make a move to a specific location on the board.
     *
     * @param board The Board object on which this move will take place
     * @param piece The Rook/Queen object that is trying to move.
     * @param loc The string location in chess-coordinates, to which the pawn is trying to move to.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidRookMove (Board board, Piece piece, String loc) {
        // The difference between the row you're going to, and the row you're at.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(piece.getLocation().substring(1));

        // The difference between the column you're going to, and the column you're at.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0,1)) -
                Arrays.asList(COLUMNS).indexOf(piece.getLocation().substring(0,1));

        String enemyColour = null;

        if (piece.getColor().equals("white")) {
            enemyColour = "black";
        } else if (piece.getColor().equals("black")) {
            enemyColour = "white";
        }

        // Boolean representing whether a vertical move is attempted.
        if (rowDiff != 0 && colDiff == 0) {
            int col =
                    Arrays.asList(COLUMNS).indexOf(piece.getLocation().substring(0,1));
            int row = Integer.parseInt(piece.getLocation().substring(1));

            boolean emptySquare;
            // check every horizontal square in front of the Bishop until the destination loc.
            for (int i = 1; i <= Math.abs(rowDiff); i++) {
                if (rowDiff > 0) {
                    row += 1;
                } else {
                    row -= 1;
                }
                String squareID = COLUMNS[col] + row;
                emptySquare = board.checkSquareEmpty(squareID);
                // stopped because there is a piece in the way of path
                if (!emptySquare && !squareID.equals(loc)) {
                    return false;
                }
                // attempting an attack at target location
                else if (!emptySquare) {
                    return board.checkSquare(loc).getColor().equals(enemyColour);
                }
            }
            return true;
        }
        // Boolean representing whether a horizontal move is attempted.
        else if (colDiff != 0 && rowDiff == 0) {
            int col =
                    Arrays.asList(COLUMNS).indexOf(piece.getLocation().substring(0,1));
            int row = Integer.parseInt(piece.getLocation().substring(1));

            boolean emptySquare;
            // check every horizontal square in front of the Bishop until the destination loc.
            for (int i = 1; i <= Math.abs(colDiff); i++) {
                if (colDiff > 0) {
                    col += 1;
                } else {
                    col -= 1;
                }
                String squareID = COLUMNS[col] + row;
                emptySquare = board.checkSquareEmpty(squareID);
                // stopped because there is a piece in the way of path
                if (!emptySquare && !squareID.equals(loc)) {
                    return false;
                }
                // attempting an attack at target location
                else if (!emptySquare) {
                    return board.checkSquare(loc).getColor().equals(enemyColour);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check whether or not a Queen can make a move to a specific location on the board.
     *
     * @param board The Board object on which this move will take place
     * @param queen The Queen object that is trying to move.
     * @param loc The string location in chess-coordinates, to which the pawn is trying to move to.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidQueenMove (Board board, Queen queen, String loc) {
        return checkValidRookMove(board, queen, loc) || checkValidBishopMove(board, queen, loc);
    }

    /**
     * Check whether or not a King can make a move to a specific location on the board.
     *
     * @param board The Board object on which this move will take place
     * @param king The King object that is trying to move.
     * @param loc The string location in chess-coordinates, to which the pawn is trying to move to.
     * @return True if the move is possible, otherwise False.
     */
    public boolean checkValidKingMove (Board board, King king, String loc) {
        //TODO check King

        // The difference between the row you're going to, and the row you're at.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(king.getLocation().substring(1));

        // The difference between the column you're going to, and the column you're at.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0,1)) -
                Arrays.asList(COLUMNS).indexOf(king.getLocation().substring(0,1));

        String enemyColour = null;
        if (king.getColor().equals("white")) {
            enemyColour = "black";
        } else if (king.getColor().equals("black")) {
            enemyColour = "white";
        }

        if (rowDiff <= 1 && rowDiff >= -1 && colDiff <= 1 && colDiff >= -1) {
            return board.checkSquareEmpty(loc) ||
                    board.checkSquare(loc).getColor().equals(enemyColour);
        } else {
            return false;
        }
    }

    /**
     * Return True if the Board is currently facing a Checkmate scenario
     * by any of the opposing side's pieces for the side of the parameter <color>.
     *
     * @param board The Board object on which the Checkmate state will be determined
     * @param color The color of the side for which a Checkmate state will be checked for.
     * @return True if the Board is currently in a Checkmate state, otherwise False.
     */
    public boolean checkCheckmate(Board board, String color) {
        String kingLoc = findKing(board, color).getColor();
        boolean checkmate;

        for (int c = 0; c <= 7; c++) {
            for (int r = 0; r <= 7; r++) {
                String squareID = COLUMNS[c] + ROWS[r];
                checkmate = checkValidMove(board, squareID, kingLoc);
                if (checkmate) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Find the King piece within this Board and return it.
     *
     * @param board The Board object on which the King piece will be found.
     * @param color The color of the King that is to be found.
     * @return The King object on this Board.
     */
    private King findKing(Board board, String color) {
        for (int c = 0; c <= 7; c++) {
            for (int r = 0; r <= 7; r++) {
                String squareID = COLUMNS[c] + ROWS[r];
                Piece piece = board.checkSquare(squareID);
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return (King) piece;
                }

            }
        }
        return null;
    }

}
