package Interfaces;

public class Move extends Document {

    private String gameId, code;
    private int number;

    public Move() {}

    public Move(String gameId, String code, int number) {
        this.gameId = gameId;
        this.code = code;
        this.number = number;
    }

    public String getGameId() { return gameId; }
    public String getCode() { return code; }
    public int getNumber() { return number; }

}