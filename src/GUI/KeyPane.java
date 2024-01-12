package GUI;

import Logic.GameLogic;
import Logic.PlaySound;
import character.BossRocket;
import character.Clearable;
import character.Rocket;
import javafx.geometry.Pos;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

import javafx.scene.paint.Color;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;

public class KeyPane extends HBox {
    private RocketPane rocketPane;
    private int keyIndex;
    private GameLogic gameLogic;
    private List<KeyCode> keySet;
    private PlaySound playSound = new PlaySound();
    private static KeyPane instance = new KeyPane();



    public KeyPane() {
        this.rocketPane = RocketPane.getInstance();
        this.playSound = PlaySound.getInstance();
        this.keySet = new ArrayList<>();
        gameLogic = GameLogic.getInstance(); // Initialize gameLogic

        this.setOnKeyPressed(event -> handleKeyPress(event.getCode())); // Set key press handler
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
        this.setPrefHeight(100);
        this.setBackground(new Background(new BackgroundFill(Color.web("348bad"), new CornerRadii(10), Insets.EMPTY)));
        this.setFocusTraversable(true); // Make KeyPane focusable
        this.requestFocus(); // Request focus
    }

    public static KeyPane getInstance() {
        if (instance == null) {
            instance = new KeyPane();
        }
        return instance;
    }

    public void updateKeyImages() {
        this.getChildren().clear();
        keySet.clear();

        Rocket rocket = rocketPane.getRocketAtIndex(gameLogic.getFocusRocketIndex());
        if (rocket != null) {
            List<KeyCode> newKeys = rocket.randomKey(); // Get random keys from the rocket

            keySet.addAll(newKeys); // Update the keySet
            setKeyIndex(0);
            for (KeyCode keyCode : newKeys) {
                this.getChildren().add(createImageViewForKeyCode(keyCode)); // Add arrow images
            }
        }
    }

    private void handleKeyPress(KeyCode pressedKey) {
        if(pressedKey == KeyCode.TAB){
            gameLogic.moveFocusRocket();
            rocketPane.moveFocusRocketUI();
            updateKeyImages();
        }
        else if (keyIndex < keySet.size() && pressedKey == keySet.get(keyIndex)) {
            playSound.playKeyClickSound();  //play sound
            try {
                ImageView imageView = (ImageView) this.getChildren().get(keyIndex);
                imageView.setEffect(null); // Remove grayscale effect
                setKeyIndex(keyIndex + 1); // Move to the next key

            } catch (ClassCastException e) {
                // Handle the case where the object is not an ImageView
                System.err.println("Error: Object is not an ImageView.");

            } catch (IndexOutOfBoundsException e) {
                // Handle the case where keyIndex is out of bounds
                System.err.println("Error: Index out of bounds.");
            }
        }
        else if(keyIndex == keySet.size() && pressedKey == KeyCode.SPACE){   //correctCase
            playSound.playSpaceCorrectSound();//play sound
            Rocket rocket = rocketPane.getRocketAtIndex(gameLogic.getFocusRocketIndex());
            if(rocket instanceof BossRocket){
                ((BossRocket) rocket).clearAllRockets();  //use clearable for BOSS
            }
            else{
                RocketPane.getInstance().bombRocket(gameLogic.getFocusRocketIndex(),false);
            }
            updateKeyImages();
            setKeyIndex(0);
        }
        // All keys have been pressed correctly
        else {
            playSound.playWrongSound();//play sound
//            rocketPane.spawnRocket(new BasicRocket());
            updateKeyImages(); // Reset the game if wrong key is pressed
            setKeyIndex(0); // Reset key index

        }




    }


    private ImageView createImageViewForKeyCode(KeyCode keyCode) {
        String imagePath = getImagePathForKeyCode(keyCode);
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitHeight(90);
        imageView.setFitWidth(90);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        imageView.setEffect(colorAdjust);

        return imageView;
    }

    private String getImagePathForKeyCode(KeyCode keyCode) {
        switch (keyCode) {
            case DOWN: return "resource/image/down.png";
            case UP: return "resource/image/top.png";
            case LEFT: return "resource/image/left.png";
            case RIGHT: return "resource/image/right.png";
            default: throw new IllegalStateException("Unexpected value: " + keyCode);
        }
    }

    public int getKeyIndex() {
        return keyIndex;
    }


    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public List<KeyCode> getKeySet() {
        return keySet;
    }

    public void setKeySet(List<KeyCode> keySet) {
        this.keySet = keySet;
    }
}
