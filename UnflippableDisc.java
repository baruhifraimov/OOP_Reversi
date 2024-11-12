public class UnflippableDisc implements Disc {
    private Player p;

    public UnflippableDisc(Player currentPlayer) {
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
        return "⭕";
    }
}
