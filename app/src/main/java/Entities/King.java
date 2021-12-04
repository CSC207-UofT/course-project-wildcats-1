package Entities;
import com.wildcats.ultimatechess.R;

public class King extends Piece {

    /**
     * A Boolean representing whether or not this King has attempted to castle.
     */
    boolean castled;

    /**
     * A new King object with its own color and unique location.
     *
     * @param color The color of this King Object.
     * @param location The location of this King object on the chessboard as a coordinate.
     */
    public King(String color, String location) {
        super(color, location);
        if (this.color.equals("White")){
            this.image = R.drawable.king_white;
        }
        else{
            this.image = R.drawable.king_black;
        }
    }

    @Override
    /**
     * @return a deep copy of this Piece object.
     */
    Piece getCopy() {
        return new King(this.color, this.location);
    }

    /**
     * Mutate the unmoved attribute as false, to indicate that this Pawn object has moved.
     */
    public void moved() {
        this.unmoved = false;
    }

}