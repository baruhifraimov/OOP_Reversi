import java.util.Comparator;

/**
 * Comparator for comparing positions on the board.
 * This comparator prioritizes positions based on the number of discs they flip
 * and, in case of a tie, chooses the rightmost position.
 */

public class PositionComparator implements Comparator<Position> {
    private final PlayableLogic gameStatus;


    /**
     * Constructs a PositionComparator with a reference to the current game logic.
     *
     * @param gameStatus the current game logic to evaluate positions.
     */
    public PositionComparator(PlayableLogic gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     * Compares two positions based on the number of discs they flip and their column index.
     *
     * @param p1 the first position to compare.
     * @param p2 the second position to compare.
     * @return a negative integer if p1 should come before p2,
     *         a positive integer if p2 should come before p1,
     *         or zero if they are considered equal.
     */
    @Override
    public int compare(Position p1, Position p2) {
        //  Compares based on the number of discs that has been flipped.
        int flips1 = gameStatus.countFlips(p1);
        int flips2 = gameStatus.countFlips(p2);

        if (flips1 != flips2) {
            // Give priority to the position that flips more discs
            return Integer.compare(flips2, flips1); // 0 if flip2 = flip1 , less than 0 if flips2<flips1 , >0 if flips2>flips1
        }

        //  If the number of discs flipped is the same, prioritize the rightmost column
         int check = Integer.compare(p2.col(), p1.col()); // 0 if col of p2 = cols of p1 , less than 0 if cols of p2 < cols of p1 , >0 if cols of p2 > cols of p1
        if(check == 0 ){
            return Integer.compare(p2.row(),p1.row());
        }
        return check;
    }
}
