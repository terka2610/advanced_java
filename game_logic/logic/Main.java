package logic;

public class Main {
    public static void main(String[] args) {
    	Game game = new Game(); // 

    	while (!game.isGameOver()) {
    	    RoundResult result = game.startRound();
    	    // GUI
    	}

    	/*** This is an example of how to save and load a game state.
    	 * 
    	 * 
        // Example: First round
        RoundResult firstRound = game.startRound();
        // GUI can now use these values:
        // firstRound.playerCard
        // firstRound.computerCard
        // firstRound.winner
        // firstRound.playerDeckSize
        // firstRound.computerDeckSize

        // Save game
        game.save("save1.dat");

        // Load game
        Game loadedGame = new Game();
        loadedGame.load("save1.dat");
        
        ***/
    	
    	
    	/*** This was just for me to test the game logic in the console.
    	 
        Game game = new Game();

        while (!game.isGameOver()) {
            RoundResult round = game.startRound();
            if (round == null) break;

            System.out.println("Player plays: " + round.playerCard);
            System.out.println("Computer plays: " + round.computerCard);
            System.out.println("Winner: " + round.winner);

            if ("War".equals(round.winner)) {
                System.out.println("Player war cards: " + round.playerWarCards);
                System.out.println("Computer war cards: " + round.computerWarCards);
            }

            System.out.println("Player deck size: " + round.playerDeckSize);
            System.out.println("Computer deck size: " + round.computerDeckSize);
            System.out.println("-----------------------");
        }
        
        ***/
    
    }
}
