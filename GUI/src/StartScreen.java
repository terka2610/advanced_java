import java.awt.*;
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
                    // 使用类加载器加载图片资源
                    java.net.URL imageURL = getClass().getResource("/images/fbackground.png");
                    if (imageURL == null) {
                        imageURL = getClass().getResource("images/fbackground.png");
                    }
                    
                    ImageIcon backgroundImage = null;
                    if (imageURL != null) {
                        backgroundImage = new ImageIcon(imageURL);
                        System.out.println("Successfully loaded background from resources");
                    } else {
                        // 尝试从文件系统加载
                        File file = new File("GUI/src/images/fbackground.png");
                        if (file.exists()) {
                            backgroundImage = new ImageIcon(file.getAbsolutePath());
                            System.out.println("Successfully loaded background from file system");
                        }
                    }
                    
                    if (backgroundImage != null && backgroundImage.getImage() != null) {
                        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
                    } else {
                        System.err.println("Failed to load background image");
                        setBackground(Color.BLACK); // Fallback background color
                    }
                } catch (SecurityException | IllegalArgumentException e) {
                    System.err.println("Error loading background image: " + e.getMessage());
                    setBackground(Color.BLACK); // Fallback background color
                }
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Create title label
        JLabel titleLabel = new JLabel("WAR GAME");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        // Create buttons
        JButton startButton = createButton("START");
        JButton quitButton = createButton("QUIT");
        
        // Add action listeners
        startButton.addActionListener(e -> {
            dispose(); // Close start screen
            SwingUtilities.invokeLater(() -> {
                WarGameGUI game = new WarGameGUI();
                game.setVisible(true);
            });
        });
        
        quitButton.addActionListener(ignored -> System.exit(0));
        
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
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // 设置透明背景
                setContentAreaFilled(false);
                
                // 绘制文本
                FontMetrics metrics = g.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(getText())) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                
                // 根据按钮状态改变文字颜色
                if (getModel().isPressed()) {
                    g.setColor(new Color(100, 100, 100)); // 按下时的颜色
                } else if (getModel().isRollover()) {
                    g.setColor(new Color(50, 50, 50)); // 悬停时的颜色
                } else {
                    g.setColor(Color.BLACK); // 默认颜色
                }
                
                g.setFont(getFont());
                g2d.drawString(getText(), x, y);
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setForeground(Color.BLACK);
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
            } catch (ClassNotFoundException | InstantiationException | 
                     IllegalAccessException | UnsupportedLookAndFeelException e) {
                System.err.println("无法设置Look and Feel: " + e.getMessage());
            }
            
            // 创建并显示开始界面
            StartScreen startScreen = new StartScreen();
            startScreen.setVisible(true);
        });
    }
}
