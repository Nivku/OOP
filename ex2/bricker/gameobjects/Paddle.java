package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Represents a paddle object in the game, controlled by user input.
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;


    /**
     * Constructs a new Paddle object.
     *
     * @param topLeftCorner   Position of the top-left corner of the paddle, in window
     *                        coordinates (pixels).
     *                        Note that (0,0) is the top-left corner of the window.
     * @param dimensions      Width and height of the paddle in window coordinates.
     * @param renderable      The renderable representing the paddle. Can be null if
     *                       the paddle should not be rendered.
     * @param inputListener   The user input listener responsible for detecting keyboard input.
     * @param windowDimensions The dimensions of the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.setTag("Paddle");

    }

    /**
     * Updates the position of the paddle based on user input.
     *
     * @param deltaTime The time elapsed since the last update, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            if(this.getTopLeftCorner().x()>0){
            movementDir = movementDir.add(Vector2.LEFT);}
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            if((this.getTopLeftCorner().x()+this.getDimensions().x())< windowDimensions.x()){
            movementDir = movementDir.add(Vector2.RIGHT);}
        }

        setVelocity(movementDir.mult(MOVEMENT_SPEED));

    }

}
