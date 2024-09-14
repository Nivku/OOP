package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * Represents a brick object in the game, which can collide with other game objects.
 */
public class Brick extends GameObject {
    private CollisionStrategy strategy;

    /**
     * Constructs a new Brick object.
     *
     * @param topLeftCorner Position of the top-left corner of the brick, in
     *                      window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height of the brick in window coordinates.
     * @param renderable    The renderable representing the brick. Can be null if
     *                     the brick should not be rendered.
     * @param strategy      The collision strategy associated with the brick.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy strategy) {
        super(topLeftCorner, dimensions, renderable);
        this.strategy = strategy;

    }

    /**
     * Called when a collision occurs with another game object.
     * Delegates collision handling to the associated collision strategy.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision data.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        strategy.onCollision(this, other);
    }
}
