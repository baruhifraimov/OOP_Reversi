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
        // Get the list of available moves.
        List<Position> availableMoves = gameStatus.ValidMoves();

        // check that there are more moves.
        if (availableMoves.isEmpty()) {
            gameStatus.isGameFinished();
            return new Move(null, null);
        }

        //sort by the comparator than we have defined , Biggest move will be first , and also the rightmost column when equals.
        availableMoves.sort(new PositionComparator(gameStatus));

        //takes the best move according to our comparator.
        Position bestMove = availableMoves.getFirst();

        int moveCounter = availableMoves.size();

        // If no moves are available, return a default move or handle gracefully
        if (moveCounter == 0) {
            gameStatus.isGameFinished();
            // Returning a default move with a null position and disc (modify if needed)
            return new Move(null, null);
        }

        // Select a greedy move
        chosenDisc = new SimpleDisc(greedddddyyy);


        // If the move succeeds, return the Move object
        return new Move(bestMove, chosenDisc);
    }
}

