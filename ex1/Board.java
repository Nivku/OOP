
/**
 * The Board class represents a game board for a two-dimensional grid of Marks.
 * Each cell in the grid can be marked with a specific player's symbol or left blank.
 * The class provides methods to initialize the board, retrieve the size of the board,
 * put a mark at specified coordinates, get the mark at specified coordinates, and
 * check the validity of coordinates.
 */


public class Board {

    private final int boardSize;

    private static final int DEFAULT_BOARD_SIZE = 4;


    private final Mark[][] board;

    /**
     * Default constructor to create a Board with the default size = 4 and initialize it.
     */
    public Board() {
        boardSize = DEFAULT_BOARD_SIZE;
        board = new Mark[boardSize][boardSize];
        initializeBoard();
    }

    /**
     * Constructor to create a Board with a custom size and initialize it.
     *
     * @param size The size of the square board.
     */
    public Board(int size) {
        boardSize = size;
        board = new Mark[boardSize][boardSize];
        initializeBoard();
    }

    /**
     * Retrieves the size of the board.
     *
     * @return The size of the square board.
     */
    public int getSize() {
        return boardSize;
    }

    /**
     * Puts a mark on the board at the specified coordinates.
     *
     * @param mark The mark to be placed on the board.
     * @param row  The row index of the cell.
     * @param col  The column index of the cell.
     * @return True if the mark was successfully placed, false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        Mark blankMark = Mark.BLANK;
        if (checkCoordinates(row, col)) {
            if (blankMark == getMark(row, col)) {
                board[row][col] = mark;
                return true;
            }

        }
        return false;

    }

    /**
     * Retrieves the mark at the specified coordinates on the board.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return The mark at the specified coordinates or Mark.Blank if coordinates are invalid.
     */

    public Mark getMark(int row, int col) {
        if (checkCoordinates(row, col)) {
            return board[row][col];
        }
        return Mark.BLANK;
    }


    /**
     * Initializes the board by filling all cells with a blank mark.
     */
    private void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = Mark.BLANK;
            }
        }
    }


    /**
     * Checks whether the specified coordinates are valid within the board.
     *
     * @param row The row index of the cell.
     * @param col The column index of the cell.
     * @return True if the coordinates are valid, false otherwise.
     */

    private boolean checkCoordinates(int row, int col) {

        return row < boardSize && row >= 0 && col < boardSize && col >= 0;
    }


}



