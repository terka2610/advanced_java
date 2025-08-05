package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DeckOfCards {
    private List<Card> playerDeck = new ArrayList<>();
    private List<Card> computerDeck = new ArrayList<>();
    private String[] suits = { "clubs", "diamonds", "hearts", "spades" };
    private String[] ranks = { "2", "3", "4", "5", "6", "7", "8", "9", "10", 
                             "jack", "queen", "king", "ace" };

    private void shuffleDeck(List<Card> deck) {
        for (int i = deck.size() - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));		//shuffling the deck function
            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);
        }
    }
    private void initializeDeck() {
        playerDeck.clear();
        computerDeck.clear();
        List<Card> fullDeck = new ArrayList<>();		//deck initialization
        
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
    private int compareCards(Card card1, Card card2) {
        int rank1 = Arrays.asList(ranks).indexOf(card1.getValue());
        int rank2 = Arrays.asList(ranks).indexOf(card2.getValue());
        return Integer.compare(rank1, rank2);
    }
    //TODO: Add a war game class and make the methods for the game
}