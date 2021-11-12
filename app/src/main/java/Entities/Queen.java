package Entities;
import com.wildcats.ultimatechess.R;

public class Queen extends Piece {

    public Queen(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.queen_white;
        }
        else{
            this.image = R.drawable.queen_black;
        }
    }

}