package bricker.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;


/**
 * Represents a heart object in the game, which can collide with other game objects.
 */
public class Heart extends GameObject {


    private GameObjectCollection collection;
    private BrickerGameManager gameManager;



    /**
     * Constructs a new Heart object.
     *
     * @param topLeftCorner Position of the top-left corner of the heart,
     *                     in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the heart in window coordinates.
     * @param renderable    The renderable representing the heart. Can be null if the heart
     *                      should not be rendered.
     * @param collection    The collection of game objects.
     * @param gameManager   The game manager responsible for managing the game state.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 GameObjectCollection collection, GameManager gameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.collection = collection;
        this.gameManager = (BrickerGameManager) gameManager;
    }


    /**
     * Called when a collision occurs with another game object.
     * Adds a heart to the game manager and removes the heart from the collection.
     *
     * @param other     The other game object involved in the collision.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals("Paddle")){
                return super.shouldCollideWith(other);
        }
        return false;

    }


    /**
     * Called when a collision occurs with another game object.
     * Adds a heart to the game manager and removes the heart from the collection.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision data.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameManager.addHeart();
        collection.removeGameObject(this);

    }

}
