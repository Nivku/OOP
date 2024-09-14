/**
 * The Renderer interface defines a contract for classes responsible for rendering
 * the game board.
 */
public interface Renderer {

    /**
     * Renders the provided game board.
     *
     * @param board The board to be rendered.
     */
    void renderBoard(Board board);
}