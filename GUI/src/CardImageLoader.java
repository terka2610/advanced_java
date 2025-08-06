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
        
        // 如果是背景牌，使用 Background.png
        if (cardName.equals("Background")) {
            cardName = "Background";
        }

        try {
            // 尝试多个可能的路径加载图片
            ImageIcon originalIcon = null;
            String[] possiblePaths = {
                "/images/" + cardName,             // 类路径下的 images 目录
                "GUI/src/images/" + cardName,      // 项目根目录下的路径
                "src/images/" + cardName,          // src 目录下的路径
                "images/" + cardName               // 当前目录下的路径
            };

            // 先尝试从资源路径加载
            for (String path : possiblePaths) {
                java.net.URL imageUrl = CardImageLoader.class.getResource(path + ".png");
                if (imageUrl != null) {
                    originalIcon = new ImageIcon(imageUrl);
                    break;
                }
            }

            // 如果资源路径加载失败，尝试从文件系统加载
            if (originalIcon == null) {
                for (String path : possiblePaths) {
                    java.io.File file = new java.io.File(path + ".png");
                    if (file.exists()) {
                        originalIcon = new ImageIcon(file.getAbsolutePath());
                        break;
                    }
                }
            }

            if (originalIcon == null) {
                System.err.println("Can't find image: " + cardName);
                return null;
            }
            
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
