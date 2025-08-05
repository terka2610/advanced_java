import java.awt.*;
import javax.swing.*;

/**
 * Panel for displaying player's cards
 */
public class CardDisplayPanel extends JPanel {
    private final JLabel playerLabel;    // Label to show player name
    private final JLabel cardLabel;      // Label to show current card
    private final JLabel scoreLabel;     // Label to show player's score

    public CardDisplayPanel(String playerName) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize components
        playerLabel = new JLabel(playerName);
        cardLabel = new JLabel();
        scoreLabel = new JLabel("Score: 0");

        // Set up card label
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setPreferredSize(new Dimension(100, 150));

        // Add components to panel
        add(playerLabel, BorderLayout.WEST);
        add(cardLabel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.EAST);
    }

    /**
     * Updates the displayed card
     * @param cardName Name of the card to display (e.g., "AS" for Ace of Spades)
     */
    public void updateCard(String cardName) {
        ImageIcon cardImage = CardImageLoader.getCardImage(cardName);
        if (cardImage != null) {
            cardLabel.setIcon(cardImage);
            cardLabel.setText(""); // 清除任何文本
        } else {
            cardLabel.setIcon(null);
            cardLabel.setText(""); // 不显示 No Card 文字
        }
    }

    /**
     * Updates the player's score
     * @param score New score to display
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
