package Entities;
import com.wildcats.ultimatechess.R;

public class Rook extends Piece {

    public Rook(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.rook_white;
        }
        else{
            this.image = R.drawable.rook_black;
        }
    }

}