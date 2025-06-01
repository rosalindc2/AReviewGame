import java.util.Scanner;

public class ReviewMode {
    private FlashcardSet set;
    private Scanner scanner;
    private String difficulty; // easy, medium, hard

    public ReviewMode(FlashcardSet set, Scanner scanner, String difficulty) {
        this.set = set;
        this.scanner = scanner;
        this.difficulty = difficulty;
    }

    // return earned turns based on diffculty & answers
    public int startReview() {
        if (set.getSize() == 0) {
            System.out.println("This set has no cards to review.");
            return 0;
        }

        System.out.println("Choose review mode:");
        System.out.println("1 - Auto-check (type the definition, program checks correctness)");
        System.out.println("2 - Self-check (you check your answer yourself)");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine();

        int turnsEarned = 0;

        if (choice.equals("1")) {
            turnsEarned = autoCheckMode();
        } else if (choice.equals("2")) {
            turnsEarned = selfCheckMode();
        } else {
            System.out.println("Invalid choice, returning to menu.");
        }

        System.out.println("You earned " + turnsEarned + " turn(s) for the game mode.");
        return turnsEarned;
    }

    private int autoCheckMode() {
        int correct = 0;
        int wrong = 0;
        int streak = 0;
        int turns = 0;

        System.out.println("Auto-check mode. Type the definition for each term.");

        for (int i = 0; i < set.getSize(); i++) {
            String term = set.getTerm(i);
            String definition = set.getDefinition(i);

            System.out.println("\nTerm: " + term);
            System.out.print("Your answer: ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase(definition)) {
                System.out.println("Correct!");
                correct++;
                streak++;
                turns += calculateTurnsForStreak(streak);
            } else {
                System.out.println("Wrong. Correct answer: " + definition);
                wrong++;
                streak = 0; // reset streak on wrong answer
            }
        }

        printSummary(correct, wrong);
        return turns;
    }

    private int selfCheckMode() {
        int correct = 0;
        int wrong = 0;
        int streak = 0;
        int turns = 0;

        System.out.println("Self-check mode. Think of the definition, then check yourself.");

        for (int i = 0; i < set.getSize(); i++) {
            String term = set.getTerm(i);
            String definition = set.getDefinition(i);

            System.out.println("\nTerm: " + term);
            System.out.print("Press enter to see the definition...");
            scanner.nextLine();

            System.out.println("Definition: " + definition);

            while (true) {
                System.out.print("Did you get it right? (y/n): ");
                String input = scanner.nextLine().toLowerCase();
                if (input.equals("y")) {
                    correct++;
                    streak++;
                    turns += calculateTurnsForStreak(streak);
                    break;
                } else if (input.equals("n")) {
                    wrong++;
                    streak = 0;
                    break;
                } else {
                    System.out.println("Please answer 'y' or 'n'.");
                }
            }
        }

        printSummary(correct, wrong);
        return turns;
    }

    private int calculateTurnsForStreak(int streak) {
        // easy: every correct = 1 turn
        // medium: every 2 correct in a row = 1 turn
        // hard: every 5 correct in a row = 1 turn

        if (difficulty.equalsIgnoreCase("easy")) {
            return 1;
        } else if (difficulty.equalsIgnoreCase("medium")) {
            if (streak % 2 == 0) return 1;
        } else if (difficulty.equalsIgnoreCase("hard")) {
            if (streak % 5 == 0) return 1;
        }
        return 0;
    }

    private void printSummary(int correct, int wrong) {
        System.out.println("\nReview completed.");
        System.out.println("Correct answers: " + correct);
        System.out.println("Wrong answers: " + wrong);
        int total = correct + wrong;
        double percent = total == 0 ? 0 : (correct * 100.0) / total;
        System.out.printf("Accuracy: %.2f%%\n", percent);
    }
}
