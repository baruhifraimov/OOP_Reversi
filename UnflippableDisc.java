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
    public void reset_flags() {
    }

    @Override
    public void setFlipped(boolean flipped) {}

    @Override
    public void setOwner(Player player) {
        this.p = player;
    }

    @Override
    public void set_flag_bomb(boolean flag){
        boom = flag;
    }
    @Override
    public boolean get_flag_bomb(){
        return true;
    }

    @Override
    public String getType() {
        return "⭕";
    }
}
