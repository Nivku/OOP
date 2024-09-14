package ascii_art;
import image.Image;
import image.Utils;
import image_char_matching.SubImgCharMatcher;


/**
 * The AsciiArtAlgorithm class is responsible for converting an image into ASCII art.
 * It divides the image into smaller sub-images, calculates the brightness of each sub-image,
 * and replaces it with a corresponding ASCII character based on brightness.
 */

public class AsciiArtAlgorithm {


    private final int resolution;  // Resolution for splitting the image into sub-images
    private final SubImgCharMatcher matcher;  // Matcher for mapping brightness to ASCII characters
    private final Image image;  // The input image

    /**
     * Constructs an AsciiArtAlgorithm object with the given image,
     * resolution, and sub-image character matcher.
     *
     * @param image              The input image to be converted to ASCII art
     * @param resolution         The resolution for splitting the image into sub-images
     * @param subImgCharMatcher The matcher used to map brightness to ASCII characters
     */
    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher subImgCharMatcher) {
        this.image = image;
        this.resolution = resolution;
        this.matcher = subImgCharMatcher;
    }


    /**
     * Runs the ASCII art conversion algorithm on the input image.
     *
     * @return A 2D character array representing the ASCII art version of the input image
     */

    public char[][] run() {
        Image wrapImage = Utils.WrapImage(image);
        Image[][] subImages = Utils.splitToSubImages(wrapImage, resolution);
        char[][] asciiImage = new char[subImages.length][subImages[0].length];
        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[0].length; j++) {
                double brightness = Utils.calculateBrightness(subImages[i][j]);
                asciiImage[i][j] = matcher.getCharByImageBrightness(brightness);
            }
        }
        return asciiImage;
    }

}





