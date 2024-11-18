import java.util.List;
import java.util.Random;

public class RandomAI extends AIPlayer {


    public RandomAI(boolean isPlayerOne) {

        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
       List<Position> availableMoves = gameStatus.ValidMoves(); //change valid move to moves and not positions.
       int moveCounter = availableMoves.size();
       if (moveCounter!=0){
        Random pos = new Random();
        int low = 0;
        int high = moveCounter-1;
        int result = pos.nextInt(high-low) + low;
        Position nextMove = availableMoves.get(result);

        Random curDisc = new Random();
        low = 0;
        high =2;
        result = curDisc.nextInt(high-low) + low;
        //switch - case
    }
}
