package ascii_art;

import shell_exceptions.CharSetEmptyException;
import shell_exceptions.IncorrectCommandException;
import shell_exceptions.IncorrectFormatException;
import shell_exceptions.OutOfBoundsResException;
import ascii_output.*;
import image.Image;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

/**
 * The Shell class serves as the command-line interface for interacting with the ASCII art generation system.
 * It allows users to manipulate the character set, adjust image resolution, specify input/output methods,
 * and generate ASCII art from an input image.
 */

public class Shell {
    private static final String IMAGE_ERROR_MESSAGE = "Did not execute due to problem with image file";
    private static final String OUT_OF_BOUNDS_RE_EXCEPTION = "Did not change" +
            " resolution due to exceeding boundaries.";
    private static final String INCORRECT_COMMAND_MESS = "Did not execute due to incorrect command.";
    private static final String INCORRECT_FORMAT_MESS = "Did not change resolution due to incorrect format.";
    private static final String ADD_INCORRECT_FORMAT = "Did not add due to incorrect format.";
    private static final String REMOVE_INCORRECT_FORMAT = "Did not remove due to incorrect format.";
    private static final String EMPTY_CHARSET_EXCP = "Did not execute. Charset is empty.";
    private final SubImgCharMatcher subImgCharMatcher;
    private final char[] defChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private int resolution = 128;
    private Image image;


    private final AsciiOutputFactory factory = new AsciiOutputFactory();

    private AsciiOutput asciiOutput;


    /**
     * Constructs a Shell object, initializes default settings, and loads the default image.
     */

    public Shell() {
        this.subImgCharMatcher = new SubImgCharMatcher(defChars);
        asciiOutput = new ConsoleAsciiOutput();
        try {
            this.image = new Image("cat.jpeg");
        } catch (IOException e) {
            System.out.println(IMAGE_ERROR_MESSAGE);
        }
    }

    /**
     * Starts the command-line interface and waits for user input to execute commands.
     */

    public void run() {
        while (true) {
            System.out.print(">>> ");
            String command = KeyboardInput.readLine();
            if (command.equals("exit")) {
                break;
            }
            if (command.equals("chars")) {
                System.out.println(subImgCharMatcher);
                continue;
            }
            String[] splitCommand = command.split(" ");
            try {
                chooseCommand(splitCommand);
            } catch (CharSetEmptyException | OutOfBoundsResException | IncorrectCommandException |
                     IncorrectFormatException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println(IMAGE_ERROR_MESSAGE);
            }

        }
    }

    /**
     * Executes the specified command based on the user input.
     *
     * @param splitCommand The parsed command entered by the user
     * @throws IncorrectCommandException If the entered command is incorrect
     * @throws OutOfBoundsResException   If the new resolution exceeds image boundaries
     * @throws IOException               If an I/O error occurs while reading the image file
     * @throws IncorrectFormatException  If the entered command format is incorrect
     * @throws CharSetEmptyException     If the character set is empty
     */

    private void chooseCommand(String[] splitCommand) throws IncorrectCommandException,
            OutOfBoundsResException, IOException, IncorrectFormatException, CharSetEmptyException {
        switch (splitCommand[0]) {
            case "add":
                addRemoveCommand(splitCommand[1], true);
                break;
            case "remove":
                addRemoveCommand(splitCommand[1], false);
                break;
            case "res":
                resCommand(splitCommand[1]);
                break;
            case "image":
                this.image = new Image(splitCommand[1]);
                break;
            case "output":
                outputCommand(splitCommand[1]);
                break;
            case "asciiArt":
                runAlgorithm();
                break;
            default:
                throw new IncorrectCommandException(INCORRECT_COMMAND_MESS);
        }
    }

    /**
     * Executes the ASCII art generation algorithm and outputs the result.
     *
     * @throws CharSetEmptyException If the character set is empty
     */

