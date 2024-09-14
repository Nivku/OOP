package bricker.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * Represents a special type of camera that follows an object but deactivates
 * after a certain number of collisions.
 */
public class SpecialCamera extends Camera {
    /**
     * Number of collisions required to break a brick.
     */
    public static final int COLLISION_TO_BREAK = 4;

    private final GameManager gameManager;
    private final Ball ball;
    private final int numOfCollisions;


    /**
     * Constructs a new SpecialCamera object.
     *
     * @param objToFollow           The game object to follow.
     * @param deltaRelativeToObject The relative position of the camera to the object it follows.
     * @param dimensions            The dimensions of the camera.
     * @param windowDimensions      The dimensions of the game window.
     * @param gameManager           The game manager instance.
     */
    public SpecialCamera(GameObject objToFollow, Vector2 deltaRelativeToObject,
                         Vector2 dimensions, Vector2 windowDimensions, GameManager gameManager) {
        super(objToFollow, deltaRelativeToObject, dimensions, windowDimensions);
            this.gameManager = gameManager;
            this.ball =  (Ball) objToFollow;
            this.numOfCollisions = ball.getCollisionCounter();

    }

    /**
     * Updates the state of the SpecialCamera.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int currentNumOfCollisions = ball.getCollisionCounter();
        if (currentNumOfCollisions - numOfCollisions >= COLLISION_TO_BREAK) {
            gameManager.setCamera(null);
        }
    }
}

