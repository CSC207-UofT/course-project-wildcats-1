package Entities;

import android.graphics.drawable.Drawable;


public class Piece {

    String color;
    String location;
    boolean eliminated;
    int image;

    public Piece(String color, String location) {
        this.color = color;
        this.location = location;
        this.eliminated = false;
    }

    public String getColor() { return color; }
    public String getLocation() { return location; }
    public boolean isEliminated() { return eliminated; }

    public void move(String location) {
        this.location = location;
    }
    public int getImage(){
        return image;
    }

    public void eliminate() {
        this.eliminated = true;
        this.location = "";
    }

}