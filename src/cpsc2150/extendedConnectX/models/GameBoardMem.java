package cpsc2150.extendedConnectX.models;
import java.util.*;
// Andy Bodell
/**
 * This class is designed to create the gameboard using a HashMap, place tokens, and keep track of
 * what marker/player is at each position on the gameboard
 *@invariant MINROWS <= height <= MAXROWS
 *@invariant MINCOLUMNS <= width <= MAXCOLUMNS
 *@invariant MINNUMTOWIN <= winningNum <= MAXNUMTOWIN
 *@invariant Must place a marker within the specified dimensions
 *@invariant Markers cannot be placed at a non-empty position
 *@invariant Board has no gaps between tokens
 *
 * @correspondence self = gameBoard, number of rows] = height, [number of columns] = width,
 * [number of consecutive tokens in a row to win] = winningNum
 */

public class GameBoardMem extends AbsGameBoard implements IGameBoard {
    private int width;
    private int height;
    private static final char BLANK_SPACE = ' ';
    private int winningNum;
    private Map<Character, List<BoardPosition>> gameBoard;

    /**
     * This will be the constructor that will create the gamebaord
     * @pre [The user has chosen to play a game of connectX with a memory efficient gameboard]
     * @param rows number of rows in the gameboard
     * @param cols number of columns in the gameboard
     * @param numToWin the number of consecutive tokens in a row to win the game
     * @post [The rows x cols gameboard has been created and the map is empty]
     */
    public GameBoardMem(int rows, int cols, int numToWin) {
        this.height = rows;
        this.width = cols;
        this.winningNum = numToWin;
        this.gameBoard = new HashMap<Character, List<BoardPosition>>();
    }

    public int getNumRows() { return height; }

    public int getNumColumns() {
        return width;
    }

    public int getNumToWin() {
        return winningNum;
    }

    public void placeToken(char p, int c) {
        // if the key for the player token is not already in the board (first turn) we must add it along with an empty list
        gameBoard.putIfAbsent(p, new ArrayList<BoardPosition>());
        BoardPosition pos = new BoardPosition(0, c);
        for (int i = 0; whatsAtPos(pos) != BLANK_SPACE && i < getNumRows(); i++) {
            pos = new BoardPosition(i, c);
        }
        // when the loop ends we will be at the first blank space in the column
        gameBoard.get(p).add(pos);
    }

    public char whatsAtPos(BoardPosition pos) {
        // traverse the map
        for (Map.Entry<Character, List<BoardPosition>> v : gameBoard.entrySet()) {
            // if the value at key v, which is a list, contains the position then we return the key
            if (v.getValue().contains(pos)) {
                return v.getKey();
            }
        }
        // otherwise we return a blank space
        return BLANK_SPACE;
    }

    @Override
    public boolean isPlayerAtPos(BoardPosition pos, char player) {
        // if the key is not in the map at all, we can return false right away
        if (!gameBoard.containsKey(player)) {
            return false;
        }
        // traverse the map
        for (Map.Entry<Character, List<BoardPosition>> v : gameBoard.entrySet()) {
            // check to see if the list at key v contains the position as well as if the key is equal to the players token
            if (v.getValue().contains(pos) && v.getKey().equals(player)) {
                return true;
            }
        }
        return false;
    }
}
