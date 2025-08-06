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
        setLayout(new BorderLayout(20, 0)); // 增加组件之间的间距
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15),
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true)
        ));
        setOpaque(false); // 使面板透明，以显示父容器的背景

        // Initialize components with styled fonts and colors
        playerLabel = new JLabel(playerName);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerLabel.setForeground(new Color(50, 50, 50));
        
        cardLabel = new JLabel();
        cardLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cardLabel.setPreferredSize(new Dimension(120, 180)); // 增加卡牌大小
        
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(50, 50, 50));

        // 创建包装面板，添加渐变背景
        JPanel wrapperPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, new Color(240, 240, 240),
                                                    0, h, new Color(220, 220, 220));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w, h, 15, 15);
            }
        };
        wrapperPanel.setOpaque(false);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Add components to wrapper panel
        wrapperPanel.add(playerLabel, BorderLayout.WEST);
        wrapperPanel.add(cardLabel, BorderLayout.CENTER);
        wrapperPanel.add(scoreLabel, BorderLayout.EAST);

        // Add wrapper panel to main panel
        add(wrapperPanel, BorderLayout.CENTER);
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
