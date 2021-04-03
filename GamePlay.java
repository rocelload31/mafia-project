import java.util.Scanner;
//main class
public class GamePlay {
    //properties
    static String[] playerNames;
    static Player[] players;
    static boolean ifCreated = false;
    static boolean ifStarted = false;
    static boolean ifDay = true;
    static int numOfPlayers = 0;
    static int numOfAssignedRoles = 0;
    static int dayCounter = 0;
    static int nightCounter = 0;
    static int detectiveCounter = 0;
    static int indexOfKilled;
    static int numOfMafia = 0;
    static int numOfVillagers = 0;
    //methods
    static void checkWon () {
    }
    static void printNames () {
        for (Player p : players) {
            System.out.print(p.getName() + " ");
        }
    }
    static void printList () {
        for (Player p : players) {
            System.out.println(p.getName() + ": " + p.getClass().getSimpleName());
        }
    }
    static int findIndexByName (String name) {
        for (int i=0 ; i< players.length ; i++) {
            if (players[i].name.equals(name)) {
                return i;
            }
        }
        return -1;
    }
    static void assign_role (String name , String role) {
        int index = findIndexByName(name);
        if (index==-1) {
            System.out.println("user not found");
            return;
        }
        switch (role) {
            case "bulletproof":
                players[index] = new Bulletproof(players[index].name);
                numOfAssignedRoles++;
                break;
            case "detective":
                players[index] = new Detective(players[index].name);
                numOfAssignedRoles++;
                break;
            case "doctor":
                players[index] = new Doctor(players[index].name);
                numOfAssignedRoles++;
                break;
            case "godfather":
                players[index] = new GodFather(players[index].name);
                numOfAssignedRoles++;
                break;
            case "joker":
                players[index] = new Joker(players[index].name);
                numOfAssignedRoles++;
                break;
            case "silencer":
                players[index] = new Silencer(players[index].name);
                numOfAssignedRoles++;
                break;
            case "villager":
                players[index] = new Villager(players[index].name);
                numOfAssignedRoles++;
                break;
            case "mafia":
                players[index] = new Mafia(players[index].name);
                numOfAssignedRoles++;
                break;
            default:
                System.out.println("role not found");
        }
    }
    static void dayVote() {
        String voter;
        String votee;
        int indexOfVoter;
        int indexOfVotee;
        Scanner sc = new Scanner(System.in);
        voter = sc.next();
        do {
            indexOfVoter = findIndexByName(voter);
            votee = sc.next();
            indexOfVotee = findIndexByName(votee);
            if (indexOfVoter==-1 || indexOfVotee==-1) {
                System.out.println("user not found");
            }
            else if (players[indexOfVoter].ifSilenced) {
                System.out.println("voter is silenced");
                players[indexOfVoter].ifSilenced = false;
            }
            else if (players[indexOfVoter].ifDead) {
                System.out.println("voter already dead");
            }
            else if (players[indexOfVotee].ifDead) {
                System.out.println("votee already dead");
            }
            else {
                players[indexOfVotee].numOfVotes++;
            }
            voter = sc.next();
        }
        while (voter.equals("end_vote")==false);
    }
    static void nightVote () {
        int index = 0;
        //Mafia.votes[voterIndex] = players[voteeIndex].getName();
        for (int i=0 ; i<Mafia.votes.length ; i++) {
            /*
            for (String s : Mafia.votes) {
                System.out.print(s + " ");
            }*/
            index = findIndexByName(Mafia.votes[i]);
          //  System.out.println(index);
            if (index!=-1) {
                players[index].numOfVotes++;
            }
        }
    }
    static int findDead () { //in day returns the index of the dead person. If no one was ejected returns -1; if joker died returns -2;
        int maxVote = players[0].numOfVotes;
        int index;
        for (index=1 ; index<players.length ; index++) {
            if (players[index].numOfVotes > maxVote) {
                maxVote = players[index].numOfVotes;
            }
        }
        for (int i=0 ; i<players.length ; i++) {
            if (players[i].numOfVotes==maxVote) {
                for (int j=i+1 ; j<players.length ; j++) { //checks if more than one person is voted
                    if (players[j].numOfVotes==maxVote) {
                        if (ifDay) {
                            return -1;
                        }
                        else { //night
                            if (players[i].ifCured) {
                                return j;
                            }
                            else if (players[j].ifCured) {
                                return j;
                            }
                            else {
                                return -1; //no body died
                            }
                        }
                    }
                } //if the program reaches this line then one person is voted
                if (ifDay) {
                    if (players[i] instanceof Joker) {
                        return -2;
                    }
                    return i;
                }
                else
                { //night
                    if (players[i].ifCured) {
                        players[i].ifTriedToBEKilled = true;
                        return i; //mafia tried to kill i // i was not killed
                    }
                    else {
                        return i;
                    }
                }
            }
        }
        return -3;
    }
    static Player findSilenced () {
        String silenced;
        for (Player p : players) {
            if (p.ifSilenced) {
                return p;
            }
        }
        return null;
    }
    static void count () { //counts num of mafias and villagers
        for (Player p : players) {
            if (p instanceof Mafia) {
                numOfMafia++;
            }
            else if (p instanceof Villager){
                numOfVillagers++;
            }
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        //operations
        do {
            String opp = scanner.next();
            switch (opp) {
                //create a new game
                case "create_game":
                    ifCreated = true;
                    String test = scanner.nextLine();
                    playerNames = test.split(" ");
                    numOfPlayers = playerNames.length-1;
                    //creating an array of players
                    players = new Player[numOfPlayers];
                    for (int i=0 ; i<numOfPlayers ; i++) {
                        players[i] = new Player(playerNames[i+1]);
                    }
                    break;
                case "assign_role":
                    if (ifCreated==false) {
                        System.out.println("no game created");
                    }
                    else {
                        assign_role(scanner.next(), scanner.next());
                    }
                    break;
                case "start_game":
                    if (numOfPlayers>numOfAssignedRoles) {
                        System.out.println("one or more player do not have a role");
                        break;
                    }
                    if (ifCreated==false) {
                        System.out.println("no game created");
                        break;
                    }
                     if (ifStarted==true) {
                        System.out.println("game has already started");
                        break;
                    }
                    else {
                        ifStarted = true;
                        printList();
                    }
                    break;
                default:
                    System.out.println("not recognized!!!");
            }
            count();
            Mafia.votes = new String[players.length];
            while (ifStarted) {
                ////////////////////////////////////////////////////////////////////////////////////////////////day mode
                if (ifDay) {
                    dayCounter++;
                    System.out.println("Day " + dayCounter);
                    //daily report
                    if (nightCounter>0) {
                        switch (indexOfKilled) {
                            case -1:
                                System.out.println("nobody was killed");
                                break;
                            default:
                                System.out.println("mafia tried to kill " + players[indexOfKilled].getName());
                                if (players[indexOfKilled].ifTriedToBEKilled) {
                                    System.out.println(players[indexOfKilled].getName() + " was not killed");
                                } else {
                                    if (players[indexOfKilled] instanceof Mafia) {
                                        numOfMafia--;
                                    }
                                    else if (players[indexOfKilled] instanceof Villager) {
                                        numOfVillagers--;
                                    }
                                    System.out.println(players[indexOfKilled].getName() + " was killed");
                                }
                        }
                        if (findSilenced()!=null) {
                            System.out.println("Silenced " + findSilenced().getName());
                        }
                    }
                    //daily vote
                    dayVote();
                    int indexOfDead = findDead();
                    if (indexOfDead==-1) {
                        System.out.println("nobody died");
                    }
                    else if (indexOfDead==-2) {
                        System.out.println("Joker won!");
                        /////////////////end of the game
                    }
                    else {
                        players[indexOfDead].ifDead = true;
                        if (players[indexOfDead] instanceof Mafia) {
                            numOfMafia--;
                        }
                        else if (players[indexOfDead] instanceof Villager) {
                            numOfVillagers--;
                        }
                        System.out.println(players[indexOfDead].name + " died");
                    }
                    //making all of the votes zero
                    for (Player p : players) {
                        p.numOfVotes = 0;
                    }
                    ifDay = false;
                    for (Player p : players) {
                        p.ifSilenced = false;
                        p.ifTriedToBEKilled = false;
                    }
                }
                //////////////////////////////////////////////////////////////////////////////////////////////night mode
                else {
                    nightCounter++;
                    System.out.println("Night " + nightCounter);
                    //list of players needed to be awaken and alive
                    for (Player p : players) {
                        if (p.ifDead==false && (p instanceof Mafia|| p instanceof Doctor || p instanceof Detective || p instanceof Silencer)) {
                            System.out.println(p.getName() + ": " + p.getClass().getSimpleName());
                        }
                    }
                    //
                    int firstIndex = 0;
                    int secondIndex = 0;
                    int silencerCounter = 0;
                    String firstPlayer = scanner.next();
                    do {
                        firstIndex = findIndexByName(firstPlayer);
                        String secondPlayer = scanner.next();
                        secondIndex = findIndexByName(secondPlayer);
                        if (players[firstIndex].ifDead || players[secondIndex].ifDead) {
                            System.out.println("user is dead");
                        }
                        //process of voting in night
                        else if (players[firstIndex] instanceof Mafia) { /**/
                            if (players[firstIndex] instanceof Silencer) {
                                if (silencerCounter == 1 ) {
                                    Mafia.votes[firstIndex] = players[secondIndex].getName();
                                    /**/System.out.println("silencer night vote ok");
                                }
                            }
                            else {
                                Mafia.votes[firstIndex] = players[secondIndex].getName();
                                /**/ System.out.println("night vote ok");
                            }
                        }
                        //god awakes a villager
                        else {
                            switch (players[firstIndex].getClass().getSimpleName()) {
                                case "Doctor":
                                    Doctor.cure(players[secondIndex]);
                                    /**/System.out.println("cure vote ok");
                                    break;
                                case "Detective":
                                    if (detectiveCounter==1) {
                                        System.out.println("detective has already asked");
                                        break;
                                    }
                                    else {
                                        Detective.ask(players[secondIndex]);
                                    }
                                    /**/System.out.println("detect vote ok");
                                    break;
                                case "Silencer":
                                    Silencer.Silence(players[secondIndex]);
                                    silencerCounter++;
                                    /**/System.out.println("silence vote ok");
                                    break;
                                default:
                                    System.out.println("user can not wakeup during night");
                            }
                        }
                        ///////////////////
                        firstPlayer = scanner.next();
                    }
                    while (firstPlayer.equals("end_night")==false);
                    nightVote();
                    indexOfKilled = findDead();
                    //making all of the votes zero
                    for (Player p : players) {
                        p.numOfVotes = 0;
                    }
                    ifDay = true;
                    for (Player p : players) {
                        p.ifCured = false;
                        p.ifVoted = false;
                    }
                }
            }
        }
        while (true);
    }
}
