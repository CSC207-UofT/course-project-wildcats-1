package Entities;
import com.wildcats.ultimatechess.R;


public class Bishop extends Piece {

    /**
     * A new Bishop object with its own color and unique location.
     *
     * @param color The color of this Bishop Object.
     * @param location The location of this Bishop object on the chessboard as a coordinate.
     */
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