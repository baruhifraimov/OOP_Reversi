import java.util.List;

public class GameLogic implements PlayableLogic {
    private final int BOARDSIZE = 8;
    private Player p1, p2;
    private Disc[][] boardDiscs = new Disc[BOARDSIZE][BOARDSIZE]; // Locating disc position on the board
    private Player lastPlayer; //Checks who was last

//    public GameLogic() {
//        initBoard(); //initiating the board
//    }

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
        if (lastPlayer!=p1 && boardDiscs[a.row()][a.col()] == null) {
            boardDiscs[a.row()][a.col()] = disc;
            lastPlayer = p1;
            return true;
        } else if (boardDiscs[a.row()][a.col()] == null){
            boardDiscs[a.row()][a.col()] = disc;
            lastPlayer = p2;
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
        return List.of();
    }

    @Override
    public int countFlips(Position a) {
        int flipCounter=0;
        //DOWN
       for (int i = a.row()+1; i < getBoardSize(); i++) {
           if(boardDiscs[i][a.col()].getOwner() != null && boardDiscs[i][a.col()].getOwner()==lastPlayer  )
           {
                flipCounter++;
           }
           else {
               break;
           }
        }
       //UP
        for (int i = a.row()-1; i >= 0; i--) {
            if(boardDiscs[i][a.col()].getOwner() != null && boardDiscs[i][a.col()].getOwner()==lastPlayer  )
            {
                flipCounter++;
            }
            else {
                break;
            }
        }

        //LEFT
        for (int i = a.col()-1; i >= 0; i--) {
            if(boardDiscs[a.row()][i].getOwner() != null && boardDiscs[a.row()][i].getOwner()==lastPlayer  )
            {
                flipCounter++;
            }
            else {
                break;
            }
        }

        //RIGHT
        for (int i = a.col()+1; i < getBoardSize(); i++) {
            if(boardDiscs[a.row()][i].getOwner() != null && boardDiscs[a.row()][i].getOwner()==lastPlayer  )
            {
                flipCounter++;
            }
            else {
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
        return lastPlayer!=p1;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public void reset() {
        initBoard();
    }

    @Override
    public void undoLastMove() {

    }
}
