package Entities;
import com.wildcats.ultimatechess.R;

public class King extends Piece {

    public King(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.king_white;
        }
        else{
            this.image = R.drawable.king_black;
        }
    }

}