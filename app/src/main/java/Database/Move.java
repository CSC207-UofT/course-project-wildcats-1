package Database;

public class Move extends Document {

    private String color, code;

    public Move() {}

    public Move(String color, String code) {
        this.color = color;
        this.code = code;
    }

    public String getColor() { return color; }
    public String getCode() { return code; }

}