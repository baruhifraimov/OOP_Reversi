public class Move {

    // The position where the disc is placed
    Position position;

    // The disc placed at the given position
    Disc disc;

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
}
