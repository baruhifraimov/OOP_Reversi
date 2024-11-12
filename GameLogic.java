import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GameLogic implements PlayableLogic {
    private final int BOARDSIZE = 8;
    private Player p1, p2;
    private Disc[][] boardDiscs = new Disc[BOARDSIZE][BOARDSIZE]; // Locating disc position on the board
    private Player lastPlayer; //Checks who was last
    LinkedList<Position> history = new LinkedList<>(); // Collects all the disc locations on the board

    public GameLogic() {

    }

    private Player beforeLastPlayer() {
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
        if (boardDiscs[a.row()][a.col()] == null) {
            if (lastPlayer != p1) {
                disc.setOwner(p1);
                boardDiscs[a.row()][a.col()] = disc;
                history.addLast(new Position(a.row(), a.col()));
                System.out.printf("Player 1 placed a %s in (%d,%d)\n", disc.getType(), a.row(), a.col());
                System.out.println();
                lastPlayer = p1;
            } else {
                disc.setOwner(p2);
                boardDiscs[a.row()][a.col()] = disc;
                System.out.printf("Player 2 placed a %s in (%d,%d)\n", disc.getType(), a.row(), a.col());
                history.addLast(new Position(a.row(), a.col()));
                System.out.println();
                lastPlayer = p2;
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
        int flipCounter = 0;
        //DOWN
        for (int i = a.row() + 1; i < BOARDSIZE; i++) {
            if (boardDiscs[i][a.col()] != null && boardDiscs[i][a.col()].getOwner() == lastPlayer) {
                flipCounter++;
            } else {
                break;
            }
        }
        //UP
        for (int i = a.row() - 1; i >= 0; i--) {
            if (boardDiscs[i][a.col()] != null && boardDiscs[i][a.col()].getOwner() == lastPlayer) {
                flipCounter++;
            } else {
                break;
            }
        }
        //LEFT
        for (int i = a.col() - 1; i >= 0; i--) {
            if (boardDiscs[a.row()][i] != null && boardDiscs[a.row()][i].getOwner() == lastPlayer) {
                flipCounter++;
            } else {
                break;
            }
        }
        //RIGHT
        for (int i = a.col() + 1; i < BOARDSIZE; i++) {
            if (boardDiscs[a.row()][i] != null && boardDiscs[a.row()][i].getOwner() == lastPlayer) {
                flipCounter++;
            } else {
                break;
            }
        }
        DUR:
        for (int i = a.col() + 1; i < BOARDSIZE; i++) {
            for (int j = a.row() - 1; j >= 0; j--) {
                if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner() == lastPlayer) {
                    flipCounter++;
                } else {
                    break DUR;
                }
            }
        }
        DUL:
        for (int i = a.col() - 1; i >= 0; i--) {
            for (int j = a.row() - 1; j >= 0; j--) {
                if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner() == lastPlayer) {
                    flipCounter++;
                } else {
                    break DUL;
                }
            }
        }
        DDR:
        for (int i = a.col() + 1; i < BOARDSIZE; i++) {
            for (int j = a.row() + 1; j < BOARDSIZE; j++) {
                if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner() == lastPlayer) {
                    flipCounter++;
                } else {
                    break DDR;
                }
            }
        }
        DDL:
        for (int i = a.col() - 1; i >= 0; i--) {
            for (int j = a.row() + 1; j < BOARDSIZE; j++) {
                if (boardDiscs[j][i] != null && boardDiscs[j][i].getOwner() == lastPlayer) {
                    flipCounter++;
                } else {
                    break DDL;
                }
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
            lastPlayer = beforeLastPlayer();
            boardDiscs[h_row][h_col] = null;
            history.removeLast();

        }
    }
}
