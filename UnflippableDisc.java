public class UnflippableDisc implements Disc {
    private Player p;
    private final boolean flipped = false;
    private boolean boom = false;

    public UnflippableDisc(Player currentPlayer) {
            this.p = currentPlayer;
    }

    @Override
    public Player getOwner() {
        return this.p;
    }


    @Override
    public void reverseFlipped() {
    }

    @Override
    public void setFlipped(boolean flipped) {}

    @Override
    public void setOwner(Player player) {
        this.p = player;
    }

    @Override
    public void set_bombFlag(boolean flag){
        boom = flag;
    }
    @Override
    public boolean bombFlag(){
        return true;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}
