package cpsc2150.extendedConnectX.models;
// Andy Bodell
/**
 * This class is designed to keep track of each different position on the gameboard by
 * having variables for the row and column position
 *
 * @author Andy Bodell
 * @version 1.0
 *
 * @invariant MINROWS <= ROW_POS < MAXROWS AND MINCOLUMNS <= COL_POS < MAXCOLUMNS
 *
 */
public class BoardPosition {
    private final int ROW_POS;
    private final int COL_POS;

    /**
     * Constructor that will create the board position with specified row and column
     *
     * @param row holds the row number
     * @param col holds the column number
     *
     * @pre MINROWS <= row < MAXROWS AND MINCOLUMNS <= col < MAXCOLUMNS
     *
     * @post ROW_POS = row AND COL_POS = col
     *
     */
    public BoardPosition(int row, int col) {
        this.ROW_POS = row;
        this.COL_POS = col;
    }

    /**
     * returns the row position
     *
     * @return number of the row
     *
     * @pre MINROWS <= ROW_POS < MAXROWS
     *
     * @post [returns the correct row position]
     *
     */
    public int getRow() {
        return ROW_POS;
    }

    /**
     * returns the column position
     *
     * @return number of the column
     *
     * @pre MINCOLUMNS <= COL_POS < MAXCOLUMNS
     *
     * @post [returns the correct column position]
     *
     */
    public int getColumn() {
        return COL_POS;
    }

    /**
     * overrides default toString to return a string in the format "row, col"
     *
     *
     * @return a String in the format "row, col"
     *
     * @pre [BoardPosition object exists]
     *
     * @post [the string is in the format "row, col"]
     *
     */
    @Override
    public String toString() {
        return (ROW_POS + "," + COL_POS);
    }

    /**
     * Overrides the default equals method to return a boolean if two BoardPositions
     * have the same row and same column
     *
     * @param o any object that we want to compare
     *
     * @return true or false
     *
     * @post [if the positions have same row and same column returns true, otherwise
     * returns false]
     */
    @Override
    public boolean equals(Object o) {
        // if the object passed in is the same instance of the object being compared to return true
        if (o == this) {
            return true;
            // if the object passed in is null return false
        } else if (o == null) {
            return false;
        }

        // if o is an instance of BoardPosition create a BoardPosition to compare it to so we can see if the
        // row and column numbers are identical
        if (o instanceof BoardPosition) {
            BoardPosition compare = (BoardPosition) o;
            if (ROW_POS == compare.ROW_POS && COL_POS == compare.COL_POS) {
                return true;
            }
        }
        return false;
    }
}
