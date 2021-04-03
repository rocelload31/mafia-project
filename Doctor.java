public class Doctor extends Villager {
    //constructor
    public Doctor(String playerName) {
        super(playerName);
    }
    //methods
    static void cure (Player patient) {
        patient.ifCured = true;
    }
}
