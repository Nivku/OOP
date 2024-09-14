package bricker.brick_strategies;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Counter;

import java.util.Random;

/**
 * This class is a factory for creating various collision strategies based on the specified strategy name.
 * It provides a method to build and return the appropriate collision strategy instance with the required
 * dependencies.
 */

public class CollisionStrategyFactory {
    private final SoundReader soundReader;
    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final GameManager gameManager;
    private GameObjectCollection collection;
    private Counter counter;
    private Random random = new Random();


    /**
     * Constructor for CollisionStrategyFactory.
     *
     * @param imageReader      Responsible for reading images.
     * @param soundReader      Responsible for reading sounds.
     * @param inputListener    Listener for user input.
     * @param windowController Controller for managing the game window.
     * @param gameManager      Manager for handling game-related functionalities.
     */
    public CollisionStrategyFactory(ImageReader imageReader, SoundReader soundReader,
                                    UserInputListener inputListener,
                                    WindowController windowController, GameManager
                                            gameManager, GameObjectCollection collection, Counter counter){
        this.imageReader =imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.gameManager = gameManager;
        this.collection = collection;
        this.counter = counter;

    }


    /**
     * Builds and returns a collision strategy based on the specified range of strategy numbers.
     * Generates a random strategy number within the specified range and instantiates the
     * corresponding collision strategy.
     *
     * @param start The lower bound of the range for generating random strategy numbers.
     * @param end   The upper bound of the range for generating random strategy numbers.
     * @return A CollisionStrategy object representing the selected collision strategy.
     */
    public CollisionStrategy buildStrategy(int start, int end){
        int strategyNumber;
        if (start == 0 && end == 0){//case all
            strategyNumber = random.nextInt(10);}
        else {
            strategyNumber = random.nextInt(start,end);
        }
        CollisionStrategy strategy = null;
        switch (strategyNumber){
            case 0,1,2,3,4:
                strategy = new BasicCollisionStrategy(collection, counter);
                break;
            case 5:
                strategy = new AddBallsStrategy(collection, counter, imageReader, soundReader);
                break;
            case 6:
                strategy = new AddPaddleStrategy(collection, counter, imageReader,
                        inputListener, windowController);
                break;
            case 7:
                strategy = new CameraCollisionStrategy(collection, counter,windowController,gameManager);
                break;
            case 8:
                strategy = new ReturnDisqualificationStrategy(collection,counter,imageReader, gameManager);
                break;
            case 9:
                strategy = new DoubleBehaviorStrategy(collection, counter,imageReader,soundReader,
                        inputListener,windowController,gameManager);

        }
        return strategy;
    }




}

