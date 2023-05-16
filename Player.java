import java.util.*;

public class Player {
    private ArrayList<Card> hands = new ArrayList<Card>();
    private ArrayList<Card> playedCards = new ArrayList<Card>();
    private String name;

    public Player(ArrayList<Card> hands, String  name) {
        this.hands = hands;
        this.name = name;
    }

    public void showPlayerCards() {
        System.out.println(hands);
    }

    public void drawCards(Card card) {
        hands.add(card);
    }

    public int compareCard(Card tempCard) {
        int index = -1;
        for (int i = 0; i < hands.size(); i++) {
            if (tempCard.getRank().equals(hands.get(i).getRank()) && tempCard.getSuit().equals(hands.get(i).getSuit())) {
                index = i;
                break;
            }
        }

        return index;
    }

    // debugging purpose
    // public void showSpecificCard(int i) {
    //     System.out.println(hands.get(i));
    // }
    // debugging purpose   
    // public int noOfCardLeft() {
    //     return hands.size();
    // }


    public void playCard(Card tempCard) {
        hands.remove(compareCard(tempCard));
        playedCards.add(tempCard);
    }


    public Boolean isValidMove(Card card) {
        if(compareCard(card) == -1){
            return false;
        } else {
            return true;
        }
    }

    public String getName() {
        return name;
    }

    public boolean hasPlayedCard(Card card) {
        return playedCards.contains(card);
    }

    public boolean handIsEmpty() {
        return hands.isEmpty();
    }

}
