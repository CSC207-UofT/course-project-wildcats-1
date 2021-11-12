package Entities;
import com.wildcats.ultimatechess.R;

public class Knight extends Piece {

    public Knight(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.knight_white;
        }
        else{
            this.image = R.drawable.knight_black;
        }
    }

}