import java.util.Scanner;

public class GameText {
    private final Editor editor;
    private final Scanner scanner;
    private int gameTurnsEarned; // stores turns earned across reviews

    public GameText() {
        editor = new Editor();
        scanner = new Scanner(System.in);
        gameTurnsEarned = 0;
    }

    public void run() {
        while (true) {
            System.out.println("\n--- ` A Review Game  ---" +
                    "\nOptions:");
            System.out.println("1. list sets");
            System.out.println("2. add set");
            System.out.println("3. remove set");
            System.out.println("4. view/edit set");
            System.out.println("5. review set");
            System.out.println("6. game mode");
            System.out.println("7. exit");
            System.out.print("Choose: ");
            String input = scanner.nextLine();

            if (input.equals("1")) {
                editor.listSets();

            } else if (input.equals("2")) {
                System.out.print("Enter new set name: ");
                String name = scanner.nextLine();
                editor.addSet(name);
                FlashcardSet set = editor.getSet(editor.sets.size() - 1);
                if (set != null) {
                    editSet(set);
                }

            } else if (input.equals("3")) {
                editor.listSets();
                System.out.print("Enter set number to remove: ");
                int indexToRemove = convertInputToIndex(scanner.nextLine());
                editor.removeSet(indexToRemove);

            } else if (input.equals("4")) {
                editor.listSets();
                System.out.print("Enter set number to view/edit: ");
                int indexToView = convertInputToIndex(scanner.nextLine());
                FlashcardSet setToView = editor.getSet(indexToView);
                if (setToView == null) {
                    System.out.println("Invalid set number.");
                } else {
                    editSet(setToView);
                }

            } else if (input.equals("5")) {
                editor.listSets();
                System.out.print("Enter set number to review: ");
                int indexToReview = convertInputToIndex(scanner.nextLine());
                FlashcardSet setToReview = editor.getSet(indexToReview);
                if (setToReview == null) {
                    System.out.println("Invalid set number.");
                } else {
                    System.out.print("Choose difficulty (easy, medium, hard): ");
                    String difficulty = scanner.nextLine();

                    ReviewMode reviewMode = new ReviewMode(setToReview, scanner, difficulty);
                    int earnedTurns = reviewMode.startReview();

                    System.out.println("You earned " + earnedTurns + " game turns.");
                    gameTurnsEarned = gameTurnsEarned + earnedTurns; // add turns to stored total
                }

            } else if (input.equals("6")) {
                if (gameTurnsEarned <= 0) {
                    System.out.println("You have no game turns earned yet. Review cards to earn turns.");
                } else {
                    GameMode game = new GameMode(scanner, gameTurnsEarned);
                    game.start();
                    gameTurnsEarned = 0; // reset turns after playing
                }

            } else if (input.equals("7")) {
                System.out.println("Exiting app.");
                scanner.close();
                return;

            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    private void editSet(FlashcardSet set) {
        while (true) {
            System.out.println("\nEditing set: " + set.name);
            System.out.println("Commands: list, add, remove, clear, back, /e (exit to main menu)");
            System.out.print("Enter command: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("list")) {
                set.listCards();

            } else if (command.equalsIgnoreCase("add")) {
                System.out.println("Add cards: enter cards separated by double spaces:");
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
                        String term = cardPair.substring(0, position);
                        String def = cardPair.substring(position + 1);
                        if (term.length() == 0 || def.length() == 0) {
                            System.out.println("Empty term or definition in: '" + cardPair + "'");
                            continue;
                        }
                        set.addCard(term, def);
                        System.out.println("Added card: " + term + " - " + def);
                    }
                }

            } else if (command.equalsIgnoreCase("remove")) {
                set.listCards();
                System.out.print("Enter card number to remove: ");
                int cardIndex = convertInputToIndex(scanner.nextLine());
                set.removeCard(cardIndex);

            } else if (command.equalsIgnoreCase("clear")) {
                set.clearCards();

            } else if (command.equalsIgnoreCase("back") || command.equalsIgnoreCase("/e")) {
                break;

            } else {
                System.out.println("Unknown command.");
            }
        }
    }

    private int convertInputToIndex(String input) {
        if (input == null || input.length() == 0) {
            return -1;
        }
        int num = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c < '0' || c > '9') {
                return -1;
            }
            num = num * 10 + (c - '0');
        }
        return num - 1;
    }
}


