// Written by Liam Kinney, kinne351
public class Queen {
    private int row;
    private int col;
    private boolean isBlack;

    public Queen(int row, int col, boolean isBlack){
        this.row = row;
        this.col = col;
        this.isBlack = isBlack;
    }

    public boolean isMoveLegal(Board board, int endRow, int endCol){
        // Always do verify source and destination first
        if(board.verifySourceAndDestination(row, col, endRow, endCol, isBlack) == false)
            return false;

        boolean adjacent = board.verifyAdjacent(row, col, endRow, endCol);
        boolean vertical = board.verifyVertical(row, col, endRow, endCol);
        boolean horizontal = board.verifyHorizontal(row, col, endRow, endCol);
        boolean diagonal = board.verifyDiagonal(row, col, endRow, endCol);

        return (adjacent || vertical || horizontal || diagonal);
    }
}
