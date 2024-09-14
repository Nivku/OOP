/**
 * The Tournament class represents a series games played between two players
 * over a specified number of rounds. It tracks the wins for each player and ties, and prints
 * the tournament results at the end.
 */

public class Tournament {

    private final int rounds;
    private final Renderer renderer;
    private final Player player1;

    private final Player player2;

    private int player1Wins = 0;

    private int player2Wins = 0;

    private int tiesCounter = 0;


    /**
     * Constructs a Tournament with the specified number of rounds, renderer, and players.
     *
     * @param rounds   The number of rounds in the tournament.
     * @param renderer The renderer for displaying the game board.
     * @param player1  The first player participating in the tournament.
     * @param player2  The second player participating in the tournament.
     */

    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Plays the Tic Tac Toe tournament over the specified number of rounds.
     *
     * @param size        The size of the game board.
     * @param winStreak   The streak length required for a win.
     * @param playerName1 The name of player 1.
     * @param playerName2 The name of player 2.
     */

    public void playTournament(int size, int winStreak,
                               String playerName1, String playerName2) {
        for (int round = 0; round < rounds; round++) {
            Game game;
            if (round % 2 == 0) {
                game = new Game(player1, player2, size, winStreak, renderer);
                gameWinCheck(game.run(), true);

            } else {
                game = new Game(player2, player1, size, winStreak, renderer);
                gameWinCheck(game.run(), false);
            }
        }
        printTournamentResult(playerName1, playerName2);
    }


    /**
     * Checks the result of a game and updates the counters for wins and ties accordingly.
     *
     * @param mark   The mark of the winner ('X', 'O') or 'BLANK' for a tie.
     * @param Parity True if it's player is Mark.x, False -  otherwise.
     */

    private void gameWinCheck(Mark mark, boolean Parity) {
        if (mark == Mark.BLANK) {
            this.tiesCounter++;

        } else if (Parity) {
            if (mark == Mark.X) {
                player1Wins++;
            } else {
                player2Wins++;
            }
        } else {
            if (mark == Mark.X) {
                player2Wins++;
            } else {
                player1Wins++;

            }
        }
    }


    /**
     * Prints the final results of the tournament, including the number of wins for each player
     * and the number of tied games.
     *
     * @param player1Name The name of player 1.
     * @param player2Name The name of player 2.
     */
    private void printTournamentResult(String player1Name, String player2Name) {
        System.out.println("######### Results #########");
        System.out.println("Player 1, " + player1Name + " won: " + this.player1Wins + " rounds");
        System.out.println("Player 2, " + player2Name + " won: " + this.player2Wins + " rounds");
        System.out.println("Ties: " + this.tiesCounter);
    }

    /**
     * The main method for running the Tic Tac Toe tournament. It accepts command-line arguments
     * for the number of rounds, board size, win streak, renderer type, and player types.
     *
     * @param args Command-line arguments.
     */


    public static void main(String[] args) {
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(args[3], size);

        PlayerFactory playerFactory = new PlayerFactory();
        Player player1 = playerFactory.buildPlayer(args[4]);
        Player player2 = playerFactory.buildPlayer(args[5]);

        if (renderer == null) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        }
        if (player1 == null || player2 == null) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        }
        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, args[4].toLowerCase(), args[5].toLowerCase());
    }

}
