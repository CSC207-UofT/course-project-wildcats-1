package Entities;
import com.wildcats.ultimatechess.R;

public class Knight extends Piece {

    /**
     * A new Knight object with its own color and unique location.
     *
     * @param color The color of this Knight Object.
     * @param location The location of this Knight object on the chessboard as a coordinate.
     */
    public Knight(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.knight_white;
        }
        else{
            this.image = R.drawable.knight_black;
        }
    }

    @Override
    /**
     * @return a deep copy of this Piece object.
     */
    Piece getCopy() {
        return new Knight(this.color, this.location);
    }

}