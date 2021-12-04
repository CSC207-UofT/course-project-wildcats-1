package UseCases;

import Controllers.PieceLayoutManager;
import Entities.Board;
import Entities.Piece;

/**
 * Class used to update the layout displayed on the screen based on the current state of the board 
 * class.
 */

public class BoardUpdator {
    final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    final char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8'};

    /**
     * Check whether or not a specific move can be made from a piece at one location on the board,
     * to another different one
     *
     * @param board The Board object whose state will be copied into the layout
     * @param boardLayout  The Layout manager that contains the images displayed on screen.
     */
    public void updateBoard(Board board, PieceLayoutManager boardLayout){
        for (int i=0; i<8; i++){
            for (int k=0; k<8; k++){
                Piece piece = board.checkSquare("" + letters[i] + nums[7 - k]);
                if (piece !=null){
                    boardLayout.editImageAt(i,k, piece.getImage());
                }
                else{
                    boardLayout.editImageAt(i,k, 0);
                }
            }
        }
    }
}
