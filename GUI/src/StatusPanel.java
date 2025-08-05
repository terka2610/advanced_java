import javax.swing.*;
import java.awt.*;

/**
 * Panel for displaying game status and messages
 */
public class StatusPanel extends JPanel {
    private final JLabel statusLabel;
    private final Font messageFont;

    public StatusPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Initialize status label with custom font
        messageFont = new Font("Arial", Font.BOLD, 18);
        statusLabel = new JLabel("Welcome to War Card Game!", SwingConstants.CENTER);
        statusLabel.setFont(messageFont);

        // Add components
        add(statusLabel, BorderLayout.CENTER);
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
