package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.ImageRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.Puck;

import java.util.Random;

/**
 * AddBallsStrategy Class
 * This class implements the CollisionStrategy interface and is responsible for handling collisions
 * with bricks in the game. When a collision occurs, it removes the brick, decrements a counter,
 * and adds two new balls to the game at the position of the removed brick. Each new ball has a random
 * initial velocity and is created with a specified image and sound.
 */

public class AddBallsStrategy implements CollisionStrategy {
    /**
     * Path to the mock ball image asset.
     */
    public static final String MOCK_PATH = "assets/mockBall.png";
    /**
     * Path to the blop sound effect asset.
     */
    public static final String BLOP_PATH = "assets/blop_cut_silenced.wav";
    private final ImageReader imageReader;
    private static final int BALL_SIZE =20;
    private final SoundReader soundReader;
    private GameObjectCollection collection;
    private danogl.util.Counter counter;

    private Random random = new Random();

    /**
     * Constructor for AddBallsStrategy.
     *
     * @param collection   The collection of game objects.
     * @param counter      Counter to keep track of game state.
     * @param imageReader  Responsible for reading images.
     * @param soundReader  Responsible for reading sounds.
     */

    public AddBallsStrategy(GameObjectCollection collection,
                            Counter counter,
                            ImageReader imageReader,
                            SoundReader soundReader) {
        this.collection = collection;
        this.counter = counter;
        this.imageReader =imageReader;
        this.soundReader = soundReader;
    }

    /**
     * Handles the collision by removing the brick, decrementing the
     * counter of the left bricks, and adding two new balls.
     * Each new ball is created with a random direction.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */


    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
       Vector2 placeOFBrick = obj1.getCenter();
        collection.removeGameObject(obj1);
        counter.decrement();
        ImageRenderable ballImage =
                imageReader.readImage(MOCK_PATH, true);
        Sound collsionSound = soundReader.readSound(BLOP_PATH);
        for (int i = 0; i < 2; i++) {
            GameObject ball = new Puck(placeOFBrick, new Vector2((float)
                    (BALL_SIZE*0.75), (float) (BALL_SIZE*0.75)), ballImage, collsionSound);
            int val1 = random.nextInt(2);
            int val2 = random.nextInt(2);
            ball.setVelocity(new Vector2((float)Math.pow(-1,val1),(float)Math.pow(-1,val2)).mult(100));
            collection.addGameObject(ball);
        }

    }
}
