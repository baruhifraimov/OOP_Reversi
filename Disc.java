/**
 * The Disc interface defines the characteristics of a game in a chess-like game.
 * Implementing classes should provide information about the player who owns the Disc.
 */
public interface Disc {

    /**
     * Get the player who owns the Disc.
     *
     * @return The player who is the owner of this game disc.
     */
    Player getOwner();

    boolean get_flag_bomb();
    void set_flag_bomb(boolean flag);
    void reset_flags();

    void setFlipped(boolean flipped);

    /**
     * Set the player who owns the Disc.
     *
     */
    void setOwner(Player player);

    /**
     * Get the type of the disc.
     * use the:
     *          "⬤",         "⭕"                "💣"
     *      Simple Disc | Unflippedable Disc | Bomb Disc |
     * respectively.
     */
    String getType();

}