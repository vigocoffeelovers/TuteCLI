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
     * Choose (if exists) a pair of singing cards to sing them in the game. Choose the best 
     * pair among all posibilities to sing.
     * (TUTE > 40s > 20s)
     * 
     * In the case that the chosen one was a "Tute sing", the method will return 4 cards (4 Horses or 4 Kings).
     * 
     * @param game Game where the player is playing
     * @return the list of cards to sing
     */
    public ArrayList<Cards> sing(Table game) {
        
        ArrayList< ArrayList<Cards> > singCards = canSing(game);
        
        if (singCards.isEmpty())
            return new ArrayList<>();
        
        for (ArrayList<Cards> sings : singCards) { //TUTE
            if (sings.size()==4)
                return sings;
        }
        
        for (ArrayList<Cards> sings : singCards) { //40s
            if (sings.get(0).getSuit().equals(game.getTriunfo().getSuit())) {
                this.sings.add(sings.get(0).getSuit());
                return sings;
            }
        }
        
        this.sings.add(singCards.get(0).get(0).getSuit()); //20s (da igual cual, pilla el primer palo que le llega)
        return singCards.get(0);
        
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
     * Search the cards in the player hand which can be singed.
     * 
     * In the case that the chosen one was a "Tute sing".
     * 
     * @param game Game where the player is playing
     * @return List of cards which can be singed
     */
    private ArrayList< ArrayList<Cards> > canSing (Table game) {
        ArrayList< ArrayList<Cards> > cardsToSing = new ArrayList<>();
        
        ArrayList<Cards> horses = new ArrayList<>();
        ArrayList<Cards> kings  = new ArrayList<>();
        for (Cards d : hand) {//Filtro las cartas de la mano y dejo solo los caballos y los reyes
            if (d.getNumber()==Numbers.HORSE) horses.add(d);
            else if (d.getNumber().equals(Numbers.KING)) kings.add(d);
        }
        
        if (kings.size()==4) {
            cardsToSing.add( kings );
            sings.addAll( Arrays.asList(Suits.values()) );
            return cardsToSing;
        }
        
        if (horses.size()==4) {
            cardsToSing.add( horses );
            sings.addAll(Arrays.asList(Suits.values()));
            return cardsToSing;
        }
        
        for (Cards h : horses) {
            
            if (this.sings.contains(h.getSuit())) //Si ya la ha cantado antes o si ya esta añadida a la lista de posibles cantes
                continue;
            
            for (Cards k : kings) {
                
                if (h.getSuit().equals(k.getSuit())) {
                    cardsToSing.add( new ArrayList<>(Arrays.asList(h,k)) );
                    break;
                }
                
            }
        }
        
        return cardsToSing;
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