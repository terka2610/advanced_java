package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeckOfCards implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<String> playerDeck;
    private final List<String> computerDeck;
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
    private static final String[] SUITS = {"H", "D", "C", "S"};

    public DeckOfCards() {
        playerDeck = new ArrayList<>();
        computerDeck = new ArrayList<>();
    }

    public void initializeDeck() {
        List<String> fullDeck = new ArrayList<>();
        
        // 创建一副完整的牌
        for (String suit : SUITS) {
            for (String rank : RANKS) {
                fullDeck.add(rank + suit);
            }
        }
        
        // 洗牌
        Collections.shuffle(fullDeck);
        
        // 平均分配给玩家和电脑
        int halfSize = fullDeck.size() / 2;
        playerDeck.addAll(fullDeck.subList(0, halfSize));
        computerDeck.addAll(fullDeck.subList(halfSize, fullDeck.size()));
    }

    public int compareCards(String card1, String card2) {
        char rank1 = card1.charAt(0);
        char rank2 = card2.charAt(0);
        
        int index1 = -1;
        int index2 = -1;
        
        for (int i = 0; i < RANKS.length; i++) {
            if (RANKS[i].charAt(0) == rank1) index1 = i;
            if (RANKS[i].charAt(0) == rank2) index2 = i;
        }
        
        return Integer.compare(index1, index2);
    }

    public List<String> getPlayerDeck() {
        return playerDeck;
    }

    public List<String> getComputerDeck() {
        return computerDeck;
    }
}
