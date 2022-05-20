package cpsc2150.extendedConnectX.controllers;

import cpsc2150.extendedConnectX.models.*;
import cpsc2150.extendedConnectX.views.*;

/**
 * The controller class will handle communication between our View and our Model ({@link IGameBoard})
 * <p>
 * This is where you will write code
 * <p>
 * You will need to include your {@link IGameBoard} interface
 * and both of the {@link IGameBoard} implementations from Project 4
 * If your code was correct you will not need to make any changes to your {@link IGameBoard} implementation class
 *
 * @version 2.0
 */
public class ConnectXController {

    /**
     * <p>
     * The current game that is being played
     * </p>
     */
    private IGameBoard curGame;

    /**
     * <p>
     * The screen that provides our view
     * </p>
     */
    private ConnectXView screen;

    /**
     * <p>
     * Constant for the maximum number of players.
     * </p>
     */
    public static final int MAX_PLAYERS = 10;

    // create an array of 10 possible tokens that people can use
    private char[] possibleTokens = {'X', 'O', 'S', 'D', 'A', 'W', 'K', 'C', 'M', 'T'};
    
    /**
     * <p>
     * The number of players for this game. Note that our player tokens are hard coded.
     * </p>
     */
    private int numPlayers;

    // use this in order to reset the game if there is a win
    private boolean resetGame;

    // will be used to determine who's turn it is
    private int currPlayer;
    /**
     * <p>
     * This creates a controller for running the Extended ConnectX game
     * </p>
     * 
     * @param model
     *      The board implementation
     * @param view
     *      The screen that is shown
     * 
     * @post [ the controller will respond to actions on the view using the model. ]
     */
    public ConnectXController(IGameBoard model, ConnectXView view, int np) {
        this.curGame = model;
        this.screen = view;
        numPlayers = np;
    }

    /**
     * <p>
     * This processes a button click from the view.
     * </p>
     * 
     * @param col 
     *      The column of the activated button
     * 
     * @post [ will allow the player to place a token in the column if it is not full, otherwise it will display an error
     * and allow them to pick again. Will check for a win as well. If a player wins it will allow for them to play another
     * game hitting any button ]
     */
    public void processButtonClick(int col) {
        // if there is a win we want to start a new game when we click on
        // the column
        if (resetGame) {
            newGame();
            return;
        }

        // now check for a tie
        if (curGame.checkTie()) {
            newGame();
            return;
        }

        // tell the current player it is their turn
        screen.setMessage("Player " + possibleTokens[(currPlayer + 1) % numPlayers] + ", it is your turn to play");
        // if the column is full, tell them to pick a different one
        if (!curGame.checkIfFree(col)) {
            screen.setMessage("That column is full, pick a different one");
        } else if (curGame.checkIfFree(col) && !curGame.checkForWin(col)) {
            // find where to place in the column
            int openSpot = -1;
            for (int i = 0; i < curGame.getNumRows(); i++) {
                BoardPosition pos = new BoardPosition(i, col);
                if (curGame.whatsAtPos(pos) == ' ') {
                    openSpot = i;
                    break; // once we find an open spot we can exit the loop
                }
            }
            curGame.placeToken(possibleTokens[currPlayer % numPlayers], col);
            screen.setMarker(openSpot, col, possibleTokens[currPlayer % numPlayers]);
            currPlayer++;
        }

        if (curGame.checkTie()) {
            screen.setMessage("Game has ended in a tie, click a button to play again.");
            resetGame = true;
            return;
        } else if (curGame.checkForWin(col)) {
            currPlayer -= 1;
            screen.setMessage("Player " + possibleTokens[currPlayer % numPlayers] + ", wins.  Click a button to play again.");
            resetGame = true;
            return;
        }

    }

    /**
     * <p>
     * This method will start a new game by returning to the setup screen and controller
     * </p>
     * 
     * @post [ a new game gets started ]
     */
    private void newGame() {
        //close the current screen
        screen.dispose();
        
        //start back at the set up menu
        SetupView screen = new SetupView();
        SetupController controller = new SetupController(screen);
        screen.registerObserver(controller);
    }
}