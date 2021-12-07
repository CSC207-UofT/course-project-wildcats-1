package UseCases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import Entities.*;

public class MoveChecker {

    /**
     * Letters representing the chess board columns.
     */
    private final String[] COLUMNS =
            new String[]{"a", "b", "c", "d", "e", "f", "g", "h"};
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
        MoveChecker checker = new MoveChecker();

        if (piece instanceof Pawn) {
            return checker.checkValidPawnMove(board, (Pawn) piece, loc2);
        } else if (piece instanceof Bishop) {
            return checker.checkValidBishopMove(board, piece, loc2);
        } else if (piece instanceof Rook) {
            return checker.checkValidRookMove(board, piece, loc2);
        } else if (piece instanceof Knight) {
            return checker.checkValidKnightMove(board, (Knight) piece, loc2);
        } else if (piece instanceof Queen) {
            return checker.checkValidQueenMove(board, (Queen) piece, loc2);
        } else if (piece instanceof King) {
            return checker.checkValidKingMove(board, (King) piece, loc2);
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

        // integer of the difference between the column you're at, from the column you're going to.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0, 1)) -
                Arrays.asList(COLUMNS).indexOf(pawn.getLocation().substring(0, 1));

        // The difference between the row you're at, from the row you're going to.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(pawn.getLocation().substring(1));

        // True if only the pawn is White
        boolean whiteTurn = pawn.getColor().equals("White");

        // True if only the pawn is Black
        boolean blackTurn = pawn.getColor().equals("Black");

        // Board state after the piece move
        Board changedBoard = new Board();
        changedBoard.setBoard(board.getBoard());
        changedBoard.movePiece(pawn.getLocation(), loc);
        Piece toUpdate = changedBoard.checkSquare(loc);
        System.out.println(toUpdate.getClass().getName());
        toUpdate.move(loc);

        // True if the side belonging to the piece is in Checkmate after the move
//        boolean checked = checkChecked(changedBoard, pawn.getColor());

        // move 1 forward
        if (colDiff == 0 && rowDiff == 1 && whiteTurn && board.checkSquareEmpty(loc)) {
            return !checkChecked(changedBoard, pawn.getColor());
        }
        // move 1 forward
        else if (colDiff == 0 && rowDiff == -1 && blackTurn && board.checkSquareEmpty(loc)) {
            return !checkChecked(changedBoard, pawn.getColor());
        }
        // move 2 forward
        else if (colDiff == 0 && rowDiff == 2 && whiteTurn &&
                pawn.getPlayStatus() && board.checkSquareEmpty(loc)) {
            String squareForward = loc.substring(0, 1) + 3;
            return board.checkSquareEmpty(squareForward) &&
                    !checkChecked(changedBoard, pawn.getColor());
        }
        // move 2 forward
        else if (colDiff == 0 && rowDiff == -2 && blackTurn &&
                pawn.getPlayStatus() && board.checkSquareEmpty(loc)) {
            String squareForward = loc.substring(0, 1) + 6;
            return board.checkSquareEmpty(squareForward) &&
                    !checkChecked(changedBoard, pawn.getColor());
        }
        //  checking en passant & attack
        else if (Math.abs(colDiff) == 1 && rowDiff == 1 && whiteTurn) {
            boolean empty = board.checkSquareEmpty(loc);

            // en passant
            if (empty) {
                String enemy_loc = loc.charAt(0) + pawn.getLocation().substring(1);
                Piece poss_pawn = board.checkSquare(enemy_loc);
                return (poss_pawn instanceof Pawn && poss_pawn.getColor().equals("Black") &&
                        ((Pawn) poss_pawn).getMovedTwice() &&
                        !checkChecked(changedBoard, pawn.getColor())&&
                        !((Pawn) poss_pawn).isPromoted());
            }
            // attack
            else {
                Piece poss_enemy = board.checkSquare(loc);
                return (poss_enemy.getColor().equals("Black") &&
                        !checkChecked(changedBoard, pawn.getColor()));
            }
        }
        //  checking attack & en passant
        else if (Math.abs(colDiff) == 1 && rowDiff == -1 && blackTurn) {
            boolean empty = board.checkSquareEmpty(loc);

            // en passant
            if (empty) {
                String enemy_loc = loc.charAt(0) + pawn.getLocation().substring(1);
                Piece poss_pawn = board.checkSquare(enemy_loc);
                return (poss_pawn instanceof Pawn && poss_pawn.getColor().equals("White") &&
                        ((Pawn) poss_pawn).getMovedTwice() &&
                        !checkChecked(changedBoard, pawn.getColor()));
            }
            // attack
            else {
                Piece poss_enemy = board.checkSquare(loc);
                return (poss_enemy.getColor().equals("White") &&
                        !checkChecked(changedBoard, pawn.getColor()));
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
        if (knight.getColor().equals("White")) {
            enemyColour = "Black";
        } else if (knight.getColor().equals("Black")) {
            enemyColour = "White";
        }

        // Board state after the piece move
        Board changedBoard = new Board();
        changedBoard.setBoard(board.getBoard());
        changedBoard.movePiece(knight.getLocation(), loc);
        Piece toUpdate = changedBoard.checkSquare(loc);
        System.out.println(toUpdate.getClass().getName());
        toUpdate.move(loc);

        // True if the side belonging to the piece is in Checkmate after the move
//        boolean checked = checkChecked(changedBoard, knight.getColor());

        // When the move attempted is L shaped from the origin
        if ((Math.abs(rowDiff) == 2 && Math.abs(colDiff) == 1) ||
                (Math.abs(rowDiff) == 1 && Math.abs(colDiff) == 2)) {
            if (board.checkSquareEmpty(loc)) {
                return !checkChecked(changedBoard, knight.getColor());
            } else {
                return board.checkSquare(loc).getColor().equals(enemyColour) &&
                        !checkChecked(changedBoard, knight.getColor());
            }
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
        if (piece.getColor().equals("White")) {
            enemyColour = "Black";
        } else if (piece.getColor().equals("Black")) {
            enemyColour = "White";
        }

        // Board state after the piece move
        Board changedBoard = new Board();
        changedBoard.setBoard(board.getBoard());
        changedBoard.movePiece(piece.getLocation(), loc);
        Piece toUpdate = changedBoard.checkSquare(loc);
        System.out.println(toUpdate.getClass().getName());
        toUpdate.move(loc);


        // True if the side belonging to the piece is in Checkmate after the move
//        boolean checked = checkChecked(changedBoard, piece.getColor());

        // When the horizontal & vertical shift are equal in magnitude.
        if (Math.abs(colDiff) == Math.abs(rowDiff)) {
            int col =
                    Arrays.asList(COLUMNS).indexOf(piece.getLocation().substring(0,1));
            int row = Integer.parseInt(piece.getLocation().substring(1));

            boolean emptySquare;
            // check every horizontal square in front of the Bishop until the destination loc.

            for (int i = 0; i < Math.abs(rowDiff); i++) {
                if (rowDiff > 0) {
                    row += 1;
                } else if (rowDiff < 0) {
                    row -= 1;
                }
                if (colDiff > 0) {
                    col += 1;
                } else if (colDiff < 0) {
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
                    return board.checkSquare(loc).getColor().equals(enemyColour) &&
                            !checkChecked(changedBoard, piece.getColor());
                }


            }
            return !checkChecked(changedBoard, piece.getColor());
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

        if (piece.getColor().equals("White")) {
            enemyColour = "Black";
        } else if (piece.getColor().equals("Black")) {
            enemyColour = "White";
        }

        // Board state after the piece move
        Board changedBoard = new Board();
        changedBoard.setBoard(board.getBoard());
        changedBoard.movePiece(piece.getLocation(), loc);
        Piece toUpdate = changedBoard.checkSquare(loc);
        toUpdate.move(loc);

        // True if the side belonging to the piece is in Checkmate after the move
//        boolean checked = checkChecked(changedBoard, piece.getColor());

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
                    return board.checkSquare(loc).getColor().equals(enemyColour) &&
                            !checkChecked(changedBoard, piece.getColor());
                }
            }
            return !checkChecked(changedBoard, piece.getColor());
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
                    return board.checkSquare(loc).getColor().equals(enemyColour) &&
                            !checkChecked(changedBoard, piece.getColor());
                }
            }
            return !checkChecked(changedBoard, piece.getColor());
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
    public boolean checkValidQueenMove (Board board, Piece queen, String loc) {
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
        // The difference between the row you're going to, and the row you're at.
        int rowDiff = Integer.parseInt(loc.substring(1)) -
                Integer.parseInt(king.getLocation().substring(1));

        // The difference between the column you're going to, and the column you're at.
        int colDiff = Arrays.asList(COLUMNS).indexOf(loc.substring(0,1)) -
                Arrays.asList(COLUMNS).indexOf(king.getLocation().substring(0,1));

        String enemyColour = null;
        if (king.getColor().equals("White")) {
            enemyColour = "Black";
        } else if (king.getColor().equals("Black")) {
            enemyColour = "White";
        }

        // Board state after the piece move
        Board changedBoardOne = new Board();
        changedBoardOne.setBoard(board.getBoard());
        changedBoardOne.movePiece(king.getLocation(), loc);
        Piece toUpdate = changedBoardOne.checkSquare(loc);
        System.out.println(toUpdate.getClass().getName());
        toUpdate.move(loc);


        // True if the side belonging to the piece is in Checkmate after the move
//        boolean checked = checkChecked(changedBoard, king.getColor());

        if (rowDiff <= 1 && rowDiff >= -1 && colDiff <= 1 && colDiff >= -1) {
            if (board.checkSquareEmpty(loc)) {
                return !checkChecked(changedBoardOne, king.getColor());
            } else {
                return board.checkSquare(loc).getColor().equals(enemyColour) &&
                        !checkChecked(changedBoardOne, king.getColor());
            }
        } else if (rowDiff == 0 && Math.abs(colDiff) == 2) {
            // will store the column value of rook to be castled
            int col =
                    Arrays.asList(COLUMNS).indexOf(king.getLocation().substring(0,1));
            String row = loc.substring(1);
            // the rook that will be moved to produce the final board state
            Rook rook;
            // the original rook on the board
            Rook rookOriginal;
            if (colDiff < 0) {
                col -= 1;
                rook = (Rook) changedBoardOne.checkSquare(COLUMNS[0] + row);
                rookOriginal = (Rook) board.checkSquare(COLUMNS[0] + row);
            } else {
                col += 1;
                rook = (Rook) changedBoardOne.checkSquare(COLUMNS[7] + row);
                rookOriginal = (Rook) board.checkSquare(COLUMNS[7] + row);
            }
            // rook is not in expected location (has moved already)
            if (rook == null) {
                return false;
            }

            // square one unit away, between king's original loc and loc
            String squareForward = COLUMNS[col] + row;

            // Board state after the king move one unit horizontally
            Board changedBoardTwo = new Board();
            changedBoardTwo.setBoard(board.getBoard());
            changedBoardTwo.movePiece(king.getLocation(), squareForward);
            Piece toUpdateTwo = changedBoardTwo.checkSquare(squareForward);
            toUpdateTwo.move(squareForward);

            // Board state after moving rook to complete castling
            changedBoardOne.movePiece(rook.getLocation(), squareForward);
            rook.move(squareForward);

            return checkValidRookMove(board, rookOriginal, squareForward) &&
                    rookOriginal.getPlayStatus() && king.getPlayStatus() &&
                    !checkChecked(board, king.getColor()) &&
                    !checkChecked(changedBoardTwo, king.getColor()) &&
                    !checkChecked(changedBoardOne, king.getColor());

        } else {
            return false;
        }
    }

    /**
     * Return True if the Board is currently facing a Checkmate scenario
     * by any of the opposing side's pieces for the side of the parameter <color>.
     *
     * @param board The Board object on which the Checked state will be determined
     * @param color The Color of the side for which a Checked state will be checked for.
     * @return True if the Board is currently in a Checked state, otherwise False.
     */
    public boolean checkChecked(Board board, String color) {
        String kingLoc;
        if ((findKing(board, color))!=null) {
            kingLoc = (Objects.requireNonNull(findKing(board, color))).getLocation();
        } else {
            return true;
        }

        boolean checkmate = false;

        for (int c = 0; c <= 7; c++) {
            for (int r = 0; r <= 7; r++) {
                String squareID = COLUMNS[c] + ROWS[r];
                if (!board.checkSquareEmpty(squareID) &&
                        !board.checkSquare(squareID).getColor().equals(color)) {
                    checkmate = checkValidMove(board, squareID, kingLoc);
                }
                if (checkmate) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Find the King piece within this Board that belongs to the player with <color> and return it.
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
    

    /**
     * Return True if there exists at least one piece for the player of <color> than can move
     *
     * @param board The Board object on which a possible piece move will be calculated.
     * @param color The color of Player's pieces to be analyzed for possible moves.
     * @return True if the player of <color> has a single legal move.
     */
    public boolean checkNoMoves(Board board, String color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        ArrayList<String> squares = new ArrayList<>();
        String squareID;

        for (int c = 0; c <= 7; c++) {
            for (int r = 0; r <= 7; r++) {
                squareID = COLUMNS[c] + ROWS[r];
                squares.add(squareID);
                Piece piece = board.checkSquare(squareID);
                if (piece != null && piece.getColor().equals(color)) {
                    pieces.add(piece);
                }
            }
        }
        for (int i = 0; i < pieces.size(); i ++) {
            Piece piece = pieces.get(i);
            for (int s = 0; s < squares.size(); s++) {
                squareID = squares.get(s);
                if (checkValidMove(board, piece.getLocation(), squareID)) {
                    return false;
                }
            }
        }
        return true;
    }

}
