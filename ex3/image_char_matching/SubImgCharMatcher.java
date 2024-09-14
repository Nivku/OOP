package image_char_matching;

import java.util.*;

/**
 * The SubImgCharMatcher class is responsible for mapping image brightness to ASCII characters.
 * It allows adding and removing characters to the mapping, and finding the closest character
 * for a given brightness value.
 */

public class SubImgCharMatcher {

    private final TreeMap<Double, TreeSet<Integer>> charTreeSet; // TreeMap for mapping brightness to chars
    private final TreeMap<Double, Integer> stretchedSet; // Stretched version of charTreeSet for faster lookup
    private final Set<Integer> setToPrint; // Set containing characters to print
    private boolean stretched; // Flag indicating if stretchedSet needs to be updated


    /**
     * Constructs a SubImgCharMatcher object with the given character set.
     *
     * @param charset The character set used for mapping brightness to characters
     */

    public SubImgCharMatcher(char[] charset){
        this.charTreeSet = new TreeMap<Double,  TreeSet<Integer>>();
        this.setToPrint = new TreeSet<>();
        for (char c : charset) {
            addChar(c);
            setToPrint.add((int)c);
        }
        stretchedSet = new TreeMap<Double, Integer>();
    }

    /**
     * Maps the given brightness value to the closest character in the character set.
     *
     * @param brightness The brightness value to be mapped
     * @return The closest character corresponding to the given brightness value
     */

    public char getCharByImageBrightness(double brightness)
    {
        if(!stretched) {

            double minBrightness = charTreeSet.firstKey();
            double maxBrightness = charTreeSet.lastKey();
            stretchedSet.clear();

            for (Map.Entry<Double, TreeSet<Integer>> entry : charTreeSet.entrySet()) {

                double stretchedBrightness =
                        (entry.getKey() - minBrightness) / (maxBrightness - minBrightness);
                stretchedSet.put(stretchedBrightness,entry.getValue().first());
            }
            stretched = true;
        }

        Map.Entry<Double, Integer> floorEntry = stretchedSet.floorEntry(brightness);
        Map.Entry<Double, Integer> ceilEntry = stretchedSet.ceilingEntry(brightness);
        double diff1 = brightness - floorEntry.getKey();
        double diff2 = ceilEntry.getKey() - brightness;
        if (diff1 <= diff2) {
            return (char) (int) floorEntry.getValue();
        } else {
            return (char) (int) ceilEntry.getValue();
        }

    }

    /**
     * Adds a character to the character set mapping.
     *
     * @param c The character to be added
     */


    public void addChar(char c) {

        //todo - if we had somthing which is inside?


        double brightness = getBrightnessByChar(c);

        TreeSet<Integer> charTreeMap = charTreeSet.get(brightness);

        if (charTreeMap == null)
        {
            charTreeMap = new TreeSet<Integer>();
            charTreeSet.put(brightness,charTreeMap);

        }

        charTreeMap.add((int) c);
        setToPrint.add((int) c);
        stretched = false;
    }


    /**
     * Removes a character from the character set mapping.
     *
     * @param c The character to be removed
     */

    public void removeChar(char c) {
        if( !setToPrint.contains((int) c)){
            return;
        }

        double brightness = getBrightnessByChar(c);

        TreeSet<Integer> brightnessTreeSet = charTreeSet.get(brightness);

        if (brightnessTreeSet == null) {
            return;
        } else if (brightnessTreeSet.size() == 1) {
            charTreeSet.remove(brightness);

        }

        setToPrint.remove((int) c);
        brightnessTreeSet.remove((int) c);


        stretched = false;
    }

    /**
     * Returns a string representation of the character set.
     *
     * @return A string containing all characters in the character set
     */

    @Override
    public String toString() {
        if( setToPrint.isEmpty()){
            return "";
        }
        String str = "";
        for (Integer c: setToPrint) {
            str += (char) (int) c +" " ;
        }
    return str;}



    /**
     * Calculates the brightness of a character.
     *
     * @param c The character whose brightness is to be calculated
     * @return The brightness value of the character
     */
    private double getBrightnessByChar(char c){
        double brightness = 0;
        boolean[][] boolArray = CharConverter.convertToBoolArray(c);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                if(boolArray[i][j]){
                    brightness++;
                }
            }
        }
        // todo double?
        brightness /= 16*16;
        return brightness;
    }

}
