package image;

import java.awt.*;


/**
 * The Utils class provides utility methods for image manipulation.
 */

public class Utils {

    /**
     * Wraps the input image to a square shape by padding with white pixels.
     *
     * @param image The input image to be wrapped
     * @return A new Image object representing the wrapped image
     */

    public static Image WrapImage(Image image){
        int oldHeight = image.getHeight();
        int oldWidth = image.getWidth();
        int height = findSqrt(oldHeight);
        int width = findSqrt(oldWidth);
      // add
        Color[][] warppedImage = new Color[height][width];

     for(int row = 0; row < height; row++){
         for (int col = 0; col < width; col++)
         {
            Color pixel =  pixelDecision(image, row, col,height - oldHeight,width - oldWidth,
                     oldHeight,oldWidth);
             warppedImage[row][col] = pixel;

         }
     }
     return new Image(warppedImage, width, height);
    }


    /**
     * Finds the next power of two greater than or equal to the input size.
     *
     * @param size The input size
     * @return The next power of two greater than or equal to the input size
     */

    private static int findSqrt(int size){
        int square = 1;
        while (square < size){
            square *= 2;
        }
        return square;
    }

    /**
     * Splits the input image into sub-images of the specified resolution.
     *
     * @param image      The input image to be split
     * @param resolution The resolution for splitting the image
     * @return A 2D array of Image objects representing the sub-images
     */

    public static Image[][] splitToSubImages(Image image, int resolution){
        int sizeOfImage = image.getHeight()/resolution;
        Image[][] subImagesArray = new Image[resolution][image.getWidth()/sizeOfImage];
        for (int t = 0; t < resolution; t++) {
        for (int i = 0; i < image.getWidth()/sizeOfImage; i++) {
            Color[][] subImage = new Color[sizeOfImage][sizeOfImage];
            for (int j = 0; j < sizeOfImage; j++) {
                for (int k = 0; k < sizeOfImage; k++) {
                    subImage[j][k] = image.getPixel(j+(t*sizeOfImage),k+((i)*sizeOfImage));
                }
            }
            subImagesArray[t][i] = new Image(subImage,sizeOfImage, sizeOfImage);
        }}
        return subImagesArray;

    }

    /**
     * Calculates the average brightness of the input image.
     *
     * @param image The input image
     * @return The average brightness of the image (normalized to the range [0, 1])
     */


    public static double calculateBrightness(Image image){
        double sum = 0;
        for (int i = 0; i < image.getHeight(); i++) {//check if this the height i need
            for (int j = 0; j < image.getWidth(); j++) {
                sum += convertToGrayScale(image.getPixel(i,j));
            }
        }
        sum /= image.getHeight()*image.getWidth();
        return sum/255;
    }

    /**
     * Makes a decision on the pixel color based on its position in the wrapped image.
     *
     * @param image     The original image
     * @param row       The row index of the pixel in the wrapped image
     * @param col       The column index of the pixel in the wrapped image
     * @param heightGap The height difference between the wrapped image and the original image
     * @param widthGap  The width difference between the wrapped image and the original image
     * @param oldRows   The height of the original image
     * @param oldCols   The width of the original image
     * @return The color of the pixel
     */

    private static Color pixelDecision(Image image,int row, int col,int heightGap,
                                       int widthGap, int oldRows, int oldCols)
    {

        if ( row < heightGap / 2  || row >= heightGap / 2 + oldRows  ){
            return new Color(255,255,255);
        } else if ( col < widthGap / 2  || col >= widthGap / 2 + oldCols ) {
            return new Color(255,255,255);
        }
        else {
           return image.getPixel(row - (heightGap / 2),col - (widthGap / 2));
        }
    }

    /**
     * Converts the input color to grayscale.
     *
     * @param pixel The input color
     * @return The brightness value of the color (normalized to the range [0, 255])
     */

    private static double convertToGrayScale(Color pixel)
    {
       return pixel.getRed() * 0.2126 + pixel.getGreen() * 0.7152
                + pixel.getBlue() * 0.0722;
    }


}
