/**
 * The PlayerFactory class is responsible for creating instances of different types of players
 * based on the specified player type string.
 *
 */

public class PlayerFactory {

    private final static String HUMAN_PLAYER = "human";
    private final static String WHATEVER_PLAYER = "whatever";
    private final static String CLEVER_PLAYER = "clever";
    private final static String GENIUS_PLAYER = "genius";

    /**
     * Constructs a PlayerFactory.
     */

    public PlayerFactory(){
    }


    /**
     * Builds and returns a player instance based on the specified player type.
     *
     * @param type The player type string (e.g., "human", "whatever", "clever", "genius").
     * @return An instance of the specified player type, or null if the type is not recognized.
     */


    public Player buildPlayer(String type){
        String lowerCaseString = type.toLowerCase();

        switch (lowerCaseString) {
            case HUMAN_PLAYER -> {
                return new HumanPlayer();
            }
            case WHATEVER_PLAYER -> {
                return new WhateverPlayer();
            }
            case CLEVER_PLAYER -> {
                return new CleverPlayer();
            }
            case GENIUS_PLAYER -> {
                return new GeniusPlayer();
            }
        }
        return null;

    }


}
