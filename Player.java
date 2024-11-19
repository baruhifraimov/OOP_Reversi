public abstract class Player {
    /** Indicates whether the player is Player One. */
    protected boolean isPlayerOne;

    /** Tracks the number of wins achieved by the player. */
    protected int wins;

    /** The initial number of bombs available to each player. */
    protected static final int initial_number_of_bombs = 3;

    /** The initial number of unflippable discs available to each player. */
    protected static final int initial_number_of_unflippedable = 2;

    /** The current number of bombs available to the player. */
    protected int number_of_bombs;

    /** The current number of unflippable discs available to the player. */
    protected int number_of_unflippedable;

    /**
     * Constructs a new Player instance and initializes the player's state.
     *
     * @param isPlayerOne true if the player is Player One, false otherwise.
     */
    public Player(boolean isPlayerOne) {
        this.isPlayerOne = isPlayerOne;
        reset_bombs_and_unflippedable();
        wins = 0;
    }

    /**
     * Determines whether the player is Player One.
     *
     * @return true if the player is Player One, false otherwise.
     */
    public boolean isPlayerOne() {
        return isPlayerOne;
    }

    /**
     * Retrieves the number of wins accumulated by this player.
     *
     * @return The total number of wins achieved by the player.
     */
    public int getWins() {
        return wins;
    }

    /**
     * Increments the win counter by one when the player wins a round or match.
     */
    public void addWin() {
        this.wins++;
    }

    /**
     * Determines whether this player is human.
     * This method must be implemented by subclasses to specify player type.
     *
     * @return true if the player is human, false otherwise.
     */
    abstract boolean isHuman();

    /**
     * Retrieves the number of bombs available to the player.
     *
     * @return The current number of bombs.
     */
    public int getNumber_of_bombs() {
        return number_of_bombs;
    }

    /**
     * Retrieves the number of unflippable discs available to the player.
     *
     * @return The current number of unflippable discs.
     */
    public int getNumber_of_unflippedable() {
        return number_of_unflippedable;
    }

    /**
     * Adds one bomb to the player's inventory.
     */
    public void add_bomb() {
        number_of_bombs++;
    }

    /**
     * Adds one unflippable disc to the player's inventory.
     */
    public void add_unflippedable() {
        number_of_unflippedable++;
    }

    /**
     * Reduces the number of bombs in the player's inventory by one.
     * This should only be called when the player uses a bomb.
     */
    public void reduce_bomb() {
        number_of_bombs--;
    }

    /**
     * Reduces the number of unflippable discs in the player's inventory by one.
     * This should only be called when the player uses an unflippable disc.
     */
    public void reduce_unflippedable() {
        number_of_unflippedable--;
    }

    /**
     * Resets the number of bombs and unflippable discs to their initial values.
     * This is typically used at the start of a new game.
     */
    public void reset_bombs_and_unflippedable() {
        this.number_of_bombs = initial_number_of_bombs;
        this.number_of_unflippedable = initial_number_of_unflippedable;
    }
}
