package cpsc2150.extendedConnectX.models;
// Andy Bodell
/**
 * Interface for our game board classes which contains default and primary methods
 * @defines MAXROWS: Z - the maximum number of rows the gameboard can have
 *          MAXCOLUMNS: Z - the maximum number of columns the gameboard can have
 *          MINROWS: Z - the minimum number of rows the gameboard can have
 *          MINCOLUMNS: Z - the minimum number of columns the gameboard can have
 *          MAXNUMTOWIN: Z - the maximum number of consecutive tokens required to win
 *          MINNUMTOWIN: Z - the minimum number of consecutive tokens required to win
 *          MAXPLAYERS: Z - the maximum number of players that can play the game
 *          MINPLAYERS: Z - the minimum number of players that can play the game
 *
 * Initialization ensures:
 *                      Every space in the gameboard is a blank character and the dimensions
 *                      of the board are (MINROWS <= [number of rows] <= MAXROWS) x (MINCOLUMNS <= [number of columns] <= MAXCOLUMNS)
 *
 * @constraints MINROWS <= [number of rows] <= MAXROWS AND MINCOLUMNS <= [number of columns] <= MAXCOLUMNS AND
 * MINNUMTOWIN <= [number in a row to win] <= MAXNUMTOWIN AND MINPLAYERS <= [number of players] <= MAXPLAYERS
 */
public interface IGameBoard {
    public static final int MAXROWS = 100;
    public static final int MAXCOLUMNS = 100;
    public static final int MINROWS = 3;
    public static final int MINCOLUMNS = 3;
    public static final int MAXNUMTOWIN = 25;
    public static final int MINNUMTOWIN = 3;
    public static final int MAXPLAYERS = 10;
    public static final int MINPLAYERS = 2;

    /**
     * This method will get the number of rows in the gameboard
     * @post getNumRows() = MAXROWS
     * @return The number of rows in the gameboard
     */
    public int getNumRows();

    /**
     * This method will get the number of columns in the gameboard
     * @post getNumColumns() = MAXCOLUMNS
     * @return The number of columns in the gameboard
     */
    public int getNumColumns();

    /**
     * This method will get the number of consecutive tokens in a row that are needed to win
     * @post getNumToWin() = NUMTOWIN
     * @return An integer stating the number of consecutive tokens needed in a row to win the game
     *
     */
    public int getNumToWin();

    /**
     * This method will allow the user to place marker p in column c in the lowest available row
     * @pre checkIfFree(c) = true AND 0 <= c < MAXCOLUMNS
     * @param p this is the type of marker the user will be placing
     * @param c this is the column number the user will place their marker in
     * @post iff [column c has empty spaces then the marker will be placed in lowest row number that is a blank space]
     *
     */
    public void placeToken(char p, int c);

    /**
     * This function returns what is in the gameboard at a certain position
     * @pre 0 <= pos.getRow() < MAXROWS AND 0 <= pos.getColumn() <= MAXCOLUMNS
     * @param pos This is the game board position that is being checked
     * @post [returns the content at pos which either is an empty space, or one of the markers] AND self = #self
     * @return ['X' if X is at the position, 'O' if O is at the position, OR " " if the position is blank]
     */
    public char whatsAtPos(BoardPosition pos);

