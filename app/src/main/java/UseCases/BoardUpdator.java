package UseCases;

import Controllers.PieceLayoutManager;
import Entities.Board;
import Entities.Piece;

public class BoardUpdator {
    private Piece piece;
    final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    final char[] nums = {'1', '2', '3', '4', '5', '6', '7', '8'};

    public void updateBoard(Board board, PieceLayoutManager boardLayout){
        for (int i=0; i<8; i++){
            for (int k=0; k<8; k++){
                piece = board.checkSquare(""+letters[i]+nums[7-k]);
                if (piece!=null){
                    boardLayout.editImageAt(i,k, piece.getImage());
                }
            }
        }
    }
}
