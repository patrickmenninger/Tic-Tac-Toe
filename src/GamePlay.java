import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class GamePlay {
    private static int playerTurn = 1;
    private static String EMPTY = " ";

    private static String[][] moves = new String[][]{{EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}, {EMPTY, EMPTY, EMPTY}};
    private static String playerLetter;
    private static boolean continuePlay = true;
    private static int amountMoves = 0;
    private static boolean AI = true;
    private static String AIchoice;
    private static boolean AImove;
    private static int x = 10;
    private static int y = 10;
    private static String AIletter;
    private static int bestMoveI = 0;
    private static int bestMoveJ = 0;
    private static int amountMovesHolder = 0;

    private static Map<String, Integer> scoreLookup  = new HashMap<>() {{
        put("X", 1);
        put("O", -1);
        put("tie", 0);
    }};

    //Prints the board so that you can see your moves
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

    //Takes input from playerMove to change the value on the board at the specified position
    public static void updateBoard(int column, int rows, String letter) {

        //If an the row and column are not on the array it skips this code and goes to catch
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

            /*Clears the screen so more than one board doesn't show, then prints the new board with the new values*/
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

    //Clears the screen so it don't get crowded
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    //Takes user input to set the row and column and place either a "X" or an "O"
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
        x = input1.nextInt() - 1;
        System.out.println("Player " + playerTurn + ", type the row number of your move");
        y = input1.nextInt() - 1;


        updateBoard(x, y, playerLetter);
    }

    //Switches from "X" to "O" and vice versa after a move
    public static void updatePlayer() {
        if (!(AI)) {
            if (playerTurn == 1) {
                playerTurn = 2;
            } else if (playerTurn == 2) {
                playerTurn = 1;
            }
        }
    }

    public static void play() {
        printBoard();

        Scanner input2 = new Scanner(System.in);

        System.out.println("Do you want to play against AI? Enter yes or no.");
        AIchoice = input2.nextLine();

        if (AIchoice.equals("yes") || (AIchoice.equals("Yes"))) {
            AI = true;

            System.out.println("You will start as O");
            playerLetter = input2.nextLine();

            playerLetter.equals("O")
            AIletter = "X"
            if (playerLetter.equals("X")) {
                AIletter = "O";

                System.out.println("Player " + playerTurn + ", type the column number of your move");
                x = input2.nextInt() - 1;
                System.out.println("Player " + playerTurn + ", type the row number of your move");
                y = input2.nextInt() - 1;

                updateBoard(x, y, playerLetter);
                amountMoves++;
            } else if (playerLetter.equals("O")) {
                AIletter = "X";
            }
        } else {
            AI = false;
            ;
        }

        printBoard();
        checkForWinOverall();
    }

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
                        bestMoveI = i;
                        bestMoveJ = j;
                    }
                }
            }
        }
        updateBoard(bestMoveI, bestMoveJ, AIletter);
    }

    public static double minimax(int depth, boolean isMaximizing) {
        Double result = checkForWinMinimax(depth);

        if (result != null) {
            return result;
        }

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

    private static String checkRows() {
       for (int row = 0; row < 3; row++) {
           if (moves[0][row].equals(moves[1][row]) && (moves[1][row].equals(moves[2][row])) && !(moves[0][row].equals(EMPTY))) {
               return moves[0][row];
           }

       }
        return null;
    }

    private static String checkColumns() {
        for (int col = 0; col < 3; col++) {
            if (moves[col][0].equals(moves[col][1]) && (moves[col][1].equals(moves[col][2])) && !(moves[col][0].equals(EMPTY))) {
                return moves[col][0];
            }

        }
        return null;
    }

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
            winner =  moves[1][1];
        }
        if (!(moves[1][1].equals(EMPTY)) && moves[0][2].equals(moves[1][1]) && (moves[1][1].equals(moves[2][0]))) {
            winner = moves[1][1];
        }


        if (winner != null && winner.equals("X")) {
            winnerScore = (double)(1.0)/depth;
        } else if (winner != null && winner.equals("O")) {
            winnerScore = (double)(-1.0)/depth;
        } else if (winner != null && winner.equals("tie")) {
            winnerScore = 0.0;
        }
        return winnerScore;
    }
}
