/**
 * The Player interface defines a contract for classes representing players in a Tic Tac Toe game.
 */
public interface Player {

    /**
     * Plays a turn on the board, making a move according to the player's strategy.
     *
     * @param board The Tic Tac Toe board on which the move is to be made.
     * @param mark  The mark (X or O) associated with the player.
     */
    void playTurn(Board board, Mark mark);
}