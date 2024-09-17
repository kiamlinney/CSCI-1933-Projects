// Written by Liam Kinney, kinne351
public class Rook {
    private int row;
    private int col;
    private boolean isBlack;

    public Rook(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        if(board.verifySourceAndDestination(row, col, endRow, endCol, isBlack) == false)
            return false;
        return (board.verifyHorizontal(row, col, endRow, endCol) || board.verifyVertical(row, col, endRow, endCol));
    }
}
