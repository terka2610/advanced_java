package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeckOfCards implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private List<Card> playerDeck = new ArrayList<>();
    private List<Card> computerDeck = new ArrayList<>();
    private String[] suits = {"Club", "Diamond", "Heart", "Spade"};
    private String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

    private void shuffleDeck(List<Card> deck) {
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }

    void initializeDeck() {
        playerDeck.clear();
        computerDeck.clear();
        List<Card> fullDeck = new ArrayList<>();

        for (String suit : suits) {
            for (String rank : ranks) {
                Card card = new Card();
                card.setSuit(suit);
                card.setValue(rank);
                fullDeck.add(card);
            }
        }
        shuffleDeck(fullDeck);
        for (int i = 0; i < fullDeck.size(); i++) {
            if (i < fullDeck.size() / 2) {
                playerDeck.add(fullDeck.get(i));
            } else {
                computerDeck.add(fullDeck.get(i));
            }
        }
    }

    int compareCards(Card card1, Card card2) {
        return Integer.compare(card1.getNumericValue(), card2.getNumericValue());
    }
    
    public List<Card> getPlayerDeck() {
        return playerDeck;
    }

    public List<Card> getComputerDeck() {
        return computerDeck;
    }
    //TODO: Add a war game class and make the methods for the game
    // Add start a round and start a game classes
    // Add a joker for a deck
}