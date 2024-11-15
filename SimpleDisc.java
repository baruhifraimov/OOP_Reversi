public class SimpleDisc implements Disc {
    private Player p;
    private boolean flipped,boom = false;

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
    public boolean bombFlag(){
        return boom;
    }
    @Override
    public void set_bombFlag(boolean flag){
        boom = flag;
    }

    @Override
    public void reverseFlipped(){
        flipped = false;
        boom = false;
    }

    @Override
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }


}
