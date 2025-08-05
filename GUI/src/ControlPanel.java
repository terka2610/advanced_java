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
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Initialize buttons
        playRoundButton = createStyledButton("Play Round");
        newGameButton = createStyledButton("New Game");

        // Add action listeners
        playRoundButton.addActionListener(_ -> playRound());
        newGameButton.addActionListener(_ -> startNewGame());

        // Add buttons to panel
        add(Box.createVerticalGlue());
        add(playRoundButton);
        add(Box.createVerticalStrut(10));
        add(newGameButton);
        add(Box.createVerticalGlue());
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    private void playRound() {
        // TODO: Implement game logic
        // This should be connected to the game's backend logic
        mainGUI.updateGameState("Heart10", "Club2", "Player A wins this round!");
    }

    private void startNewGame() {
        // TODO: Implement new game logic
        mainGUI.updateGameState(null, null, "New game started!");
        playRoundButton.setEnabled(true);
    }

    /**
     * Enables or disables the Play Round button
     */
    public void setPlayEnabled(boolean enabled) {
        playRoundButton.setEnabled(enabled);
    }
}
