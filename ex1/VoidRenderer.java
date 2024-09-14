
/**
 * The VoidRenderer class implements the Renderer interface with an empty
 * implementation for the renderBoard method. When this renderer is used,
 * it does not print the Tic Tac Toe board to the screen.
 */


public class VoidRenderer implements Renderer {

    /**
     * An empty implementation of the renderBoard method. When this method is called
     *
     * @param board The Tic Tac Toe board to be rendered, but not printed.
     */

    @Override
    public void renderBoard(Board board) {
    }


}
