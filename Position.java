public class Position {
    // Row index of the position
    private int row;

    // Column index of the position
    private int col;

    /**
     * Constructs a {@code Position} object with the specified row and column.
     *
     * @param row the row index of the position
     * @param col the column index of the position
     */
    public Position(int row, int col){
        this.row = row;
        this.col = col;
    }

    /**
     * Constructs a new {@code Position} object by copying another position.
     * The row and column values of the provided position are copied directly.
     *
     * @param position the position to copy
     */
    public Position(Position position){
        this.row = position.row();
        this.col = position.col();
    }


    /**
     * Gets the row index of this position.
     *
     * @return the row index
     */
    public int row(){
        return this.row;
    }

    /**
     * Gets the column index of this position.
     *
     * @return the column index
     */
    public int col(){
        return this.col;
    }

    /**
     * Sets a new value for the column index of this position.
     *
     * @param new_col the new column index to set
     */
    public void setCol(int new_col){
        this.col = new_col;
    }

    /**
     * Sets a new value for the row index of this position.
     *
     * @param new_row the new row index to set
     */
    public void setRow(int new_row){
        this.row = new_row;
    }

    /**
     * Checks if this position is equal to another object.
     * Two positions are considered equal if their row and column indices are the same.
     *
     * @param o the object to compare with this position
     * @return {@code true} if the positions are equal, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return row == position.row() && col == position.col();
    }

    /**
     * Returns a string representation of this position in the format "(row, col)".
     *
     * @return a string representing this position
     */
    @Override
    public String toString() {
        return "(" + this.row + "," + this.col + ")";
    }

    /**
     * if there is more than one Move that is the biggest one , than choose the one that is in the rightmost slot
     * @param o the object to be compared.
     * @return an int , -1 if less than , 0 if equals , 1 if bigger than.
     */
  /*  @Override
    public int compareTo(Position o) {
        return this.col() - o.col();
    }
    */
}
