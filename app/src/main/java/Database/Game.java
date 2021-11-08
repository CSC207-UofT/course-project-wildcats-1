package Database;

public class Game {

    private final String playerWhiteID, playerBlackID;

    public Game(String playerWhiteID, String playerBlackID) {
        this.playerWhiteID = playerWhiteID;
        this.playerBlackID = playerBlackID;
    }

    public String getPlayerWhiteID() { return playerWhiteID; }
    public String getPlayerBlackID() { return playerBlackID; }

}