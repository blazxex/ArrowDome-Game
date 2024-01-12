package GUI;


import Logic.GameLogic;
import Logic.GameOverCallback;
import Logic.PlaySound;
import character.Accelerator;
import character.AdvanceRocket;
import character.BossRocket;
import character.Rocket;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class RocketPane extends Pane implements GameOverCallback {
    private GameLogic gameLogic;
    private List<Rocket> rockets = new CopyOnWriteArrayList<>();
    private Map<Rocket, ImageView> rocketViews = new ConcurrentHashMap<>();
    private Map<Rocket, TranslateTransition> rocketTransitions = new ConcurrentHashMap<>();
    private static RocketPane instance;
    private int threadSleepSet = 2500;
    private int scoreForUpDifficultyLv;
    private PlaySound playSound = new PlaySound();
    private boolean threadStop = false;


    public RocketPane() {
        gameLogic = GameLogic.getInstance();
        startRocketSpawner();
        GameLogic.getInstance().setGameOverCallback(this);
    }

    public static RocketPane getInstance() {
        if (instance == null) {
            instance = new RocketPane();
        }
        return instance;
    }

    public void startRocketSpawner() {
        new Thread(() -> {
            while (true) {
                try {
                    if(!isThreadStop()){
                    Rocket rocket = gameLogic.randomRocketLauncher();
                    Platform.runLater(
                            () -> {
                                spawnRocket(rocket);
                                if(KeyPane.getInstance().getKeySet().isEmpty())KeyPane.getInstance().updateKeyImages();
                                if(scoreForUpDifficultyLv >= 10 ){
                                    setScoreForUpDifficultyLv(0);
                                    threadSleepSet = (int) (threadSleepSet*0.95);
                                }

                            }
                    );

                }
                    Thread.sleep(threadSleepSet);
                } catch (InterruptedException e) {
                    //Thread.currentThread().interrupt();
                    playSound.stopPlayGamePageBGMSound();
                    e.printStackTrace();
                }
            }
        }).start();
    }

   public void spawnRocket(Rocket rocket) {
        Image image = new Image(rocket.getRocketImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(100);
        imageView.setFitWidth(50);

        Random rand = new Random();
        imageView.setLayoutX(rand.nextDouble() * (this.getWidth() - imageView.getFitWidth()));
        imageView.setLayoutY(-5);
       TranslateTransition transition;
       if(rocket instanceof Accelerator){
           ((Accelerator) rocket).acceleration();
            transition = new TranslateTransition(Duration.seconds(rocket.getMaxSpeed()), imageView);

       }
       else{
           transition = new TranslateTransition(Duration.seconds(rocket.getMaxSpeed()), imageView);
       }

        transition.setToY(this.getHeight()-100);

        transition.setOnFinished(e -> rocketHitFloor());
        transition.play();

        this.getChildren().add(imageView);
        rockets.add(rocket);
        rocketViews.put(rocket, imageView);
        rocketTransitions.put(rocket, transition);
        gameLogic.checkOneRocketOnScreen();

    }

    public void bombRocket(int index,boolean hitFloor) {
        if (!rockets.isEmpty()) {
            playSound.playExplosionSound();//play sound
            Rocket rocketToRemove = rockets.remove(index);
            ImageView imageView = rocketViews.remove(rocketToRemove);
            TranslateTransition transition = rocketTransitions.remove(rocketToRemove);
            Platform.runLater(() -> {
                if (transition != null) {
                    transition.stop();
                }
                if (imageView != null) {

                    Image gifImage = new Image("resource/image/explosion.gif");
                    ImageView gifImageView = new ImageView(gifImage);
                    Bounds boundsInParent = imageView.getBoundsInParent();
                    double x = boundsInParent.getMinX();
                    double y = boundsInParent.getMinY();
                    gifImageView.setFitHeight(100); // Set desired height
                    gifImageView.setFitWidth(100);  // Set desired width
                    gifImageView.setX(x); // Set to the same position as the old ImageView
                    gifImageView.setY(y);
                    this.getChildren().remove(imageView);
                    this.getChildren().add(gifImageView);
                    int score = rocketToRemove.getScore();
                    Image scoreImage = selectScoreImage(rocketToRemove.getScore());
                    ImageView scoreImageView = new ImageView(scoreImage);
                    if(!hitFloor){
                        // Implement this method
                        gameLogic.scoreIncrement(rocketToRemove);
                        ScorePane.getInstance().updateScore();
                        if(score==1){
                            scoreImageView.setFitHeight(50);
                            scoreImageView.setFitWidth(50);
                        }
                        if(score==3){
                            scoreImageView.setFitHeight(70);
                            scoreImageView.setFitWidth(70);
                        }
                        if(score==5){
                            scoreImageView.setFitHeight(90);
                            scoreImageView.setFitWidth(90);
                        }

                        scoreImageView.setX(x); // Position near the explosion
                        scoreImageView.setY(y - 50); // Position above the explosion
                        scoreImageView.setOpacity(0); // Start fully transparent
                        this.getChildren().add(scoreImageView);

                        FadeTransition fade = new FadeTransition(Duration.seconds(1), scoreImageView);
                        fade.setFromValue(0);  // Start from fully transparent
                        fade.setToValue(1);    // Fade to fully opaque
                        fade.play();
                    }


                    // Pause for a short duration and then remove the gifImageView and scoreImageView
                    PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Adjust duration as needed
                    pause.setOnFinished(event -> {
                        this.getChildren().remove(gifImageView);

                        this.getChildren().remove(scoreImageView);


                    });
                    pause.play();


                    gameLogic.checkOneRocketOnScreen();
                }
            });


        }
    }



    public Image selectScoreImage(int score) {
        String imagePath;
        if (score == 1) {
            imagePath = "resource/image/score_1.png";
        } else if (score == 3 ) {
            imagePath = "resource/image/score_3.png";
        } else {
            imagePath = "resource/image/score_5.png";
        }
        return new Image(imagePath);
    }
    public void removeFocusRocket(ImageView imageView){

        DropShadow highlight = new DropShadow();
        highlight.setColor(Color.YELLOW);
        highlight.setRadius(10); // Adjust the radius for the size of the glow
        highlight.setSpread(0.5); // Adjust the spread for the intensity of the glow
        if (imageView != null) {
            imageView.setEffect(null);
        }


    }

    public void moveFocusRocketUI(){
        if(getRockets().size()>1){
            Rocket curentRocket = gameLogic.getFocusRocket();
            ImageView currentView = rocketViews.get(curentRocket);
            setFocusRocket(currentView);
        }
    }
    public void rocketHitFloor() {
        if (getRocketViews() != null && !getRocketViews().isEmpty()) {
            bombRocket(0, true);
            GameLogic.getInstance().reduceLife();
            GameLifePane.getInstance().updateLives();
            Rocket rocket = getRocketAtIndex(0);
            if(rocket!=null){
                setFocusRocket(getRocketViews().get(rocket));
                gameLogic.setFocusRocket(0, getRocketAtIndex(0));
            }

        }
    }

    public Rocket getRocketAtIndex(int index) {
        if (index >= 0 && index < rockets.size()) {
            return rockets.get(index);
        } else {
            return null;
        }
    }

    public List<Rocket> getRockets() {
        return rockets;
    }

    public void setRockets(List<Rocket> rockets) {
        this.rockets = rockets;
    }

    public void setFocusRocket(ImageView imageView){
        DropShadow highlight = new DropShadow();
        highlight.setColor(Color.WHITE);
        highlight.setRadius(10);
        highlight.setSpread(0.5); // Adjust the spread for the intensity of the glow

        imageView.setEffect(highlight);
    }


    public void BossClearRocket(){
        for(int i =0; i<5; i++)bombRocket(0,false);
    }


    public void onGameOver() {
        Platform.runLater(() -> {

            int finalScore = GameLogic.getInstance().getScore();
            GameOverPane gameOverPane = new GameOverPane(finalScore,new Stage());

            //Set up the new game
            for(int i = 0; i < 5; i++)bombRocket(0,true);
            setThreadStop(true);

            GameLogic.getInstance().setScore(0);
            GameLogic.getInstance().setCurrentLives(5);
            GameLifePane.getInstance().updateLives();
            ScorePane.getInstance().updateScore();
            threadSleepSet = 2500;
            playSound.stopPlayGamePageBGMSound();


            Scene gameOverScene = new Scene(gameOverPane, 1280  , 720);
            Stage stage = (Stage) this.getScene().getWindow(); // This gets the current stage
            stage.setScene(gameOverScene);
            stage.setTitle("Game Over");
            stage.show();


        });
    }




    public Map<Rocket, ImageView> getRocketViews() {
        return rocketViews;
    }



    public int getScoreForUpDifficultyLv() {
        return scoreForUpDifficultyLv;
    }

    public void setScoreForUpDifficultyLv(int scoreForUpDifficultyLv) {
        this.scoreForUpDifficultyLv = scoreForUpDifficultyLv;
    }

    public void setRocketViews(Map<Rocket, ImageView> rocketViews) {
        this.rocketViews = rocketViews;
    }

    public boolean isThreadStop() {
        return threadStop;
    }

    public void setThreadStop(boolean threadStop) {
        this.threadStop = threadStop;
    }
}
