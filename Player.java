public class Player {
    //properties
    String name;
    boolean ifDead = false;
    boolean ifSilenced = false;
    boolean ifCured = false;
    boolean ifTriedToBEKilled = false;
    boolean ifVoted = false;
    boolean ifBulletUsed = false;
    int numOfVotes = 0;
    //constructor
    public Player (String playerName) {
        name = playerName;
        ifDead = false;
    }
    //getters
    public String getName() {
        return name;
    }
    //setters
    public void setIfDead(boolean ifDead) {
        this.ifDead = ifDead;
    }
    public void setName(String name) {
        this.name = name;
    }
}
