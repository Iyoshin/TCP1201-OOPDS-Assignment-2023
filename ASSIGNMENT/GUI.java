import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {

    private Deck deck;
    private Player player1;
    private Player player2;
    private Player player3;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Set up the game play scene
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 1200, 720);
        stage.setResizable(false);

        // Set background for the game play
        Image icon = new Image("IMG/Game logo.png");
        Image bg = new Image("IMG/Background.png");
        stage.getIcons().add(icon);
        BackgroundImage background = new BackgroundImage(bg, null, null, null, null);
        Background bg1 = new Background(background);

        pane.setBackground(bg1);
        stage.setTitle("GO-BOOM!!");

        // Generate a deck
        deck = new Deck();
        deck.createDeck();
        deck.shuffleDeck();

        // Distribute cards to players
        player1 = new Player(new ArrayList<>(), "Player 1");
        player2 = new Player(new ArrayList<>(), "Player 2");
        player3 = new Player(new ArrayList<>(), "Player 3");

        for (int i = 0; i < 7; i++) {
            player1.drawCards(deck.dealCards());
            player2.drawCards(deck.dealCards());
            player3.drawCards(deck.dealCards());
        }

        // Display cards on hand for each player
        VBox playersCards = new VBox();
        playersCards.setSpacing(20);
        playersCards.setPadding(new Insets(30));

        displayPlayerCards(player1, playersCards);
        displayPlayerCards(player2, playersCards);
        displayPlayerCards(player3, playersCards);

        // Create and display the center card
        StackPane centerCardPane = new StackPane();
        centerCardPane.setPadding(new Insets(30));
        centerCardPane.setPrefWidth(200); 
        centerCardPane.setPrefHeight(200);
        centerCardPane.setTranslateX(450);
        centerCardPane.setTranslateY(-500);

        Card centerCard = deck.dealCards();
        Image centerCardImage = new Image("IMG/" + centerCard.toString() + ".png");
        ImageView centerCardImageView = new ImageView(centerCardImage);
        centerCardImageView.setFitWidth(100);
        centerCardImageView.setPreserveRatio(true);

        Text centerText = new Text("CENTER");
        Font font = Font.loadFont(getClass().getResourceAsStream("Font/Game-Font.ttf"), 50);
        centerText.setFont(font);
        centerText.setFill(Color.GREEN);
        centerText.setTranslateY(-90);
        centerText.setTranslateX(0);

        centerCardPane.getChildren().addAll(centerCardImageView, centerText);
       

        pane.getChildren().addAll(playersCards, centerCardPane);
     

        stage.setScene(scene);
        stage.show();
    }

private void displayPlayerCards(Player player, VBox playersCards) {
        VBox playerCards = new VBox();
        playerCards.setSpacing(5);

        Text playerName = new Text(player.getName());
        //Text playerScore = new Text("Score: " + player.getScore());
        //Text playerHand = new Text("Cards in hand:");
        Font font = Font.loadFont(getClass().getResourceAsStream("Font/Game-Font.ttf"),45);
        playerName.setFont(font);
        playerName.setFill(Color.BROWN);

        HBox cardImages = new HBox();
        cardImages.setSpacing(10);

        for (Card card : player.getHands()) {
            Image image = new Image("IMG/" + card.toString() + ".png");
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);
            cardImages.getChildren().add(imageView);
          
        }
      
        playerName.setTranslateX(80);
        //playerScore.setTranslateX(100);
        //playerHand.setTranslateX(100);
        cardImages.setTranslateX(80);

        playerCards.getChildren().addAll(playerName,cardImages);
        playersCards.getChildren().add(playerCards);
    }
}