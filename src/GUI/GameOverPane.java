package GUI;

import Component.GameButton;
import Logic.PlaySound;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverPane extends VBox {
    private PlaySound playSound;
    public GameOverPane(int finalScore, Stage primaryStage) {
        super(10);
        setAlignment(Pos.CENTER);
        this.playSound = PlaySound.getInstance();

        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 35));
        gameOverText.setFill(Color.WHITE);

        Text scoreText = new Text("Your score: " + finalScore);
        scoreText.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 25));
        scoreText.setFill(Color.WHITE);

        GameButton exitButton = new GameButton("Exit");
        exitButton.setOnAction(e -> {
            playSound.stopPlayStartPageBGMSound();
            System.exit(0);
        });

        GameButton backToHomeButton = new GameButton("Back",250);
        backToHomeButton.setOnAction(e -> {
            playSound.playBackSound();
            Stage stage = (Stage) this.getScene().getWindow();
            stage.close();
            StartPage startPage = new StartPage();
            startPage.start(primaryStage);

        });
        HBox buttonRow = new HBox(20, backToHomeButton,exitButton);
        buttonRow.setAlignment(Pos.CENTER);

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("resource/image/GameOver.png", 1280, 720, true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        this.setBackground(new Background(backgroundImage));

        getChildren().addAll(gameOverText, scoreText, buttonRow);
        this.setAlignment(Pos.CENTER);
        this.getStylesheets().add("GUI/style.css");
    }
}
