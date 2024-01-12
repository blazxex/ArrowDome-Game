package Component;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TwoColumn extends HBox {
    public TwoColumn(String imagePath, String text,boolean isSquare,boolean isRocket) {
        ImageView imageView;
        try {
            imageView = new ImageView(new Image(imagePath));
        } catch (Exception e) {
            imageView = new ImageView();
            System.err.println("Error loading image: " + e.getMessage());
        }
        Text newText = new Text(text);
        initialize(imageView,newText,isSquare,isRocket);
    }
    public TwoColumn(String imagePath, String text) {
        ImageView imageView = new ImageView(new Image(imagePath));
        Text newText = new Text(text);

        initialize(imageView,newText,false,false);
    }


    private void initialize(ImageView imageView, Text text,boolean isSquare,boolean isRocket) {


        VBox imageBox = new VBox();
        if(isSquare){
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
        }
        else if(isRocket){
            imageView.setFitWidth(50);
            imageView.setFitHeight(100);
        }
        else{
            imageView.setFitWidth(120);
            imageView.setFitHeight(50);
        }
        imageBox.setPrefHeight(70);
        imageBox.setPrefWidth(150);
        imageBox.setAlignment(Pos.CENTER);

        imageBox.getChildren().add(imageView);
        imageBox.setSpacing(10); // Adjust spacing as needed

        VBox textBox = new VBox();
        text.setFont(Font.font("Orbitron", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 20));
        textBox.getChildren().add(text);
        textBox.setSpacing(10); // Adjust spacing as needed
        textBox.setAlignment(Pos.CENTER);


        this.getChildren().addAll(imageBox, textBox);

        // Optional: Set alignment and spacing for the HBox
        this.setSpacing(20); // Adjust spacing as needed
        this.setAlignment(Pos.CENTER_LEFT);




    }
}
