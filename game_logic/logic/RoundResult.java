package logic;

import java.util.ArrayList;
import java.util.List;

public class RoundResult {
    public Card playerCard;
    public Card computerCard;
    public String winner; // "Player", "Computer"
    public int playerDeckSize;
    public int computerDeckSize;
    
    public List<Card> playerWarCards = new ArrayList<>();   
    public List<Card> computerWarCards = new ArrayList<>();
}

