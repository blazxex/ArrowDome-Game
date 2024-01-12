package GUI;


 import Logic.GameLogic;
 import javafx.scene.image.Image;
 import javafx.scene.layout.HBox;
 import javafx.scene.layout.Pane;
 import javafx.scene.control.Label;
 import javafx.scene.image.ImageView;
 import javafx.scene.shape.Rectangle;
 import javafx.geometry.Insets;
 import javafx.geometry.Pos;
 import javafx.geometry.Insets;
 import javafx.scene.text.Font;
 import javafx.scene.text.FontPosture;
 import javafx.scene.text.FontWeight;

public class ScorePane extends HBox {
    private int score = 0;
    private Label scoreLabel;
    private ImageView imageView;
    private static ScorePane instance;

    public ScorePane() {

        // Set up the HBox for ImageView
        super(10); // Spacing between children
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        this.setOpacity(0.7);

        HBox imageBox = new HBox();
        imageView = new ImageView(new Image("resource/image/rocketScore.png"));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        imageBox.getChildren().add(imageView);

        // Set up the HBox for the score
        HBox scoreBox = new HBox();
        scoreLabel = new Label("0");
        scoreLabel.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        scoreBox.getChildren().add(scoreLabel);

        this.getChildren().addAll(imageBox, scoreBox);


        Rectangle background = new Rectangle();
        background.setArcWidth(30.0); // Rounded corner width
        background.setArcHeight(30.0); // Rounded corner height
        background.setOpacity(0.7);
        this.getChildren().add(background);

        // Set padding and spacing
        this.setPadding(new Insets(10, 10, 10, 10));
        imageBox.setPadding(new Insets(5, 5, 5, 5));
        scoreBox.setPadding(new Insets(5, 5, 5, 5));
    }

    public static ScorePane getInstance() {
        if (instance == null) {
            instance = new ScorePane();
        }
        return instance;
    }
    public void updateScore() {
        score = GameLogic.getInstance().getScore();
        scoreLabel.setText( Integer.toString(score));
    }

}