    /**
     * This function will check to see if the game has resulted in a tie
     * @pre [Neither player has been declared the winner yet]
     * @post [If the game results in a tie, the game will be over, if there is no tie the game will continue]
     * AND self = #self
     * @return [true if none of the positions in the game board are empty]
     */
    public default boolean checkTie() {
        // scan the top row to see if it is full
        for (int i = 0; i < getNumColumns(); i++) {
            BoardPosition topRow = new BoardPosition(getNumRows() - 1, i);
            // if any position in the top row is empty then there is not a tie
            // because the board is not full
            if (whatsAtPos(topRow) == ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * returns true if column c can accept another token
     * @param c number of column that will be checked
     * @return true if the column is not full, false if the column is full
     * @pre 0 <= c < getNumColumns()
     * @post [if true then column can accept another token] AND self = #self
     */
    public default boolean checkIfFree(int c) {
        // create a boardposition object for the top row of the column
        BoardPosition topRow = new BoardPosition(getNumRows() - 1,c);
        // if the variable in the top row is blank then the column is free
        if (whatsAtPos(topRow) == ' ') {
            return true;
        }
        return false;
    }

    /**
     * returns true if last token placed in column c results in a win
     * @param c column number that will be checked
     * @return true iff [checkHorizWin() OR checkVertWin() OR checkDiagWin() are true
     * and first index of the column is not empty]
     * @pre 0 <= c < getNumColumns()
     * @post [if true game will be ended and current player will be named the winner, if false game will continue]
     * AND self = #self
     */
    public default boolean checkForWin(int c) {
        // need to go the most recently placed token
        // start on the top row
        for (int i = getNumRows() - 1 ; i >= 0; i--) {
            BoardPosition pos = new BoardPosition(i, c);
            // find the first position that is not blank
            if (whatsAtPos(pos) != ' ') {
                if (checkHorizWin(pos, whatsAtPos(pos)) || checkDiagWin(pos, whatsAtPos(pos))
                        || checkVertWin(pos, whatsAtPos(pos))) {
                    // if there is a win of any kind return true
                    return true;
                }
            }
        }
        // if the loop goes through the entire column without finding a token return false
        return false;
    }

    /**
     * This function will check to see if the player is at the position
     * @pre 0 <= pos.getRow() < MAXROWS AND 0 <= pos.getColumn() <= MAXCOLUMNS
     * @param pos The board position that will be checked
     * @param player The marker that will be checked
     * @post [will return true if the player is present in the game board at position pos] AND self = #self
     * @return [True if player has their marker at the position that is being checked
     * False if player does not have their marker at the position that is being checked]
     */
    public default boolean isPlayerAtPos(BoardPosition pos, char player) {
        if (whatsAtPos(pos) == player) {
            return true;
        }
        return false;
    }

    /**
     * This function checks to see if the last marker placed resulted in winning in a row horizontally
     * @pre 0 <= pos.getRow() < MAXROWS AND 0 <= pos.getColumn() < MAXCOLUMNS AND [pos is the location the last marker was placed]
     * @param pos This is the board position the player placed their token at
     * @param p This is the type of marker that was placed
     * @post [returns true if placed marker is the last to make up NUMTOWIN number of consecutive markers needed to win horizontally] AND self = #self
     * @return [True if there are NUMTOWIN tokens in a row horizontally, false if there are not]
     */
    public default boolean checkHorizWin(BoardPosition pos, char p) {
        int consecutiveCount = 1;
        int row = pos.getRow();
        int col = pos.getColumn();

        // check to the right, until we reach a blank space or a different token
        for (int i = col + 1; i < getNumColumns(); i++) {
            BoardPosition toRight = new BoardPosition(row, i);
            // if we encounter the player at the next position, add to consecutive count
            if (isPlayerAtPos(toRight, p)) {
                consecutiveCount++;
                // if consecutive count equals NUMTOWIN we can return true
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                // if we encounter a blank space or a different token, we can exit the loop
                break;
            }
        }

        // now, we need to check to the left
        for (int i = col - 1; i >= 0; i--) {
            BoardPosition toLeft = new BoardPosition(row, i);
            // if we encounter the player at the next position, add to the token count
            if (isPlayerAtPos(toLeft, p)) {
                consecutiveCount++;
                // if consecutive count equals getNumToWin() we can return true
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                // if we encounter a different token or blank space, we can exit the loop
                break;
            }
        }
        // if consecutive count never equals getNumToWin() then we will return false
        return false;
    }

    /**
     * This function checks to see if the last token placed resulted in getNumToWin() in a row vertically
     * @pre 0 <= pos.getRow() < MAXROWS AND 0 <= pos.getColumn() < MAXCOLUMNS AND [pos is the location the last marker was placed]
     * @param pos This is the board position the player placed their marker at
     * @param p This is the type of marker that was placed
     * @post [returns true if placed marker is the last to make up getNumToWin() number of consecutive markers needed to win vertically] AND self = #self
     * @return [True if there are getNumToWin() tokens in a row vertically, false if there is not]
     */
    public default boolean checkVertWin(BoardPosition pos, char p) {
        int consecutiveCount = 1;
        int row = pos.getRow();
        int col = pos.getColumn();

        // traverse down

        for (int i = row - 1; i >= 0; i--) {
            BoardPosition next = new BoardPosition(i, col);
            if (isPlayerAtPos(next, p)) {
                consecutiveCount++;
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    /**
     * This function checks to see if the last token placed resulted in getNumToWin() in a row diagonally
     * @pre 0 <= pos.getRow() < MAXROWS AND 0 <= pos.getColumn() < MAXCOLUMNS AND [pos is the location the last marker was placed]
     * @param pos This is the board position the player placed their marker at
     * @param p This is the type of marker that was placed
     * @post [returns true if placed marker is the last to make up getNumToWin() number of consecutive markers needed to win diagonally] AND self = #self
     * @return [True if there are getNumToWin tokens in a row diagonally, false if there is not]
     */
    public default boolean checkDiagWin(BoardPosition pos, char p) {
        int row = pos.getRow();
        int col = pos.getColumn();
        int consecutiveCount = 1;
        // check down and to the left
        for (int i = row + 1, j = col - 1; i < getNumRows() && j >= 0; i++, j--) {
            BoardPosition downToLeft = new BoardPosition(i, j);
            // if the same token is found at the next position, increment consecutiveCount
            if (isPlayerAtPos(downToLeft, p)) {
                consecutiveCount++;
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                // if a different token or a blank space is encountered we can exit the loop
                break;
            }
        }

        // check up and to the right
        for (int i = row - 1, j = col + 1; i >= 0 && j < getNumColumns(); i--, j++) {
            BoardPosition upToRight = new BoardPosition(i, j);
            if (isPlayerAtPos(upToRight, p)) {
                consecutiveCount++;
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                break;
            }
        }

        // check down and to the right, have to reset consecutive count for this diagonal
        consecutiveCount = 1;
        for (int i = row + 1, j = col + 1; i < getNumRows() && j < getNumColumns(); i++, j++) {
            BoardPosition downRight = new BoardPosition(i,j);
            if (isPlayerAtPos(downRight, p)) {
                consecutiveCount++;
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                break;
            }
        }

        // check up and to the left
        for (int i = row - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            BoardPosition upLeft = new BoardPosition(i, j);
            if (isPlayerAtPos(upLeft, p)) {
                consecutiveCount++;
                if (consecutiveCount == getNumToWin()) {
                    return true;
                }
            } else {
                break;
            }
        }
        // if consecutiveCount never equals getNumToWin() then we return false
        return false;
    }


}
