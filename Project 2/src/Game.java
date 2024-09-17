// Written by Liam Kinney, kinne351
import java.util.Scanner;

public class Game {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        Board myBoard = new Board();

        boolean isBlacksTurn = false;
        String currentTurn = "";

        System.out.println("======================= Chess =======================");
        System.out.println("The format you will be using to input your moves is:\n[start row] [end row] [start column] [end column]");
        System.out.println("Remember to include spaces between them.\n");
        System.out.println("If at any point you want to resign, input 'r'");
        System.out.println("=====================================================");

        Fen.load("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", myBoard);

        game: while(true) {
            System.out.println("\n" + myBoard);

            if(isBlacksTurn)
                currentTurn = "black";
            else
                currentTurn = "white";

            System.out.printf("It is currently %s's turn.\n", currentTurn);
            move: while(true) {
                System.out.print("What is your move?\n: ");
                String move = s.nextLine();
                String[] moves = move.split(" ");
                int startRow = 0, startCol = 0, endRow = 0, endCol = 0;

                if(moves[0].equalsIgnoreCase("r")){
                    String loser = "";
                    if(currentTurn.equals("white"))
                        loser = "black";
                    else loser = "white";

                    System.out.printf("\n%s resigned :(\n%s won!\n", currentTurn, loser);
                    break game;
                }

                if(moves.length < 4)
                    System.out.println("Please input your full move!\n");
                else if(moves.length > 4)
                    System.out.println("That's too many values! Please only input 4.\n");
                else {
                    startRow = Integer.parseInt(moves[0]);
                    startCol = Integer.parseInt(moves[1]);
                    endRow = Integer.parseInt(moves[2]);
                    endCol = Integer.parseInt(moves[3]);

                    Piece startingPiece = myBoard.getPiece(startRow, startCol);
                    if (startingPiece == null)
                        System.out.println("\nThat's not a piece!\n");
                    else if(!startingPiece.isMoveLegal(myBoard, endRow, endCol))
                        System.out.println("\nInvalid move!\n");
                    else if(startingPiece.getIsBlack() != isBlacksTurn)
                        System.out.printf("\nYou can't move the opponents pieces! Remember, you're playing %s...\n\n", currentTurn);
                    else {
                        myBoard.movePiece(startRow, startCol, endRow, endCol);
                        break move;
                    }
                }
            } // Move loop

            isBlacksTurn = !isBlacksTurn; // Switching whose turn it is

            if(Piece.pawnPromotion(myBoard)[0] != -1)
                promotePawn(myBoard, currentTurn); // promotePawn is made down below

            if(myBoard.isGameOver()) {
                System.out.printf("%s has won the game!", currentTurn);
                break game;
            }
        } // Game loop
    } // Main

    public static void promotePawn(Board myBoard, String currentTurn){
        Scanner s = new Scanner(System.in);
        System.out.printf("%s's pawn got promoted! Select which piece to be promoted to.\n", currentTurn);
        System.out.print("1) Queen. 2) Rook. 3) Bishop. 4) Knight.\n: ");
        int pawnPromChoice = s.nextInt();
        int[] location = Piece.pawnPromotion(myBoard);
        Piece promotionPiece = null;
        switch(pawnPromChoice){
            case 1: // Queen
                if(currentTurn.equals("white"))
                    promotionPiece = new Piece('\u2655', location[0], location[1], false);
                if(currentTurn.equals("black"))
                    promotionPiece = new Piece('\u265b', location[0], location[1], true);
                break;
            case 2: // Rook
                if(currentTurn.equals("white"))
                    promotionPiece = new Piece('\u2656', location[0], location[1], false);
                if(currentTurn.equals("black"))
                    promotionPiece = new Piece('\u265c', location[0], location[1], true);
                break;
            case 3: // Bishop
                if(currentTurn.equals("white"))
                    promotionPiece = new Piece('\u2657', location[0], location[1], false);
                if(currentTurn.equals("black"))
                    promotionPiece = new Piece('\u265d', location[0], location[1], true);
                break;
            case 4: // Knight
                if(currentTurn.equals("white"))
                    promotionPiece = new Piece('\u2658', location[0], location[1], false);
                if(currentTurn.equals("black"))
                    promotionPiece = new Piece('\u265e', location[0], location[1], true);
                break;
            default:
                System.out.println("Invalid input! Please enter 1, 2, 3, or 4 for your desired promotion piece.");
        } // Switch case

        myBoard.setPiece(location[0], location[1], promotionPiece);

    } // promotePawn

} // Game class
