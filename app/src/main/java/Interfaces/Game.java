package Interfaces;

public class Game extends Document {

    private String playerWhiteID, playerBlackID;

    public Game() {}

    public Game(String playerWhiteID, String playerBlackID) {
        this.playerWhiteID = playerWhiteID;
        this.playerBlackID = playerBlackID;
    }

    public String getPlayerWhiteID() { return playerWhiteID; }
    public String getPlayerBlackID() { return playerBlackID; }

}