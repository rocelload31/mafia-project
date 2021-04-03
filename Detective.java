public class Detective extends Villager {
    //constructor
    public Detective(String playerName) {
        super(playerName);
    }
    //methods
    static void ask (Player suspect) {
        if (suspect instanceof GodFather) {
            System.out.println("No");
        }
        else {
            System.out.println("Yes");
        }
    }
}
