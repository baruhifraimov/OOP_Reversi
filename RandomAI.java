import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer {
    Player randAI;


    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

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
        Disc chosenDisc;

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

