/**
 * Represents a bomb disc in a Reversi-like game. A bomb disc has special behavior
 * that may include flipping or exploding mechanics during gameplay.
 */
public class BombDisc implements Disc {

    // The player who owns this disc
    private Player p;

    // Indicates whether the disc has been flipped
    private boolean flipped;

    // Indicates whether the disc is currently flagged as a bomb
    private boolean boom = false;

    /**
     * Constructs a BombDisc with the specified owner.
     *
     * @param currentPlayer the player who owns this disc.
     */
    public BombDisc(Player currentPlayer) {
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
     * Marks the disc as flipped when ownership is transferred.
     *
     * @param player the {@link Player} to set as the owner of this disc.
     */
    @Override
    public void setOwner(Player player) {
        this.p = player;
        flipped = true; // Marks the disc as flipped when ownership changes
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
     * Sets the flipped status of the disc.
     *
     * @param flipped the new flipped status of the disc.
     */
    @Override
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
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
     * Returns the type of the disc as a string representation.
     *
     * @return a string representing the disc type, "💣" for a bomb disc.
     */
    @Override
    public String getType() {
        return "💣";
    }
}
