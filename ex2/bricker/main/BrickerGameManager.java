package bricker.main;

import bricker.brick_strategies.*;
import bricker.brick_strategies.CollisionStrategyFactory;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.*;
import danogl.util.Counter;
import danogl.util.Vector2;
import bricker.gameobjects.*;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Random;


/**
 * The BrickerGameManager class represents the game manager responsible for managing the Bricker game.
 * It extends the GameManager class and includes functionality related to initializing game elements,
 * updating game state, and handling game events.
 */
public class BrickerGameManager extends GameManager{


    /**
     * Number of bricks in each row.
     */
    private static final int NUM_OF_BRICKS_IN_ROWS = 7;

    /**
     * Number of rows of bricks.
     */
    private static final int NUM_OF_ROWS = 8;

    /**
     * Default width of the game window.
     */
    public static final int DEF_WIDTH = 700;

    /**
     * Default height of the game window.
     */
    public static final int DEF_HEIGHT = 500;

    /**
     * Offset from the window border.
     */
    public static final int BORDER_OFFSET = 5;

    /**
     * Path to the brick image asset.
     */
    public static final String BRICK_PATH = "assets/brick.png";

    /**
     * Path to the ball image asset.
     */
    public static final String BALL_PATH = "assets/ball.png";

    /**
     * Path to the sound effect for the ball.
     */
    public static final String BALL_SOUND = "assets/blop_cut_silenced.wav";

    /**
     * Size of the ball represented as a vector (width, height).
     */
    public static final Vector2 BALL_SIZE = new Vector2(20.0F, 20.0F);

    /**
     * Path to the paddle image asset.
     */
    public static final String PADDLE_PATH = "assets/paddle.png";

    /**
     * Size of the paddle represented as a vector (width, height).
     */
    public static final Vector2 PADDLE_SIZE = new Vector2(100.0F, 15.0F);

    /**
     * Path to the background image asset.
     */
    public static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";

    /**
     * Prompt message displayed when the player wins.
     */
    public static final String WIN_PROMPT = "You win!";

    /**
     * Prompt message displayed when the player loses.
     */
    public static final String LOSE_PROMPT = "You lose!";

    /**
     * Prompt message asking the player if they want to play again.
     */
    public static final String PLAY_AGAIN_PROMPT = " play again?";

    /**
     * Factor for ball velocity adjustment.
     */
    public static final int BALL_APPED_FACTOR = 170;

    private Random random = new Random();
    private final int numOfBricksInRows;
    private final int numOfRows;
    private int BOARD_SIZE = 5;
    private GameObject ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private danogl.util.Counter counter;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private LifeView lifeView;

    /**
     * Constructs a new BrickerGameManager object with the specified window title, window dimensions,
     * number of bricks in rows, and number of rows.
     *
     * @param windowTitle          The title of the game window.
     * @param windowDimensions    The dimensions of the game window.
     * @param numOfBricksInRows   The number of bricks in each row.
     * @param numOfRows           The number of rows of bricks.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions,
                              int numOfBricksInRows, int numOfRows) {
        super(windowTitle, windowDimensions);
        this.numOfBricksInRows = numOfBricksInRows;
        this.numOfRows = numOfRows;

    }
    /**
     * Constructs a new BrickerGameManager object with the specified window title and window dimensions.
     * The number of bricks in rows and number of rows are set to default values.
     *
     * @param windowTitle          The title of the game window.
     * @param windowDimensions    The dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
        this.numOfBricksInRows = NUM_OF_BRICKS_IN_ROWS;
        this.numOfRows = NUM_OF_ROWS;

    }


    /**
     * Initializes the game by setting up game elements such as the ball, paddle, bricks, background, etc.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener
            inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.imageReader = imageReader;
        this.soundReader =soundReader ;
        this.inputListener = inputListener;
        this.windowController = windowController;
        windowDimensions = windowController.getWindowDimensions();
        this.counter = new Counter(numOfRows*numOfBricksInRows);
        // create ball
        createBall();
        // create paddle
        createPaddle();
        //create brick
        createBricks();
        //create boards
        createBorders();
        // create backround
        createBackground(imageReader);
        AddPaddleStrategy.setAnotherPaddle(false);
        this.lifeView = new LifeView(this, imageReader,
                gameObjects(), windowController);

    }


    /**
     * Creates a ball object, sets its initial position and velocity, and adds it to the game.
     */

    private void createBall() {
        ImageRenderable ballImage =
        imageReader.readImage(BALL_PATH, true);
        Sound collsionSound = soundReader.readSound(BALL_SOUND);
        this.ball = new Ball(Vector2.ZERO, BALL_SIZE, ballImage, collsionSound);
        int val1 = random.nextInt(2);
        int val2 = random.nextInt(2);
        ball.setVelocity(new Vector2((float)Math.pow(-1,val1),
                (float)Math.pow(-1,val2)).mult(BALL_APPED_FACTOR));
        ball.setCenter(windowDimensions.mult(0.5F));
        gameObjects().addGameObject(ball);
    }

