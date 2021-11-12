package Entities;
import com.wildcats.ultimatechess.R;

public class Pawn extends Piece {

    boolean firstMove;
    boolean movedTwo;

    public Pawn(String color, String location) {
        super(color, location);
        this.firstMove = true;
        this.movedTwo = false;
        if (this.color.equals("White")){
            this.image = R.drawable.pawn_white;
        }
        else{
            this.image = R.drawable.pawn_black;
        }

    }

}