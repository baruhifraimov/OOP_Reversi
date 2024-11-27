public class Move {

    // The position where the disc is placed
    private final Position position;

    // The disc placed at the given position
    private final Disc disc;

    /**
     * Constructs a Move with the specified position and disc.
     *
     * @param position the position on the board where the disc is placed
     * @param disc the disc that is placed at the given position
     */
    public Move(Position position, Disc disc){
        this.position = position;
        this.disc = disc;
    }

    /**
     * Gets the position associated with this move.
     *
     * @return the position where the disc is placed
     */
    public Position position(){
        return this.position;
    }

    /**
     * Gets the disc associated with this move.
     *
     * @return the disc that is placed at the given position
     */
    public Disc disc(){
        return this.disc;
    }
/**
 * Prints a message indicating the undoing of the last move.
 * If there is no previous move, it prints a message indicating that.
 */
public void printUndoMove() {
        if (disc != null) {
            System.out.printf("\tUndo: removing %s from (%d,%d)\n", disc.getType(), position.row(), position.col());
        } else {
            System.out.println("\tNo previous move available to undo.");
        }
    }

    /**
     * Prints a message indicating the undoing of a disc flip.
     *
     * @param p the position where the disc is flipped back
     * @param disc the disc that is flipped back
     */
    public void printUndoFlip(Position p, Disc disc) {
        if (disc != null) {
            System.out.printf("\tUndo: flipping back %s in (%d,%d)\n", disc.getType(), p.row(), p.col());
        }
    }

}
