package UseCases;

import android.app.Activity;
import android.content.Context;

import Entities.Board;

public class MoveBuffer {
    private Activity activity;
    private String click1;
    private String click2;
    private BoardManager boardManager = new BoardManager();
    private String lastMove;

    public MoveBuffer(Context context){
        this.activity = (Activity) context;
    }
    public String addClick(String cord, Board board){
        if (this.click1==null && !board.checkSquareEmpty(cord)) {
                this.click1 = cord;
                System.out.println("CLICK 1: " + this.click1);
        }
        else if (this.click1 != null && !this.click1.equals(cord) &&
                boardManager.checkValidMove(board, this.click1, cord)) {
               this.click2 = cord;
               System.out.println("CLICK 2: " + this.click2);

               lastMove = this.click1 + this.click2;
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
}
