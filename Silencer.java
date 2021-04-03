public class Silencer extends Mafia{
    //constructor
    public Silencer(String playerName) {
        super(playerName);
    }
    //methods
    static void Silence (Player silencedPlayer) {
        silencedPlayer.ifSilenced = true;
    }
}
