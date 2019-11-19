package game;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class creates a Tute player.
 * @author VigoCoffeeLovers
 */
public class Player {
    
    String name;
    ArrayList<Cards> hand = new ArrayList<>();
    int points;
    ArrayList<Suits> sings = new ArrayList<>();
    
    
    /**
     * Creates a player ready to play a Tute match.
     * @param name Name of the player. It will work as an identifier for him. //TODO create a number id?
     */
    public Player(String name) {
        this.name = name;
    }
    
    /**
     * Choose a card from among his hand to play in his next play.
     * @param game Game where the player is playing
     * @return the chosen card to play
     */
    public Cards playCard(Table game) {
        Cards chosen_card = hand.get((int)(Math.random()*hand.size())); //TODO Now is being choosing a random card
        hand.remove(chosen_card);
        return chosen_card;
    }
    
    /**
     * Choose (if exists) a pair of singing cards to sing them in the game.
     * @param game Game where the player is playing
     * @return the pair of cards to sing
     */
    public ArrayList<Cards> sing(Table game) {//TODO When the method 'canSing()' is ready, change this return for "return canSing().subList(0, 2);"
        
        ArrayList<Suits> singingSuits = canSing(game);
        
        if (singingSuits.isEmpty())
            return new ArrayList<>();
        
        for (Suits suit : singingSuits) { //Si son las 40, canta eso directamente
            if (suit.equals(game.getTriunfo().getSuit())) {
                this.sings.add(suit);
                return new ArrayList<>(Arrays.asList(Cards.getCard(Numbers.HORSE, suit),Cards.getCard(Numbers.KING, suit)));
            }
        }
        
        this.sings.add(singingSuits.get(0)); //Si no hay 40s, canta las primeras 20 que tengas
        return new ArrayList<>(Arrays.asList(Cards.getCard(Numbers.HORSE, singingSuits.get(0)),Cards.getCard(Numbers.KING, singingSuits.get(0))));
        
    }
    
    public void receiveCard(Cards card) {
        hand.add(card);
    }
    
    /**
     * Checks what card in the player hand can be played.
     * @param game Game where the player is playing
     * @return A list with allowed cards to play
     */
    private ArrayList<Cards> checkPlays(Table game) {
        ArrayList<Cards> allowed = new ArrayList<>();
        for (Cards c : hand) {
            if (canPlay(game, c))
                allowed.add(c);
        }
        return allowed;
    }
    
    /**
     * Check if the given card can be played.
     * @param game Game where the player is playing
     * @param card Card to check
     * @return True if the card can be played or False if not
     */
    private boolean canPlay(Table game, Cards card) { //TODO define the rules
        return true;
    }
    
    /**
     * Search the pairs of the cards in the player hand which can be singed.
     * @param game Game where the player is playing
     * @return List of cards which can be singed
     */
    private ArrayList<Suits> canSing (Table game) {
        ArrayList<Suits> suitsToSing = new ArrayList<>();
        
        ArrayList<Cards> figures = new ArrayList<>();
        for (Cards d : hand) //Filtro las cartas de la mano y dejo solo los caballos y los reyes
            if (d.getNumber()==Numbers.HORSE || d.getNumber()==Numbers.KING)
                figures.add(d);
        
        for (int i=0; i<figures.size(); i++) { //Escojo una por una las cartas de la mano filtrada para compararla con todas las demas (solo las siguientes)
            
            if (this.sings.contains(figures.get(i).getSuit()) || suitsToSing.contains(figures.get(i).getSuit())) //Si ya la ha cantado antes o si ya esta añadida a la lista de posibles cantes
                continue;
            
            if (figures.get(i).getNumber() == Numbers.HORSE) { //Compruebo si es un caballo
                for (int j=i+1; j<figures.size(); j++) { //Escojo una por una las cartas siguientes a la escogida antes
                    if ( figures.get(j).getNumber()==Numbers.KING && figures.get(j).getSuit()==figures.get(i).getSuit() ) { //Comparo ambas cartas escogidas comprobando primero si la segunda es un rey y despues si es del mismo palo que la primera
                        suitsToSing.add(figures.get(i).getSuit());
                        break;
                     }
                }
            } else { //Como no es un caballo solo puede ser un rey (la mano esta filtrada con solo los caballos y los reyes)
                for (int j=i+1; j<figures.size(); j++) {
                    if ( figures.get(j).getNumber()==Numbers.HORSE && figures.get(j).getSuit()==figures.get(i).getSuit() ) {
                        suitsToSing.add(figures.get(i).getSuit());
                        break;
                    }
                }
            }
            
        }
        
        return suitsToSing;
    }
    
    public void addPoints(int points) {
        this.points += points;
    }
    
    public int getPoints() {
        return points;
    }
    
    public void setPoints(int points) {
        this.points = points;
    }
    
    public ArrayList<Cards> getHand() {
        return hand;
    }
    
    @Override
    public String toString() {
        return name.toUpperCase();
    }
    
}