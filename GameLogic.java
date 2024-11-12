import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GameLogic implements PlayableLogic {
    private final int BOARDSIZE = 8;
    private Player p1, p2;
    private Disc[][] boardDiscs = new Disc[BOARDSIZE][BOARDSIZE]; // Locating disc position on the board
    private Player lastPlayer = getSecondPlayer(); //Checks who was last
    LinkedList<Position> history = new LinkedList<>(); // Collects all the disc locations on the board

    public GameLogic() {
        super();
    }

    private Player currentPlayer() {
        if (lastPlayer == p1) {
            return p2;
        }
        return p1;
    }


    private void initBoard() {
        boardDiscs = new Disc[BOARDSIZE][BOARDSIZE];
        int midBoard = BOARDSIZE / 2;
        boardDiscs[midBoard][midBoard] = new SimpleDisc(p1);
        boardDiscs[midBoard - 1][midBoard - 1] = new SimpleDisc(p1);
        boardDiscs[midBoard][midBoard - 1] = new SimpleDisc(p2);
        boardDiscs[midBoard - 1][midBoard] = new SimpleDisc(p2);
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        ValidMoves();
        if (boardDiscs[a.row()][a.col()] == null && countFlips(a) > 0) {
            if (lastPlayer != p1) {
                disc.setOwner(p1);
                if (disc.getType().equals("💣")) {
                    if (p1.getNumber_of_bombs() > 0) {
                        p1.reduce_bomb();
                        boardDiscs[a.row()][a.col()] = disc;
                        history.addLast(new Position(a.row(), a.col()));
                        System.out.printf("Player 1 placed a %s in (%d,%d)\n No. of Bombs discs left: %d\n", disc.getType(), a.row(), a.col(), p1.getNumber_of_bombs());
                        System.out.println();
                        lastPlayer = p1;
                    }
                } else if (disc.getType().equals("⭕")) {
                    if (p1.getNumber_of_unflippedable() > 0) {
                        p1.reduce_unflippedable();
                        boardDiscs[a.row()][a.col()] = disc;
                        history.addLast(new Position(a.row(), a.col()));
                        System.out.printf("Player 1 placed a %s in (%d,%d)\n No. of Unflippable discs left: %d\n", disc.getType(), a.row(), a.col(), p1.getNumber_of_unflippedable());
                        System.out.println();
                        lastPlayer = p1;
                    }

                } else if (disc.getType().equals("⬤")) {
                    boardDiscs[a.row()][a.col()] = disc;
                    history.addLast(new Position(a.row(), a.col()));
                    System.out.printf("Player 1 placed a %s in (%d,%d)\n", disc.getType(), a.row(), a.col());
                    System.out.println();
                    lastPlayer = p1;
                } else {
                    return false;
                }

            } else {
                disc.setOwner(p2);
                if (disc.getType().equals("💣")) {
                    if (p2.getNumber_of_bombs() >= 0) {
                        p2.reduce_bomb();
                        boardDiscs[a.row()][a.col()] = disc;
                        System.out.printf("Player 2 placed a %s in (%d,%d)\n No. of Bombs discs left: %d\n", disc.getType(), a.row(), a.col(), p2.getNumber_of_bombs());
                        history.addLast(new Position(a.row(), a.col()));
                        System.out.println();
                        lastPlayer = p2;
                    }
                } else if (disc.getType().equals("⭕")) {
                    if (p2.getNumber_of_unflippedable() >= 0) {
                        p2.reduce_unflippedable();
                        boardDiscs[a.row()][a.col()] = disc;
                        System.out.printf("Player 2 placed a %s in (%d,%d)\n No. of Unflippeable discs left: %d\n", disc.getType(), a.row(), a.col(), p2.getNumber_of_unflippedable());
                        history.addLast(new Position(a.row(), a.col()));
                        System.out.println();
                        lastPlayer = p2;
                    }

                } else if (disc.getType().equals("⬤")) {
                    boardDiscs[a.row()][a.col()] = disc;
                    System.out.printf("Player 2 placed a %s in (%d,%d)\n", disc.getType(), a.row(), a.col());
                    history.addLast(new Position(a.row(), a.col()));
                    System.out.println();
                    lastPlayer = p2;
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        return boardDiscs[position.row()][position.col()];
    }

    @Override
    public int getBoardSize() {
        return BOARDSIZE;
    }

    //A valid move is one where at least one piece is reversed (flipped over).
    @Override
    public List<Position> ValidMoves() {
        List<Position> vMoves = new ArrayList<>();
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (boardDiscs[i][j] == null) {
                    if (countFlips(new Position(i, j)) > 0) {
                        vMoves.add(new Position(i, j));
                    }
                }
            }
        }
        return vMoves;
    }

    @Override
    public int countFlips(Position a) {
        int down = auxCountFlips(a, +1, 0);
        int up = auxCountFlips(a, -1, 0);
        int left = auxCountFlips(a, 0, -1);
        int right = auxCountFlips(a, 0, +1);
        int dur = auxCountFlips(a, -1, +1);
        int dul = auxCountFlips(a, -1, -1);
        int ddr = auxCountFlips(a, +1, +1);
        int ddl = auxCountFlips(a, +1, -1);
        return down + up + left + right + dur + dul + ddr + ddl;
//        int flipCounter = 0, upCounter=0,downCounter=0,leftCounter=0,rightCounter=0,durCounter=0,dulCounter=0,ddrCounter=0,ddlCounter=0;
//        //DOWN
//        for (int i = a.row() + 1; i < BOARDSIZE; i++) {
//            if (boardDiscs[i][a.col()] != null && boardDiscs[i][a.col()].getOwner().equals(lastPlayer)) {
//                downCounter++;
//            }
//            else if (boardDiscs[i][a.col()] != null && boardDiscs[i][a.col()].getOwner().equals(currentPlayer())){
//                flipCounter += downCounter;
//                break;
//            }
//                else{
//                    break;
//            }
//        }
//        //UP
//        for (int i = a.row() - 1; i >= 0; i--) {
//            if (boardDiscs[i][a.col()] != null && boardDiscs[i][a.col()].getOwner().equals(lastPlayer)) {
//                upCounter++;
//            }
//            else if(boardDiscs[i][a.col()] != null && boardDiscs[i][a.col()].getOwner().equals(currentPlayer())){
//                flipCounter+= upCounter;
//                break;
//            }
//            else{
//                break;
//
//
//            }
//        }
//        //LEFT
//        for (int i = a.col() - 1; i >= 0; i--) {
//            if (boardDiscs[a.row()][i] != null && boardDiscs[a.row()][i].getOwner().equals(lastPlayer)) {
//                leftCounter++;
//            }
//            else if(boardDiscs[a.row()][i] != null && boardDiscs[a.row()][i].getOwner().equals(currentPlayer())){
//                flipCounter += leftCounter;
//                break;
//            }
//            else {
//                break;
//
//
//            }
//        }
//        //RIGHT
//        for (int i = a.col() + 1; i < BOARDSIZE; i++) {
//            if (boardDiscs[a.row()][i] != null && boardDiscs[a.row()][i].getOwner().equals(lastPlayer)) {
//                rightCounter++;
//            }
//            else if(boardDiscs[a.row()][i] != null && boardDiscs[a.row()][i].getOwner().equals(currentPlayer())){
//                flipCounter += rightCounter;
//                break;
//            }
//            else{
//                break;
//
//
//            }
//        }
//        // DIAGONAL UP RIGHT
//        for (int i = a.col() + 1, j = a.row() - 1; i < BOARDSIZE && j >= 0; i++, j--) {
//            if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(lastPlayer)) {
//                    durCounter++;
//                } else if(boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(currentPlayer())) {
//                flipCounter+= durCounter;
//                    break;
//                }
//            else{
//                break;
//            }
//            }
//
//        // DIAGONAL UP LEFT
//        for (int i = a.col() - 1, j = a.row() - 1; i >= 0 && j >= 0; i--, j--) {
//            if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(lastPlayer)) {
//                dulCounter++;
//            }
//            else if(boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(currentPlayer())){
//                flipCounter+=dulCounter;
//            }
//            else {
//                break;
//            }
//        }
//        // DIAGONAL DOWN RIGHT
//        for (int i = a.col() + 1, j = a.row() + 1; i < BOARDSIZE && j < BOARDSIZE; i++, j++) {
//            if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(lastPlayer)) {
//                ddrCounter++;
//            }
//            else if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(currentPlayer())){
//                flipCounter +=ddrCounter;
//            }else {
//                break;
//            }
//        }
//        // DIAGONAL DOWN LEFT
//        for (int i = a.col() - 1, j = a.row() + 1; i >= 0 && j < BOARDSIZE; i--, j++) {
//            if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(lastPlayer)) {
//                ddlCounter++;
//            }
//            else if(boardDiscs[j][i] != null && boardDiscs[j][i].getOwner().equals(currentPlayer())){
//                flipCounter+=ddlCounter;
//            }
//            else {
//                break;
//            }
//        }
//        return flipCounter;
    }

    private int auxCountFlips(Position a, int m_row, int m_col) {
        int counter = 0, flipCounter = 0;
        while (((a.row() + m_row) < BOARDSIZE) && ((a.row() + m_row) >= 0) && ((a.col() + m_col) < BOARDSIZE) && ((a.col() + m_col) >= 0)) {
            if (boardDiscs[a.row() + m_row][a.col() + m_col] != null && boardDiscs[a.row() + m_row][a.col() + m_col].getOwner().equals(lastPlayer)) {
                counter++;
                m_row += m_row;
                m_col += m_col;
            }
            else if (boardDiscs[a.row() + m_row][a.col() + m_col] != null && boardDiscs[a.row() + m_row][a.col() + m_col].getOwner().equals(currentPlayer())) {
                flipCounter += counter;
                break;
            }
            else{
                break;
            }
        }
        return flipCounter;
    }


    @Override
    public Player getFirstPlayer() {
        return this.p1;
    }

    @Override
    public Player getSecondPlayer() {
        return this.p2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.p1 = player1;
        this.p2 = player2;
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return lastPlayer != p1;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public void reset() {
        lastPlayer = p2;
        history.clear();
        p1.reset_bombs_and_unflippedable();
        p2.reset_bombs_and_unflippedable();
        initBoard();
    }

    @Override
    public void undoLastMove() {
        if (!history.isEmpty() && (p1.isHuman() && p2.isHuman())) {
            int h_row = history.peekLast().row();
            int h_col = history.peekLast().col();
            //Undo: removing ⬤ from (4, 2)
            //Undo: flipping back ⬤ in (4, 3)
            System.out.printf("Undo: removing %s from (%d,%d)\n\n", boardDiscs[h_row][h_col].getType(), h_row, h_col);
            lastPlayer = currentPlayer();
            if (boardDiscs[h_row][h_col].getType().equals("💣")) {
                boardDiscs[h_row][h_col].getOwner().add_bomb();
                System.out.printf("Restoring %s Bombs: %d left\n", lastPlayer.toString(), lastPlayer.getNumber_of_bombs());
                boardDiscs[h_row][h_col] = null;
                history.removeLast();
            } else if (boardDiscs[h_row][h_col].getType().equals("⭕")) {
                boardDiscs[h_row][h_col].getOwner().add_unflippedable();
                System.out.printf("Restoring %s Bombs: %d left", lastPlayer.toString(), lastPlayer.getNumber_of_unflippedable());
                boardDiscs[h_row][h_col] = null;
                history.removeLast();
            } else {
                boardDiscs[h_row][h_col] = null;
                history.removeLast();
            }

        }
    }
}
