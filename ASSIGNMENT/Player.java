import java.util.*;

public class Player {
    private ArrayList<Card> hands = new ArrayList<Card>();
    private ArrayList<Card> playedCards = new ArrayList<Card>();
    private String name;
    private int score;

    public Player(ArrayList<Card> hands, String name) {
        this.hands = hands;
        this.name = name;
        this.score = 0;
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
            if (tempCard.getRank().equals(hands.get(i).getRank())
                    && tempCard.getSuit().equals(hands.get(i).getSuit())) {
                index = i;
                break;
            }
        }

        return index;
    }

    // debugging purpose
    // public void showSpecificCard(int i) {
    // System.out.println(hands.get(i));
    // }
    // debugging purpose
    // public int noOfCardLeft() {
    // return hands.size();
    // }

    public void playCard(Card tempCard) {
        hands.remove(compareCard(tempCard));
        playedCards.add(tempCard);
    }

    public Boolean isValidCard(Card card) {
        if (compareCard(card) == -1) {
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

    // REMAINING CARDS
    public ArrayList<Card> getHands() {
        return hands;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int calculateScore() {
        int score = 0;
        for (Card card : hands) {
            if (!hands.equals(null)) {
                int rankValue = card.getRankValue();
                // ACE, 1
                if (rankValue == 13) {
                    score += 1;
                }
                // 10,J,Q,K
                else if (rankValue >= 9) {
                    score += 10;
                }
                // 2-9
                else {
                    score += rankValue + 1;
                }
            }
        }
        setScore(score);
        return score;
    }

}