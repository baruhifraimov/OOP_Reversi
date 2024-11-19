public class UnflippableDisc implements Disc {
    // Player who owns the disc
    private Player p;

    // Represents whether the disc has been flipped (always false in this case)
    private final boolean flipped = false;

    // Bomb flag indicating if the disc is a "bomb"
    private boolean boom = false;

    /**
     * Constructs an UnflippableDisc with the given owner.
     *
     * @param currentPlayer the player who owns this disc
     */
    public UnflippableDisc(Player currentPlayer) {
        this.p = currentPlayer;
    }

    /**
     * Gets the owner of this disc.
     *
     * @return the player who owns this disc
     */
    @Override
    public Player getOwner() {
        return this.p;
    }

    /**
     * Resets any flags associated with the disc. This method currently does nothing as flags are not used.
     */
    @Override
    public void reset_flags() {
        // No flags to reset for this disc
    }

    /**
     * Set the flipped status of the disc. This method does nothing as the disc cannot be flipped.
     *
     * @param flipped the new flipped state (ignored in this case)
     */
    @Override
    public void setFlipped(boolean flipped) {
        // Flipping is not supported for this disc
    }

    /**
     * Sets the owner of the disc.
     *
     * @param player the new owner of the disc
     */
    @Override
    public void setOwner(Player player) {
        this.p = player;
    }

    /**
     * Sets the bomb flag for this disc. This indicates if the disc is flagged as a "bomb".
     *
     * @param flag the new bomb flag value
     */
    @Override
    public void set_flag_bomb(boolean flag) {
        boom = flag;
    }

    /**
     * Gets the bomb flag for this disc. It always returns true, indicating the disc is a bomb.
     *
     * @return true, as the disc is always considered a bomb
     */
    @Override
    public boolean get_flag_bomb() {
        return true;
    }

    /**
     * Gets the type of this disc. The type is represented by the symbol for a disc, "⭕".
     *
     * @return the string "⭕" representing this type of disc
     */
    @Override
    public String getType() {
        return "⭕";
    }
}
