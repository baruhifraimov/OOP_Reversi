public class SimpleDisc implements Disc {
    private Player p;
    boolean flipped;

    public SimpleDisc(Player currentPlayer) {
        this.p = currentPlayer;
    }

    @Override
    public Player getOwner() {
        return this.p;
    }

    @Override
    public void setOwner(Player player) {
        this.p = player;

    }

    @Override
    public String getType() {
        return "⬤";
    }

    @Override
    public boolean beenFlipped() {
        return flipped;
    }

    @Override
    public void reverseFlipped(){
        flipped = false;
    }

    @Override
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }


}
