package bricker.gameobjects;

import bricker.brick_strategies.AddPaddleStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;


/**
 * Represents a special type of paddle that is active for a limited duration.
 * After a certain number of collisions with a ball, it gets deactivated.
 */
public class SingeltonPaddle extends Paddle {


    private Counter counter  = new Counter(4);
    private final GameObjectCollection collection;

    /**
     * Constructs a new SingletonPaddle object.
     *
     * @param topLeftCorner     Position of the top-left corner of the paddle, in window coordinates (pixels).
     *                          Note that (0,0) is the top-left corner of the window.
     * @param dimensions        Width and height of the paddle in window coordinates.
     * @param renderable        The renderable representing the paddle. Can be null
     *                         if the paddle should not be rendered.
     * @param inputListener     The input listener for user interactions.
     * @param windowDimensions  Dimensions of the game window.
     * @param collection        Collection of game objects to manage the paddle's lifecycle.
     */

    public SingeltonPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                           UserInputListener inputListener,
                           Vector2 windowDimensions,GameObjectCollection collection) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.collection = collection;
        this.setTag("SingletonPaddle");
    }

    /**
     * Updates the state of the SingletonPaddle.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }



    /**
     * Handles collision events involving the SingletonPaddle.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("Ball") || other.getTag().equals("Puck") ){
            counter.decrement();
            if (counter.value() == 0) {
                AddPaddleStrategy.setAnotherPaddle(false);
                collection.removeGameObject(this);
            }

        }

    }


}
