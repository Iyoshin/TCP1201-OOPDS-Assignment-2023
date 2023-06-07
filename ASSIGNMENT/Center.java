import java.util.ArrayList;

public class Center {

    private ArrayList<Card> cards;

    public Center() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public ArrayList<Card> getCenterCards() {
        return cards;
    }

    public void setCenterCards(ArrayList<Card> centerCard) {
        this.cards = centerCard;
    }

    public void clearCenter() {
        cards.clear();
    }

    public void printCenter() {
        System.out.println("Center: " + cards);
    }

}
