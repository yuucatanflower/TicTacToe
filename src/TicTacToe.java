
import java.util.Scanner;

public class TicTacToe {

    /*
     * Define instance variables
     *
     * Define a constructor
     *
     * Define public and private methods
     *
     * */
    public static final int ROWS = 3;
    public static final int COLS = 3;

    private char[][] gameBoard;
    private Player p1;
    private Player p2;

    public TicTacToe(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.gameBoard = new char[ROWS][COLS];

        char currentNumber = '1';
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameBoard[i][j] = currentNumber;
                currentNumber++;
            }
        }
    }

    public TicTacToe(TicTacToe original) {
        this.gameBoard = new char[ROWS][COLS];
        this.p1 = original.p1;
        this.p2 = original.p2;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                this.gameBoard[r][c] = original.gameBoard[r][c];
            }
        }
    }

    public boolean fieldEmpty(int r,int c) {

        return gameBoard[r][c] != 'O' && gameBoard[r][c] != 'X';

    }

    private void prettyPrintGameBoard() {
        for (int i = 0; i < ROWS; i++) {
            System.out.println(gameBoard[i][0] + " | " + gameBoard[i][1] + " | " + gameBoard[i][2]+" ");
            if (i < ROWS - 1) {
                System.out.println("--+---+--");
            }
        }
    }

    public boolean placeTic(Player p, int row, int col) {

        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
            return false;
        }
        if (gameBoard[row][col] != 'X' && gameBoard[row][col] != 'O') {
            gameBoard[row][col] = p.getSymbol();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkIfWon(Player p) {
        char symbol = p.getSymbol();

        for (int i = 0; i < ROWS; i++) {
            if (gameBoard[i][0] == symbol
                    && gameBoard[i][1] == symbol
                    && gameBoard[i][2] == symbol) {
                return true;
            }
            if (gameBoard[0][i] == symbol
                    && gameBoard[1][i] == symbol
                    && gameBoard[2][i] == symbol) {
                return true;
            }
        }
        if (gameBoard[0][0] == symbol
                && gameBoard[1][1] == symbol
                && gameBoard[2][2] == symbol) {
            return true;
        }

        if (gameBoard[0][2] == symbol
                && gameBoard[1][1] == symbol
                && gameBoard[2][0] == symbol) {
            return true;
        }
        return false;
    }

    // Inside the TicTacToe class

    public void playGame() {
        Scanner input = new Scanner(System.in);
        Player currentPlayer = p1;
        int rounds = 0;

        while (true) {
            this.prettyPrintGameBoard();
            System.out.println();

            // FIX: Differentiate between Human and CPU turns
            if (currentPlayer.isHuman()) {
                currentPlayer.makeMove(this, input);
            } else {
                System.out.println(currentPlayer.getName() + " is thinking...");

                Player opponent = (currentPlayer == p1) ? p2 : p1;
                currentPlayer.cpuMove(this, opponent);
            }

            if (this.checkIfWon(currentPlayer)) {
                System.out.println(currentPlayer.getName() + " has won the game!");
                this.prettyPrintGameBoard();
                return;
            }

            rounds++;

            if (rounds == ROWS * COLS) {
                System.out.println("Nobody wins! It's a draw.");
                this.prettyPrintGameBoard();
                return;
            }

            currentPlayer = (currentPlayer == p1) ? p2 : p1;
        }
    }


    public static void main(String[] args) {


        Player p1 = new Player('X', "Player1",false);
        Player p2 = new Player('O', "Player2",true);

        TicTacToe ttt = new TicTacToe(p1, p2);

        ttt.playGame();


    }
}
