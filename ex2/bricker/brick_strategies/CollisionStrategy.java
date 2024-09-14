package bricker.brick_strategies;
import danogl.GameObject;

/**
 * This interface defines the contract for collision strategies in the brick game. Classes implementing
 * this interface must provide an implementation for the onCollision method, which handles the collision
 * between two game objects
 */

public interface CollisionStrategy {

    /**
     * Handles the collision between two game objects.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */

    void onCollision(GameObject obj1, GameObject obj2);
}
