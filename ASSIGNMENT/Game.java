import java.util.ArrayList;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Game {
    private static Scanner input = new Scanner(System.in);
    private int currentPlayer;
    private int trickCounter;
    private int roundCount;

    public Game() {
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getTrickCounter() {
        return trickCounter;
    }

    public void setTrickCounter(int trickCounter) {
        this.trickCounter = trickCounter;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }

    public void saveFile(Player[] player, Center center, Deck deck) {
        System.out.print("Do you wish to save file? (y/n) ");
        String ans = input.next();
        if (ans.toLowerCase().equals("y")) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
                for (Player players : player) {
                    writer.write(players.getName() + "\n"); // player name

                }
                for (Player players : player) {
                    writer.write(players.getHands() + "\n"); // player hands

                }
                for (Player players : player) {
                    writer.write(players.getScore() + "\n"); // player score

                }
                // System.out.println("the currentPlayer in save: " + getCurrentPlayer());
                // System.out.println("the trickCounter in save: " + getTrickCounter());
                // System.out.println("the roundcount in save: " + getRoundCount());
                writer.write(getCurrentPlayer() + "\n");
                writer.write(getTrickCounter() + "\n");
                writer.write(center.getCenterCards().toString() + "\n");
                writer.write(deck.getCardList().toString() + "\n");
                writer.write(Integer.toString(getRoundCount()));

                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFile(Player[] player, Center center, Deck deck) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("output.txt"));
            int count = 1; // count line
            String name;
            ArrayList<Card> loadCard;
            int score;
            int currentPlayer;
            int roundCount;
            int trickCounter;
            String line;

            while ((line = reader.readLine()) != null) {
                if (count < 5) {
                    // System.out.println(count);
                    name = line;
                    player[count - 1].setName(name);
                }
                if (count >= 5 && count < 9) {
                    // System.out.println(count);
                    String replace = line.replace("[", "");
                    String replace1 = replace.replace("]", "");
                    if (replace1.contains(",")) {
                        loadCard = new ArrayList<>();
                        String replace2 = replace1.replace(" ", "");
                        String[] cardStr = replace2.split(",");
                        for (int i = 0; i < cardStr.length; i++) {
                            Card card = new Card(cardStr[i].substring(1, 2).toUpperCase(), cardStr[i].substring(0, 1));
                            loadCard.add(card);
                        }
                    } else {
                        loadCard = new ArrayList<>();
                        Card card = new Card(replace1.substring(1, 2).toUpperCase(), replace1.substring(0, 1));
                        loadCard.add(card);
                    }
                    player[count - 5].setHands(loadCard);
                }
                if (count >= 9 && count < 13) {
                    score = Integer.parseInt(line);
                    player[count - 9].setScore(score);
                }
                if (count == 13) {
                    currentPlayer = Integer.parseInt(line);
                    setCurrentPlayer(currentPlayer);
                }
                if (count == 14) {
                    trickCounter = Integer.parseInt(line);
                    setTrickCounter(trickCounter);
                }
                if (count == 15) {
                    String replace = line.replace("[", "");
                    String replace1 = replace.replace("]", "");
                    if (replace1.contains(",")) {
                        loadCard = new ArrayList<>();
                        String replace2 = replace1.replace(" ", "");
                        String[] cardStr = replace2.split(",");
                        for (int i = 0; i < cardStr.length; i++) {
                            Card card = new Card(cardStr[i].substring(1, 2).toUpperCase(), cardStr[i].substring(0, 1));
                            loadCard.add(card);
                        }
                    } else {
                        loadCard = new ArrayList<>();
                        Card card = new Card(replace1.substring(1, 2).toUpperCase(), replace1.substring(0, 1));
                        loadCard.add(card);
                    }

                    center.setCenterCards(loadCard);
                }
                if (count == 16) {
                    String replace = line.replace("[", "");
                    String replace1 = replace.replace("]", "");
                    if (replace1.contains(",")) {
                        loadCard = new ArrayList<>();
                        String replace2 = replace1.replace(" ", "");
                        String[] cardStr = replace2.split(",");
                        for (int i = 0; i < cardStr.length; i++) {
                            Card card = new Card(cardStr[i].substring(1, 2).toUpperCase(), cardStr[i].substring(0, 1));
                            loadCard.add(card);
                        }
                    } else {
                        loadCard = new ArrayList<>();
                        Card card = new Card(replace1.substring(1, 2).toUpperCase(), replace1.substring(0, 1));
                        loadCard.add(card);
                    }

                    deck.setCardList(loadCard);
                }
                if (count == 17) {
                    roundCount = Integer.parseInt(line);
                    setRoundCount(roundCount);
                }

                count++;
                // System.out.println(line);

            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

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

    public void mainGame() {
        boolean exit = false;
        boolean turnEnd = false;
        boolean gameEnd = false;
        boolean loadGame = false;
        boolean restart = false;
        int[] totalScore = new int[4];

        System.out.print("Do you wish to load saved game? (y/n) ");
        String ans = input.next();
        if (ans.toLowerCase().equals("y")) {
            loadGame = true;
        }

        do { // restart loop
            

            if (restart) {
                System.out.print("Do you wish to load saved game? (y/n) ");
                ans = input.next();
                if (ans.toLowerCase().equals("y")) {
                    loadGame = true;
                }

            }

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

            int trickCounter = 1;
            int currentPlayer = -1;
            int winner = -1;
            restart = false;

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
                    ans = input.next();
                    if (ans.toLowerCase().equals("y")) {
                        restart = true;
                    } else {
                        exit = true;
                    }
                    break;
                }

                Card leadCard = null;
                Center center = new Center();
                int i = 1;

                if (trickCounter == 1 && !loadGame) {
                    // since only 1st loop require the deck to determine the 1st player, then the
                    // subsequent trick is determined by the winning player
                    // to prevent the lead card from deck is too large from players' hand, there
                    // exists this loop
                    System.out.println("The game has started!");
                    leadCard = deck.dealCards();
                    while (leadCard.getRankValue() > 7) {
                        deck.returnCard(leadCard);
                        leadCard = deck.dealCards();
                    }

                    center.addCard(leadCard);
                    System.out.println(" ~~~~ TRICK #" + trickCounter + " ~~~~ ");
                    printPlayer(player);
                    deck.printDeck();
                    center.printCenter();
                    printScore(player);
                }

                do { // loop for each player
                    if (!loadGame) {
                        if (i == 1 && trickCounter == 1) {
                            currentPlayer = determineFirstPlayer(leadCard);
                        } else if (i == 1 && trickCounter != 1) {
                            getCurrentPlayer();
                        } else if (currentPlayer != -1 && currentPlayer != 3) { // if not -1 or 3
                            currentPlayer++;
                        } else { // else back to 0
                            currentPlayer = 0;
                        }
                    } else {
                        if (i == 1 && trickCounter != 1) {
                            // System.out.println("error 2");
                            getCurrentPlayer();
                        } else if (currentPlayer != -1 && currentPlayer != 3) { // if not -1 or 3
                            // System.out.println("error 3");
                            currentPlayer++;
                        } else { // else back to 0
                            // System.out.println("error 4");
                            currentPlayer = 0;
                        }
                    }

                
                    setTrickCounter(trickCounter);
                    setRoundCount(i);
                    setCurrentPlayer(currentPlayer);

                    if (loadGame) {
                        loadFile(player, center, deck);
                        printPlayer(player);
                        deck.printDeck();
                        center.printCenter();
                        printScore(player);

                        if (getTrickCounter() == 1) {
                            leadCard = center.getCenterCards().get(0);
                        } 
                        currentPlayer = getCurrentPlayer();
                        trickCounter = getTrickCounter();
                        roundCount = getRoundCount();
                    }

                    // System.out.println("the currentPlayer in game: " + getCurrentPlayer());
                    // System.out.println("the trickCounter in game: " + getTrickCounter());
                    // System.out.println("the roundcount in game: " + getRoundCount());
                    do { // player input loop

                        turnEnd = false;
                        System.out.print(player[getCurrentPlayer()].getName() + " plays: ");
                        String cardName = input.next();
                        System.out.println();

                        if (cardName.toLowerCase().equals("s")) {
                            System.out.print("Do you want to restart? (y/n) ");
                            ans = input.next();
                            if (ans.toLowerCase().equals("y")) {
                                restart = true;
                                saveFile(player, center, deck);
                                break;
                            } else {
                                System.out.println("The game continues.");
                                System.out.println();
                            }
                        }
                        if (cardName.toLowerCase().equals("x")) {
                            System.out.print("Do you want to quit? (y/n) ");
                            ans = input.next();
                            if (ans.toLowerCase().equals("y")) {
                                exit = true;
                                saveFile(player, center, deck);
                                break;
                            } else {
                                System.out.println("The game continues.");
                                System.out.println();
                            }
                        }
                        if ((cardName.toLowerCase().equals("d") && !deck.isEmpty())) {
                            Card nextCard = deck.dealCards();
                            while (true) {
                                player[getCurrentPlayer()].drawCards(nextCard);
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
                        if (cardName.toLowerCase().equals("d") && deck.isEmpty()) {
                            System.out.println(player[getCurrentPlayer()].getName() + "'s turn is skipped.");
                            break;
                        }

                        if (!cardName.toLowerCase().equals("d") && !cardName.toLowerCase().equals("x")
                                && !cardName.toLowerCase().equals("s")) {

                            Card tempCard = new Card(cardName.substring(1, 2).toUpperCase(), cardName.substring(0, 1));
                            if (trickCounter != 1 && i == 1) {
                                leadCard = tempCard;
                            }
                            if (!player[getCurrentPlayer()].isValidCard(tempCard)) {
                                System.out.println("Invalid! Choose a card from your hands."); // check for card, does
                                                                                               // it exists in player
                                                                                               // hands
                            } else if (!isValidMove(tempCard, leadCard)) {
                                System.out.println("Invalid! Choose a card of the same suit/rank as lead card."); // check
                                                                                                                  // for
                                                                                                                  // player
                                                                                                                  // move,
                                                                                                                  // do
                                                                                                                  // they
                                                                                                                  // play
                                                                                                                  // same
                                                                                                                  // rank/suit
                            } else {
                                center.addCard(tempCard);
                                player[getCurrentPlayer()].playCard(tempCard);
                                turnEnd = true;

                            }

                        }

                        System.out.println(" ~~~~ TRICK #" + trickCounter + " ~~~~ ");
                        printPlayer(player);
                        deck.printDeck();
                        center.printCenter();
                        printScore(player);
                        loadGame = false;
                    } while (!turnEnd);

                    i++;

                    if (!exit && restart) {
                        break;
                    }

                    if (exit) {
                        break;
                    }

                } while (i != 5); // loop for 4 players

                if (!exit && restart) {
                    System.out.println("Game is restarting...");
                    System.out.println();
                    break;
                }

                if (!exit && !restart) {

                    if (trickCounter != 1) {
                        leadCard = center.getCenterCards().get(0);
                    }
                    int trickWinner = determineTrickWinner(leadCard, center.getCenterCards(), player);
                    System.out.println("The winner of the trick is: " + player[trickWinner].getName());
                    System.out.println();
                    trickCounter++;

                    currentPlayer = trickWinner;
                    setCurrentPlayer(currentPlayer);
                    System.out.println(" ~~~~ TRICK #" + trickCounter + " ~~~~ ");
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
