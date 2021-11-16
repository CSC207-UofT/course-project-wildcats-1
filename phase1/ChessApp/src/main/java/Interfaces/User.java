package Interfaces;

public class User extends Document {

    private String name, password;
    private int numGamesWon, numGamesLost;

    public User() {}

    public User(String name, String password, int numGamesWon, int numGamesLost) {
        this.name = name;
        this.password = password;
        this.numGamesWon = numGamesWon;
        this.numGamesLost = numGamesLost;
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public int getNumGamesWon() { return numGamesWon; }
    public int getNumGamesLost() { return numGamesLost; }

    public static class UserRef extends Document {
        private String userID;
        public UserRef() {}
        public UserRef(String userID) { this.userID = userID; }
        public String getUserID() { return userID; }
    }

}