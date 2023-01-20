
import java.util.Scanner;

/**
 * This creates a game of Tic-Tac-Toe that starts of by giving the user
 * the option to play against an AI. If they play against an AI they
 * will be assigned the letter "O" and then the game ends when either the
 * AI or the player gets 3 letters in a row or the board is full
 *
 * @author Patrick Menninger
 */
public class GamePlay {

    // Used when the player doesn't want to play against AI to determine whose move it is
    private static int playerTurn = 1;

    // Created to easier understand the blank spaces on the board
    private static final String EMPTY = " ";

    // Creates the array that stores the EMPTY value or the letter in each square
    private static final String[][] moves = new String[][]{{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};

    // Initializes the String variable to hold the player's letter
    private static String playerLetter;

    // Created to determine if no player has won yet and to continue playing
    private static boolean continuePlay = true;

    // Created to stop the game once this reaches 9 moves because the board is full
    private static int amountMoves = 0;

    // Used to determine if the player is playing against an AI
    private static boolean AI = true;

    // Created to determine if it's the AI's move
    private static boolean AImove;

    // Created to store the letter the AI is using
    private static String AIletter;

    // Stores the best move for the column for the computer
    private static int bestMoveX = 0;

    //stores the best move for the row for the computer
    private static int bestMoveY = 0;


    /**
     * Prints the board so that you can see your moves.
     */
    public static void printBoard() {
        clearScreen();

        System.out.println(" _____ _____ _____");
        System.out.println("|     |     |     |");
        System.out.println("|  " + moves[0][0] + "  |  " + moves[1][0] + "  |  " + moves[2][0] + "  |");
        System.out.println("|_____|_____|_____|");
        System.out.println("|     |     |     |");
        System.out.println("|  " + moves[0][1] + "  |  " + moves[1][1] + "  |  " + moves[2][1] + "  |");
        System.out.println("|_____|_____|_____|");
        System.out.println("|     |     |     |");
        System.out.println("|  " + moves[0][2] + "  |  " + moves[1][2] + "  |  " + moves[2][2] + "  |");
        System.out.println("|_____|_____|_____|\n");
    }


    /**
     * Takes input from playerMove to change the value on the board at the specified position.
     *
     * @param column
     * @param rows
     * @param letter
     */
    public static void updateBoard(int column, int rows, String letter) {

        //If the row and column are not on the array it skips this code and goes to catch
        try {

            //Checks to see if it is an open square
            if ((moves[column][rows].equals(EMPTY))) {
                amountMoves++;
                moves[column][rows] = letter;
                updatePlayer();
            } else {
                System.out.println("\n----------------------------------------------------------------------------");
                System.out.println("You cannot go on a square that has already been played on, choose another one.");
                System.out.println("----------------------------------------------------------------------------\n");
                playerMove();
            }

            //Clears the screen so more than one board doesn't show, then prints the new board with the new values
            printBoard();


            //Pauses the game quickly before it displays the next players turn
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //This is displayed if an OutOfBoundsError is found
        catch (Exception e) {
            System.out.println("\n-----------------------------------------------------");
            System.out.println("Please enter a number from 1 - 3 for rows and columns.");
            System.out.println("-----------------------------------------------------\n");
            playerMove();
        }
    }


    /**
     * Clears the screen so it doesn't get crowded.
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }


    /**
     * Takes user input to set the row and column and place either a "X" or an "O".
     */
    public static void playerMove() {
        AImove = false;

        Scanner input1 = new Scanner(System.in);

        if (!(AI)) {
            if (playerTurn == 1) {
                playerLetter = "X";
            } else if (playerTurn == 2) {
                playerLetter = "O";
            }
        }


        System.out.println("Player " + playerTurn + ", type the column number of your move");

        // Takes in the users input of the column they want to put their letter
        int x = input1.nextInt() - 1;
        System.out.println("Player " + playerTurn + ", type the row number of your move");

        // Takes in the users input of the row they want to put their letter
        int y = input1.nextInt() - 1;


        updateBoard(x, y, playerLetter);
    }


    /**
     * Switches from "X" to "O" and vice versa after a move.
     */
    public static void updatePlayer() {
        if (!(AI)) {
            if (playerTurn == 1) {
                playerTurn = 2;
            } else if (playerTurn == 2) {
                playerTurn = 1;
            }
        }
    }

    /**
     * Starts the gameplay by asking the user if they want to
     * play against an AI.
     */
    public static void play() {
        printBoard();

        Scanner input2 = new Scanner(System.in);

        System.out.println("Do you want to play against AI? Enter yes or no.");

        // Created and initialized to take in the players input if they want to play against AI
        String AIchoice = input2.nextLine();

        if (AIchoice.equals("yes") || (AIchoice.equals("Yes"))) {
            AI = true;

            System.out.println("You will start as O");
            playerLetter = "O";

            AIletter = "X";

        } else {
            AI = false;
            printBoard();
        }


        checkForWinOverall();
    }

    /**
     * Checks if either the AI or the player has won the game.
     */
    public static void checkForWinOverall() {
        while (continuePlay) {
            if (AI) {
                moveAI();
                checkForWin();
                if (!(continuePlay)) {
                    break;
                }
            }

            playerMove();
            checkForWin();
            if (!(continuePlay)) {
                break;
            }
        }
    }

    /**
     * Determines the way in which the AI or player won.
     * This displays the column or row they won on.
     */
    public static void checkForWin() {


        if (!(moves[1][0].equals(EMPTY)) && moves[0][0].equals(moves[1][0]) && (moves[1][0].equals(moves[2][0]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on row 1!");
            } else {
                System.out.println(playerLetter + " wins on row 1!");
            }
        } else if (!(moves[1][1].equals(EMPTY)) && moves[0][1].equals(moves[1][1]) && (moves[1][1].equals(moves[2][1]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on row 2!");
            } else {
                System.out.println(playerLetter + " wins on row 2!");
            }
        } else if (!(moves[1][2].equals(EMPTY)) && moves[0][2].equals(moves[1][2]) && (moves[1][2].equals(moves[2][2]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on row 3!");
            } else {
                System.out.println(playerLetter + " wins on row 3!");
            }
        } else if (!(moves[0][1].equals(EMPTY)) && moves[0][0].equals(moves[0][1]) && (moves[0][1].equals(moves[0][2]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on column 1!");
            } else {
                System.out.println(playerLetter + " wins on column 1!");
            }
        } else if (!(moves[1][1].equals(EMPTY)) && moves[1][0].equals(moves[1][1]) && (moves[1][1].equals(moves[1][2]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on column 2!");
            } else {
                System.out.println(playerLetter + " wins on column 2!");
            }
        } else if (!(moves[2][1].equals(EMPTY)) && moves[2][0].equals(moves[2][1]) && (moves[2][1].equals(moves[2][2]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on column3!");
            } else {
                System.out.println(playerLetter + " wins on column 3!");
            }
        } else if (!(moves[1][1].equals(EMPTY)) && moves[0][0].equals(moves[1][1]) && (moves[1][1].equals(moves[2][2]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on a diagonal!");
            } else {
                System.out.println(playerLetter + " wins on a diagonal!");
            }
        } else if (!(moves[1][1].equals(EMPTY)) && moves[0][2].equals(moves[1][1]) && (moves[1][1].equals(moves[2][0]))) {
            continuePlay = false;
            if ((AI) && AImove) {
                System.out.println("AI wins on a diagonal!");
            } else {
                System.out.println(playerLetter + " wins on a diagonal!");
            }
        } else if (amountMoves == 9) {
            continuePlay = false;
            System.out.println("Draw!");
        }

    }

    /**
     * Determines the best move for the AI by calling the minimax function.
     * It then updates the board based on the determined best move.
     */
    public static void moveAI() {

        AImove = true;

        double bestScore = Integer.MIN_VALUE;
        int bestDepth = 8;
        boolean exit = false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (moves[i][j].equals(EMPTY)) {
                    moves[i][j] = AIletter;
                    double score = minimax(1, false);
                    moves[i][j] = EMPTY;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMoveX = i;
                        bestMoveY = j;
                    }
                }
            }
        }
        updateBoard(bestMoveX, bestMoveY, AIletter);
    }

    /**
     * Finds the best move for the AI by looking at all the possibilities and outcomes.
     * It then assigns point values to each outcome.
     * The function assigns a value of 1 divided by the
     * number of times it had to call the recursive function.
     * The path with the bigger number from that formula is chosen.
     *
     * @param depth
     * @param isMaximizing
     * @return t
     */
    public static double minimax(int depth, boolean isMaximizing) {
        Double result = checkForWinMinimax(depth);

        // Ends the recursive function
        if (result != null) {
            return result;
        }

        /*
         Starts of by placing an AI move in the first row and column.
         It then pretends to place the player move in the first column second row.
         It repeats this process until either someone has won or there is a tie.
         This function prioritizes an AI win by assigning a bigger number to each path the AI wins.
         */
        if (isMaximizing) {
            double bestScore = Integer.MIN_VALUE;


            for (int col = 0; col < 3; col++) {
                for (int row = 0; row < 3; row++) {
                    if (moves[col][row].equals(EMPTY)) {
                        moves[col][row] = AIletter;
                        double score = minimax(depth + 1, false);
                        moves[col][row] = EMPTY;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            double bestScore = Integer.MAX_VALUE;

            for (int col = 0; col < 3; col++) {
                for (int row = 0; row < 3; row++) {
                    if (moves[col][row].equals(EMPTY)) {
                        moves[col][row] = playerLetter;
                        double score = minimax(depth + 1, true);
                        moves[col][row] = EMPTY;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    /**
     * Checks each row to see if there is 3 of one letter in a row
     *
     * @return the row that was won on
     */
    private static String checkRows() {
        for (int row = 0; row < 3; row++) {
            if (moves[0][row].equals(moves[1][row]) && (moves[1][row].equals(moves[2][row])) && !(moves[0][row].equals(EMPTY))) {
                return moves[0][row];
            }

        }
        return null;
    }

    /**
     * Checks each column to see if there is 3 of one letter in a column
     *
     * @return the column that was won on
     */
    private static String checkColumns() {
        for (int col = 0; col < 3; col++) {
            if (moves[col][0].equals(moves[col][1]) && (moves[col][1].equals(moves[col][2])) && !(moves[col][0].equals(EMPTY))) {
                return moves[col][0];
            }

        }
        return null;
    }

    /**
     * Takes in the depth of the minimax function that was called
     * and determines if either the AI or the player has won.
     * This is where the point values are assigned to each outcome
     *
     * @param depth
     * @return
     */
    public static Double checkForWinMinimax(int depth) {

        String winner = null;
        Double winnerScore = null;


        if (depth == 9) {
            winner = "tie";
        }
        if (checkRows() != null) {
            winner = checkRows();
        }

        if (checkColumns() != null) {
            winner = checkColumns();
        }

        if (!(moves[1][1].equals(EMPTY)) && moves[0][0].equals(moves[1][1]) && (moves[1][1].equals(moves[2][2]))) {
            winner = moves[1][1];
        }
        if (!(moves[1][1].equals(EMPTY)) && moves[0][2].equals(moves[1][1]) && (moves[1][1].equals(moves[2][0]))) {
            winner = moves[1][1];
        }


        if (winner != null && winner.equals("X")) {
            winnerScore = (double) (1.0) / depth;
        } else if (winner != null && winner.equals("O")) {
            winnerScore = (double) (-1.0) / depth;
        } else if (winner != null && winner.equals("tie")) {
            winnerScore = 0.0;
        }
        return winnerScore;
    }
}
