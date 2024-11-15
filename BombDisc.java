public class BombDisc implements Disc {
    private Player p;
    boolean flipped;

    public BombDisc(Player currentPlayer) {
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
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    @Override
    public void reverseFlipped() {
        flipped = false;
    }

    @Override
    public void setOwner(Player player) {
        this.p = player;
        flipped = true;
    }

    @Override
    public String getType() {
        return "💣";
    }
}
