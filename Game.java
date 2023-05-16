import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static int determineTrickWinner(Card leadCard, ArrayList<Card> cardsInCenter, Player[] players) {
        Card winningCard = leadCard;
        int index = -1;

        // Determine the winning card among the cards in the center
        for (Card card : cardsInCenter) {
            if (card.getSuit().equals(leadCard.getSuit())) {
                if (card.getRankValue() > winningCard.getRankValue()) {
                    winningCard = card;
                }
            }
        }

        // Find the player who played the winning card
        for (int i = 0; i < 4; i++) {
            if (players[i].hasPlayedCard(winningCard)) {
                index = i;
                break;
            }
        }

        return index;
    }

    public static int getWinningPlayerIndex(Player[] players, Player player) {
        int index = -1;
        for (int i = 0; i < 4; i++) {
            if (players[i].getName().equals(player.getName())) {
                index = i;
            } 
        }
        return index;
    }

    public static void printPlayer(Player[] players) {
        for (Player player : players) {
            System.out.print(player.getName() + ": ");
            player.showPlayerCards();
        }
    }

    public static int determineFirstPlayer(Card card) {
        int index = -1;
        if (card.getRank().equals("A") || card.getRank().equals("5") || card.getRank().equals("9")
                || card.getRank().equals("K")) {
            index = 0;
        } else if (card.getRank().equals("2") || card.getRank().equals("6") || card.getRank().equals("X")) {
            index = 1;
        } else if (card.getRank().equals("3") || card.getRank().equals("7") || card.getRank().equals("J")) {
            index = 2;
        } else {
            index = 3;
        }

        return index;

    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;

        do { // restart loop

            // create deck for this round
            Deck deck = new Deck();
            deck.createDeck();
            deck.shuffleDeck();
            deck.printDeck();

            System.out.println();

            // create player
            Player[] player = new Player[4];

            for (int i = 0; i < 4; i++) {
                ArrayList<Card> handCards = new ArrayList<Card>();
                for (int j = 0; j < 7; j++) {
                    handCards.add(deck.dealCards());
                }
                System.out.println("Player" + (i + 1) + " : ");
                player[i] = new Player(handCards, "Player" + (1 + i));
                player[i].showPlayerCards();
            }

            deck.printDeck();

            boolean win = false;
            boolean restart = false;
            int trickCounter = 1;
            int currentPlayer = -1;

            while (!win || !exit) { // game loop

                Card leadCard = null;
                Center center = new Center();

                if (trickCounter == 1) {
                    // since only 1st loop require the deck to determine the 1st player, then the
                    // subsequent trick is determined by the winning player
                    leadCard = deck.dealCards();
                    System.out.println("The game has started! \nThe first lead card is " + leadCard);
                    center.addCard(leadCard);
                } 

                

                // Determine the lead player of 1st trick

                for (int i = 0; i < 4; i++) { // player rounds
                    // first iteration use the method
                    if (i == 0 && trickCounter == 1) {
                        currentPlayer = determineFirstPlayer(leadCard);
                    } else if (i == 0 && trickCounter != 1) {
                        currentPlayer = currentPlayer;
                    } else if (currentPlayer != -1 && currentPlayer != 3) { // if not -1 or 3
                        currentPlayer++;
                    }  else { // else back to 0
                        currentPlayer = 0;
                    }

                    // printPlayer(player);
                    // center.printCenter();
                    System.out.print(player[currentPlayer].getName() + " plays: ");

                    String cardName = input.next();

                    if (cardName.equals("s")) {
                        restart = true;
                        break;
                    } else if (cardName.equals("x")) {
                        exit = true;
                        break;
                    } else if (cardName.equals("d")) {
                        Card nextCard = deck.dealCards();
                        while (true) {
                            player[currentPlayer].drawCards(nextCard);
                            if (nextCard.getSuit().equals(leadCard.getSuit())) {
                                break;
                            }
                            if (nextCard.getRank().equals(leadCard.getRank())) {
                                break;
                            }
                            nextCard = deck.dealCards();
                        }

                        printPlayer(player);
                        center.printCenter();
                        System.out.print(player[currentPlayer].getName() + " plays: ");
                        cardName = input.next();
                    } else {
                        System.out.println();
                        // create a temporary card
                        Card tempCard = new Card(cardName.substring(1, 2), cardName.substring(0, 1));
                        while (!player[currentPlayer].isValidMove(tempCard)) {
                            System.out.println("Card does not exists. Please re-enter the card.");
                            cardName = input.next();
                            tempCard = new Card(cardName.substring(1, 2), cardName.substring(0, 1));
                        }
                        // System.out.println("Card rank : " + tempCard.getRankValue());
                        // System.out.println("Card rank value: " + tempCard.getRankValue());
                        center.addCard(tempCard);
                        player[currentPlayer].playCard(tempCard);
                        printPlayer(player);
                        center.printCenter();

                        
                    }
                }

                if (!exit && !restart) {
                    if (trickCounter != 1) {
                        leadCard = center.getCards().get(0);
                    }
                    int trickWinner = determineTrickWinner(leadCard, center.getCards(), player);
                    System.out.println("The winning player of the trick is: " + player[trickWinner].getName());
                    trickCounter++;
                    
                    currentPlayer = trickWinner;
                }

                if (restart && !exit) {
                    System.out.println("Game is restarting...");
                    trickCounter = 1;
                }

                if (exit) {
                    break;
                }
            }
        } while (!exit);

        if (exit) {
            System.out.println("Goodbye!");
        }

    }
}

class Center {
    private ArrayList<Card> cards;

    public Center() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getTopCard() {
        if (!cards.isEmpty()) {
            return cards.get(cards.size() - 1);
        }
        return null;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void printCenter() {
        System.out.println("Center: " + cards);
    }
}