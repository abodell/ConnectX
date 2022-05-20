package cpsc2150.extendedConnectX.models;
// Andy Bodell
/**
 * This class is designed to create the gameboard using a 2D array, place tokens,
 * and keep track of what marker/player is at specific positions
 * @author Andy Bodell
 * @version 1.0
 *
 */
public class GameBoard extends AbsGameBoard implements IGameBoard {
    /**
     * @invariant MINROWS <= height <= MAXROWS
     * @invariant MINCOLUMNS <= width <= MAXCOLUMNS
     * @invariant MINNUMTOWIN <= winningNum <= MAXNUMTOWIN
     * @invariant Must place a marker within the specified dimensions
     * @invariant Markers cannot be placed at a non-empty position
     * @invariant Board has no gaps between tokens
     * @correspondence self = board, [number of rows] = height, [number of columns] = width,
     * [number of consecutive tokens in a row to win] = winningNum
     *
     */
    private int width;
    private int height;
    private static final char BLANK_SPACE = ' ';
    private int winningNum;
    private char[][] board;

    /**
     * Constructor that will create the gameboard for gameplay
     * @pre [The user has chosen to play a game of connectX with a fast gameBoard]
     * @param rows The number of rows to put in the gameboard
     * @param cols The number of columns to put in the gameboard
     * @param numToWin The number of consecutive tokens in a row to win the game
     * @post height = rows AND width = cols AND winningNum = numToWin AND [gameboard has been created and every
     * position is a blank space]
     */
    public GameBoard(int rows, int cols, int numToWin) {
        // initialize correctly sized board
        this.height = rows;
        this.width = cols;
        this.winningNum = numToWin;
        this.board = new char[height][width];
        // set every position in the board to blank
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j] = BLANK_SPACE;
            }
        }
    }

    public int getNumRows() {
        return height;
    }

    public int getNumColumns() {
        return width;
    }

    public int getNumToWin() {
        return winningNum;
    }

    public void placeToken(char p, int c) {
        // need to place the token in the lowest available row
        // bottom row is actually getNumRows - 1
        for (int i = 0; i < getNumRows(); i++) {
            if (board[i][c] == ' ') {
                board[i][c] = p;
                return;
            }
        }
    }

    public char whatsAtPos(BoardPosition pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        return board[row][col];
    }

}
