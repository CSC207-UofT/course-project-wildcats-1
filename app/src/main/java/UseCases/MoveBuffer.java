package UseCases;

import android.content.Context;

import Controllers.MoveBufferInterface;
import Entities.Board;

public class MoveBuffer implements MoveBufferInterface {
    private String click1;
    private String click2;
    private final BoardManager boardManager = new BoardManager();

    public MoveBuffer(Context context){
    }
    public String addClick(String cord, Board board){
        if (this.click1==null && !board.checkSquareEmpty(cord)) {
                this.click1 = cord;
                System.out.println("CLICK 1: " + this.click1);
        }
        else if (this.click1 != null && !this.click1.equals(cord) &&
                boardManager.checkMoveChecker(board, this.click1, cord)) {
               this.click2 = cord;
               System.out.println("CLICK 2: " + this.click2);

            String lastMove = this.click1 + this.click2;
               this.click1=null;
               this.click2=null;
               return lastMove;
           }
        else {
            this.click1=null;
            this.click2=null;
           }

        return null;
    }

    @Override
    public String getClick1() {
        return this.click1;
    }
}
