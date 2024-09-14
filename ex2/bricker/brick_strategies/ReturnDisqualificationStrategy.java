package bricker.brick_strategies;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.Heart;
import danogl.GameManager;


/**
 * This class implements the CollisionStrategy interface and represents a collision strategy
 * that handles collisions with bricks resulting in the return of a special game object (e.g., heart).
 * It creates and adds a special game object to the collection, removes the collided brick, and decrements
 * a counter representing the number of remaining bricks.
 */

public class ReturnDisqualificationStrategy implements CollisionStrategy {

    /**
     * Path to the heart image asset.
     */
    public static final String HEART_PATH = "assets/heart.png";

    /**
     * Size of a heart.
     */
    public static final int HEART_SIZE = 30;

    /**
     * Factor to adjust the speed.
     */
    public static final int SPEED_FACTOR = 100;

    private final GameObjectCollection collection;
    private final danogl.util.Counter counter;
    private final ImageReader imageReader;
    private final GameManager gameManager;


    /**
     * Constructor for ReturnDisqualificationStrategy.
     *
     * @param collection The collection of game objects.
     * @param counter    Counter representing the number of remaining bricks.
     * @param imageReader Responsible for reading images.
     * @param gameManager Manager for handling game-related functionalities.
     */



    public ReturnDisqualificationStrategy(GameObjectCollection collection,
                                          Counter counter,
                                          ImageReader imageReader,
                                          GameManager gameManager) {
        this.collection = collection;
        this.counter = counter;
        this.imageReader = imageReader;
        this.gameManager = gameManager;
    }

    /**
     * Handles the collision by creating and adding a special game object, removing the collided brick,
     * and decrementing the counter.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */


    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        collection.removeGameObject(obj1);
        Vector2 placeOFBrick = obj1.getCenter();
        ImageRenderable heartImage =
                imageReader.readImage(HEART_PATH, true);
        Heart heart = new Heart(Vector2.ZERO, new Vector2(HEART_SIZE,HEART_SIZE),
                heartImage, collection, gameManager);
        heart.setCenter(placeOFBrick);
        heart.setVelocity(new Vector2(0,1).mult(SPEED_FACTOR));
        collection.addGameObject(heart);
        counter.decrement();

    }

}
