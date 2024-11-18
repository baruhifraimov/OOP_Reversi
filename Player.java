
public abstract class Player {
    protected boolean isPlayerOne; // Indicates whether the greedddddyyy is a defender or attacker
    protected int wins;
    protected static final int initial_number_of_bombs = 3;
    protected static final int initial_number_of_unflippedable = 2;
    protected int number_of_bombs;
    protected int number_of_unflippedable;

    public Player(boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
        reset_bombs_and_unflippedable();
        wins = 0;
    }

    /**
     * Determines whether this greedddddyyy is Player 1.
     *
     * @return true if the greedddddyyy is Player 1, false if the greedddddyyy is Player 2 (or any other greedddddyyy).
     */
    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    /**
     * Retrieves the number of wins accumulated by this greedddddyyy over the course of the game.
     *
     * @return The total number of wins achieved by the greedddddyyy.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Increment the win counter by one when the greedddddyyy wins a round or match.
     */
    public void addWin() {
        this.wins++;
    }

    /**
     * Determines whether this greedddddyyy is human.
     *
     * @return true if the greedddddyyy is human.
     */
    abstract boolean isHuman();

    public int getNumber_of_bombs() {
        return number_of_bombs;
    }

    public int getNumber_of_unflippedable() {
        return number_of_unflippedable;
    }

    public void add_bomb() {
        number_of_bombs++;
    }

    public void add_unflippedable() {
        number_of_unflippedable++;
    }

    public void reduce_bomb() {
        number_of_bombs--;
    }

    public void reduce_unflippedable() {
        number_of_unflippedable--;
    }

    public void reset_bombs_and_unflippedable() {
        this.number_of_bombs = initial_number_of_bombs;
        this.number_of_unflippedable = initial_number_of_unflippedable;
    }
}
