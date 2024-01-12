package GUI;

import Logic.GameLogic;
import Logic.PlaySound;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;


public class GamePage extends BorderPane{


    public GamePage() {
        try {
            KeyPane keyPane = KeyPane.getInstance();
            keyPane.updateKeyImages();
            this.setBottom(keyPane);

            RocketPane rocketPane = RocketPane.getInstance();
            rocketPane.setThreadStop(false);
            this.setCenter(rocketPane);

            GameLifePane gameLifePane = GameLifePane.getInstance();
            ScorePane scorePane = ScorePane.getInstance();



            // Set top container
            HBox topContainer = new HBox();
            topContainer.setPadding(new Insets(0));
            Region filler = new Region();
            HBox.setHgrow(filler, Priority.ALWAYS);
            topContainer.getChildren().addAll(gameLifePane, filler, scorePane);
            BorderPane.setMargin(topContainer, new Insets(10, 10, 10, 10));
            this.setTop(topContainer);

            // Set background image
            BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true);
            BackgroundImage myBI = new BackgroundImage(new Image("resource/image/background.png", 1280, 720, false, true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    backgroundSize);
            this.setBackground(new Background(myBI));



        } catch (Exception e) {

            e.printStackTrace();
        }
    }





}


