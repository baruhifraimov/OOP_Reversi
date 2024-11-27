import java.util.*;

public class GameLogic implements PlayableLogic {
    private final int BOARDSIZE = 8;
    private Player p1, p2; // Player's entity
    private Player lastPlayer; //Checks who was last
    private Disc[][] boardDiscs;// Locating disc position on the board
    private boolean flip_enabler; // Flag for enabling collecting data for flipping discs

    // Stacks for tracking game state
    private final Stack<Position> discsFlipStacker; // Collects data of disc to flip locations
    private final Stack<Position> discsFlipStackerCopy;
    private final Stack<Position> moveHistory; // Collects all the disc locations on the board
    private final Stack<Disc> flipHistory; // Collects data of setOwner.
    private final Stack<Integer> undoCountStack; // Collects data how many discs been flipped in each round
    private final ArrayList<Position> counterNewCheck = new ArrayList<>();




    /**
     * Constructs a new GameLogic instance with initialized game state.
     */
    public GameLogic() {
        super();
        boardDiscs = new Disc[BOARDSIZE][BOARDSIZE];
        flipHistory = new Stack<>();
        moveHistory = new Stack<>();
        undoCountStack = new Stack<>();
        discsFlipStacker = new Stack<>();
        discsFlipStackerCopy = new Stack<>();
        lastPlayer = getSecondPlayer();
    }

    /**
     * Determines the current player based on the last move.
     *
     * @return The current player.
     */
    public Player currentPlayer() {
        if (lastPlayer == p1) {
            return p2;
        }
        return p1;
    }

