package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game implements Serializable {
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
            handleWar(result);
        }

        result.playerDeckSize = deck.getPlayerDeck().size();
        result.computerDeckSize = deck.getComputerDeck().size();

        return result;
    }

    private void handleWar(RoundResult result) {
        List<String> warPile = new ArrayList<>();
        warPile.add(result.playerCard);
        warPile.add(result.computerCard);

        // 确保双方有足够的牌进行战争
        for (int i = 0; i < 3 && hasEnoughCards(); i++) {
            warPile.add(deck.getPlayerDeck().remove(0));
            warPile.add(deck.getComputerDeck().remove(0));
        }

        if (!hasEnoughCards()) {
            // 如果任一方的牌不够，将所有牌平均分配
            Collections.shuffle(warPile);
            int half = warPile.size() / 2;
            deck.getPlayerDeck().addAll(warPile.subList(0, half));
            deck.getComputerDeck().addAll(warPile.subList(half, warPile.size()));
            return;
        }

        // 抽取新的牌进行比较
        String playerWarCard = deck.getPlayerDeck().remove(0);
        String computerWarCard = deck.getComputerDeck().remove(0);
        warPile.add(playerWarCard);
        warPile.add(computerWarCard);

        int compare = deck.compareCards(playerWarCard, computerWarCard);
        if (compare > 0) {
            deck.getPlayerDeck().addAll(warPile);
            result.winner = "Player";
        } else if (compare < 0) {
            deck.getComputerDeck().addAll(warPile);
            result.winner = "Computer";
        } else {
            // 如果还是平局，继续战争
            handleWar(result);
        }
    }

    private boolean hasEnoughCards() {
        return !deck.getPlayerDeck().isEmpty() && !deck.getComputerDeck().isEmpty();
    }
}
