package GUI;

import Component.TwoColumn;
import Logic.PlaySound;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class HowTo extends Pane {

    private EventHandler<ActionEvent> onBackAction;
    private PlaySound playSound ;

    public HowTo() {
        this.playSound =  PlaySound.getInstance();
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(20, 20, 20, 20));



        Image image = new Image("resource/image/backButton.png");
        ImageView backImg = new ImageView(image);
        backImg.setFitHeight(30);
        backImg.setFitWidth(30);
        backImg.setCursor(Cursor.HAND);

        backImg.setOnMouseClicked(event -> {

            if (onBackAction != null) {
                playSound.playBackSound();//play sound
                onBackAction.handle(new ActionEvent());
            }
        });



        Label gameIntro = new Label("In a world at the brink of destruction, only the swiftest agents can turn \nthe tide with the Arrow Dome. Are you quick enough to save the world?");
        gameIntro.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 25));
        Label title = new Label("How To Play");
        title.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        Label gameName = new Label("Arrow Dome");

        VBox topContainer = new VBox(backImg);
        topContainer.setAlignment(Pos.TOP_LEFT);

        TwoColumn arrowRight = new TwoColumn("resource/image/right.png","Press the Right Arrow Key",true,false);
        TwoColumn arrowLeft = new TwoColumn("resource/image/left.png","Press the Left Arrow Key",true,false);
        TwoColumn arrowUp = new TwoColumn("resource/image/top.png","Press the Top Arrow Key",true,false);
        TwoColumn arrowDown = new TwoColumn("resource/image/down.png","Press the Down Arrow Key",true,false);
        TwoColumn spaceBar= new TwoColumn("resource/image/spaceBar.png","Press the Space Bar to blast a rocket or get a new key.");
        TwoColumn tab = new TwoColumn("resource/image/tabButton.png","Press Tab to move to the next rocket");

        TwoColumn normalMissile = new TwoColumn("resource/image/rocket.png","Basic Rocket: You will get 1 point for each of these rockets.",false,true);
        TwoColumn advanceMissile = new TwoColumn("resource/image/advanceRocket.png","Advance Rocket: You will get 3 points for each of these rockets.",false,true);
        TwoColumn bossMissile = new TwoColumn("resource/image/BossRocket.png","Boss Rocket: You will get 5 points for each of these rockets, \nand it will blast all rockets from the field.",false,true);

        TwoColumn heartSystem = new TwoColumn(
                "resource/image/heartSystem.png",
                "You will start with 5 hearts. If a rocket hits the floor, you will lose 1 heart.\nIf you run out of hearts, the game is over."
        );

        Label  intensity = new Label("Every time you reach a score of 10, the difficulty increases.");
        intensity.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 30));
        intensity.setAlignment(Pos.CENTER);





        layout.getChildren().addAll(
                topContainer, title,gameIntro,arrowRight, arrowLeft, arrowUp, arrowDown, spaceBar, tab,
                normalMissile, advanceMissile, bossMissile, heartSystem, intensity
        );

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // Bind the size of the ScrollPane to the size of this Pane
        scrollPane.prefWidthProperty().bind(this.widthProperty());
        scrollPane.prefHeightProperty().bind(this.heightProperty());

        // Set the ScrollPane as the child of this Pane
        this.getChildren().add(scrollPane);
    }



    public void setOnBackAction(EventHandler<ActionEvent> onBackAction) {
        this.onBackAction = onBackAction;
    }

}
