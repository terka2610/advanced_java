import java.awt.*;
import javax.swing.*;
import logic.Game;
import logic.RoundResult;

/**
 * Main GUI class for the War Card Game
 * This class creates and manages the main game window
 */
public class WarGameGUI extends JFrame {
    private CardDisplayPanel playerAPanel;    // Panel for Player A's cards
    private CardDisplayPanel playerBPanel;    // Panel for Player B's cards
    private StatusPanel statusPanel;          // Panel for game status and scores
    private ControlPanel controlPanel;        // Panel for control buttons
    private Game game;                        // Game logic instance

    public WarGameGUI() {
        // Set up the main window
        setTitle("War Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(1000, 700));

        // Create a main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                int w = getWidth();
                int h = getHeight();
                
                // Create gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(45, 62, 80),
                                                    w, h, new Color(53, 92, 125));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Initialize components
        initializeComponents();

        // Create player area container
        JPanel gameArea = new JPanel(new BorderLayout(20, 20));
        gameArea.setOpaque(false);
        
        // Add components to game area
        gameArea.add(playerBPanel, BorderLayout.NORTH);
        gameArea.add(statusPanel, BorderLayout.CENTER);
        gameArea.add(playerAPanel, BorderLayout.SOUTH);
        
        // Add game area and control panel to main panel
        mainPanel.add(gameArea, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);
        
        // Add main panel to frame
        add(mainPanel);

        // Pack and center the window
        pack();
        setLocationRelativeTo(null);
        
        // 初始化时显示背景牌
        updateGameState("Background", "Background", "Welcome to the Card Game!");
    }
    
    /**
     * Start a new game
     */
    public void startNewGame() {
        game = new Game();
        updateGameState("Background", "Background", "New Game!");
        updateScores();
    }
    
    /**
     * Play one round of the game
     */
    public void playRound() {
        RoundResult result = game.startRound();
        if (result == null) {
            handleGameEnd();
            return;
        }
        
        // Update card display
        String playerCard = convertCardToImageName(result.playerCard);
        String computerCard = convertCardToImageName(result.computerCard);
        
        // Update game status
        String message;
        if (result.winner.equals("War")) {
            message = "War! It's a tie, entering war mode...";
        } else if (result.winner.equals("Player")) {
            message = "You won this round!";
        } else {
            message = "Computer won this round!";
        }
        
        updateGameState(playerCard, computerCard, message);
        updateScores();
        
        // Check if game is over
        if (result.playerDeckSize == 0 || result.computerDeckSize == 0) {
            handleGameEnd();
        }
    }
    
    /**
     * Handle game end condition
     */
    private void handleGameEnd() {
        String message;
        if (game.getDeck().getPlayerDeck().isEmpty()) {
            message = "Game Over! Computer Wins!";
        } else if (game.getDeck().getComputerDeck().isEmpty()) {
            message = "Game Over! Congratulations, You Win!";
        } else {
            message = "Game Over!";
        }
        updateGameState(null, null, message);
        controlPanel.disablePlayButton();
    }
    
    /**
     * Update score display
     */
    private void updateScores() {
        int playerScore = game.getDeck().getPlayerDeck().size();
        int computerScore = game.getDeck().getComputerDeck().size();
        playerAPanel.updateScore(playerScore);
        playerBPanel.updateScore(computerScore);
    }
    
    /**
     * Convert card object to image filename
     */
    private String convertCardToImageName(String card) {
        if (card == null) return null;
        // Card format is "AS" (Ace of Spades), "2H" (2 of Hearts), etc.
        String rank = card.substring(0, 1);
        String suit = card.substring(1, 2);
        
        String suitName = switch (suit) {
            case "S" -> "Spade";
            case "H" -> "Heart";
            case "C" -> "Club";
            case "D" -> "Diamond";
            default -> "";
        };
        
        String rankName = switch (rank) {
            case "A" -> "A";
            case "K" -> "K";
            case "Q" -> "Q";
            case "J" -> "J";
            case "T" -> "10";
            default -> rank;
        };
        
        // Return without .png extension as CardImageLoader will add it
        return suitName + rankName;
    }

    private void initializeComponents() {
        // Initialize all panels
        playerAPanel = new CardDisplayPanel("Player");
        playerBPanel = new CardDisplayPanel("Computer");
        statusPanel = new StatusPanel();
        controlPanel = new ControlPanel(this);
        
        // Initialize game logic and display initial state
        game = new Game();
        playerAPanel.updateCard("Background");
        playerBPanel.updateCard("Background");
        statusPanel.updateStatus("Welcome to the Card Game!");
        updateScores();
    }

    /**
     * Updates the game state on the GUI
     * @param playerACard Card played by Player A
     * @param playerBCard Card played by Player B
     * @param message Status message to display
     */
    public void updateGameState(String playerACard, String playerBCard, String message) {
        // Ensure GUI updates happen on EDT
        SwingUtilities.invokeLater(() -> {
            playerAPanel.updateCard(playerACard);
            playerBPanel.updateCard(playerBCard);
            statusPanel.updateStatus(message);
        });
    }

    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        // Ensure GUI is created on EDT
        SwingUtilities.invokeLater(() -> {
            WarGameGUI gui = new WarGameGUI();
            gui.setVisible(true);
        });
    }
}
