import java.util.ArrayList;
// simple, interacts w/ cards & self-explanatory names
public class FlashcardSet {
    public String name;
    public ArrayList<Flashcard> cards;

    public FlashcardSet(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public void addCard(String term, String definition) {
        cards.add(new Flashcard(term, definition));
    }

    public void removeCard(int index) {
        if (index < 0 || index >= cards.size()) {
            System.out.println("Invalid card number.");
            return;
        }
        cards.remove(index);
        System.out.println("Card removed.");
    }

    public void clearCards() {
        cards.clear();
        System.out.println("All cards cleared in set '" + name + "'.");
    }

    public int getSize() {
        return cards.size();
    }

    public void listCards() {
        if (cards.size() == 0) {
            System.out.println("No cards in this set.");
            return;
        }
        for (int i = 0; i < cards.size(); i++) {
            Flashcard c = cards.get(i);
            System.out.println((i + 1) + ". " + c.term + " - " + c.definition);
        }
    }
    public String getTerm(int index) {
        if (index < 0 || index >= cards.size()) return null;
        return cards.get(index).term;
    }

    public String getDefinition(int index) {
        if (index < 0 || index >= cards.size()) return null;
        return cards.get(index).definition;
    }
}
