package bricker.brick_strategies;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;

import java.util.Random;

/**
 * This class implements the CollisionStrategy interface and represents a collision
 * strategy with double behavior.
 * It randomly selects two collision strategies from a set of possible strategies
 * and applies them sequentially
 * when a collision occurs. It also allows for a third optional strategy if one of the strategies is another
 * DoubleBehaviorStrategy
 */


public class DoubleBehaviorStrategy implements CollisionStrategy {


    private final GameObjectCollection collection;
    private final Counter counter;
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;
    private CollisionStrategy strategy3;
    private CollisionStrategyFactory factory;


    /**
     * Constructor for DoubleBehaviorStrategy.
     *
     * @param collection       The collection of game objects.
     * @param counter          Counter representing the number of remaining bricks.
     * @param imageReader      Responsible for reading images.
     * @param soundReader      Responsible for reading sounds.
     * @param inputListener    Listener for user input.
     * @param windowController Controller for managing the game window.
     * @param gameManager      Manager for handling game-related functionalities.
     */
    public DoubleBehaviorStrategy(GameObjectCollection collection,
                                  Counter counter,
                                  ImageReader imageReader, SoundReader soundReader,
                                   UserInputListener inputListener,
                                  WindowController windowController, GameManager gameManager) {
        this.collection = collection;
        this.counter = counter;
        this.strategy1 = null;
        this.strategy2 = null;
        this.strategy3 = null;
        this.factory = new CollisionStrategyFactory(imageReader, soundReader,
                inputListener, windowController, gameManager,collection, counter);
        this.randomBehavior();

    }



    /**
     * Handles the collision by removing the brick, decrementing the counter, and applying the selected
     * collision strategies sequentially.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        collection.removeGameObject(obj1);
        counter.decrement();
        strategy1.onCollision(obj1, obj2);
        counter.increment();
        strategy2.onCollision(obj1, obj2);
        counter.increment();
        if(strategy3 != null){
            strategy3.onCollision(obj1, obj2);
            counter.increment();
        }

    }


    /**
     * Performs random behavior selection for strategies.
     * Uses a random number generator to determine the behavior to apply to strategies.
     */
    private void randomBehavior(){
        Random random = new Random();
        int firstBehavior = random.nextInt(5,10);
        if (firstBehavior == 9){
            this.strategy1  = factory.buildStrategy(5,9);
            this.strategy3  = factory.buildStrategy(5,9);
        }
        else{
            this.strategy1  = factory.buildStrategy(5,9);
        }
        this.strategy2  = factory.buildStrategy(5,9);

    }


}
