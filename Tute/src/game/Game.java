package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author VigoCoffeeLovers
 */
public class Game {

    private Table table;

    public Game(ArrayList<Player> players) {
        table = new Table(players);
    }

    public void start() {
        table.startGame();
    }

    public static void main(String[] args) {

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(
                new Human("Sergio"),
                new Player("Marcos"),
                new Player("Roi"),
                new Player("Pablo")
        ));

        Game game = new Game(players);
        game.start();

    }

}

class Human extends Player {

    public Human(String name) {
        super(name);
    }

    @Override
    public Cards playCard(Table game) { //TODO mostrar las cartas que se jugaron antes, antes de pedir carta al humano

        Cards chosenCard = null;
        String choosenNameCard = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        do {

            try {
                choosenNameCard = reader.readLine();
            } catch (IOException ex) {
                ex.printStackTrace();
                System.exit(1);
            }
            try {
                chosenCard = Cards.valueOf(choosenNameCard);
                if (!hand.contains(chosenCard)) {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.err.println("*The card [" + choosenNameCard + "] does not exist in your hand, try again");
                System.out.print(" - ");
                chosenCard = null;
            }
        } while (chosenCard == null);

        hand.remove(chosenCard);

        return chosenCard;
    }
}
