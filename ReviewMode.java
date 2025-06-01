import java.util.Scanner;

public class ReviewMode {
    private FlashcardSet set;
    private Scanner scanner;

    public ReviewMode(FlashcardSet set, Scanner scanner) {
        this.set = set;
        this.scanner = scanner;
    }

    public void startReview() {
        if (set.getSize() == 0) {
            System.out.println("This set has no cards to review.");
            return;
        }

        System.out.println("Choose review mode:");
        System.out.println("1 - Auto-check (type the definition, program checks correctness)");
        System.out.println("2 - Self-check (you check your answer yourself)");
        System.out.print("Enter choice: ");

        String choice = scanner.nextLine().trim();

        if (choice.equals("1")) {
            autoCheckMode();
        } else if (choice.equals("2")) {
            selfCheckMode();
        } else {
            System.out.println("Invalid choice, returning to menu.");
        }
    }

    private void autoCheckMode() {
        int correct = 0;
        int wrong = 0;

        System.out.println("Auto-check mode. Type the definition for each term.");

        for (int i = 0; i < set.getSize(); i++) {
            String term = set.getTerm(i);
            String definition = set.getDefinition(i);

            System.out.println("\nTerm: " + term);
            System.out.print("Your answer: ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase(definition.trim())) {
                System.out.println("Correct!");
                correct++;
            } else {
                System.out.println("Wrong. Correct answer: " + definition);
                wrong++;
            }
        }

        printSummary(correct, wrong);
    }

    private void selfCheckMode() {
        int correct = 0;
        int wrong = 0;

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
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("y")) {
                    correct++;
                    break;
                } else if (input.equals("n")) {
                    wrong++;
                    break;
                } else {
                    System.out.println("Please answer 'y' or 'n'.");
                }
            }
        }

        printSummary(correct, wrong);
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
