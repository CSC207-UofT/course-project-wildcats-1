package UseCases;

import java.util.ArrayList;

import Entities.Board;
import Entities.Piece;
import Interfaces.User;

public class GameManager {

    private Board board;
    private ArrayList<Piece> whitePiecesOut, blackPiecesOut;
    private User playerWhite, playerBlack;
    private boolean playerWhiteInTurn;

    /**
     *
     * @param startBoard The starting board containing all pieces needed to start the game.
     */
    public GameManager(Board startBoard) {
        this.board = startBoard;
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

    /**
     * Moves a piece from startLoc to targetLoc and changes the turn to the opposing player.
     *
     * If a piece is removed, it is added to either whitePiecesOut or blackPiecesOut.
     *
     * @param startLoc  The starting location of the piece being moved.
     * @param targetLoc The target location of the piece being moved.
     */
    public void makeMove(String startLoc, String targetLoc) {
        if(!board.checkSquareEmpty(targetLoc)){
            Piece removedPiece = board.removePiece(targetLoc);
            if (removedPiece.getColor().equals("White")){
                this.whitePiecesOut.add(removedPiece);
            }else{
                this.blackPiecesOut.add(removedPiece);
            }
        }
        board.movePiece(startLoc, targetLoc);
        if(this.playerWhiteInTurn){
            this.playerWhiteInTurn = false;
        }else{
            this.playerWhiteInTurn = true;
        }
    }

}