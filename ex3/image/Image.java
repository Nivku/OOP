package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * The Image class represents an image with its pixel data stored as a 2D array of Color objects.
 * This class provides methods for reading an image from a file, accessing pixel data, and saving the image.
 */
public class Image {


    private final Color[][] pixelArray; // 2D array to store pixel data as Color objects
    private final int width; // Width of the image
    private final int height; // Height of the image

    /**
     * Constructs an Image object by reading an image file from the specified filename.
     *
     * @param filename The filename of the image to be loaded
     * @throws IOException if an error occurs while reading the image file
     */

    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object with the provided pixel array, width, and height.
     *
     * @param pixelArray A 2D array of Color objects representing pixel data
     * @param width      The width of the image
     * @param height     The height of the image
     */

    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the width of the image.
     *
     * @return The width of the image
     */

    public int getWidth() {
        return width;
    }

    /**
     * Gets the height of the image.
     *
     * @return The height of the image
     */

    public int getHeight() {
        return height;
    }


    /**
     * Gets the Color object representing the pixel at the specified coordinates.
     *
     * @param x The x-coordinate of the pixel
     * @param y The y-coordinate of the pixel
     * @return The Color object representing the pixel at the specified coordinates
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }


    /**
     * Saves the image to a file with the specified filename.
     *
     * @param fileName The filename for saving the image
     */

    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
