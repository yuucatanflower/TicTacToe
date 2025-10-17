
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Player {
    private final char symbol;
    private final String name;
    private final boolean isHuman;
    
    public Player(String name, char symbol, boolean isHuman) {
        this.symbol = symbol;
        this.name = name;
        this.isHuman = isHuman;
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void cpuMove(TicTacToe ttt, Player opponent) {

        if (isHuman) {
            return;
        }

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                // Check if the cell is empty
                if (ttt.fieldEmpty(r, c)) {
                    // Create a TRUE COPY of the board for simulation
                    TicTacToe future = new TicTacToe(ttt);
                    future.placeTic(this, r, c);

                    if (future.checkIfWon(this)) {
                        ttt.placeTic(this, r, c);
                        return;
                    }
                }
            }
        }
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (ttt.fieldEmpty(r, c)) {
                    TicTacToe future = new TicTacToe(ttt);
                    future.placeTic(opponent, r, c);

                    if (future.checkIfWon(opponent)) {
                        ttt.placeTic(this, r, c);
                        return;
                    }
                }
            }
        }

        List<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (ttt.fieldEmpty(r, c)) {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            Random rand = new Random();
            int[] move = emptyCells.get(rand.nextInt(emptyCells.size()));
            ttt.placeTic(this, move[0], move[1]);
        }
    }

    public void makeMove(TicTacToe ttt, Scanner input) {
        while (true) {
            System.out.print(this.name + ", make your move (choose a number 1-9): ");
            String line = input.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number 1 - 9.");
                continue;
            }

            if (choice < 1 || choice > 9) {
                System.out.println("Invalid choice. Please choose a number between 1 and 9.");
                continue;
            }

            int row = (choice - 1) / 3;
            int col = (choice - 1) % 3;

            boolean moveWasSuccessful = ttt.placeTic(this, row, col);

            if (moveWasSuccessful) {
                break;
            } else {
                System.out.println("That spot is already taken. Try again.");
            }
        }
    }
}
