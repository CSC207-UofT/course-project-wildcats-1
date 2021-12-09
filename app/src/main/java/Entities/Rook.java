package Entities;
import com.wildcats.ultimatechess.R;

public class Rook extends Piece {

    /**
     * A new Rook object with its own color and unique location.
     *
     * @param color The color of this Rook Object.
     * @param location The location of this Rook object on the chessboard as a coordinate.
     */
    public Rook(String color, String location) {
        super(color, location);
        if (this.color.equals("White")) {
            this.image = R.drawable.rook_white;
        } else {
            this.image = R.drawable.rook_black;
        }
    }

    @Override
    /**
     * @return a deep copy of this Piece object.
     */
    Piece getCopy() {
        return new Rook(this.color, this.location);
    }

}