package game;

public enum Numbers {
    ACE     (11),
    TWO     (0),
    THREE   (10),
    FOUR    (0),
    FIVE    (0),
    SIX     (0),
    SEVEN   (0),
    JACK    (2),
    HORSE   (3),
    KING    (4);
    
    private Numbers(int value) {
        this.value  = value;
    }
    
    public int getPoints() {
        return value;
    }
    
    private int value;
}
