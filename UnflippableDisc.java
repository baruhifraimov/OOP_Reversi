public class UnflippableDisc implements Disc {
    private Player p;
    boolean flipped;

    public UnflippableDisc(Player currentPlayer) {
            this.p = currentPlayer;
    }

    @Override
    public Player getOwner() {
        return this.p;
    }

    @Override
    public boolean beenFlipped() {
        return flipped;
    }

    @Override
    public void reverseFlipped() {
        flipped = false;
    }

    @Override
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    @Override
    public void setOwner(Player player) {
        this.p = player;
        flipped = true;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}
