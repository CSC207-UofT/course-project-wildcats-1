package Database;

public class Move {

    private final String color, code;

    public Move(String color, String code) {
        this.color = color;
        this.code = code;
    }

    public String getColor() { return color; }
    public String getCode() { return code; }

}