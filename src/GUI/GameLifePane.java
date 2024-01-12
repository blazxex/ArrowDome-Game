package GUI;

import Logic.GameLogic;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;

public class GameLifePane extends HBox {
    private static GameLifePane instance;

    public GameLifePane() {
        this.setSpacing(5); // Spacing between hearts
        this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-background-radius: 10;");
        this.setPrefHeight(50);
        this.setPrefWidth(200); // Adjusted the width to ensure sufficient space for hearts
        this.setAlignment(Pos.CENTER); // Align children to the center

        updateLives();
    }

    public static GameLifePane getInstance() {
        if (instance == null) {
            instance = new GameLifePane();
        }
        return instance;
    }

    public void updateLives() {
        this.getChildren().clear();
        int currentLives = GameLogic.getInstance().getCurrentLives();
        for (int i = 0; i < 5; i++) {
            ImageView heart;
            if (i < currentLives) {
                heart = setUpImage("resource/image/heart.png");
            } else {
                heart = setUpImage("resource/image/removeHeart.png"); // Placeholder for empty heart
            }
            this.getChildren().add(heart);
        }
    }

    public ImageView setUpImage(String imagePath){
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        return imageView;
    }
}
