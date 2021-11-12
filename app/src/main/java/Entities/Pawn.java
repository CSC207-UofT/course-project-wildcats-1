package Entities;
import com.wildcats.ultimatechess.R;


public class Pawn extends Piece {

    /**
     * A Boolean representing whether or not a Pawn object has made a 2 unit move.
     */
    boolean movedTwo;

    /**
     * A new Pawn object with its own color and unique location.
     *
     * @param color The color of this Pawn Object.
     * @param location The location of this Pawn object on the chessboard as a coordinate.
     */
    public Pawn(String color, String location) {
        super(color, location);
        this.movedTwo = false;
        if (this.color.equals("White")){
            this.image = R.drawable.pawn_white;
        }
        else{
            this.image = R.drawable.pawn_black;
        }
    }

    /** Return True if this Pawn object has moved two units, otherwise return False.
     * @return whether this Pawn object has moved two units.
     */
    public boolean getMovedTwice() {
        return this.movedTwo;
    }

    /**
     * Mutate the unmoved attribute as false, to indicate that this Pawn object has moved.
     */
    public void moved() {
        this.unmoved = false;
    }

}