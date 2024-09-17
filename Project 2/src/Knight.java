// Written by Liam Kinney, kinne351
public class Knight {
    private int row;
    private int col;
    private boolean isBlack;

    public Knight(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if(board.verifySourceAndDestination(row, col, endRow, endCol, isBlack) == false)
            return false;
        return (board.verifyKnight(row, col, endRow, endCol)); // verifyKnight method is implemented in Board.java
    }
}
