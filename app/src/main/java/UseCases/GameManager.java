package UseCases;

import java.util.ArrayList;

import Pieces.Board;
import Pieces.Piece;
import Database.User;
import Pieces.Queen;

public class GameManager {

    private Board board;
    private ArrayList<Piece> whitePiecesOut, blackPiecesOut;
    private User playerWhite, playerBlack;
    private boolean playerWhiteInTurn;
    private final String[] LETTER_COORDINATES;

    /**
     *
     * @param inputBoard The starting board containing all pieces needed to start the game.
     */
    public GameManager(Board inputBoard) {
        this.board = inputBoard;
        this.LETTER_COORDINATES = new String[]{"A", "B", "C", "D", "E", "F", "G", "H"};

    }

    public void startGame() {
//        Need to input players, place pieces, set white first

        this.playerWhiteInTurn = true;
    }

    public void setUpWhite(User white) {
        playerWhite = white;
    }

    public void setUpBlack(User black) {
        playerBlack = black;
    }

    public void endGame() {

    }

    public Board getBoard() {
        return board;
    }

    public void makeMove(String currSpot, String newSpot) {
        Piece movingPiece = board.checkSquare(currSpot);
        Object potentialPiece = board.checkSquare(newSpot);

        MoveChecker checker = new MoveChecker();
        Boolean condition1 = true;
        // Boolean condition1 = checker.checkValidMove(board, currSpot, newSpot);
        Boolean condition2 = true;
        // condition2 = !Player.isChecked
        if(condition1 && condition2){
            if(potentialPiece != null)
                board.removePiece(newSpot);
            board.addPiece(movingPiece, newSpot);
            board.removePiece(currSpot);
            if (board.getRowNum(newSpot) == 1 || board.getRowNum(newSpot) == 8){
                board.removePiece(newSpot);
                board.addPiece(new Queen("black", newSpot));
            }

        }

    }

    /**
     * @param pawn The pawn to be moved.
     * @param loc The location to which the move should be attempted.
     * @return True if the pawn move is possible, otherwise False.
     */


}