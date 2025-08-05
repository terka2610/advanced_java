import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

/**
 * Utility class for loading and caching card images
 */
public class CardImageLoader {
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 150;

    /**
     * Gets the card image for the specified card name
     * @param cardName Name of the card (e.g., "Club2" for 2 of Clubs)
     * @return ImageIcon of the card, scaled to proper size
     */
    public static ImageIcon getCardImage(String cardName) {
        if (cardName == null) return null;

        // Check cache first
        if (imageCache.containsKey(cardName)) {
            return imageCache.get(cardName);
        }

        try {
            // Load image
            java.net.URL imageUrl = CardImageLoader.class.getResource("/images/" + cardName + ".png");
            if (cardName.equals("back")) {
                imageUrl = CardImageLoader.class.getResource("/images/Background.png");
            }
            if (imageUrl == null) {
                System.err.println("找不到图片文件: " + cardName);
                return null;
            }
            ImageIcon originalIcon = new ImageIcon(imageUrl);
            
            // Scale image
            Image scaledImage = originalIcon.getImage().getScaledInstance(
                CARD_WIDTH, CARD_HEIGHT, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            
            // Cache and return
            imageCache.put(cardName, scaledIcon);
            return scaledIcon;
        } catch (Exception e) {
            System.err.println("Error loading card image: " + cardName);
            return null;
        }
    }

    /**
     * Clears the image cache
     */
    public static void clearCache() {
        imageCache.clear();
    }
}
