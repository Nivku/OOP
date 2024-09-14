
/**
 * The Game class represents a Tic Tac Toe game, managing the players, board, and game flow.
 * It allows two players (X and O) to take turns and checks for winning conditions or a draw.
 */


public class Game {

    private final Player playerX;
    private final Player playerO;
    private final Renderer renderer;
    private final Board board;
    private final int winStreak;

    private int CurrentStreak;

    private int blankCells;

    private boolean winner = false;


    /**
     * Constructs a Tic Tac Toe game with default settings.
     *
     * @param playerX  The player representing 'X'.
     * @param playerO  The player representing 'O'.
     * @param renderer The renderer for displaying the game board.
     */
    public Game(Player playerX, Player
            playerO, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board();
        this.winStreak = board.getSize() - 1;
        this.blankCells = this.getBoardSize() * this.getBoardSize();

    }

    /**
     * Constructs a Tic Tac Toe game with custom settings.
     *
     * @param playerX   The player representing 'X'.
     * @param playerO   The player representing 'O'.
     * @param size      The size of the game board.
     * @param winStreak The streak length required for a win.
     * @param renderer  The renderer for displaying the game board.
     */

    public Game(Player playerX, Player
            playerO, int size, int
                        winStreak, Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.board = new Board(size);
        this.winStreak = checkStreakValidity(winStreak, size);
        this.blankCells = this.getBoardSize() * this.getBoardSize();
    }

    /**
     * Retrieves the streak length required for a win.
     *
     * @return The streak length for a win.
     */

    public int getWinStreak() {
        return winStreak;
    }


    /**
     * Retrieves the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getBoardSize() {
        return board.getSize();
    }


    /**
     * Runs the Tic Tac Toe game until a winner is determined or the game ends in a draw.
     *
     * @return The mark of the winner ('X' or 'O') or 'BLANK' in case of a draw.
     */


    public Mark run() {
        while (true) {
            playerX.playTurn(this.board, Mark.X);
            this.renderer.renderBoard(board);
            blankCells--;
            checkAllLinesStreak(Mark.X);

            if (endGameCheck(Mark.X) != null) {
                return endGameCheck(Mark.X);
            }

            playerO.playTurn(this.board, Mark.O);
            this.renderer.renderBoard(board);
            blankCells--;
            checkAllLinesStreak(Mark.O);

            if (endGameCheck(Mark.O) != null) {
                return endGameCheck(Mark.O);
            }
        }
    }


    /**
     * Checks the validity of the streak length and adjusts it if necessary.
     *
     * @param winStreak The streak length required for a win.
     * @param size      The size of the game board.
     * @return The adjusted streak length.
     */
    private int checkStreakValidity(int winStreak, int size) {
        if (winStreak < 2 || winStreak > size) {
            return size;
        }
        return winStreak;
    }

    /**
     * Checks for a winning streak in all directions on the game board.
     *
     * @param mark The mark ('X' or 'O') to check for a streak.
     */

    private void checkAllLinesStreak(Mark mark) {

        checkStraightStreak(board, mark, true);
        checkStraightStreak(board, mark, false);
        checkLeftDiagonalStreak(board, mark);
        checkRightDiagonalStreak(board, mark);

    }

    /**
     * Checks for a winning streak in straight lines (rows or columns) on the game board.
     *
     * @param board         The game board.
     * @param mark          The mark ('X' or 'O') to check for a streak.
     * @param lineDirection True for rows, false for columns.
     */

    private void checkStraightStreak(Board board, Mark mark, boolean lineDirection) {
        for (int row = 0; row < board.getSize(); row++) {
            CurrentStreak = 0;
            for (int col = 0; col < board.getSize(); col++) {
                if (lineDirection) {
                    checkStreakLength(board, mark, row, col);
                } else {
                    checkStreakLength(board, mark, col, row);
                }
            }
        }
    }

    /**
     * Checks for a winning streak in the left diagonal direction on the game board.
     *
     * @param board The game board.
     * @param mark  The mark ('X' or 'O') to check for a streak.
     */

    private void checkLeftDiagonalStreak(Board board, Mark mark) {
        int startingCol;
        int boardLimit = board.getSize() - 1;
        for (int row = 0; row <= boardLimit; row++) {
            if (row == 0) {
                startingCol = 0;
            } else {
                startingCol = boardLimit;
            }
            for (int col = startingCol; col <= boardLimit; col++) {
                int tempRow = row;
                int tempCol = col;
                this.CurrentStreak = 0;
                while (tempRow <= boardLimit && tempCol >= 0) {
                    checkStreakLength(board, mark, tempRow, tempCol);
                    tempRow++;
                    tempCol--;
                }
            }
        }
    }

    /**
     * Checks for a winning streak in the right diagonal direction on the game board.
     *
     * @param board The game board.
     * @param mark  The mark ('X' or 'O') to check for a streak.
     */

    private void checkRightDiagonalStreak(Board board, Mark mark) {
        int startingCol;
        int boardLimit = board.getSize() - 1;
        for (int row = 0; row <= boardLimit; row++) {
            if (row == 0) {
                startingCol = boardLimit;
            } else {
                startingCol = 0;
            }
            for (int col = startingCol; col >= 0; col--) {
                int tempRow = row;
                int tempCol = col;
                this.CurrentStreak = 0;
                while (tempRow <= boardLimit && tempCol <= boardLimit) {
                    checkStreakLength(board, mark, tempRow, tempCol);
                    tempRow++;
                    tempCol++;
                }
            }
        }
    }


    /**
     * Checks the length of the streak in a specified direction on the game board.
     * change the flag of the winner if necessary, or change CurrentStrike value.
     * @param board The game board.
     * @param mark  The mark ('X' or 'O') to check for a streak.
     * @param row   The row index of the cell.
     * @param col   The column index of the cell.
     */

    private void checkStreakLength(Board board, Mark mark, int row, int col) {
        if (mark == board.getMark(row, col)) {
            CurrentStreak++;
            if (CurrentStreak == this.winStreak) {
                this.winner = true;
            }
        } else {
            CurrentStreak = 0;
        }
    }

    /**
     * Checks for a winner or a draw after player turn.
     *
     * @param mark The mark ('X' or 'O') to check for a winner.
     * @return The mark of the winner ('X' or 'O') or 'BLANK' in case of a draw.
     */

    private Mark endGameCheck(Mark mark) {
        if (this.winner) {
            return mark;
        } else if (blankCells == 0) {
            return Mark.BLANK;
        }
        return null;
    }

}





