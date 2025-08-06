import java.awt.*;
import javax.swing.*;

/**
 * Panel for displaying game status and messages
 */
public class StatusPanel extends JPanel {
    private final JLabel statusLabel;
    private final Font messageFont;

    public StatusPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20),
            BorderFactory.createLineBorder(new Color(200, 200, 200, 100), 2, true)
        ));
        setOpaque(false);

        // 创建一个半透明的面板来显示状态
        JPanel statusBackground = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 创建圆角矩形的渐变背景
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255, 200),
                                                    0, getHeight(), new Color(240, 240, 240, 200));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        statusBackground.setOpaque(false);
        statusBackground.setLayout(new BorderLayout());
        
        // 初始化状态标签，使用更现代的字体
        messageFont = new Font("Arial", Font.BOLD, 24);
        statusLabel = new JLabel("Welcome to War Card Game!", SwingConstants.CENTER);
        statusLabel.setFont(messageFont);
        statusLabel.setForeground(new Color(50, 50, 50));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // 添加阴影效果
        statusLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            statusLabel.getBorder()
        ));

        // 将标签添加到背景面板
        statusBackground.add(statusLabel, BorderLayout.CENTER);
        
        // 将背景面板添加到主面板
        add(statusBackground, BorderLayout.CENTER);
    }

    /**
     * Updates the status message
     * @param message New message to display
     */
    public void updateStatus(String message) {
        statusLabel.setText(message);
        
        // Change text color based on message content
        if (message.contains("War!")) {
            statusLabel.setForeground(Color.RED);
        } else if (message.contains("wins")) {
            statusLabel.setForeground(new Color(0, 128, 0)); // Dark Green
        } else {
            statusLabel.setForeground(Color.BLACK);
        }
    }
}
