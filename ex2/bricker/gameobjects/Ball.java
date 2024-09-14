package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Ball class represents a game object that behaves like a ball in the game world.
 * It extends the GameObject class and includes additional functionality related to collision handling.
 */
public class Ball extends GameObject {
    private Sound collisionSound;// Sound played upon collision
    private int collisionCounter;// Counter to keep track of collisions
    /**
     * Constructs a new Ball object.
     *
     * @param topLeftCorner Position of the top-left corner of the ball, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the ball in window coordinates.
     * @param renderable    The renderable representing the ball. Can be null if the ball is not rendered.
     * @param collisionSound The sound played when the ball collides with another object.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.setTag("Ball");
    }



    /**
     * Handles the collision event when the ball collides with another game object.
     * Upon collision, it reverses the ball's velocity and plays the collision sound.
     *
     * @param other     The GameObject with which the ball collided.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVelocity = getVelocity().flipped(collision.getNormal());
        setVelocity(newVelocity);
        collisionSound.play();
        collisionCounter++;

    }

    /**
     * Retrieves the number of collisions the ball has encountered.
     *
     * @return The number of collisions.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
