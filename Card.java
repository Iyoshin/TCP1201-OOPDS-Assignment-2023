public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    public int getRankValue() {
        int rankValue = -1;
        switch (rank) {
            case "2":
            rankValue = 1;
            break;

            case "3":
            rankValue = 2;
            break;

            case "4":
            rankValue = 3;
            break;

            case "5":
            rankValue = 4;
            break;

            case "6":
            rankValue = 5;
            break;

            case "7":
            rankValue = 6;
            break;

            case "8":
            rankValue = 7;
            break;

            case "9":
            rankValue = 8;
            break;

            case "X":
            rankValue = 9;
            break;

            case "J":
            rankValue = 10;
            break;

            case "Q":
            rankValue = 11;
            break;

            case "K":
            rankValue = 12;
            break;
            
            case "A":
            rankValue = 13;
            break;
        }
        

        return rankValue;
    }


    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String toString() {
        return suit + rank;
    }
}
