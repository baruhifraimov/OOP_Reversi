import java.util.List;
import java.util.Random;

public class GreedyAI extends AIPlayer{
    private Player greedddddyyy;
    private Disc chosenDisc;

    /**
     * Constructs a GreedyAI instance, associating it with a player role (Player One or Player Two).
     *
     * @param isPlayerOne true if this AI is Player One, false if it is Player Two.
     */
    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Makes a move for the greedy AI. The AI evaluates the current game state and selects a move
     * that flips the maximum number of discs on the board. If multiple moves are equally optimal,
     * one is chosen arbitrarily. Additionally, the AI randomly selects the type of disc to place.
     *
     * @param gameStatus the current state of the game, encapsulated in a {@link PlayableLogic} object.
     * @return a {@link Move} object containing the selected position and the chosen disc.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        Random random = new Random();
        Position maxMove = null;
        // Determine the greedy
        if (isPlayerOne) {
            greedddddyyy = gameStatus.getFirstPlayer();
        } else {
            greedddddyyy = gameStatus.getSecondPlayer();
        }
        // Get the list of available moves
        List<Position> availableMoves = gameStatus.ValidMoves();

        // Chooses the largest move
        int maxFlips = 0;
        for(Position i: availableMoves){
            if(gameStatus.countFlips(i) > maxFlips){
                maxFlips = gameStatus.countFlips(i);
                maxMove = new Position(i.row(),i.col());
            }
        }
        int moveCounter = availableMoves.size();

        // If no moves are available, return a default move or handle gracefully
        if (moveCounter == 0) {
            gameStatus.isGameFinished();
            // Returning a default move with a null position and disc (modify if needed)
            return new Move(null, null);
        }

        // Select a greedy move
        Position nextMove = maxMove;

        // Select a random disc type
        int selectedDiscType = random.nextInt(3);


        // Determine the disc to place based on random selection and greedddddyyy state
        if (selectedDiscType == 0) {
            chosenDisc = new SimpleDisc(greedddddyyy);
        } else if (selectedDiscType == 1 && greedddddyyy.getNumber_of_bombs() > 0) {
            chosenDisc = new BombDisc(greedddddyyy);
        } else if (selectedDiscType == 2 && greedddddyyy.getNumber_of_unflippedable() > 0) {
            chosenDisc = new UnflippableDisc(greedddddyyy);
        } else {
            chosenDisc = new SimpleDisc(greedddddyyy);
        }


        // If the move succeeds, return the Move object
        return new Move(nextMove, chosenDisc);
    }
}

