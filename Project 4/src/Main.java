// Written by Liam Kinney, kinne351

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows = 0, cols = 0, flags = 0;

        String green = Minefield.ANSI_GREEN, yellow = Minefield.ANSI_YELLOW, red = Minefield.ANSI_RED, reset = Minefield.ANSI_GREY_BG;

        String instructions = """ 
                ==================================== Minesweeper ====================================
                Instructions:
                    First, select desired difficulty level, easy mode is a 5 x 5 field with 5 mines.
                    Medium mode is a 9 x 9 field with 12 mines, and INSANE mode is a 20 x 20 field
                    with 40 mines. Then select whether you wish to play in debug mode. Next, input
                    the starting coordinates, in the format [X] [Y]. Please make sure that there is
                    a space between the two coordinate values. Then you will make your guess. If you
                    do not wish to place a flag, just enter the coordinates as [X] [Y] like before.
                    If you do wish to place a flag, follow with an 'F', such as: [Y] [X] [F], but be
                    careful! Once you place a flag, you cannot undo it, and you only have 1 flag per
                    mine! Finally, if you reveal every square or place a flag on every mine, the game
                    ends and you win. But if you guess a square with a mine, then of course you lose.
                =====================================================================================
                """;

        System.out.println(instructions);

        boolean levelChosen;
        do {
            System.out.println("Select difficulty level:");
            System.out.printf("1) %sEasy%s 2) %sMedium%s 3) %sINSANE%s\n: ", green, reset, yellow, reset, red, reset);
            int level = s.nextInt();

            levelChosen = switch(level){
                case 1 -> { rows = 5; cols = 5; flags = 5; yield true; }
                case 2 -> { rows = 9; cols = 9; flags = 12; yield true; }
                case 3 -> { rows = 20; cols = 20; flags = 40; yield true; }
                default -> { System.out.println("Invalid input, select 1, 2, or 3."); yield false; }
            };

        } while(!levelChosen);

        Minefield mf = new Minefield(rows, cols, flags);

        s.nextLine();
        boolean debugMode = false;
        boolean debugModeChosen;
        do {
            System.out.print("\nDebug mode on? Y or N.\n: ");
            String debugChoice = s.nextLine();
            debugModeChosen = switch(debugChoice){
                case "Y", "y" -> { debugMode = true; yield true; }
                case "N", "n" -> true; // debugMode is already false, just yields true for debugModeChosen
                default -> { System.out.println("Invalid input."); yield false; }
            };
        } while(!debugModeChosen);

        boolean validStart = false;
        String startCoords;
        String[] splitCoords;
        int startX = 0, startY = 0;
        do {
            boolean exceptionCaught = false;
            System.out.print("\nEnter starting coordinates.\n: ");
            startCoords = s.nextLine();
            splitCoords = startCoords.split(" ");

            try { // Ensuring the correct format is entered
                startX = Integer.parseInt(splitCoords[0]);
                startY = Integer.parseInt(splitCoords[1]);
            } catch(NumberFormatException e){
                System.out.println("Make sure input does not have a space in the beginning or any letters!");
                exceptionCaught = true;
            } catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Invalid input. Remember to include the space between X and Y.");
                exceptionCaught = true;
            } finally {
                if(!exceptionCaught) {
                    if (Integer.parseInt(splitCoords[0]) < 0 || Integer.parseInt(splitCoords[1]) < 0 || Integer.parseInt(splitCoords[0]) >= rows || Integer.parseInt(splitCoords[1]) >= cols) {
                        System.out.println("Out of bounds!");
                        exceptionCaught = true;
                    }
                    if (startCoords.split(" ").length != 2)
                        System.out.println("Please input two values!");
                    else if(!exceptionCaught)
                        validStart = true;
                }
            }

        } while(!validStart);

        mf.createMines(startX, startY, flags);
        mf.evaluateField();
        mf.revealMines(startX, startY);

        if(debugMode) {
            System.out.println();
            mf.printMinefield();
        }
        else System.out.println("\n" + mf);

        for(;;){ // Main game loop
            System.out.println("Flags remaining: " + flags);
            String guess;
            String[] guesses;
            int xGuess = 0, yGuess = 0;
            boolean flag = false;
            boolean isInt = true;

            boolean validGuess = false;
            do {
                System.out.print("Enter guess.\n: ");
                guess = s.nextLine();
                guesses = guess.split(" ");

                boolean exceptionCaught = false;
                try { // Ensuring valid guess
                    xGuess = Integer.parseInt(guesses[0]);
                    yGuess = Integer.parseInt(guesses[1]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid guess!");
                    exceptionCaught = true;
                    isInt = false;
                } finally {
                    if(xGuess < 0 || xGuess >= rows || yGuess < 0 || yGuess >= cols) {
                        System.out.println("Out of bounds!");
                        isInt = false;
                        exceptionCaught = true;
                    }
                    if(guesses.length > 2 && !guesses[2].equalsIgnoreCase("F")) { // If there is a third value, it must be an F
                        System.out.println("Invalid guess!");
                        exceptionCaught = true;
                    }
                    else if(isInt && guesses.length > 2 && guesses[2].equalsIgnoreCase("F")){ // If third value is F, check if there are enough flags
                        if(flags < 1) {
                            System.out.println("Out of flags!");
                            exceptionCaught = true;
                        }
                        else {
                            flag = true;
                            flags--;
                        }
                    }

                    if(!exceptionCaught)
                        validGuess = true;
                }
            } while(!validGuess);

            mf.guess(xGuess, yGuess, flag);

            if(mf.loser()) { // If mine is hit
                System.out.printf("\nOops you hit a mine! You %slose%s :(\n", red, reset);
                System.out.println("These were the mine locations:\n");
                System.out.println(mf); // Revealing mine locations
                break;
            }

            if(mf.gameOver()) { // If either every square is revealed or flags are placed on every mine
                System.out.printf("\nYou %swin!%s", green, reset);
                break;
            }

            if(debugMode) {
                System.out.println();
                mf.printMinefield();
            }
            else System.out.println("\n" + mf);
        }

    }
}
