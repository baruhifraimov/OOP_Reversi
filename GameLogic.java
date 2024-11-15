import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {
    private final int BOARDSIZE = 8;
    private Player p1, p2; // Player's entity
    private Player lastPlayer; //Checks who was last
    private Disc[][] boardDiscs = new Disc[BOARDSIZE][BOARDSIZE]; // Locating disc position on the board
    private Stack<Position> discsFlipStacker; // Collects data of disc to flip locations
    private Stack<Position> discsFlipStackerCopy;
    private Stack<Position> moveHistory; // Collects all the disc locations on the board
    private Stack<Disc> flipHistory; // Collects data of setOwner.
    private Stack<Integer> undoCountStack; // Collects data how many discs been flipped in each round
    private boolean flip_enabler; // Flag for enabling collecting data for flipping discs

    public GameLogic() {
        super();
        flipHistory = new Stack<>();
        moveHistory = new Stack<>();
        undoCountStack = new Stack<>();
        discsFlipStacker = new Stack<>();
        discsFlipStackerCopy = new Stack<>();
        lastPlayer = getSecondPlayer();
    }

    private Player currentPlayer() {
        if (lastPlayer == p1) {
            return p2;
        }
        return p1;
    }

    private int getPlayerNo(Player p) {
        if (p.equals(p1)) {
            return 1;
        } else {
            return 2;
        }
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
        boolean flag = false;
        if (boardDiscs[a.row()][a.col()] == null && countFlips(a) > 0) {
            flip_enabler = true;// Move is enabled, flag for enabling flipping
            flag = aux_locate_disc(disc, a);
        }
        return flag;
    }

    private boolean aux_locate_disc(Disc disc, Position a) {
        Player p = currentPlayer();
        int no = getPlayerNo(p);
        switch (disc.getType()) {
            case "💣":
                if (p.getNumber_of_bombs() > 0) {
                    p.reduce_bomb();
                    boardDiscs[a.row()][a.col()] = disc;
                    moveHistory.addLast(new Position(a.row(), a.col()));
                    flipAction(a);
                    //System.out.printf("Player %d placed a %s in (%d,%d)\n No. of Bombs discs left: %d\n", no, disc.getType(), a.row(), a.col(), p1.getNumber_of_bombs());
                    System.out.printf("Player %d placed a %s in (%d,%d)\n", no, disc.getType(), a.row(), a.col());
                    System.out.println();
                    lastPlayer = p;
                }
                return true;
            case "⭕":
                if (p.getNumber_of_unflippedable() > 0) {
                    p.reduce_unflippedable();
                    boardDiscs[a.row()][a.col()] = disc;
                    moveHistory.addLast(new Position(a.row(), a.col()));
                    flipAction(a);
                    //System.out.printf("Player %d placed a %s in (%d,%d)" + "\n No. of Unflippable discs left: %d\n", no, disc.getType(), a.row(), a.col(), p1.getNumber_of_unflippedable());
                    System.out.printf("Player %d placed a %s in (%d,%d)" + "\n ",no, disc.getType(), a.row(), a.col());
                    System.out.println();
                    lastPlayer = p;
                }
                return true;
            case "⬤":
                boardDiscs[a.row()][a.col()] = disc;
                moveHistory.addLast(new Position(a.row(), a.col()));
                flipAction(a);
                System.out.printf("Player %d placed a %s in (%d,%d)\n", no, disc.getType(), a.row(), a.col());
                System.out.println();
                lastPlayer = p;
                return true;
            default:
                return false;
        }

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

    // Taking position a and spreading checks x,y,z for possible flips
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

    // same as auxCountFlips but under the control of flipAction for managing conflicts
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

    // Here the flip action takes a place, who to flip and how much
    private void flipAction(Position a) {
        int undoCount = 0;
        flipPositionFinder(a);
        while (!discsFlipStacker.isEmpty()) {
            Position i = discsFlipStacker.peek();
            Disc d = boardDiscs[i.row()][i.col()];
            if (d!=null && !d.getOwner().equals(currentPlayer()) && !d.getType().equals("⭕")) {
                d.setOwner(currentPlayer());
                System.out.printf("Player %s flipped the %s in %s \n", getPlayerNo(currentPlayer()), d.getType(), i);
                flipHistory.push(d);
                discsFlipStackerCopy.add(discsFlipStacker.pop());
                undoCount++;
            } else {
                discsFlipStacker.pop();
            }
        }
        undoCountStack.add(undoCount);
        flip_enabler = false;

    }

    private int auxCountFlips(Position a, int m_row, int m_col) {
        int counter = 0, flipCounter = 0,explosion = 0;
        int rep_row = m_row, rep_col = m_col;
        Stack<Position> discsFlipStackerCheck = new Stack<>();
        // scan the whole board
        while (((a.row() + m_row) < BOARDSIZE) && ((a.row() + m_row) >= 0) && ((a.col() + m_col) < BOARDSIZE) && ((a.col() + m_col) >= 0)) {
            // check if it's not empty and if it's my opponent disc
            if (boardDiscs[a.row() + m_row][a.col() + m_col] != null && boardDiscs[a.row() + m_row][a.col() + m_col].getOwner().equals(lastPlayer)) {
                // check if it can be flipped
                if (!boardDiscs[a.row() + m_row][a.col() + m_col].getType().equals("⭕")) {
                    if(boardDiscs[a.row() + m_row][a.col() + m_col].getType().equals("💣") && !boardDiscs[a.row() + m_row][a.col() + m_col].bombFlag()){
                        // Bomb position
                        Position bomb_pos = new Position(a.row() + m_row,a.col() + m_col);
                        counter+=explode(bomb_pos, +1, 0,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, -1, 0,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, 0, -1,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, 0, +1,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, -1, +1,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, -1, -1,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, +1, +1,discsFlipStackerCheck);
                        counter+=explode(bomb_pos, +1, -1,discsFlipStackerCheck);
                        // PUT THE EXPLOSION LOGIC THAT ALSO RETURNS HOW MUCH HE CAN EXPLODE
                    }
                    // CHECK IF THE DISC BEEN EXPLODED, DON'T COUNT IT
                    // ADD FLAG TO DISC THAT TELLS US IF THE DISC EXPLODED
                    else {
                        counter++;}
                }

                //Checks if the player turn is on.
                if (flip_enabler) {
                        // check if it can be flipped again (double check)
                        if (!boardDiscs[a.row() + m_row][a.col() + m_col].getType().equals("⭕")) {
                            // add to the flip stack if it can be flipped
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

    public int explode(Position a, int m_row, int m_col, Stack<Position> discsFlipStackerCheck) {
        int counter = 0;
        int check_row = a.row() + m_row;
        int check_col = a.col() + m_col;
        // Ensure we're within bounds
        if (check_row >= 0 && check_row < BOARDSIZE && check_col >= 0 && check_col < BOARDSIZE) {
            if (boardDiscs[check_row][check_col] != null && boardDiscs[check_row][check_col].getOwner().equals(lastPlayer)) {
                // If it's a bomb, recursively explode
                if (boardDiscs[check_row][check_col].getType().equals("💣") && !boardDiscs[check_row][check_col].bombFlag()) {
                    boardDiscs[check_row][check_col].set_bombFlag(true);
                    discsFlipStackerCheck.add(new Position(check_row, check_col));
                    counter++; // Count this bomb
                    // Recursive explosion
                    counter += explode(new Position(check_row, check_col), +1, 0, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), -1, 0, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), 0, -1, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), 0, +1, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), -1, +1, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), -1, -1, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), +1, +1, discsFlipStackerCheck);
                    counter += explode(new Position(check_row, check_col), +1, -1, discsFlipStackerCheck);
                } else if (!boardDiscs[check_row][check_col].bombFlag()) {
                    // Regular disc, mark as exploded
                    boardDiscs[check_row][check_col].set_bombFlag(true);
                    discsFlipStackerCheck.add(new Position(check_row, check_col));
                    counter++;
                }
            }
        }
        return counter;
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
        int player1_discs=0,player2_discs=0;
        if (this.ValidMoves().isEmpty())
        {
            for (int i = 0; i < BOARDSIZE; i++) {
                for (int j = 0; j < BOARDSIZE; j++) {
                    if(boardDiscs[i][j] != null && boardDiscs[i][j].getOwner().equals(p1)){
                        player1_discs++;
                    }
                    else if (boardDiscs[i][j] != null && boardDiscs[i][j].getOwner().equals(p2) ){
                        player2_discs++;
                    }
                }
            }
            if(player1_discs>player2_discs){
                getFirstPlayer().addWin();
                System.out.printf("Player %s wins with %d discs! Player %s had %d discs.",getPlayerNo(p1),player1_discs,getPlayerNo(p2),player2_discs);
            }
            else if(player1_discs<player2_discs){
                getSecondPlayer().addWin();
                System.out.printf("Player %s wins with %d discs! Player %s had %d discs.",getPlayerNo(p2),player2_discs,getPlayerNo(p1),player1_discs);

            }
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        lastPlayer = p2;
        moveHistory.clear();
        discsFlipStacker.clear();
        undoCountStack.clear();
        flipHistory.clear();
        p1.reset_bombs_and_unflippedable();
        p2.reset_bombs_and_unflippedable();
        initBoard();
    }

    @Override
    public void undoLastMove() {
        // Undo Moves
        if (!moveHistory.isEmpty() && (p1.isHuman() && p2.isHuman())) {
            System.out.println("\nUndoing last move:");
            int h_row = moveHistory.peek().row();
            int h_col = moveHistory.peek().col();
            System.out.printf("\tUndo: removing %s from (%d,%d)\n", boardDiscs[h_row][h_col].getType(), h_row, h_col);
            lastPlayer = currentPlayer();
            if (boardDiscs[h_row][h_col].getType().equals("💣")) {
                boardDiscs[h_row][h_col].getOwner().add_bomb();
                System.out.printf("\tRestoring %s Bombs: %d left\n", getPlayerNo(lastPlayer), lastPlayer.getNumber_of_bombs());
                boardDiscs[h_row][h_col] = null;
                moveHistory.removeLast();
            } else if (boardDiscs[h_row][h_col].getType().equals("⭕")) {
                boardDiscs[h_row][h_col].getOwner().add_unflippedable();
                System.out.printf("\tRestoring %s Bombs: %d left\n", getPlayerNo(lastPlayer), lastPlayer.getNumber_of_unflippedable());
                boardDiscs[h_row][h_col] = null;
                moveHistory.removeLast();
            } else {
                boardDiscs[h_row][h_col] = null;
                moveHistory.removeLast();
            }

        }
        // Undo Flips
        if (!undoCountStack.isEmpty() && (p1.isHuman() && p2.isHuman())) {
            int j = undoCountStack.pop();
            while (j > 0) {
                for (int i = 0; i < j; i++) {
                    if (!flipHistory.isEmpty()) {
                        Position p = discsFlipStackerCopy.pop();
                        System.out.printf("\tUndo: flipping back %s in (%d,%d)\n", flipHistory.peek().getType(),p.row(),p.col());
                        flipHistory.pop().setOwner(lastPlayer);
                        j--;
                    }
                }
            }
        }
    }
}

