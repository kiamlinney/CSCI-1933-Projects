// Written by Liam Kinney, kinne351

import java.util.Queue;
import java.util.Random;

public class Minefield {
    /**
    Global Section
    */
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_RED_DARK = "\u001B[38;2;185;10;0m";
    public static final String ANSI_ORANGE = "\u001B[38;2;255;140;0m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_GREY_BG = "\u001b[0m";

    private Cell[][] field;
    private int rows, cols, numFlags;
    private boolean mineHit;
    /**
     * Constructor
     * @param rows       Number of rows.
     * @param columns    Number of columns.
     * @param flags      Number of flags, should be equal to mines
     */
    public Minefield(int rows, int columns, int flags) {
        this.rows = rows;
        this.cols = columns;
        numFlags = flags;

        field = new Cell[rows][columns];

        for(int i = 0; i < field.length; i++) // Filling the field with cells
            for(int j = 0; j < field[0].length; j++)
                field[i][j] = new Cell(false, "0");

    }
    /**
     * evaluateField
     *
     * @function When a mine is found in the field, calculate the surrounding 9x9 tiles values. If a mine is found, increase the count for the square.
     */
    public void evaluateField() {
        for(int i = 0; i < field.length; i++)
            for(int j = 0; j < field[i].length; j++){
                int[][] coordinates = { {i + 1, j}, {i - 1, j}, {i, j + 1}, {i, j - 1}, {i + 1, j + 1}, {i - 1, j - 1}, {i + 1, j - 1}, {i - 1, j + 1} };

                for(int[] coord : coordinates)
                    if(!(coord[0] < 0 || coord[1] < 0 || coord[0] >= field.length || coord[1] >= field[0].length))
                        if(field[coord[0]][coord[1]].getStatus().equals("M") && !field[i][j].getStatus().equals("M"))
                            field[i][j].setStatus(String.valueOf(Integer.parseInt(field[i][j].getStatus()) + 1));
            }
    }
    /**
     * createMines
     *
     * @param x       Start x, avoid placing on this square.
     * @param y        Start y, avoid placing on this square.
     * @param mines      Number of mines to place.
     */
    public void createMines(int x, int y, int mines) {
        Random random = new Random();

        for(int i = 0; i < mines; i++){
            int xRand = random.nextInt(rows);
            int yRand = random.nextInt(cols);

            if(xRand != x && yRand != y && !field[xRand][yRand].getRevealed() && !field[xRand][yRand].getStatus().equals("M"))
                field[xRand][yRand].setStatus("M");
            else i--; // Ensures enough mines are placed. If valid place is not found, i is decremented to try again.
        }
    }

    /**
     * guess
     *
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     * @param flag    A boolean value that allows the user to place a flag on the corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed, true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) {
        if(x < 0 || y < 0 || x > field.length || y > field[0].length)
            return false;

        if(flag && numFlags > 0) {
            field[x][y].setStatus("F");
            field[x][y].setRevealed(true);
            numFlags--;
            return false;
        }

        if(field[x][y].getStatus().equals("0")) {
            revealZeroes(x, y);
            return false;
        }
        if(field[x][y].getStatus().equals("M")) {
            mineHit = true;
            loser();
            return true;
        }

        field[x][y].setRevealed(true); // Reveals information if cell is not a zero or a mine
        return false;
    }

    public boolean loser(){
        if(mineHit) // Reveals all mine locations if user hits a mine and loses.
            for(Cell[] cell : field)
                for(Cell c : cell) {
                    if (c.getStatus().equals("M"))
                        c.setRevealed(true);
                    if(c.getStatus().equals("F"))
                        c.setStatus("M");
                }
        return mineHit;
    }

    /**
     * gameOver
     *
     * @return boolean Return false if game is not over and squares have yet to be revealed, otherwise return true.
     */
    public boolean gameOver() {
        boolean mineRemaining = false;
        if(numFlags == 0){
            loop: for(Cell[] cell : field)
                for(Cell c : cell)
                    if(c.getStatus().equals("M")) { // If numFlags is 0 and there's still a mine, they did not win
                        mineRemaining = true;
                        break loop;
                    }
        if(!mineRemaining)
            return true;
        }

        for(Cell[] cell : field) // If every square is revealed
            for(Cell c : cell)
                if(!c.getRevealed())
                    return false;
        return true;
    }

    /**
     * revealField
     *
     * This method should follow the psuedocode given.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x      The x value the user entered.
     * @param y      The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        Stack1Gen<int[]> stack = new Stack1Gen<>();
        stack.push(new int[] {x, y});

        while(!stack.isEmpty()){
            int[] top = stack.pop();
            int xCoord = top[0];
            int yCoord = top[1];

            field[xCoord][yCoord].setRevealed(true);

            int[][] neighbors = { {xCoord-1, yCoord}, {xCoord+1, yCoord}, {xCoord, yCoord-1}, {xCoord, yCoord+1} };
            for(int[] n : neighbors){
                int nX = n[0]; // X coordinate neighbor
                int nY = n[1]; // Y coordinate neighbor

                if(nX >= 0 && nY >= 0 && nX < field.length && nY < field[0].length)
                    if(!field[nX][nY].getRevealed() && field[nX][nY].getStatus().equals("0"))
                        stack.push(n);
            }
        }
    }

    /**
     * revealMines
     *
     * This method should follow the psuedocode given.
     * Why might a queue be useful for this function?
     *
     * @param x     The x value the user entered.
     * @param y     The y value the user entered.
     */
    public void revealMines(int x, int y) {
        Q1Gen<int[]> queue = new Q1Gen<>();
        queue.add(new int[] {x, y});

        while(queue.length() != 0) {
            int[] front = queue.remove();
            int xCoord = front[0], yCoord = front[1];

            field[xCoord][yCoord].setRevealed(true);

            if(field[xCoord][yCoord].getStatus().equals("M"))
                break;

            int[][] neighbors = { {xCoord-1, yCoord}, {xCoord+1, yCoord}, {xCoord, yCoord-1}, {xCoord, yCoord+1} };
            for(int[] n : neighbors){
                int nX = n[0]; // X coordinate neighbor
                int nY = n[1]; // Y coordinate neighbor

                if(nX >= 0 && nY >= 0 && nX < field.length && nY < field[0].length && !field[nX][nY].getRevealed())
                    queue.add(n);
            }
        }
    }

    /**
     * revealStart
     *
     * @param x       The x value the user entered.
     * @param y       The y value the user entered.
     */
//    public void revealStart(int x, int y) {
//    }
    /**
     * printMinefield
     *
     * @fuctnion This method should print the entire minefield, regardless if the user has guessed a square.
     * *This method should print out when debug mode has been selected. 
     */
    public void printMinefield() {
        StringBuilder mineField = new StringBuilder("     ");
        for(int k = 0; k < field.length; k++) {
            if(k > 9) // If INSANE mode is selected
                mineField.append(k).append("  ");
            else mineField.append(k).append("   ");
        }
        mineField.append("\n");

        for(int i = 0; i < field.length; i++){
            if(i > 9) // If INSANE mode is selected
                mineField.append(ANSI_GREY_BG).append(i);
            else mineField.append(ANSI_GREY_BG).append(i).append(" ");
            for(int j = 0; j < field[i].length; j++){
                String color = switch(field[i][j].getStatus()){
                    case "0" -> ANSI_YELLOW;
                    case "1" -> ANSI_BLUE_BRIGHT;
                    case "2" -> ANSI_GREEN;
                    case "3" -> ANSI_ORANGE;
                    case "4", "5", "6", "7", "8" -> ANSI_RED;
                    case "M" -> ANSI_RED_DARK;
                    case "F" -> ANSI_RED_BRIGHT;
                    default -> ANSI_GREY_BG;
                };

                mineField.append("   ").append(color).append(field[i][j].getStatus());
            }
            mineField.append("\n").append(ANSI_GREY_BG);
        }
        System.out.println(mineField);
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been revealed to the user or that the user has guessed.
     */
    public String toString() {
        StringBuilder mineField = new StringBuilder("     ");
        for(int k = 0; k < field.length; k++) {
            if(k > 9) // If INSANE mode is selected
                mineField.append(k).append("  ");
            else mineField.append(k).append("   ");
        }
        mineField.append("\n");

        for(int i = 0; i < field.length; i++){
            if(i > 9) // If INSANE mode is selected
                mineField.append(ANSI_GREY_BG).append(i);
            else mineField.append(ANSI_GREY_BG).append(i).append(" ");
            for(int j = 0; j < field[i].length; j++){
                String color = switch(field[i][j].getStatus()){
                    case "0" -> ANSI_YELLOW;
                    case "1" -> ANSI_BLUE_BRIGHT;
                    case "2" -> ANSI_GREEN;
                    case "3" -> ANSI_ORANGE;
                    case "4", "5", "6", "7", "8" -> ANSI_RED;
                    case "M" -> ANSI_RED_DARK;
                    case "F" -> ANSI_RED_BRIGHT;
                    default -> ANSI_GREY_BG;
                };

                if(!field[i][j].getRevealed())
                    mineField.append(ANSI_GREY_BG).append("   -");
                else
                    mineField.append("   ").append(color).append(field[i][j].getStatus());
            }
            mineField.append("\n").append(ANSI_GREY_BG);
        }

       return mineField.toString();
    }
}