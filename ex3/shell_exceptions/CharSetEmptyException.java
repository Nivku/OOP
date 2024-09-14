package shell_exceptions;



/**
 * The CharSetEmptyException class represents an
 * exception that is thrown when an operation cannot be
 * executed because the character set is empty.
 */

public class CharSetEmptyException extends Exception {

    /**
     * the constructor with relevant message
     * @param m
     */
    public CharSetEmptyException(String m) {
        super(m);
    }
}

