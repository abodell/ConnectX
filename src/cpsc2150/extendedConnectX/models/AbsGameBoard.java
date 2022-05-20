package cpsc2150.extendedConnectX.models;
// Andy Bodell
public abstract class AbsGameBoard implements IGameBoard {
    /**
     * This method will create a string to print to the terminal showing the gameboard state
     * @post [A formatted string will be returned] AND gameBoard = #gameBoard
     * @return [A string that shows the current state of the gameboard]
     */
    @Override
    public String toString() {
        String board = "";
        // print the column numbers on top of the board
        for (int i = 0; i < getNumColumns(); i++) {
            if (i < 10) {
                board += "| " + i;
            } else {
                board += "|" + i;
            }
        }
        board += "|";
        board += "\n";
        // now, traverse the entire board and print out its contents
        for (int i = getNumRows() - 1; i >= 0; i--) {
            board += "|";
            for (int j = 0; j < getNumColumns(); j++) {
                BoardPosition curPos = new BoardPosition(i, j);
                board += whatsAtPos(curPos) + " |";
            }
            board += "\n";
        }
        return board;
    }
}
