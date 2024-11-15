public class BombDisc implements Disc {
    private Player p;
    private boolean flipped,boom = false;

    public BombDisc(Player currentPlayer) {
            this.p = currentPlayer;
    }

    @Override
    public Player getOwner() {
        return this.p;
    }

    @Override
    public void set_bombFlag(boolean flag){
        boom = flag;
    }
    @Override
    public boolean bombFlag(){
        return boom;
    }

    @Override
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    @Override
    public void reverseFlipped() { //cancels the flip flag that has been set to true
        flipped = false;
        boom = false;
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
