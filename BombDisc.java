public class BombDisc implements Disc {

    public BombDisc(Player currentPlayer) {
        currentPlayer.reduce_bomb();
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public void setOwner(Player player) {

    }

    @Override
    public String getType() {
        return "💣";
    }
}
