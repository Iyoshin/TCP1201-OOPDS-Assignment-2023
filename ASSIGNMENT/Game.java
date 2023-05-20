import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static int determineTrickWinner(Card leadCard, ArrayList<Card> cardsInCenter, Player[] players) {
        Card winningCard = leadCard;
        int index = -1;

        // Determine the winning card among the cards in the center
        for (Card card : cardsInCenter) {
            if (card.getSuit().equals(leadCard.getSuit()) && !winningCard.equals(null)) {
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

    public static boolean isValidMove(Card card, Card leadCard) {
        if ((!card.getSuit().equals(leadCard.getSuit())) && (!card.getRank().equals(leadCard.getRank()))) {
            return false;
        } else {
            return true;
        }
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

    public static void printScore(Player[] player) {
        System.out.print("Score: ");

            for (int i = 0; i < 4; i++) {
                int score = player[i].getScore();

                if (i < 3) {
                    System.out.print(player[i].getName() + " = " + score + " | ");
                } else if (i == 3) {
                    System.out.print(player[i].getName() + " = " + score);
                }
            }
            System.out.println();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean exit = false;
        boolean turnEnd = false;
        boolean gameEnd = false;
        int[] totalScore = new int[4];

        do { // restart loop

            // create deck
            Deck deck = new Deck();
            deck.createDeck();
            deck.shuffleDeck();
            gameEnd = false;

            System.out.println();

            // create player
            Player[] player = new Player[4];

            for (int i = 0; i < 4; i++) {
                ArrayList<Card> handCards = new ArrayList<Card>();
                for (int j = 0; j < 7; j++) {
                    handCards.add(deck.dealCards());
                }   
                player[i] = new Player(handCards, "Player" + (i + 1));
            }
            

            boolean restart = false;

            int trickCounter = 1;
            int currentPlayer = -1;
            int winner = -1;
            while (!gameEnd) { // game loop


                // inherit back the previous round score if exists
                for (int i = 0; i < 4; i++) {
                    player[i].setScore(totalScore[i]);
                }

                // checking if any of the player's hand is empty
                for (int i = 0; i < 4; i++) {
                    if (player[i].handIsEmpty()) {
                        gameEnd = true;
                        winner = i;
                        break;
                    }
                }
                // break if player's hand is empty
                if (gameEnd) {
                    for (int i = 0; i < 4; i++) {
                        player[i].calculateScore();
                        totalScore[i] += player[i].calculateScore();
                    }
                    
                    System.out.println("Winner of this round is: " + player[winner].getName());
                    printScore(player);
                    System.out.print("Do you want to restart? (y/n) ");
                    String ans = input.next();
                    if (ans.equals("y") || ans.equals("Y")) {
                        restart = true;
                    }
                    break;
                }

                Card leadCard = null;
                Center center = new Center();
                int i = 0;
                if (trickCounter == 1) {
                    // since only 1st loop require the deck to determine the 1st player, then the
                    // subsequent trick is determined by the winning player
                    // to prevent the lead card from deck is too large from players' hand, there exists this loop
                    leadCard = deck.dealCards();
                    while (leadCard.getRankValue() > 7){
                        deck.returnCard(leadCard);
                        leadCard = deck.dealCards();
                    }
                    System.out.println("The game has started!");
                    center.addCard(leadCard);
                    printPlayer(player);
                    deck.printDeck();
                    center.printCenter();
                    printScore(player);
                }

                do { // loop for each player

                    if (i == 0 && trickCounter == 1) {
                        currentPlayer = determineFirstPlayer(leadCard);
                    } else if (i == 0 && trickCounter != 1) {
                        currentPlayer = currentPlayer;
                    } else if (currentPlayer != -1 && currentPlayer != 3) { // if not -1 or 3
                        currentPlayer++;
                    } else { // else back to 0
                        currentPlayer = 0;
                    }

                    do { // player input loop

                        turnEnd = false;
                        System.out.print(player[currentPlayer].getName() + " plays: ");
                        String cardName = input.next();
                        System.out.println();

                        if (cardName.equals("s") || cardName.equals("S")) {
                            restart = true;
                            break;
                        }
                        if (cardName.equals("x") || cardName.equals("X")) {
                            exit = true;
                            break;
                        }
                        if ((cardName.equals("d") && !deck.isEmpty()) ||
                                (cardName.equals("D") && !deck.isEmpty())) {
                            Card nextCard = deck.dealCards();
                            while (true) {
                                player[currentPlayer].drawCards(nextCard);
                                if (nextCard.getSuit().equals(leadCard.getSuit())) {
                                    break;
                                }
                                if (nextCard.getRank().equals(leadCard.getRank())) {
                                    break;
                                }
                                if (deck.isEmpty() && !isValidMove(nextCard, leadCard)) {
                                    System.out.println("Deck is empty");
                                    turnEnd = true;
                                    break;
                                }
                                nextCard = deck.dealCards();

                            }
                        }
                        if (cardName.equals("d") && deck.isEmpty()) {
                            System.out.println(player[currentPlayer].getName() + "'s turn is skipped.");
                            break;
                        }

                        if (!cardName.equals("d") && !cardName.equals("x") && !cardName.equals("s")) {

                            Card tempCard = new Card(cardName.substring(1, 2).toUpperCase(), cardName.substring(0, 1));
                            if (trickCounter != 1 && i == 0) {
                                leadCard = tempCard;
                            }
                            if (!player[currentPlayer].isValidCard(tempCard)) {
                                System.out.println("Invalid! Choose a card from your hands."); // check for card, does
                                                                                               // it exists in player
                                                                                               // hands
                            } else if (!isValidMove(tempCard, leadCard)) {
                                System.out.println("Invalid! Choose a card of the same suit/rank as lead card."); // check for player move,
                                                                                                                  // do they play same rank/suit
                            } else {
                                center.addCard(tempCard);
                                player[currentPlayer].playCard(tempCard);
                                turnEnd = true;
                            }

                        }

                        printPlayer(player);
                        deck.printDeck();
                        center.printCenter();
                        printScore(player);

                    } while (!turnEnd);

                    i++;

                    if (!exit && restart) {
                        System.out.println("Game is restarting...");
                        break;
                    }

                    if (exit) {
                        break;
                    }

                } while (i != 4); // loop for 4 players

                if (!exit && !restart) {
                    if (trickCounter != 1) {
                        leadCard = center.getCards().get(0);
                    }
                    int trickWinner = determineTrickWinner(leadCard, center.getCards(), player);
                    System.out.println("The winner of the trick is: " + player[trickWinner].getName());
                    System.out.println();
                    trickCounter++;

                    currentPlayer = trickWinner;
                    printPlayer(player);
                    deck.printDeck();
                    printScore(player);
                }

                if (exit) {
                    break;
                }
            }

            if (gameEnd && !restart) {
                break;
            }

        } while (!exit);

        if (exit) {
            System.out.println("Goodbye.");
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void printCenter() {
        System.out.println("Center: " + cards);
    }
}
