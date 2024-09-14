package bricker.gameobjects;

import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a puck object in the game, which is a specialized type of ball.
 */
public class Puck extends Ball {
    /**
     * Constructs a new Puck object.
     *
     * @param topLeftCorner   Position of the top-left corner of the puck, in window coordinates (pixels).
     *                        Note that (0,0) is the top-left corner of the window.
     * @param dimensions      Width and height of the puck in window coordinates.
     * @param renderable      The renderable representing the puck. Can be null if the puck
     *                       should not be rendered.
     * @param collisionSound  The sound to play when the puck collides with another object.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
        this.setTag("Puck");

    }
}
