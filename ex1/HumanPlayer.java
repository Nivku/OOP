import java.util.Scanner;

/**
 * The HumanPlayer class implements the Player interface, representing a human player
 * in a Tic Tac Toe game. This class allows the user to make moves on the game board by
 * entering coordinates through the console input.
 */

public class HumanPlayer implements Player {

    /**
     * Constructs a HumanPlayer.
     */
    public HumanPlayer() {
    }

    /**
     * Implements the playTurn method from the Player interface, allowing the human player
     * to make a move on the Tic Tac Toe board. The player is prompted to enter coordinates
     * through the console input.
     *
     * @param board The Tic Tac Toe board on which the move is to be made.
     * @param mark  The mark (X or O) associated with the human player.
     */

    @Override
    public void playTurn(Board board, Mark mark) {
        System.out.println(Constants.playerRequestInputString(mark.toString()));
        while (true) {
            int coordinates = KeyboardInput.readInt();
            int row = coordinates / 10;
            int col = coordinates % 10;
            if (board.getMark(row,col) != Mark.BLANK) {
                System.out.println(Constants.OCCUPIED_COORDINATE);
                continue;
            }
            if (!board.putMark(mark, row, col)) {
                System.out.println(Constants.INVALID_COORDINATE);
                continue;
            }
            break;
        }

    }


}






