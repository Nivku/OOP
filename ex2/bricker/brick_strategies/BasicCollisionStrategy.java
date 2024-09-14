package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

/**
 * This class implements the CollisionStrategy interface and is a basic collision strategy
 * for handling collisions with bricks in the game. When a collision occurs, it removes the
 * brick, and decrements a counter representing the number of remaining bricks.
 */

public class BasicCollisionStrategy implements CollisionStrategy {

    private final GameObjectCollection collection;
    private final danogl.util.Counter  counter;

    /**
     * Constructor for BasicCollisionStrategy.
     *
     * @param collection The collection of game objects.
     * @param counter    Counter representing the number of remaining bricks.
     */

    public BasicCollisionStrategy(GameObjectCollection collection, danogl.util.Counter counter) {
        this.collection = collection;
        this.counter = counter;
    }

    /**
     * Handles the collision by removing the brick and decrementing the counter representing the number
     * of remaining bricks.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */

    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        collection.removeGameObject(obj1);
        counter.decrement();

    }
}
