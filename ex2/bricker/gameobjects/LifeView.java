package bricker.gameobjects;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;


/**
 * Represents the view for displaying life-related UI elements in the game.
 * This includes hearts indicating player's life count and numerical representation of life count.
 */
public class LifeView {

    /**
     * Size of a heart represented as a vector (width, height).
     */
    public static final Vector2 HEART_SIZE = new Vector2(30, 30);

    /**
     * Size of the border.
     */
    public static final int BORD_SIZE = 20;

    /**
     * Number of hearts initially available to the player.
     */
    public final int NUM_HEARTS = 4;

    /**
     * Path to the heart image asset.
     */
    public static final String HEART_PATH = "assets/heart.png";

    private Heart[] heartsObjects;
    private TextRenderable numera;
    private int hearts=3;
    private ImageReader imageReader;
    private GameObjectCollection collection;
    private WindowController windowController;
    private GameManager gameManager;



    /**
     * Constructor for LifeView class.
     *
     * @param gameManager      Instance of GameManager providing game-related functionalities.
     * @param imageReader      Instance of ImageReader for loading heart images.
     * @param collection       Collection of game objects to manage.
     * @param windowController Controller for managing game window.
     */
    public LifeView(GameManager gameManager, ImageReader imageReader,
                    GameObjectCollection collection, WindowController windowController) {
        this.heartsObjects = new Heart[NUM_HEARTS];
        this.imageReader = imageReader;
        this.collection = collection;
        this.windowController = windowController;
        this.gameManager = gameManager;
        createHearts();
        createNumericalLife();
    }

    /**
     * Creates heart objects representing the player's lives and adds them to the game's UI.
     */
    private void createHearts() {
        ImageRenderable heartImage =
                imageReader.readImage("assets/heart.png", true);
        for (int i = 1; i < hearts+1; i++) {
            Heart heart = new Heart(Vector2.ZERO, HEART_SIZE, heartImage, collection, gameManager);
            heart.setCenter(new Vector2(BORD_SIZE +(heart.getDimensions().y()+10)*i,
                    (int)windowController.getWindowDimensions().y()-BORD_SIZE));
            collection.addGameObject(heart, Layer.UI);
            heartsObjects[i-1] = heart;
        }
    }



    /**
     * Creates a numerical text object representing the remaining number of hearts
     * (lives) and adds it to the game's UI.
     * It also sets the color of the text based on the number of hearts remaining.
     */
    private void createNumericalLife() {
        TextRenderable number = new TextRenderable(String.valueOf(hearts));
        GameObject num = new GameObject(Vector2.ZERO, HEART_SIZE, number);
        num.setCenter(new Vector2(BORD_SIZE+5, (int)windowController.
                getWindowDimensions().y()-(BORD_SIZE+5)));
        collection.addGameObject(num, Layer.UI);
        this.numera = number;
        SetNumberColor();
    }


    /**
     * Adds a heart to the game UI if the number of hearts is less than or equal to 3.
     */
    public void addHeart(){
        if(getHearts() <= 3) {
            ImageRenderable heartImage =
                    imageReader.readImage(HEART_PATH, true);
            Heart heart = new Heart(Vector2.ZERO, HEART_SIZE, heartImage, collection,gameManager);
            heart.setCenter(new Vector2(BORD_SIZE+(heart.getDimensions().y()+10)*
                    (hearts+1), (int)windowController.getWindowDimensions().y()-BORD_SIZE));
            collection.addGameObject(heart, Layer.UI);
            setHeartsObjects(heart);
            SetNumberColor();
        }
    }


    /**
     * Sets the color of the numerical text based on the remaining number of hearts.
     */
    private void SetNumberColor() {
        this.numera.setString(String.valueOf(hearts));
        switch (hearts){
            case 1:
                this.numera.setColor(Color.red);
                break;
            case 2:
                this.numera.setColor(Color.yellow);
                break;
            default:
                this.numera.setColor(Color.green);
                break;
        }

    }

    /**
     * Retrieves the number of hearts remaining.
     *
     * @return The number of hearts.
     */
    public int getHearts() {
        return hearts;
    }


    /**
     * Sets the heart objects displayed on the UI.
     *
     * @param heart The heart object to set.
     */
    public void setHeartsObjects(Heart heart) {
        heartsObjects[this.hearts++] = heart;
    }

    /**
     * Decrements the life view by removing a heart and updating the numerical life display.
     * Decreases the count of hearts indicating player's life,
     * removes the corresponding heart object from the collection,
     * and updates the color of the numerical life display.
     */
    public void decrementLifeView()
    {
        hearts--;
        collection.removeGameObject(heartsObjects[hearts], Layer.UI);
        SetNumberColor();

    }






}
