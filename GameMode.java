import java.util.Random;
import java.util.Scanner;

public class GameMode {
    private final Scanner scanner;
    private final int turns;
    private final Random random;

    public GameMode(Scanner scanner, int turns) {
        this.scanner = scanner;
        this.turns = turns;
        this.random = new Random();
    }

    public void start() {
        System.out.println("Starting game mode with " + turns + " turn(s).");
        System.out.println("Choose game:");
        System.out.println("1 - Rock Paper Scissors");
        System.out.println("2 - Guess the Number");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine();

        if (choice.equals("1")) {
            playRockPaperScissors();
        } else if (choice.equals("2")) {
            playGuessNumber();
        } else {
            System.out.println("Invalid choice, exiting game mode.");
        }
    }

    private void playRockPaperScissors() {
        int streak = 0;
        for (int i = 0; i < turns; i++) {
            System.out.println("\nTurn " + (i + 1) + " of " + turns);
            System.out.println("Choose: 1. Rock 2. Paper 3. Scissors");
            System.out.print("Your move: ");

            String input = scanner.nextLine();
            int playerMove = toNumber(input);

            if (playerMove < 1 || playerMove > 3) {
                System.out.println("Invalid move. Turn lost.");
                streak = 0;
                continue;
            }

            int compMove = random.nextInt(3) + 1;
            System.out.println("Computer chose: " + moveName(compMove));

            int result = getResult(playerMove, compMove);
            if (result == 1) {
                System.out.println("You win this round!");
                streak++;
            } else if (result == 0) {
                System.out.println("It's a tie.");
            } else {
                System.out.println("You lose this round.");
                streak = 0;
            }

            System.out.println("Current winning streak: " + streak);
        }
        System.out.println("Game over.");
    }

    private void playGuessNumber() {
        System.out.print("Enter the minimum number: ");
        int min = readInt();
        System.out.print("Enter the maximum number: ");
        int max = readInt();

        if (max <= min) {
            System.out.println("Invalid range.");
            return;
        }

        int secret = random.nextInt(max - min + 1) + min;

        System.out.println("Guess the number between " + min + " and " + max);
        int attempts = turns;

        for (int i = 0; i < attempts; i++) {
            System.out.print("Attempt " + (i + 1) + ": ");
            int guess = readInt();

            if (guess == secret) {
                System.out.println("Correct! You guessed the number.");
                break;
            } else if (guess < secret) {
                System.out.println("Too low.");
            } else {
                System.out.println("Too high.");
            }
        }
        System.out.println("Game over. The number was: " + secret);
    }

    private int toNumber(String input) {
        // Simple conversion without parseInt
        if (input == null || input.length() == 0) return -1;
        int num = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') return -1;
            num = num * 10 + (c - '0');
        }
        return num;
    }

    private int readInt() {
        while (true) {
            String input = scanner.nextLine();
            int val = toNumber(input);
            if (val != -1) {
                return val;
            }
            System.out.print("Invalid number, try again: ");
        }
    }

    private String moveName(int move) {
        if (move == 1) return "Rock";
        if (move == 2) return "Paper";
        if (move == 3) return "Scissors";
        return "Unknown";
    }

    // Returns 1 if player wins, 0 tie, -1 lose
    private int getResult(int player, int comp) {
        if (player == comp) return 0;

        // Rock beats Scissors (1 beats 3)
        // Scissors beats Paper (3 beats 2)
        // Paper beats Rock (2 beats 1)

        if ((player == 1 && comp == 3) || (player == 3 && comp == 2) || (player == 2 && comp == 1)) {
            return 1;
        } else {
            return -1;
        }
    }
}
