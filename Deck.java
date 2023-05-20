import java.util.*;


public class Deck{
    public ArrayList<Card> cardList = new ArrayList<Card>();
    public ArrayList<Card> playerHands = new ArrayList<Card>();
    private String rank;
    private String suit;


    public Deck(){
    }

    public void createDeck() {
        for (int i = 0; i < 4; i++) {
            if (i == 0) 
                suit = "c";
            if (i == 1)
                suit = "d";
            if (i == 2)
                suit = "h";
            if (i == 3)
                suit = "s";

            for (int j = 0; j < 13; j++) {
                if (j == 0) 
                    rank = "A";
                if (j == 1) 
                    rank = "2";
                if (j == 2) 
                    rank = "3";
                if (j == 3) 
                    rank = "4";   
                if (j == 4) 
                    rank = "5";
                if (j == 5) 
                    rank = "6";
                if (j == 6) 
                    rank = "7";
                if (j == 7) 
                    rank = "8";
                if (j == 8) 
                    rank = "9";
                if (j == 9) 
                    rank = "X";
                if (j == 10) 
                    rank = "J";
                if (j == 11) 
                    rank = "Q";
                if (j == 12) 
                    rank = "K";   
                    
                    
                cardList.add(new Card(rank, suit));

            }
        }
    }

    public void printDeck() {
        System.out.println("Deck: " + cardList);
    }

    //shuffle deck
    public void shuffleDeck() {
        Collections.shuffle(cardList);
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public Card dealCards() {
        return cardList.remove(0);
    }

    public boolean isEmpty() {
        return cardList.isEmpty();
    }

    public void returnCard(Card card) {
        cardList.add(card);
    }

}
