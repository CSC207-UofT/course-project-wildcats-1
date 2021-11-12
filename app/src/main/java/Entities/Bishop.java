package Entities;
import com.wildcats.ultimatechess.R;

public class Bishop extends Piece {

    public Bishop(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.bishop_white;
        }
        else{
            this.image = R.drawable.bishop_black;
        }
    }

}