import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer {
    private Player randAI;
    private Disc chosenDisc;

    /**
     * Constructs a RandomAI instance, associating it with a player role (Player One or Player Two).
     *
     * @param isPlayerOne true if this AI is Player One, false if it is Player Two.
     */
    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Makes a move for the random AI. The AI evaluates the current game state, selects a random
     * valid move from the available options, and randomly determines the type of disc to place.
     *
     * @param gameStatus the current state of the game, encapsulated in a {@link PlayableLogic} object.
     * @return a {@link Move} object containing the selected position and the chosen disc.
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        // Determine the greedddddyyy
        if (isPlayerOne) {
            randAI = gameStatus.getFirstPlayer();
        } else {
            randAI = gameStatus.getSecondPlayer();
        }

        // Get the list of available moves
        List<Position> availableMoves = gameStatus.ValidMoves();
        int moveCounter = availableMoves.size();

        // If no moves are available, return a default move or handle gracefully
        if (moveCounter == 0) {
            gameStatus.isGameFinished();
            // Returning a default move with a null position and disc (modify if needed)
            return new Move(null, null);
        }

        // Select a random move
        Random random = new Random();
        int selectedMoveIndex = random.nextInt(moveCounter);
        Position nextMove = availableMoves.get(selectedMoveIndex);

        // Select a random disc type
        int selectedDiscType = random.nextInt(3);

        // Determine the disc to place based on random selection and greedddddyyy state
        if (selectedDiscType == 0) {
            chosenDisc = new SimpleDisc(randAI);
        } else if (selectedDiscType == 1 && randAI.getNumber_of_bombs() > 0) {
            chosenDisc = new BombDisc(randAI);
        } else if (selectedDiscType == 2 && randAI.getNumber_of_unflippedable() > 0) {
            chosenDisc = new UnflippableDisc(randAI);
        } else {
            chosenDisc = new SimpleDisc(randAI);
        }


        // If the move succeeds, return the Move object
        return new Move(nextMove, chosenDisc);
    }
}

