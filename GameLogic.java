import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private final int BOARDSIZE = 8;
    private Player p1, p2;
    private Disc[][] boardDiscs = new Disc[BOARDSIZE][BOARDSIZE]; // Locating disc position on the board
    private Player lastPlayer; //Checks who was last
    private Stack<Position> discsFlipStacker;
    private Stack<Disc> flipHistory;
    private boolean flip_enabler;
    private Stack<Position> moveHistory; // Collects all the disc locations on the board

    public GameLogic() {
        super();
        flipHistory = new Stack<>();
        moveHistory = new Stack<>();
        discsFlipStacker = new Stack<>();
        lastPlayer = getSecondPlayer();
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
        if (boardDiscs[a.row()][a.col()] == null && countFlips(a) > 0) {
            flip_enabler = true;// Move is enabled, flag for enabling flipping
            if (lastPlayer != p1) {
                flipAction(a);
                if (disc.getType().equals("💣")) {
                    if (p1.getNumber_of_bombs() > 0) {
                        p1.reduce_bomb();
                        boardDiscs[a.row()][a.col()] = disc;
                        moveHistory.addLast(new Position(a.row(), a.col()));
                        System.out.printf("Player 1 placed a %s in (%d,%d)\n No. of Bombs discs left: %d\n", disc.getType(), a.row(), a.col(), p1.getNumber_of_bombs());
                        System.out.println();
                        lastPlayer = p1;
                    }
                } else if (disc.getType().equals("⭕")) {
                    if (p1.getNumber_of_unflippedable() > 0) {
                        p1.reduce_unflippedable();
                        boardDiscs[a.row()][a.col()] = disc;
                        moveHistory.addLast(new Position(a.row(), a.col()));
                        System.out.printf("Player 1 placed a %s in (%d,%d)\n No. of Unflippable discs left: %d\n", disc.getType(), a.row(), a.col(), p1.getNumber_of_unflippedable());
                        System.out.println();
                        lastPlayer = p1;
                    }

                } else if (disc.getType().equals("⬤")) {
                    boardDiscs[a.row()][a.col()] = disc;
                    moveHistory.addLast(new Position(a.row(), a.col()));
                    System.out.printf("Player 1 placed a %s in (%d,%d)\n", disc.getType(), a.row(), a.col());
                    System.out.println();
                    lastPlayer = p1;
                } else {
                    return false;
                }

            } else {
                flipAction(a);
                if (disc.getType().equals("💣")) {
                    if (p2.getNumber_of_bombs() >= 0) {
                        p2.reduce_bomb();
                        boardDiscs[a.row()][a.col()] = disc;
                        System.out.printf("Player 2 placed a %s in (%d,%d)\n No. of Bombs discs left: %d\n", disc.getType(), a.row(), a.col(), p2.getNumber_of_bombs());
                        moveHistory.addLast(new Position(a.row(), a.col()));
                        System.out.println();
                        lastPlayer = p2;
                    }
                } else if (disc.getType().equals("⭕")) {
                    if (p2.getNumber_of_unflippedable() >= 0) {
                        p2.reduce_unflippedable();
                        boardDiscs[a.row()][a.col()] = disc;
                        System.out.printf("Player 2 placed a %s in (%d,%d)\n No. of Unflippeable discs left: %d\n", disc.getType(), a.row(), a.col(), p2.getNumber_of_unflippedable());
                        moveHistory.addLast(new Position(a.row(), a.col()));
                        System.out.println();
                        lastPlayer = p2;
                    }

                } else if (disc.getType().equals("⬤")) {
                    boardDiscs[a.row()][a.col()] = disc;
                    System.out.printf("Player 2 placed a %s in (%d,%d)\n", disc.getType(), a.row(), a.col());
                    moveHistory.addLast(new Position(a.row(), a.col()));
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
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (boardDiscs[i][j] != null) {
                    boardDiscs[i][j].reverseFlipped();
                }
            }
        }
        return down + up + left + right + dur + dul + ddr + ddl;
    }

    public void flipPositionFinder(Position a) {
        auxCountFlips(a, +1, 0);
        auxCountFlips(a, -1, 0);
        auxCountFlips(a, 0, -1);
        auxCountFlips(a, 0, +1);
        auxCountFlips(a, -1, +1);
        auxCountFlips(a, -1, -1);
        auxCountFlips(a, +1, +1);
        auxCountFlips(a, +1, -1);
    }

    private void flipAction(Position a) {
        flipPositionFinder(a);
        while (!discsFlipStacker.isEmpty()) {
            Position i = discsFlipStacker.peek();
            Disc d = boardDiscs[i.row()][i.col()];
            if (!d.getOwner().equals(currentPlayer()) && !d.beenFlipped()) {
                d.setOwner(currentPlayer());
                System.out.printf("Flipped %s\n", i);
                flipHistory.push(d);
                discsFlipStacker.pop();
            } else {
                discsFlipStacker.pop();
            }
        }
        flip_enabler = false;
    }

    private int auxCountFlips(Position a, int m_row, int m_col) {
        int counter = 0, flipCounter = 0;
        int rep_row = m_row, rep_col = m_col;
        Stack<Position> discsFlipStackerCheck = new Stack<>();
        // scan the whole board
        while (((a.row() + m_row) < BOARDSIZE) && ((a.row() + m_row) >= 0) && ((a.col() + m_col) < BOARDSIZE) && ((a.col() + m_col) >= 0)) {
            // check if it's not empty and if it's my opponent disc
            if (boardDiscs[a.row() + m_row][a.col() + m_col] != null && boardDiscs[a.row() + m_row][a.col() + m_col].getOwner().equals(lastPlayer)) {
                counter++;
                //Checks if the player turn is on.
                if (flip_enabler) {
                    //checks if the discs been flipped before to prevent from overlapping
                    if (!boardDiscs[a.row() + m_row][a.col() + m_col].beenFlipped()) {
                        // add to the flip stack
                        discsFlipStackerCheck.add(new Position(a.row() + m_row, a.col() + m_col));
                    }
                }
            }
            // if no discs left 'and' we got our current player on the other side
            else if (boardDiscs[a.row() + m_row][a.col() + m_col] != null && boardDiscs[a.row() + m_row][a.col() + m_col].getOwner().equals(currentPlayer())) {
                flipCounter += counter;
                while (!discsFlipStackerCheck.isEmpty()) {
                    discsFlipStacker.add(discsFlipStackerCheck.pop());
                }
                break;
            } else {
                discsFlipStackerCheck.clear();
                break;
            }
            m_row += rep_row;
            m_col += rep_col;
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
        moveHistory.clear();
        p1.reset_bombs_and_unflippedable();
        p2.reset_bombs_and_unflippedable();
        discsFlipStacker.clear();
        initBoard();
        System.out.println("\nTHE GAME HAS INITIALIZED RESET\n\n\n\n\n\n\n\n\n\n ");
    }

    @Override
    public void undoLastMove() {
        if (!moveHistory.isEmpty() && (p1.isHuman() && p2.isHuman())) {
            int h_row = moveHistory.peek().row();
            int h_col = moveHistory.peek().col();
            //Undo: removing ⬤ from (4, 2)
            //Undo: flipping back ⬤ in (4, 3)
            System.out.printf("Undo: removing %s from (%d,%d)\n\n", boardDiscs[h_row][h_col].getType(), h_row, h_col);
            //Undo flip
            flipHistory.pop().setOwner(currentPlayer());
            lastPlayer = currentPlayer();
            if (boardDiscs[h_row][h_col].getType().equals("💣")) {
                boardDiscs[h_row][h_col].getOwner().add_bomb();
                System.out.printf("Restoring %s Bombs: %d left\n", lastPlayer.toString(), lastPlayer.getNumber_of_bombs());
                boardDiscs[h_row][h_col] = null;
                moveHistory.removeLast();
            } else if (boardDiscs[h_row][h_col].getType().equals("⭕")) {
                boardDiscs[h_row][h_col].getOwner().add_unflippedable();
                System.out.printf("Restoring %s Bombs: %d left", lastPlayer.toString(), lastPlayer.getNumber_of_unflippedable());
                boardDiscs[h_row][h_col] = null;
                moveHistory.removeLast();
            } else {
                boardDiscs[h_row][h_col] = null;
                moveHistory.removeLast();
            }

        }
    }
}
