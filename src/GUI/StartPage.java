package GUI;

import Component.GameButton;
import Logic.GameLogic;
import Logic.PlaySound;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class StartPage extends Application {
    private Scene startScene; // Reference to the start scene
    private PlaySound playSound;
    private Stage primaryStage;


    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        playSound = PlaySound.getInstance();
        // Create buttons
        GameButton startButton = new GameButton("Start");
        GameButton howToButton = new GameButton("How To Play",300);
        GameButton  exitButton = new GameButton("Exit");

        //setOnAciton
        startButton.setOnAction(event -> switchToGamePage());

        howToButton.setOnAction(e -> showHowToPage());
        exitButton.setOnAction(e -> {
            playSound.stopPlayStartPageBGMSound();
            System.exit(0);

        });

        //set button layout
        VBox buttonLayout = new VBox(20);
        buttonLayout.setAlignment(Pos.CENTER);

        HBox secondRowButtons = new HBox(20, howToButton,exitButton);
        secondRowButtons.setAlignment(Pos.CENTER);

        buttonLayout.getChildren().addAll(startButton, secondRowButtons);

        //make root
        StackPane root = new StackPane();
        root.getChildren().add(buttonLayout);

        // Background setup
        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("resource/image/StartBG.png", 1280, 720, true, true),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                backgroundSize
        );
        root.setBackground(new Background(backgroundImage));

        startScene = new Scene(root, 1280, 720);
        startScene.getStylesheets().add("GUI/style.css"); // Make sure this path is correct

        gamePrepare();

        primaryStage.setTitle("Arrow Dome");
        primaryStage.setScene(startScene);
//        primaryStage.setResizable(false);
        primaryStage.show();

        //play sound
        playSound.playStartPageBGMSound();

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent t) {
                System.exit(0);

            }
        });
    }
    public void switchToGamePage() {


        playSound.stopPlayStartPageBGMSound();
        playSound.playGamePageBGMSound();
        GamePage gamePage = new GamePage();

        Scene gameScene = new Scene(gamePage, 1280, 720);
        primaryStage.setScene(gameScene);
        primaryStage.show();

    }

    public void showHowToPage() {
        playSound.playStartMenuSound();//play sound
        HowTo howToPage = new HowTo();
        howToPage.setOnBackAction(e -> {
            Stage stage = (Stage) howToPage.getScene().getWindow();
            stage.close();
        });

        Stage howToStage = new Stage();
        howToStage.setScene(new Scene(new StackPane(howToPage), 1280, 720 ));
        howToStage.setScene(new Scene(new StackPane(howToPage), 1280   , 720));
        howToStage.setTitle("How To Play");
        howToStage.show();
    }

    public void gamePrepare(){
        GameLogic.getInstance().setCurrentLives(5);
        GameLogic.getInstance().setScore(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}