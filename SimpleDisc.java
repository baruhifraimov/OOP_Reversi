public class SimpleDisc implements Disc {
    private Player p;

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
}
