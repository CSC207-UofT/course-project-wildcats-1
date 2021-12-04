package Entities;
import com.wildcats.ultimatechess.R;

public class Queen extends Piece {

    /**
     * A new Queen object with its own color and unique location.
     *
     * @param color The color of this Queen Object.
     * @param location The location of this Queen object on the chessboard as a coordinate.
     */
    public Queen(String color, String location) {
        super(color, location);
        if (this.color.equals("White")) {
            this.image = R.drawable.queen_white;
        } else {
            this.image = R.drawable.queen_black;
        }

    }
    @Override
    /**
     * @return a deep copy of this Piece object.
     */
    Piece getCopy() {
        return new Queen(this.color, this.location);
    }


}