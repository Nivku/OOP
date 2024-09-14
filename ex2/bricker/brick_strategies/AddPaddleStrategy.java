package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.SingeltonPaddle;

/**
 * This class implements the CollisionStrategy interface and is responsible for handling collisions
 * with special bricks in the game. When a collision occurs, it removes the special brick, decrements
 * a counter representing the number of remaining bricks, and adds a new paddle to the game if no other
 * paddles have been added yet. The added paddle is controlled by the user through the
 * specified input listener.
 */

public class AddPaddleStrategy implements CollisionStrategy {
    /**
     * Path to the paddle image asset.
     */
    public static final String PADDLE_PATH = "assets/paddle.png";

    /**
     * Size of the paddle represented as a vector (width, height).
     */
    public static final Vector2 PADDLE_SIZE = new Vector2(100, 15);

    private final GameObjectCollection collection;
    private final Counter counter;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;
    private static boolean AnotherPaddle = false;

    /**
     * Constructor for AddPaddleStrategy.
     *
     * @param collection       The collection of game objects.
     * @param counter          Counter to know the number of left bricks.
     * @param imageReader      Responsible for reading images.
     * @param inputListener    Listener for user input to control the added paddle.
     * @param windowController Controller for managing the game window.
     */

    public AddPaddleStrategy(GameObjectCollection collection,
                             Counter counter,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             WindowController windowController) {

        this.collection = collection;
        this.counter = counter;
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        windowDimensions = windowController.getWindowDimensions();

    }

    /**
     * Static method to set the 'AnotherPaddle' flag.
     *
     * @param anotherPaddle Boolean value indicating whether another paddle has been added.
     */


    public static void setAnotherPaddle(boolean anotherPaddle) {
        AnotherPaddle = anotherPaddle;
    }

    /**
     * Handles the collision by removing the special brick, decrementing the counter representing the number
     * of remaining bricks, and adding a new paddle if no other paddles have been added yet.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        collection.removeGameObject(obj1);
        counter.decrement();
        if (!AnotherPaddle){
            ImageRenderable paddleImage =
                    imageReader.readImage(PADDLE_PATH, true);
            GameObject paddle = new SingeltonPaddle(Vector2.ZERO, PADDLE_SIZE, paddleImage,
                    inputListener, windowDimensions,collection);
            paddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()/2));
            collection.addGameObject(paddle);
            AnotherPaddle = true;
        }

    }
}
