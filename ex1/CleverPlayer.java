import java.util.Random;

/**
 * The CleverPlayer class implements the Player interface
 * with a clever strategy. The CleverPlayer marks the first empty
 * cell in a new run and then uses a specific tactic for subsequent moves. The tactic involves
 * checking if the next cell in the same row is available; if not, it marks a random empty cell.
 */

public class CleverPlayer implements Player {


    private Mark currentMark = null;

    private int lastRowMarked;
    private int lastColMarked;

    /**
     * Plays a turn on the board based on the strategy of the CleverPlayer.
     *
     * @param board The Tic Tac Toe board on which the move is to be made.
     * @param mark  The mark (X or O) associated with the CleverPlayer.
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
     * Marks a random empty cell on the Tic Tac Toe board.
     *
     * @param board The Tic Tac Toe board.
     * @param mark  The mark (X or O) associated with the CleverPlayer.
     */

    private void markRandomEmptyCell(Board board, Mark mark) {
        int boardSize = board.getSize();
        while (true) {
            Random random = new Random();
            int row = random.nextInt(boardSize);
            int col = random.nextInt(boardSize);
            if (board.putMark(mark, row, col)) {
                lastRowMarked = row;
                lastColMarked = col;
                break;
            }
        }
    }


    /**
     * Marks the next cell based on a specific tactic. If the next cell in the same row is available,
     * the CleverPlayer marks it; otherwise, it marks a random empty cell.
     *
     * @param mark  The mark (X or O) associated with the CleverPlayer.
     * @param board The Tic Tac Toe board.
     */


    private void NextMark(Mark mark, Board board) {

        if (board.putMark(mark, lastRowMarked, lastColMarked + 1)) {
            lastColMarked++;
        } else {
            markRandomEmptyCell(board, mark);
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
     * Checks if the CleverPlayer is starting a new run with a different mark.
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
