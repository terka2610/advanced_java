import javax.swing.*;
import java.awt.*;

/**
 * Main GUI class for the War Card Game
 * This class creates and manages the main game window
 */
public class WarGameGUI extends JFrame {
    private CardDisplayPanel playerAPanel;    // Panel for Player A's cards
    private CardDisplayPanel playerBPanel;    // Panel for Player B's cards
    private StatusPanel statusPanel;          // Panel for game status and scores
    private ControlPanel controlPanel;        // Panel for control buttons

    public WarGameGUI() {
        // Set up the main window
        setTitle("War Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(800, 600));

        // Initialize components
        initializeComponents();

        // Add components to the frame
        add(playerBPanel, BorderLayout.NORTH);
        add(statusPanel, BorderLayout.CENTER);
        add(playerAPanel, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.EAST);

        // Pack and center the window
        pack();
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        // Initialize all panels
        playerAPanel = new CardDisplayPanel("Player A");
        playerBPanel = new CardDisplayPanel("Player B");
        statusPanel = new StatusPanel();
        controlPanel = new ControlPanel(this);
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
