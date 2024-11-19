public class HumanPlayer extends Player {

    /**
     * Constructs a HumanPlayer object and initializes it as either Player One or Player Two.
     *
     * @param isPlayerOne a boolean indicating if this player is Player One.
     *                    - true if Player One.
     *                    - false if Player Two.
     */
    public HumanPlayer(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * Indicates that this player is a human.
     *
     * @return {@code true}, since this class represents a human player.
     */
    @Override
    boolean isHuman() {
        return true;
    }
}
