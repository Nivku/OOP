package shell_exceptions;

/**
 * The OutOfBoundsResException class represents an
 * exception that is thrown when attempting to set a resolution
 * that exceeds the boundaries of an image.
 */

public class OutOfBoundsResException extends Exception {

    /**
     * the constructor with relevant message
     * @param m
     */
    public OutOfBoundsResException(String m) {
        super(m);
    }
}