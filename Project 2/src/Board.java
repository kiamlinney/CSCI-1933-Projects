// Written by Liam Kinney, kinne351
public class Board {

    // Instance variables
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
    }

    // Accessor Methods

    public Piece getPiece(int row, int col) {
            return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    // Game functionality methods

    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        if(board[startRow][startCol] != null) {
            if (board[startRow][startCol].isMoveLegal(this, endRow, endCol)) {
                board[endRow][endCol] = board[startRow][startCol];
                board[endRow][endCol].setPosition(endRow, endCol);
                board[startRow][startCol] = null;
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver() { //
        int kingCount = 2;
        int tempCount = 0;
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null)
                    if (board[i][j].getCharacter() == '\u265a' || board[i][j].getCharacter() == '\u2654')
                        tempCount++;
            }
        if(tempCount != kingCount)
            return true;
        return false;
    } // The isGameOver method iterates through the entire board, and if the number of kings goes below 2, then the game is over

    public String toString() {
        String finalBoard = "   0 1 2 3 4 5 6 7\n";

        for(int i = 0; i < board.length; i++) {
            finalBoard += i + " ";
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j] == null)
                    finalBoard += "|" + '\u2001';
                else finalBoard += "|" + board[i][j];
            }
            finalBoard += "|\n";
        }
        return finalBoard;
    }

    public void clear() {
        for(Piece[] b : board)
            for(Piece p : b)
                    p = null;
    }

    // Movement helper functions

    public boolean verifySourceAndDestination(int startRow, int startCol, int endRow, int endCol, boolean isBlack) {
        if(startRow < 0 || startCol < 0 || endRow < 0 || endCol < 0) // Checking out of bounds, if any negative values are passed
            return false;
        if(startRow >= board.length || endRow >= board.length || startCol >= board[0].length || endCol >= board[0].length) // Checking bounds
            return false;
        if(board[startRow][startCol] == null) // Checking if starting piece is not a piece
            return false;
        if(isBlack != (board[startRow][startCol].getIsBlack())) // Checking if starting piece does not equal their own color
            return false;
        if(board[endRow][endCol] != null && board[endRow][endCol].getIsBlack() == (isBlack)) // Checking if ending piece location is either null or a piece of the opposite color
            return false;
        else
            return true;
    }

    public boolean verifyAdjacent(int startRow, int startCol, int endRow, int endCol) {
        return(Math.abs(endRow - startRow) <= 1 && Math.abs(endCol - startCol) <= 1); // Checks that start and end position are adjacent
    }

    public boolean verifyHorizontal(int startRow, int startCol, int endRow, int endCol) {
        if(startRow != endRow) // Making sure the move takes place on one row
            return false;

        if(startRow == endRow && startCol == endCol)
            return true;

        int colDiff = Math.abs(endCol - startCol); // Checking empty spaces between the two moves:

        if(colDiff == 0) // Checking for dividing by zero
            return false;

        int direction = (endCol - startCol) / colDiff; //1 if the move is left to right, -1 if it is left to right

        for(int i = 1; i < colDiff; i++)
            if(board[startRow][startCol + i * direction] != null)
                return false;

        return true;
    }

    public boolean verifyVertical(int startRow, int startCol, int endRow, int endCol) {
        if(startCol != endCol) // Making sure the move takes place on one column:
            return false;

        int rowDiff = Math.abs(endRow - startRow); // Checking empty spaces between the two moves:
        if(startRow == endRow && startCol == endCol)
            return true;
        if(rowDiff == 0) // Checking for dividing by zero
            return false;

        int direction = (endRow - startRow) / rowDiff;

        for(int i = 1; i < rowDiff; i++)
            if(board[startRow + i * direction][startCol] != null)
                return false;

        return true;
    }

    public boolean verifyDiagonal(int startRow, int startCol, int endRow, int endCol) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if(rowDiff != colDiff) // Checking if it is a diagonal move
            return false;
        if(startRow == endRow && startCol == endCol)
            return true;
        if(rowDiff == 0 || colDiff == 0) // Checking for dividing by zero
            return false;

        int rowDir = (endRow - startRow) / rowDiff;
        int colDir = (endCol - startCol) / colDiff;

        for(int i = 1; i < rowDiff; i++)
            if(board[startRow + i * rowDir][startCol + i * colDir] != null)
                return false;

        return true;
    }

    public boolean verifyKnight(int startRow, int startCol, int endRow, int endCol){
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if(startRow == endRow && startCol == endCol)
            return true;

        if((rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff == 1))
            // Checking if it is a move of either 1 square horizontally and 2 squares vertically, or 2 squares horizontally and 1 square vertically
            return true;

        else return false;
    }
}