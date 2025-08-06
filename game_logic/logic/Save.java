package logic;

import java.io.*;

public class Save {

    private static final String SAVE_DIR = "saves/";

    public static void saveGame(DeckOfCards deck, String fileName) {
        File file = new File(SAVE_DIR + fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            System.out.println("Saving to: " + file.getAbsolutePath());
            out.writeObject(deck);
            System.out.println("Game saved.");
        } catch (IOException e) {
            System.out.println("Failed to save game: " + e.getMessage());
        }
    }

    public static DeckOfCards loadGame(String fileName) {
        File file = new File(SAVE_DIR + fileName);
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            System.out.println("Loading from: " + file.getAbsolutePath());
            DeckOfCards deck = (DeckOfCards) in.readObject();
            System.out.println("Game loaded.");
            return deck;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load game: " + e.getMessage());
            return null;
        }
    }

}
