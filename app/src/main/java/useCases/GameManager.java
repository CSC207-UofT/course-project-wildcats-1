package useCases;

import java.util.ArrayList;

import Entities.Board;
import Entities.Piece;
import Entities.User;

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

    public void makeMove(Piece piece, String location) {

    }

    /**
     * @param pawn The pawn to be moved.
     * @param loc The location to which the move should be attempted.
     * @return True if the pawn move is possible, otherwise False.
     */


}