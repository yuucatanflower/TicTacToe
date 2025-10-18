
import java.util.Scanner;

public class TicTacToe {

    public static final int ROWS = 3;
    public static final int COLS = 3;

    private final char[][] gameBoard;
    private final Player p1;
    private final Player p2;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN_UNDERLINE = "\u001b[4;36m";

    private String getColoredSymbol(char symbol) {
        return switch (symbol) {
            case 'X' -> ANSI_RED + "X" + ANSI_RESET;
            case 'O' -> ANSI_BLUE + "O" + ANSI_RESET;
            default ->
                // return numbers 1-9 without color
                    String.valueOf(symbol);
        };
    }

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

    public boolean fieldEmpty(int r, int c) {

        return gameBoard[r][c] != 'O' && gameBoard[r][c] != 'X';

    }

    private void prettyPrintGameBoard() {
        for (int i = 0; i < ROWS; i++) {
            // Call getColoredSymbol for each cell
            String s0 = getColoredSymbol(gameBoard[i][0]);
            String s1 = getColoredSymbol(gameBoard[i][1]);
            String s2 = getColoredSymbol(gameBoard[i][2]);

            System.out.println(s0 + " | " + s1 + " | " + s2 + " ");
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

    public void playGame() throws InterruptedException {
        Scanner input = new Scanner(System.in);
        Player currentPlayer = p1;
        int rounds = 0;

        while (true) {
            this.prettyPrintGameBoard();
            System.out.println();

            if (currentPlayer.isHuman()) {
                currentPlayer.makeMove(this, input);
            } else {
                System.out.println(currentPlayer.getName() + " is thinking...");
                System.out.println();
                Thread.sleep(1500);
                Player opponent = (currentPlayer == p1) ? p2 : p1;
                currentPlayer.cpuMove(this, opponent);
            }

            if (this.checkIfWon(currentPlayer)) {
                System.out.println(currentPlayer.colorName() + " has won the game! ");
                System.out.println();
                this.prettyPrintGameBoard();
                return;
            }

            rounds++;

            if (rounds == ROWS * COLS) {
                System.out.println("Nobody wins! It's a draw.");
                System.out.println();
                this.prettyPrintGameBoard();
                return;
            }

            currentPlayer = (currentPlayer == p1) ? p2 : p1;
        }
    }

    public static char askForSymbol(Scanner input) {
        System.out.print("What is your symbol? ( O / X ): ");
        boolean chosen = false;
        char result = ' ';
        while (!chosen) {
            result = input.next().toUpperCase().charAt(0);
            if (result == 'O' || result == 'X') {
                chosen = true;
            } else {
                System.out.print("Invalid symbol. Use O or X.");
            }
        }
        System.out.println();
        return result;
    }

    public static String askForName(Scanner input, int playerNumber) {
        String result = null;
        while (result == null) {
            System.out.println("What is player's #" + playerNumber + " name?");
            result = input.next();
        }
        System.out.println();

        return result;
    }

    public static void main(String[] args) throws InterruptedException {
        int choice = 0;
        boolean chosen = false;

        //Player p1 = new Player('X', "Xam",false);
        //Player p2 = new Player('O', "Max",false);

        Scanner input = new Scanner(System.in);
        Player p1 = null;
        Player p2 = null;


        while (!chosen) {
            System.out.println("Choose game mode: ");
            System.out.println("[1] 1 vs 1 ");
            System.out.println("[2] 1 vs CPU ");
            System.out.println("[3] CPU vs CPU ");

            choice = Integer.parseInt(input.nextLine());

            switch (choice) {
                case 1:
                    chosen = true;
                    p1 = new Player(askForName(input, 1), askForSymbol(input), true);
                    p2 = new Player(askForName(input, 2), p1.getSymbol() == 'X' ? 'O' : 'X', true);
                    break;
                case 2:
                    chosen = true;
                    p1 = new Player(askForName(input, 1), askForSymbol(input), true);
                    p2 = new Player(p1.getSymbol()=='X'? ANSI_BLUE+"CPU BLUE"+ANSI_RESET : ANSI_RED+"CPU RED"+ANSI_RESET,
                            p1.getSymbol() == 'X' ? 'O' : 'X',
                            false);
                    break;
                case 3:
                    chosen = true;
                    p1 = new Player(ANSI_RED+"CPU RED"+ANSI_RESET, 'X', false);
                    p2 = new Player(ANSI_BLUE+"CPU BLUE"+ANSI_RESET, 'O', false);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        TicTacToe ttt = new TicTacToe(p1, p2);

        ttt.playGame();
    }
}