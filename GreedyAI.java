import java.util.List;
import java.util.Random;

public class GreedyAI extends AIPlayer{
    Player greedddddyyy;


    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

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
        Disc chosenDisc;

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

