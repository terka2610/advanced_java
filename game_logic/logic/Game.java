package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable{
	
	private static final long serialVersionUID = 1L;
    private DeckOfCards deck;

    public Game() {
        deck = new DeckOfCards();
        deck.initializeDeck(); 
    }
    
    public DeckOfCards getDeck() {
        return deck;
    }
    
    public RoundResult startRound() {
        RoundResult result = new RoundResult();

        if (deck.getPlayerDeck().isEmpty() || deck.getComputerDeck().isEmpty()) {
            return null;
        }

        result.playerCard = deck.getPlayerDeck().remove(0);
        result.computerCard = deck.getComputerDeck().remove(0);

        int compare = deck.compareCards(result.playerCard, result.computerCard);

        if (compare > 0) {
            deck.getPlayerDeck().add(result.playerCard);
            deck.getPlayerDeck().add(result.computerCard);
            result.winner = "Player";
        } else if (compare < 0) {
            deck.getComputerDeck().add(result.playerCard);
            deck.getComputerDeck().add(result.computerCard);
            result.winner = "Computer";
        } else {
            result.winner = "War";
            handleWar(result.playerCard, result.computerCard, result);
        }

        result.playerDeckSize = deck.getPlayerDeck().size();
        result.computerDeckSize = deck.getComputerDeck().size();

        return result;
    }

    private void handleWar(Card pCard, Card cCard, RoundResult roundResult) {
        List<Card> warPile = new ArrayList<>();
        warPile.add(pCard);
        warPile.add(cCard);

        for (int i = 0; i < 3 && !deck.getPlayerDeck().isEmpty(); i++) {
            Card faceDown = deck.getPlayerDeck().remove(0);
            warPile.add(faceDown);
            roundResult.playerWarCards.add(faceDown);
        }
        for (int i = 0; i < 3 && !deck.getComputerDeck().isEmpty(); i++) {
            Card faceDown = deck.getComputerDeck().remove(0);
            warPile.add(faceDown);
            roundResult.computerWarCards.add(faceDown);
        }

        if (deck.getPlayerDeck().isEmpty() || deck.getComputerDeck().isEmpty()) return;

        Card warPlayerCard = deck.getPlayerDeck().remove(0);
        Card warComputerCard = deck.getComputerDeck().remove(0);
        warPile.add(warPlayerCard);
        warPile.add(warComputerCard);

        roundResult.playerWarCards.add(warPlayerCard);
        roundResult.computerWarCards.add(warComputerCard);

        int result = deck.compareCards(warPlayerCard, warComputerCard);

        if (result > 0) {
            deck.getPlayerDeck().addAll(warPile);
            roundResult.winner = "Player";
        } else if (result < 0) {
            deck.getComputerDeck().addAll(warPile);
            roundResult.winner = "Computer";
        } else {
            roundResult.winner = "War";
            handleWar(warPlayerCard, warComputerCard, roundResult);
        }
    }

    public boolean isGameOver() {
        return deck.getPlayerDeck().isEmpty() || deck.getComputerDeck().isEmpty();
    }
    
    public void save(String fileName) {
        Save.saveGame(deck, fileName);
    }

    public void load(String fileName) {
        DeckOfCards loaded = Save.loadGame(fileName);
        if (loaded != null) {
            deck = loaded;
        }
    }
}
