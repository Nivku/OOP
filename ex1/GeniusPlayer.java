
/**
 * The GeniusPlayer class implements the Player interface.
 * with a simple strategy for playing Tic Tac Toe. The GeniusPlayer marks the first empty
 * cell in a new run and then uses a specific tactic of running on Columns.
 */

public class GeniusPlayer implements Player {


    private Mark currentMark = null;
    private int lastRowMarked;
    private int lastColMarked;


    /**
     * Plays a turn on game board based on the strategy of the GeniusPlayer.
     *
     * @param board The board on which the move is to be made.
     * @param mark  The mark (X or O) associated with the GeniusPlayer.
     */

    @Override
    public void playTurn(Board board, Mark mark) {
        if (checkNewRun(mark)) {
            markEmptyCell(board, mark);
        } else {
            NextMark(mark, board);
        }
    }

    /**
     * Marks the first empty cell in a new run, updating the last marked row and column.
     *
     * @param board The Tic Tac Toe board.
     * @param mark  The mark (X or O) associated with the GeniusPlayer.
     */

    private void markEmptyCell(Board board, Mark mark) {
        int boardSize = board.getSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    this.lastRowMarked = row;
                    this.lastColMarked = col;
                    return;
                }
            }

        }
    }


    /**
     * Marks the next cell based on a specific tactic. If the next cell in the same colum is empty,
     * the GeniusPlayer marks it. otherwise the first empty cell.
     *
     * @param mark  The mark (X or O) associated with the GeniusPlayer.
     * @param board The Tic Tac Toe board.
     */

    private void NextMark(Mark mark, Board board) {

        if (board.putMark(mark, lastRowMarked + 1, lastColMarked)) {
            lastRowMarked++;

        } else {
            markEmptyCell(board, mark);
        }
    }

    /**
     * Checks if the GeniusPlayer is starting a new run with a different mark.
     *
     * @param mark The current mark (X or O) to check against the previous mark.
     * @return True if it's a new run, false otherwise.
     */

    private boolean checkNewRun(Mark mark) {
        if (currentMark == null || mark != currentMark) {
            currentMark = mark;
            return true;
        }
        return false;
    }


}

