package bricker.brick_strategies;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.SpecialCamera;

/**
 * This class implements the CollisionStrategy interface and is responsible for
 * handling collisions
 * with special bricks in the game that activate a camera which follow the ball.
 * When a collision occurs, it removes
 * the special brick, decrements a counter, and activates the camera.
 */

public class CameraCollisionStrategy implements CollisionStrategy{

    private final GameObjectCollection collection;
    private final Counter counter;
    private final Vector2 windowDimensions;
    private final GameManager gameManager;



    /**
     * Constructor for CameraCollisionStrategy.
     *
     * @param collection       The collection of game objects.
     * @param counter          Counter representing the number of remaining bricks.
     * @param windowController Controller for managing the game window.
     * @param gameManager      Manager for handling game-related functionalities.
     */


    public CameraCollisionStrategy(GameObjectCollection collection,
                                   Counter counter,
                                   WindowController windowController,GameManager gameManager) {
        this.collection = collection;
        this.counter = counter;
        this.windowDimensions = windowController.getWindowDimensions();
        this.gameManager = gameManager;

    }

    /**
     * Handles the collision by removing the special brick, decrementing the counter, and activating the
     * camera if certain conditions are met.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */

    @Override
    public void onCollision(GameObject obj1, GameObject obj2){
        collection.removeGameObject(obj1);
        counter.decrement();
        if(gameManager.camera() == null ) {
            if (obj2.getTag().equals("Ball")) {
                gameManager.setCamera(new SpecialCamera(obj2, Vector2.ZERO,
                        windowDimensions.mult((1.2F)), windowDimensions, gameManager));

            }
        }

    }












}
