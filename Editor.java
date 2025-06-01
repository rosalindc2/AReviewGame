import java.util.ArrayList;
import java.util.Scanner;

public class Editor {
    public ArrayList<FlashcardSet> sets;

    public Editor() {
        sets = new ArrayList<>();
    }

    public void listSets() {
        if (sets.size() == 0) {
            System.out.println("No sets available.");
            return;
        }
        for (int i = 0; i < sets.size(); i++) {
            FlashcardSet set = sets.get(i);
            System.out.println((i + 1) + ". " + set.name + " (" + set.getSize() + " cards)");
        }
    }

    public void addSet(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Set name cannot be empty.");
            return;
        }
        FlashcardSet existing = getSetByName(name);
        if (existing != null) {
            System.out.println("Set with this name already exists.");
            return;
        }
        sets.add(new FlashcardSet(name));
        System.out.println("Set '" + name + "' created.");
    }

    public void removeSet(int index) {
        if (index < 0 || index >= sets.size()) {
            System.out.println("Invalid set number.");
            return;
        }
        String name = sets.get(index).name;
        sets.remove(index);
        System.out.println("Set '" + name + "' removed.");
    }

    public FlashcardSet getSet(int index) {
        if (index < 0 || index >= sets.size()) {
            return null;
        }
        return sets.get(index);
    }

    public FlashcardSet getSetByName(String name) {
        for (FlashcardSet set : sets) {
            if (set.name.equals(name)) {
                return set;
            }
        }
        return null;
    }

    public void addCards(FlashcardSet set, Scanner scanner) {
        System.out.println("Add cards (term;definition). You can add multiple cards separated by two spaces.");
        System.out.println("Example: 2+2;4  3+3;6");
        System.out.println("Type '/e' alone to stop adding.");

        while (true) {
            String line = scanner.nextLine();
            if (line.equals("/e")) {
                System.out.println("Stopped adding cards.");
                break;
            }
            // Split input by two or more spaces
            String[] cardStrings = line.split(" {2,}");

            boolean anyAdded = false;
            for (String card : cardStrings) {
                int position = card.indexOf(';');
                if (position == -1) {
                    System.out.println("Invalid card format (missing ';'): " + card);
                    continue;
                }
                String term = card.substring(0, position);
                String definition = card.substring(position + 1);
                if (term.isEmpty() || definition.isEmpty()) {
                    System.out.println("Invalid card (empty term or definition): " + card);
                    continue;
                }
                set.addCard(term, definition);
                System.out.println("Added card: " + term + " - " + definition);
                anyAdded = true;
            }
            if (!anyAdded) {
                System.out.println("No valid cards added, try again.");
            }
        }
    }

    public void removeCards(FlashcardSet set, Scanner scanner) {
        while (true) {
            set.listCards();
            System.out.print("Enter card number to remove or '/e' to exit: ");
            String input = scanner.nextLine();
            if (input.equals("/e")) {
                System.out.println("Stopped removing cards.");
                break;
            }
            int index = convertToInt(input) - 1;
            if (index < 0 || index >= set.getSize()) {
                System.out.println("Invalid card number.");
                continue;
            }
            set.removeCard(index);
        }
    }

    public static int convertToInt(String s) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        int number = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') {
                return -1;
            }
            number = number * 10 + (c - '0');
        }
        return number;
    }
}
