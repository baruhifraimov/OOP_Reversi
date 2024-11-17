import java.util.Objects;

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

    public void setCol(int new_col){
        this.col = new_col;
    }

    public void setRow(int new_row){
        this.row = new_row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row() && col == position.col();
    }


    @Override
    public String toString() {
        return "("+ this.row + "," + this.col+")";
    }
}
