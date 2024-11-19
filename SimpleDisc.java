/**
 * Represents a simple disc in a Reversi-like game. A simple disc has an owner (a player)
 * and may have various flags for game mechanics, such as being flipped or acting as a bomb.
 */
public class SimpleDisc implements Disc {

    // The player who owns this disc
    private Player p;

    // Indicates whether the disc has been flipped
    private boolean flipped;

    // Indicates whether the disc is currently flagged as a bomb
    private boolean boom = false;

    // A specific flag related to bomb functionality for simple discs
    private boolean simple_flag_bomb;

    /**
     * Constructs a SimpleDisc with the specified owner.
     *
     * @param currentPlayer the player who owns this disc.
     */
    public SimpleDisc(Player currentPlayer) {
        this.p = currentPlayer;
    }

    /**
     * Retrieves the owner of the disc.
     *
     * @return the {@link Player} who owns this disc.
     */
    @Override
    public Player getOwner() {
        return this.p;
    }

    /**
     * Sets the owner of the disc.
     *
     * @param player the {@link Player} to set as the owner of this disc.
     */
    @Override
    public void setOwner(Player player) {
        this.p = player;
    }

    /**
     * Returns the type of the disc as a string representation.
     *
     * @return a string representing the disc type, "⬤" for a simple disc.
     */
    @Override
    public String getType() {
        return "⬤";
    }

    /**
     * Retrieves the bomb flag status of the disc.
     *
     * @return true if the disc is flagged as a bomb, false otherwise.
     */
    @Override
    public boolean get_flag_bomb() {
        return boom;
    }

    /**
     * Sets the bomb flag status of the disc.
     *
     * @param flag the new bomb flag status.
     */
    @Override
    public void set_flag_bomb(boolean flag) {
        boom = flag;
    }

    /**
     * Sets the special bomb flag specific to simple discs.
     *
     * @param flag the new value for the simple bomb flag.
     */
    public void set_simple_flag_bomb(boolean flag) {
        simple_flag_bomb = flag;
    }

    /**
     * Retrieves the special bomb flag specific to simple discs.
     *
     * @return true if the simple bomb flag is active, false otherwise.
     */
    public boolean get_simple_flag_bomb() {
        return simple_flag_bomb;
    }

    /**
     * Resets the flags for this disc, including flipped and bomb statuses.
     */
    @Override
    public void reset_flags() {
        flipped = false;
        boom = false;
    }

    /**
     * Sets the flipped status of the disc.
     *
     * @param flipped the new flipped status of the disc.
     */
    @Override
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }
}
