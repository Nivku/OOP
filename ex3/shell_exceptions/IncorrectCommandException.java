package shell_exceptions;

/**
 * The IncorrectCommandException class represents an
 * exception that is thrown when an incorrect command
 * is provided in the shell.
 */

public class IncorrectCommandException extends Exception {

    /**
     * the constructor with relevant message
     * @param m
     */
    public IncorrectCommandException(String m) {
        super(m);
    }
}
