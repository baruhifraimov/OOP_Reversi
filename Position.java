public class Position {
    private int row;
    private int col;

    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    public int row(){
        return this.row;
    }

    public int col(){
        return this.col;
    }

    @Override
    public String toString() {
        return "("+ this.row + "," + this.col+")";
    }
}
