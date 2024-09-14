/**
 * The RendererFactory class is responsible for creating instances of the Renderer interface
 * based on the specified renderer type and board size.
 */

public class RendererFactory {

    private final static String NONE = "none";

    private final static String CONSOLE = "console";

    /**
     * Constructs a RendererFactory
     */
    public RendererFactory() {
    }

    /**
     * Builds a Renderer based on the specified renderer type and board size.
     *
     * @param type The type of the renderer, such as "none" or "console".
     * @param size The size of the game board.
     * @return A Renderer instance corresponding to the specified type, or null if the type is unknown.
     */

    public Renderer buildRenderer(String type, int size) {

        String lowerCaseString = type.toLowerCase();
        if (lowerCaseString.equals(NONE)) {
            return new VoidRenderer();
        } else if (lowerCaseString.equals(CONSOLE)) {
            return new ConsoleRenderer(size);

        }
        return null;

    }


}
