import java.util.Scanner;

public class GameText {
    private Editor editor;
    private Scanner scanner;

    public GameText() {
        editor = new Editor();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        new GameText().run();
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Quizlet Text App ---");
            System.out.println("1. list sets");
            System.out.println("2. add set");
            System.out.println("3. remove set");
            System.out.println("4. view/edit set");
            System.out.println("5. review set"); // renamed to clarify
            System.out.println("6. exit");
            System.out.print("Choose: ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    editor.listSets();
                    break;

                case "2":
                    System.out.print("Enter new set name: ");
                    String name = scanner.nextLine();
                    editor.addSet(name);
                    FlashcardSet set = editor.getSet(editor.sets.size() - 1);
                    if (set != null) {
                        editSet(set);
                    }
                    break;

                case "3":
                    editor.listSets();
                    System.out.print("Enter set number to remove: ");
                    int indexToRemove = convertInputToIndex(scanner.nextLine());
                    editor.removeSet(indexToRemove);
                    break;

                case "4":
                    editor.listSets();
                    System.out.print("Enter set number to view/edit: ");
                    int indexToView = convertInputToIndex(scanner.nextLine());
                    FlashcardSet setToView = editor.getSet(indexToView);
                    if (setToView == null) {
                        System.out.println("Invalid set number.");
                    } else {
                        editSet(setToView);
                    }
                    break;

                case "5":
                    editor.listSets();
                    System.out.print("Enter set number to review: ");
                    int indexToReview = convertInputToIndex(scanner.nextLine());
                    FlashcardSet setToReview = editor.getSet(indexToReview);
                    if (setToReview == null) {
                        System.out.println("Invalid set number.");
                    } else {
                        ReviewMode reviewMode = new ReviewMode(setToReview, scanner);
                        reviewMode.startReview();
                    }
                    break;

                case "6":
                    System.out.println("Exiting app.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private void editSet(FlashcardSet set) {
        while (true) {
            System.out.println("\nEditing set: " + set.name);
            System.out.println("Commands: list, add, remove, clear, back, /e (exit to main menu)");
            System.out.print("Enter command: ");
            String command = scanner.nextLine().toLowerCase();

            if (command.equals("list")) {
                set.listCards();

            } else if (command.equals("add")) {
                System.out.println("Add cards: enter multiple cards separated by double spaces:");
                System.out.println("Format: term;definition  term;definition  ...");
                System.out.println("Type '/e' alone to stop adding.");

                while (true) {
                    String line = scanner.nextLine();
                    if (line.equals("/e")) {
                        System.out.println("Stopped adding cards.");
                        break;
                    }
                    String[] cardPairs = line.split("  "); // double space separator
                    for (String cardPair : cardPairs) {
                        int position = cardPair.indexOf(';');
                        if (position == -1) {
                            System.out.println("Invalid format for card: '" + cardPair + "'. Use term;definition");
                            continue;
                        }
                        String term = cardPair.substring(0, position).trim();
                        String def = cardPair.substring(position + 1).trim();
                        if (term.isEmpty() || def.isEmpty()) {
                            System.out.println("Empty term or definition in: '" + cardPair + "'");
                            continue;
                        }
                        set.addCard(term, def);
                        System.out.println("Added card: " + term + " - " + def);
                    }
                }

            } else if (command.equals("remove")) {
                set.listCards();
                System.out.print("Enter card number to remove: ");
                int cardIndex = convertInputToIndex(scanner.nextLine());
                set.removeCard(cardIndex);

            } else if (command.equals("clear")) {
                set.clearCards();

            } else if (command.equals("back") || command.equals("/e")) {
                break;

            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    // Converts 1-based input to 0-based index, returns -1 if invalid
    private int convertInputToIndex(String input) {
        if (input == null || input.isEmpty()) {
            return -1;
        }
        int number = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                return -1;
            }
            number = number * 10 + (c - '0');
        }
        return number - 1;
    }
}
