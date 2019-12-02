package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Game {
    
    public static final String ANSI_RESET = "\033[0m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static final String ANSI_RED = "\033[0;31m";
    
    
    private static final int INIT_HAND_CARDS = 10;
    private static final int TOTAL_PLAYS_NUMBER = INIT_HAND_CARDS;
    //private static final int INIT_SHUFFLE_PERMUTATIONS = 100; //TODO?
    
    private ArrayList<Player> players;
    
    public Table table;
    
    
    
    public Game(ArrayList<Player> players) {
        table = new Table(players);
        this.players = players;
        for (Player p : players)
            p.joinGame(this);
    }
    
    
    
    
    private void finishRound(Player winner) {
        System.out.println(ANSI_RED + " ####### CONGRATULATIONS, the player " + winner + " has won this round ###### " + ANSI_RESET);
        System.exit(0); //TODO
    }
    
    
    
    
    private Map.Entry<Player, Cards> checkWonPlay() {
        Player jugadorGanador = null;
        
        ArrayList<Map.Entry<Player, Cards>> plays = new ArrayList<>(table.getPlayedCards().entrySet());
        for (Map.Entry<Player, Cards> entry : plays) {
            if (jugadorGanador == null) {
                jugadorGanador = entry.getKey();
                continue;
            }
            if (table.getPlayedCards().get(jugadorGanador).getSuit().equals(entry.getValue().getSuit())) {
                if (table.getPlayedCards().get(jugadorGanador).getNumber().compareTo(entry.getValue().getNumber()) < 0) {
                    jugadorGanador = entry.getKey();
                }
            } else {
                if (entry.getValue().getSuit().equals(table.getTriunfo().getSuit())) {
                    jugadorGanador = entry.getKey();
                }
            }
        }
        
        for (Map.Entry<Player, Cards> entry : table.getPlayedCards().entrySet()) {
            if (entry.getKey().equals(jugadorGanador))
                return entry;
        }
        return null;
    }
    
    
    
    
    private void initialDeal() {
        for (int i = 0; i < INIT_HAND_CARDS; i++) {
            for (int j = 0; j < players.size(); j++) {
                dealCard(players.get(j), (i == INIT_HAND_CARDS - 1 && j == players.size() - 1));//If is the last deal to the last player, add the Triunfo
            }
        }
    }
    
    
    
    private Cards dealCard(Player player, boolean Triunfo) {
        Cards dealingCard = table.getDeck().get(0);
        player.receiveCard(dealingCard);
        if (Triunfo) {
            table.setTriunfo(dealingCard);
        }
        table.getDeck().remove(0);
        return dealingCard;
    }
    
    
    
    
    private Cards askForCard(Player player) {
        return player.playCard();
    }

    
    
    
    private ArrayList<Cards> askForSing(Player player) {
        return player.sing();
    }
    
    
    
    
    public void startGame() {

        initialDeal();

        System.out.println("The TRIUNFO is [" + table.getTriunfo() + "]");
        System.out.println();

        for (int i = 1; i <= TOTAL_PLAYS_NUMBER; i++) {

            System.out.println("#######################################################################");
            System.out.println("########################## PLAY " + i + " ####################################" + ((i < 10) ? "#" : ""));
            System.out.println("#######################################################################");

            System.out.println("HANDS IN THE " + i + "º PLAY:");
            players.forEach((p) -> {
                System.out.print(p + "\t - \t");
                ArrayList<Cards> playableCards = p.checkPlayableCards();
                System.out.println(((p instanceof Human) ? ANSI_BLUE : "") + playableCards + ((p instanceof Human) ? ANSI_RESET : ""));
            });
            System.out.println();
            System.out.println(" -> Asking for cards...");

            System.out.println("CURRENT TRICK IN THE " + i + "º PLAY:");
            players.forEach((p) -> {
                System.out.print(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t");
                table.getPlayedCards().put(p, askForCard(p));
                System.out.print((p instanceof Human) ? ANSI_RESET : table.getPlayedCards().get(p) + "\n");
            });
            System.out.println();
            System.out.println(" -> Checking won trick player...");

            Map.Entry<Player, Cards> wonPlay = checkWonPlay();

            System.out.println("The player " + wonPlay.getKey() + " has won the play with the card [" + wonPlay.getValue() + "]");
            System.out.println();
            
            wonPlay.getKey().addPoints(Cards.calculatePoints(new ArrayList<>(table.getPlayedCards().values())));

            table.addTrick(wonPlay.getKey());

            System.out.println(" -> Asking the player " + wonPlay.getKey() + " for a sing");

            ArrayList<Cards> sing = askForSing(wonPlay.getKey());

            if (!sing.isEmpty()) {
                if (sing.size() == 4) {
                    System.out.println("The player " + wonPlay.getKey() + " has sung TUTE with the cards " + sing);
                    finishRound(wonPlay.getKey());
                }
                if (sing.get(0).getSuit().equals(table.getTriunfo().getSuit())) {
                    System.out.println("The player " + wonPlay.getKey() + " has sung the 40s with the cards " + sing);
                    wonPlay.getKey().addPoints(40);
                } else {
                    System.out.println("The player " + wonPlay.getKey() + " has sung the 20s with the cards " + sing);
                    wonPlay.getKey().addPoints(20);
                }//TODO cantar Tute (gana la partida directamente)
            }
            System.out.println();

            System.out.println("CURRENT POINTS IN THE " + i + "º PLAY:");
            ArrayList<Player> sortedPlayers = new ArrayList<>(players);
            sortedPlayers.sort(playersByPoints);
            sortedPlayers.forEach((Player p) -> {
                System.out.println(((p instanceof Human) ? ANSI_BLUE : "") + p + "\t - \t" + p.getPoints() + ((p instanceof Human) ? ANSI_RESET : ""));
            });
            System.out.println();

            Utils.rotatePlayerArray(players, wonPlay.getKey()); //put the won player as the first one to start the next play
            table.removeCurrentPlay();
        }

        players.sort(playersByPoints);

        finishRound(players.get(0));

    }


    
    
    public static void main(String[] args) {
        
        System.out.println("\033[0;31m" + "RED COLORED" +
"\033[0m" + " NORMAL");

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(
                new Human("Sergio"),
                new Player("Marcos"),
                new Player("Roi"),
                new Player("Pablo")
        ));

        Game game = new Game(players);
        game.startGame();

    }
    
    
    
    
    private final static Comparator playersByPoints = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            if (o1.getPoints() > o2.getPoints()) {
                return -1;
            } else if (o1.getPoints() < o2.getPoints()) {
                return 1;
            } else {
                return 0;
            }
        }
    };

}