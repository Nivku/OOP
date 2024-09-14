package shell_exceptions;

/**
 * The IncorrectFormatException class represents an
 * exception that is thrown when second argument of the
 * command is unknown

 */

public class IncorrectFormatException extends Exception {


    /**
     * the constructor with relevant message
     * @param m
     */
    public IncorrectFormatException(String m) {
        super(m);
    }
}
