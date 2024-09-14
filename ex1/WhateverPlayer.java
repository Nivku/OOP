import java.util.Random;

/**
 * The WhateverPlayer class implements the Player interface, representing a player
 * The WhateverPlayer randomly selects empty cell on the board.
 */

public class WhateverPlayer implements Player {

    /**
     * Plays a turn on the board by randomly selecting an empty cell.
     * The move is made according to the WhateverPlayer's strategy.
     *
     * @param board The board on which the move is to be made.
     * @param mark  The mark (X or O) associated with the player.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        while (true) {
            Random random = new Random();
            int row = random.nextInt(board.getSize());
            int col = random.nextInt(board.getSize());
            if (board.putMark(mark, row, col)) {
                break;
            }

        }

    }

}
