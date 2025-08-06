import java.awt.*;
import javax.swing.*;

/**
 * Panel containing game control buttons
 */
public class ControlPanel extends JPanel {
    private final JButton playRoundButton;
    private final JButton newGameButton;
    private final WarGameGUI mainGUI;

    public ControlPanel(WarGameGUI gui) {
        this.mainGUI = gui;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 10, 20),
            BorderFactory.createLineBorder(new Color(200, 200, 200, 100), 1, true)
        ));
        setOpaque(false);

        // 创建半透明背景面板
        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 创建渐变背景
                GradientPaint gp = new GradientPaint(0, 0, new Color(255, 255, 255, 180),
                                                    0, getHeight(), new Color(240, 240, 240, 180));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Initialize buttons
        playRoundButton = createStyledButton("Play Round");
        newGameButton = createStyledButton("New Game");

        // Add action listeners
        playRoundButton.addActionListener(_ -> playRound());
        newGameButton.addActionListener(_ -> startNewGame());

        // Add buttons to panel with proper spacing
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(playRoundButton);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalGlue());

        // Add button panel to main panel
        add(buttonPanel);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                if (getModel().isPressed()) {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(40, 60, 80),
                                                  0, getHeight(), new Color(50, 70, 90)));
                } else if (getModel().isRollover()) {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(60, 80, 100),
                                                  0, getHeight(), new Color(70, 90, 110)));
                } else {
                    g2d.setPaint(new GradientPaint(0, 0, new Color(50, 70, 90),
                                                  0, getHeight(), new Color(60, 80, 100)));
                }
                
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // 绘制光泽效果
                g2d.setPaint(new GradientPaint(0, 0, new Color(255, 255, 255, 100),
                                              0, getHeight()/2, new Color(255, 255, 255, 0)));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight()/2, 10, 10);
                
                // 绘制文本
                FontMetrics metrics = g2d.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);
            }
        };
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(150, 45));
        button.setMaximumSize(new Dimension(150, 45));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        return button;
    }

    private void playRound() {
        mainGUI.playRound();
    }

    private void startNewGame() {
        mainGUI.startNewGame();
        playRoundButton.setEnabled(true);
    }
    
    /**
     * 禁用"开始回合"按钮
     */
    public void disablePlayButton() {
        playRoundButton.setEnabled(false);
    }

    /**
     * Enables or disables the Play Round button
     */
    public void setPlayEnabled(boolean enabled) {
        playRoundButton.setEnabled(enabled);
    }
}
