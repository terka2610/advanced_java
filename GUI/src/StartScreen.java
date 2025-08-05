import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class StartScreen extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;

    public StartScreen() {
        // Set up the frame
        setTitle("War Game");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);

        // Create main panel with background image
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    // 尝试多个可能的路径
                    String[] possiblePaths = {
                        "src/images/fbackground.png",
                        "GUI/src/images/fbackground.png",
                        "images/fbackground.png",
                        "fbackground.png"
                    };
                    
                    ImageIcon backgroundImage = null;
                    String loadedPath = null;
                    
                    for (String path : possiblePaths) {
                        File file = new File(path);
                        if (file.exists()) {
                            backgroundImage = new ImageIcon(file.getAbsolutePath());
                            loadedPath = path;
                            break;
                        }
                        System.out.println("Tried path: " + path + " (not found)");
                    }
                    
                    if (backgroundImage != null && backgroundImage.getImage() != null) {
                        System.out.println("Successfully loaded background from: " + loadedPath);
                        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                    } else {
                        System.err.println("Failed to load background image from any path");
                        setBackground(Color.BLACK); // Fallback background color
                    }
                } catch (Exception e) {
                    System.err.println("Error loading background image: " + e.getMessage());
                    e.printStackTrace();
                    setBackground(Color.BLACK); // Fallback background color
                }
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create title label
        JLabel titleLabel = new JLabel("WAR GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // Create buttons
        JButton startButton = createButton("START");
        JButton quitButton = createButton("QUIT");
        
        // Add action listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close start screen
                SwingUtilities.invokeLater(() -> {
                    WarGameGUI game = new WarGameGUI();
                    game.setVisible(true);
                });
            }
        });
        
        quitButton.addActionListener(e -> System.exit(0));
        
        // Add components with spacing
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(quitButton);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        // Add panel to frame
        add(mainPanel);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isPressed()) {
                    g.setColor(new Color(20, 20, 20)); // 按下时的颜色
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(40, 40, 40)); // 悬停时的颜色
                } else {
                    g.setColor(Color.BLACK); // 默认颜色
                }
                g.fillRect(0, 0, getWidth(), getHeight());
                
                // 绘制文本
                FontMetrics metrics = g.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                g.setColor(Color.WHITE);
                g.setFont(getFont());
                g.drawString(getText(), x, y);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 50));
        button.setMaximumSize(new Dimension(200, 50));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(40, 40, 40)); // 浅一点的黑色，用于悬停效果
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.BLACK); // 恢复原来的黑色
            }
        });
        
        return button;
    }

    public static void main(String[] args) {
        // 确保在 EDT (Event Dispatch Thread) 中运行
        SwingUtilities.invokeLater(() -> {
            try {
                // 使用跨平台的Look and Feel
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("无法设置Look and Feel: " + e.getMessage());
            }
            
            // 创建并显示开始界面
            StartScreen startScreen = new StartScreen();
            startScreen.setVisible(true);
        });
    }
}