    private void runAlgorithm() throws CharSetEmptyException {
        //run ascii
        if (subImgCharMatcher.toString().isEmpty()) {
            throw new CharSetEmptyException(EMPTY_CHARSET_EXCP);
        }
        AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher);
        char[][] image = asciiArtAlgorithm.run();
        asciiOutput.out(image);
    }

    /**
     * Handles the output command by setting the output method for ASCII art.
     *
     * @param command The output command entered by the user
     * @throws IncorrectFormatException If the output format is incorrect
     */

    private void outputCommand(String command) throws IncorrectFormatException {
        this.asciiOutput = factory.build(command);
    }

    /**
     * Handles the resolution command by adjusting the image resolution.
     *
     * @param command The resolution command entered by the user
     * @throws IncorrectFormatException If the resolution command format is incorrect
     * @throws OutOfBoundsResException  If the new resolution exceeds image boundaries
     */

    private void resCommand(String command) throws IncorrectFormatException, OutOfBoundsResException {
        int newRes = resolution;
        if (command.equals("up")) {
            newRes = 2 * resolution;
        } else if (command.equals("down")) {
            newRes = resolution / 2;
        } else {
            throw new IncorrectFormatException(INCORRECT_FORMAT_MESS);
        }
        checkResolutionBounds(newRes);
        resolution = newRes;
        System.out.println("Resolution set to " + resolution + ".");
    }

    /**
     * Checks if the new resolution exceeds image boundaries.
     *
     * @param newRes The new resolution value
     * @throws OutOfBoundsResException If the new resolution exceeds image boundaries
     */

    private void checkResolutionBounds(int newRes) throws OutOfBoundsResException {
        if (newRes > image.getWidth()) {

            throw new OutOfBoundsResException(OUT_OF_BOUNDS_RE_EXCEPTION);
        }
        if (newRes < Math.max(1, ((float) image.getWidth()) / image.getHeight())) {
            throw new OutOfBoundsResException(OUT_OF_BOUNDS_RE_EXCEPTION);
        }

    }

    /**
     * Handles the add/remove command by adding or removing characters from the character set.
     *
     * @param command The add/remove command entered by the user
     * @param flag    A boolean flag indicating whether to add or remove characters
     * @throws IncorrectFormatException If the add/remove command format is incorrect
     */

    private void addRemoveCommand(String command, boolean flag) throws IncorrectFormatException {
        if (command.equals("all")) {
            addRemoveChars(32, 126, flag);
        } else if (command.equals("space")) {

            addRemoveChars(32, 32, flag);

        } else if (command.length() == 1) {
            int asciiValue = (int) command.charAt(0);
            addRemoveChars(asciiValue, asciiValue, flag);

        } else if (command.length() == 3 && command.charAt(1) == '-') {
            int startingRange = (int) command.charAt(0);
            int endingRange = (int) command.charAt(2);

            if (startingRange > endingRange) {
                addRemoveChars(endingRange, startingRange, flag);
            } else {
                addRemoveChars(startingRange, endingRange, flag);
            }
        } else if (flag) {
            throw new IncorrectFormatException(ADD_INCORRECT_FORMAT);
        } else {
            throw new IncorrectFormatException(REMOVE_INCORRECT_FORMAT);
        }

    }


    /**
     * Handles the add/remove command by adding or removing characters
     * by use the methods of the subImageMatcher
     *
     * @param start the first char to add/remove
     * @param end   the last char to add/remove
     * @param flag indicates if the method remove or add chars
     */


    private void addRemoveChars(int start, int end, boolean flag) {
        for (int firstChar = start; firstChar <= end; firstChar++) {

            if (flag) {
                subImgCharMatcher.addChar((char) firstChar);
            } else {
                subImgCharMatcher.removeChar((char) firstChar);
            }
        }
    }

    /**
     * running the Shell for asciiArt algorithm
     * @param args
     */
    public static void main(String[] args) {

        Shell shell = new Shell();
        shell.run();

    }
}



