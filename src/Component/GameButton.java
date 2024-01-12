package Component;

import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class GameButton extends Button {

    public GameButton(String text,int width) {
        super(text);
        initialize(width);
    }
    public GameButton(String text) {
        super(text);
        initialize(200);
    }

    private void initialize(int width) {
        // Apply the CSS class
        this.setPrefWidth(width);
        this.setPrefHeight(60);


        this.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
        this.setMaxSize(Button.USE_COMPUTED_SIZE, Button.USE_COMPUTED_SIZE);
        this.setOnMouseEntered(e -> this.setCursor(Cursor.HAND));
        this.setOnMouseExited(e -> this.setCursor(Cursor.DEFAULT));
        this.getStyleClass().add("button");

    }
}