    /**
     * Gets the numerical identifier of a player.
     *
     * @param p The player.
     * @return 1 for player 1, 2 for player 2.
     */
    private int getPlayerNo(Player p) {
        if (p.equals(p1)) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Initializes the board with the starting discs for each player.
     */
    private void initBoard() {
        boardDiscs = new Disc[BOARDSIZE][BOARDSIZE];
        int midBoard = BOARDSIZE / 2;
        boardDiscs[midBoard][midBoard] = new SimpleDisc(p1);
        boardDiscs[midBoard - 1][midBoard - 1] = new SimpleDisc(p1);
        boardDiscs[midBoard][midBoard - 1] = new SimpleDisc(p2);
        boardDiscs[midBoard - 1][midBoard] = new SimpleDisc(p2);
    }

    /**
     * Places a disc at a given position on the board if the move is valid.
     *
     * @param a    The position.
     * @param disc The disc to be placed.
     * @return True if the move was successful, otherwise false.
     */
    @Override
    public boolean locate_disc(Position a, Disc disc) {
        boolean flag = false;
        if (boardDiscs[a.row()][a.col()] == null && countFlips(a) > 0) {
            flip_enabler = true;// Move is enabled, flag for enabling flipping
            flag = aux_locate_disc(disc, a);
        }

        return flag;
    }

    /**
     * Helper method to handle the actual placement of a disc on the board.
     *
     * @param disc The disc to be placed.
     * @param a    The position.
     * @return True if placement is successful, otherwise false.
     */
    private boolean aux_locate_disc(Disc disc, Position a) {
        Player p = currentPlayer();
        int no = getPlayerNo(p);
        switch (disc.getType()) {
            case "💣":
                if (p.getNumber_of_bombs() > 0) {
                    p.reduce_bomb();
                    boardDiscs[a.row()][a.col()] = disc;
                    moveHistory.addLast(new Position(a));

                    //System.out.printf("Player %d placed a %s in (%d,%d)\n No. of Bombs discs left: %d\n", no, disc.getType(), a.row(), a.col(), randAI.getNumber_of_bombs());
                    System.out.printf("Player %d placed a %s in (%d,%d)\n", no, disc.getType(), a.row(), a.col());
                    flipAction(a);
                    System.out.println();
                    lastPlayer = p;
                }
                return true;
            case "⭕":
                if (p.getNumber_of_unflippedable() > 0) {
                    p.reduce_unflippedable();
                    boardDiscs[a.row()][a.col()] = disc;
                    moveHistory.addLast(new Position(a));

                    //System.out.printf("Player %d placed a %s in (%d,%d)" + "\n No. of Unflippable discs left: %d\n", no, disc.getType(), a.row(), a.col(), randAI.getNumber_of_unflippedable());
                    System.out.printf("Player %d placed a %s in (%d,%d)" + "\n ", no, disc.getType(), a.row(), a.col());
                    flipAction(a);
                    System.out.println();
                    lastPlayer = p;
                }
                return true;
            case "⬤":
                boardDiscs[a.row()][a.col()] = disc;
                moveHistory.addLast(new Position(a));

                System.out.printf("Player %d placed a %s in (%d,%d)\n", no, disc.getType(), a.row(), a.col());
                flipAction(a);
                System.out.println();
                lastPlayer = p;
                return true;
            default:
                return false;
        }

    }

    /**
     * Retrieves the disc at a specified position.
     *
     * @param position The position.
     * @return The disc at the position, or null if empty.
     */
    @Override
    public Disc getDiscAtPosition(Position position) {
        return boardDiscs[position.row()][position.col()];
    }

    /**
     * Gets the size of the board.
     *
     * @return The board size.
     */
    @Override
    public int getBoardSize() {
        return BOARDSIZE;
    }

    /**
     * Computes a list of valid positions for the current player to place a disc.
     *
     * @return A list of valid positions.
     */
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

    /**
     * Calculates the number of discs that would be flipped if a disc were placed at a position.
     *
     * @param a The position.
     * @return The number of discs to flip.
     */
    @Override
    public int countFlips(Position a) {

        int[][] directions = {
                {+1, 0}, {-1, 0}, {0, -1}, {0, +1},
                {-1, +1}, {-1, -1}, {+1, +1}, {+1, -1}
        };

        int totalFlips = 0;
        for (int[] dir : directions) {
            totalFlips += auxCountFlips(a, dir[0], dir[1], counterNewCheck);
        }
        counterNewCheck.clear();
        return totalFlips;

    }

    /**
     * Flips the discs on the board based on the position of the last placed disc.
     *
     * @param a The position where the last disc was placed.
     */
    // Here the flip action takes a place, who to flip and how much
    private void flipAction(Position a) {
        int undoCount = 0;
        countFlips(a);
        while (!discsFlipStacker.isEmpty()) {
            Position i = discsFlipStacker.peek();
            Disc d = boardDiscs[i.row()][i.col()];
            if (d != null && d.getOwner().equals(lastPlayer) && !d.getType().equals("⭕")) {
                d.setOwner(currentPlayer());
                d.setFlipped(true);
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

    /**
     * Helper method to calculate the number of discs that can be flipped in a given direction.
     *
     * @param a     The starting position.
     * @param m_row The row direction to check.
     * @param m_col The column direction to check.
     * @return The number of flippable discs in the specified direction.
     */
    private int auxCountFlips(Position a, int m_row, int m_col, ArrayList<Position> counterNewCheck) {

        int flipCounter = 0;
        int rep_row = m_row, rep_col = m_col;
        Stack<Position> discsFlipStackerCheck = new Stack<>();
        ArrayList<Position> counterNew = new ArrayList<>();
        // scan the whole board
        while (((a.row() + m_row) < BOARDSIZE) && ((a.row() + m_row) >= 0) && ((a.col() + m_col) < BOARDSIZE) && ((a.col() + m_col) >= 0)) {
            Disc disc = boardDiscs[a.row() + m_row][a.col() + m_col];
            Position currentPosition = new Position(a.row() + m_row, a.col() + m_col);
            // check if it's not empty and if it's my opponent disc
            if (disc != null && disc.getOwner().equals(lastPlayer)) {
                // check if it can be flipped
                if (!disc.getType().equals("⭕")) {
                    // if it's a bomb go to explode counter
                    if (disc.getType().equals("💣")) {
                        //Check all directions including current position
                        count_explode(currentPosition, 0, 0, discsFlipStackerCheck, counterNew, counterNewCheck);
                        //Reset the flags for the explosions, we already counted every possible flip
                        flagReset();
                    }
                    // If no bomb encountered or unflippable disc, continue counting
                    else if (disc.getType().equals("⬤") && !disc.get_flag_bomb()) {

                        if (!counterNew.contains(currentPosition)) {
                            if (!counterNewCheck.contains(currentPosition)) {
                                counterNewCheck.add(currentPosition);
                                counterNew.add(currentPosition);
                            }
                        }
                    }
                }
                //Checks if the player turn is on.
                if (flip_enabler) {
                    // check if it can be flipped again (double check)
                    if (!disc.getType().equals("⭕")) {
                        // add to the flip stack if it can be flipped
                        discsFlipStackerCheck.add(currentPosition);
                    }
                }
            }
            // if no discs left 'and' we got our current player on the other side
            else if (disc != null && disc.getOwner().equals(currentPlayer())) {
                flipCounter += counterNew.size();
                counterNewCheck.addAll(counterNew);
//                for(Position i : counterNew){
//                    if(!counterNewCheck.contains(i)){
//                        counterNewCheck.add(i);
//                        counterNew.add(currentPosition);
//                    }
//                }
                counterNew.clear();
                while (!discsFlipStackerCheck.isEmpty()) {
                    discsFlipStacker.add(discsFlipStackerCheck.pop());
                }

                break;
            } else {
                discsFlipStackerCheck.clear();

                counterNew.clear();
                break;
            }
            m_row += rep_row;
            m_col += rep_col;
        }
        return flipCounter;
    }

    /**
     * Handles the explosion logic for bomb discs.
     * Recursively counts the number of affected discs in all directions.
     *
     * @param bomb_pos              The position of the bomb.
     * @param m_row                 The row direction to check.
     * @param m_col                 The column direction to check.
     * @param discsFlipStackerCheck A stack to track discs that can be flipped.
     * @param counterNew            A list to avoid duplicate counting of discs.
     * @param counterNewCheck       A Backup check for counterNew
     */
    public void count_explode(Position bomb_pos, int m_row, int m_col, Stack<Position> discsFlipStackerCheck, ArrayList<Position> counterNew, ArrayList<Position> counterNewCheck) {
        int check_row = bomb_pos.row() + m_row;
        int check_col = bomb_pos.col() + m_col;
        // Ensure we're within bounds
        if (check_row >= 0 && check_row < BOARDSIZE && check_col >= 0 && check_col < BOARDSIZE) {
            // Get the disc at the position
            Disc disc = boardDiscs[check_row][check_col];
            Position newPosition = new Position(check_row, check_col);
            // Check if there's a disc and it's owned by the opponent
            if (disc != null && disc.getOwner().equals(lastPlayer)) {
                // If it's a bomb and not yet processed
                if (disc.getType().equals("💣") && !disc.get_flag_bomb()) {
                    if (!counterNew.contains(newPosition)) {
                        if (!counterNewCheck.contains(newPosition)) {
                            counterNew.add(newPosition);
//                            counterNewCheck.add(newPosition);
                        }
                    }
                    disc.set_flag_bomb(true); // Mark the bomb as processed
                    //counter++; // Count the bomb itself

                    if (flip_enabler) {
                        discsFlipStackerCheck.add(newPosition);
                    }

                    // Trigger recursive explosions in all directions
                    int[][] directions = {
                            {+1, 0}, {-1, 0}, {0, +1}, {0, -1},
                            {+1, +1}, {+1, -1}, {-1, +1}, {-1, -1}
                    };

                    for (int[] dir : directions) {
                        count_explode(newPosition, dir[0], dir[1], discsFlipStackerCheck, counterNew, counterNewCheck);
                    }
                }
                // If it's a regular disc and not yet processed
                else if (!disc.get_flag_bomb() && disc.getType().equals("⬤")) {
                    disc.set_flag_bomb(true); // Mark the disc as processed
                    if (!counterNew.contains(newPosition)) {
                        if (!counterNewCheck.contains(newPosition)) {
                            counterNew.add(newPosition);
//                            counterNewCheck.add(newPosition);
                        }
                    }
                    if (flip_enabler) {
                        discsFlipStackerCheck.add(newPosition);
                    }
                }
            }
        }
    }

    /**
     * Resets the flags of all discs on the board, clearing any temporary states.
     */

    private void flagReset() {
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Disc disc = boardDiscs[i][j];
                if (disc != null) {
                    disc.reset_flags();
                }
            }
        }
    }

    /**
     * Gets the first player in the game.
     *
     * @return The first player.
     */
    @Override
    public Player getFirstPlayer() {
        return this.p1;
    }

    /**
     * Gets the second player in the game.
     *
     * @return The second player.
     */
    @Override
    public Player getSecondPlayer() {
        return this.p2;
    }

    /**
     * Sets the players for the game.
     *
     * @param player1 Player 1.
     * @param player2 Player 2.
     */
    @Override
    public void setPlayers(Player player1, Player player2) {
        this.p1 = player1;
        this.p2 = player2;
    }

    /**
     * Checks whether it's the first player's turn.
     *
     * @return True if it's the first player's turn, otherwise false.
     */
    @Override
    public boolean isFirstPlayerTurn() {
        return lastPlayer != p1;
    }

    /**
     * Checks whether the game has finished.
     *
     * @return True if the game is finished, otherwise false.
     */

    @Override
    public boolean isGameFinished() {
        int player1_discs = 0, player2_discs = 0;
        if (this.ValidMoves().isEmpty()) {
            for (int i = 0; i < BOARDSIZE; i++) {
                for (int j = 0; j < BOARDSIZE; j++) {
                    if (boardDiscs[i][j] != null && boardDiscs[i][j].getOwner().equals(p1)) {
                        player1_discs++;
                    } else if (boardDiscs[i][j] != null && boardDiscs[i][j].getOwner().equals(p2)) {
                        player2_discs++;
                    }
                }
            }
            if (player1_discs > player2_discs) {
                getFirstPlayer().addWin();
                System.out.printf("Player %s wins with %d discs! Player %s had %d discs.", getPlayerNo(p1), player1_discs, getPlayerNo(p2), player2_discs);
            } else if (player1_discs < player2_discs) {
                getSecondPlayer().addWin();
                System.out.printf("Player %s wins with %d discs! Player %s had %d discs.", getPlayerNo(p2), player2_discs, getPlayerNo(p1), player1_discs);

            }
            return true;
        }
        return false;
    }

    /**
     * Resets the game state to its initial configuration.
     */
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

    /**
     * Undoes the last move and any associated flips. The following actions occur:
     * - Removes the last placed disc from the board.
     * - Restores flipped discs to their previous owner.
     * - Updates the current turn to the player who made the undone move.
     */
    @Override
    public void undoLastMove() {
        // Undo Moves
        if ((p1.isHuman() && p2.isHuman())) {
            System.out.println("Undoing last move:");
            if (!moveHistory.isEmpty()) {
                int h_row = moveHistory.peek().row();
                int h_col = moveHistory.peek().col();
                System.out.printf("\tUndo: removing %s from (%d,%d)\n", boardDiscs[h_row][h_col].getType(), h_row, h_col);
                lastPlayer = currentPlayer();
                if (boardDiscs[h_row][h_col].getType().equals("💣")) {
                    boardDiscs[h_row][h_col].getOwner().add_bomb();
                    boardDiscs[h_row][h_col] = null;
                    moveHistory.removeLast();
                } else if (boardDiscs[h_row][h_col].getType().equals("⭕")) {
                    boardDiscs[h_row][h_col].getOwner().add_unflippedable();
                    boardDiscs[h_row][h_col] = null;
                    moveHistory.removeLast();
                } else {
                    boardDiscs[h_row][h_col] = null;
                    moveHistory.removeLast();
                }
            } else {
                System.out.println("\tNo previous move available to undo.");
            }
        }
        // Undo Flips
        if (!undoCountStack.isEmpty() && (p1.isHuman() && p2.isHuman())) {
            int j = undoCountStack.pop();
            while (j > 0) {
                for (int i = 0; i < j; i++) {
                    if (!flipHistory.isEmpty()) {
                        Position p = discsFlipStackerCopy.pop();
                        System.out.printf("\tUndo: flipping back %s in (%d,%d)\n", flipHistory.peek().getType(), p.row(), p.col());
                        flipHistory.pop().setOwner(lastPlayer);
                        j--;
                    }
                }
            }
        }
        System.out.println(); // blank line
    }
}