    /**
     * Creates a paddle object, sets its initial position at the
     * bottom center of the game window, and adds it to the game.
     */
    private void createPaddle() {
        ImageRenderable paddleImage =
                imageReader.readImage(PADDLE_PATH, true);
        GameObject paddle = new Paddle(Vector2.ZERO, PADDLE_SIZE, paddleImage,
                inputListener, windowDimensions);
        paddle.setCenter(new Vector2(windowDimensions.x()/2, (int)windowDimensions.y()-30));
        gameObjects().addGameObject(paddle);
    }

    /**
     * Creates a background object using the provided image reader and adds it
     * to the game as a background layer.
     *
     * @param imageReader The image reader used to read the background image.
     */
    private void createBackground(ImageReader imageReader) {
        ImageRenderable backImage =
                imageReader.readImage(BACKGROUND_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    /**
     * Overrides the update method from the superclass (GameManager) to update the game state.
     * It checks for game end conditions and handles objects going outside the game window.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        checkForOutsideObjects();

    }


    /**g
     * Checks if the game has ended based on conditions such as the
     * ball going below the game window or all bricks being destroyed.
     * It also handles prompts for win or loss.
     */
    private void checkForOutsideObjects() {
        for (Iterator<GameObject> it = gameObjects().iterator(); it.hasNext(); ) {
            GameObject object = it.next();
            double height = object.getCenter().y();
            if(height > windowDimensions.y()){
                    gameObjects().removeGameObject(object);
                }
        }
    }

    /**
     * Checks for game end conditions, such as winning or losing.
     */
    private void checkForGameEnd() {
        double ballHeight = ball.getCenter().y();
        String prompt = "";
        if(counter.value() < 1 || inputListener.isKeyPressed(KeyEvent.VK_W)){
            prompt = WIN_PROMPT;
            //win
        }
        if(ballHeight > windowDimensions.y()){
            lifeView.decrementLifeView();
            if(lifeView.getHearts() > 0){
                int val1 = random.nextInt(2);
                int val2 = random.nextInt(2);
                ball.setVelocity(new Vector2((float)Math.pow(-1,val1),
                        (float)Math.pow(-1,val2)).mult(BALL_APPED_FACTOR));
                windowDimensions = windowController.getWindowDimensions();
                ball.setCenter(windowDimensions.mult(0.5F));

            }
            else{
            //lost
            prompt = LOSE_PROMPT;}
        }
        if (!prompt.isEmpty()){
            prompt += PLAY_AGAIN_PROMPT;
            if(windowController.openYesNoDialog(prompt)){
                this.lifeView = new LifeView(this, imageReader,
                        gameObjects(), windowController);
                windowController.resetGame();
            }
            else{
                windowController.closeWindow();
            }

        }
    }

    /**
     * Creates brick objects in rows and columns, assigning collision strategies
     * to each brick based on a random number.
     */
    private void createBricks() {
        CollisionStrategyFactory factory = new CollisionStrategyFactory(imageReader, soundReader,
                inputListener, windowController, this,gameObjects(), counter);
        Renderable brickImage =
                imageReader.readImage(BRICK_PATH, false);
        int brickSize = (int) ((windowDimensions.x()- 2* BOARD_SIZE)/numOfBricksInRows)-1;
        float brickSIZEWidth = 15;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfBricksInRows; j++) {
                CollisionStrategy strategy = factory.buildStrategy(0,0);
                GameObject brick = new Brick(new Vector2(BORDER_OFFSET+(brickSize)*j,
                                               5+(brickSIZEWidth)*i),
                                             new Vector2(brickSize-1,brickSIZEWidth-1),
                                                brickImage, strategy);
                gameObjects().addGameObject(brick);

            }
        }

    }



    /**
     * Creates border objects (left, right, and top) to prevent game objects
     * from moving outside the game window.
     */
    private void createBorders(){
        GameObject borderLeft = new GameObject(new Vector2(0,0),
                new Vector2(BORDER_OFFSET, windowDimensions.y()), null);
        gameObjects().addGameObject(borderLeft);
        GameObject borderRight = new GameObject(new Vector2(windowDimensions.x()- BORDER_OFFSET,0),
                new Vector2(BORDER_OFFSET, windowDimensions.y()), null);
        gameObjects().addGameObject(borderRight);
        GameObject borderCeil = new GameObject(new Vector2(0,0),
                new Vector2(windowDimensions.x(), BORDER_OFFSET), null);
        gameObjects().addGameObject(borderCeil);

    }
    /**
     * Adds a heart to the game .
     */
    public void addHeart(){
          lifeView.addHeart();
    }


    /**
     * The entry point of the program.
     * It instantiates a BrickerGameManager object based on command-line arguments
     * (if provided) and runs the game.
     *
     * @param args Command-line arguments specifying the number of bricks in rows and columns.
     */
    public static void main(String[] args) {
        if(args.length == 2){
        new BrickerGameManager("Bricker",
                new Vector2(DEF_WIDTH, DEF_HEIGHT),Integer.parseInt(args[0]),
                Integer.parseInt(args[1])).run();}
        else {
            new BrickerGameManager("Bricker", new Vector2(DEF_WIDTH,DEF_HEIGHT)).run();}
        }

        }






