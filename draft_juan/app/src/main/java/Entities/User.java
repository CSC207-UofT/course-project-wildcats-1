package Entities;

public class User {

    private String id;
    private int gamesWon;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getGamesWon() {
        return gamesWon;
    }

}