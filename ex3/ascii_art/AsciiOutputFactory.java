package ascii_art;


import shell_exceptions.IncorrectFormatException;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

/**
 * this class if the factory of the AsciiOutput interface
 */
public class AsciiOutputFactory {
    private static final String CONSOLE = "console"; // console output
    private static final String HTML = "html"; // html output
    private static final String INCORRECT_FORMAT_MESS =
            "Did not change output method due to incorrect format."; // message for unknown output


    /**
     * this method returns the relevant AsciiOutput according to the output string
     * @param output
     * @return
     * @throws IncorrectFormatException
     */

    public AsciiOutput build(String output) throws IncorrectFormatException {
        AsciiOutput asciiOutput = null;
        switch (output){
            case CONSOLE:
                asciiOutput = new ConsoleAsciiOutput();
                break;
            case HTML:
                asciiOutput = new HtmlAsciiOutput("out.html","Courier New");
                break;
        }
        if (asciiOutput == null){throw new IncorrectFormatException(INCORRECT_FORMAT_MESS);}

        return  asciiOutput;

    }

}

