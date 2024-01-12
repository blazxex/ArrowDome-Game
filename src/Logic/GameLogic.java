package Logic;

import GUI.KeyPane;
import GUI.RocketPane;
import GUI.StartPage;
import character.AdvanceRocket;
import character.BasicRocket;
import character.BossRocket;
import character.Rocket;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.List;
import java.util.Random;

public class GameLogic {
    private int score = 0;
    private static GameLogic instance;
    private static final Random random = new Random();
    private static final int MAX_LIVES = 5;
    private int currentLives = MAX_LIVES;
    private Pair<Integer, Rocket> focusRocket = new Pair<>(0, new BasicRocket());

    private GameOverCallback gameOverCallback;

    public static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }
    public void setGameOverCallback(GameOverCallback callback) {
        this.gameOverCallback = callback;
    }



    public void checkGameOver() {
        if (currentLives <= 0) {
            if (gameOverCallback != null) {
                gameOverCallback.onGameOver();
            }
        }
    }

    public Rocket randomRocketLauncher() {
        Rocket rocket;

        if (score < 10) {
            rocket = new BasicRocket();
        } else if (score < 25) {
            rocket = random.nextBoolean() ? new BasicRocket() : new AdvanceRocket();
        } else {
            int choice = random.nextInt(3);
            if (choice == 0) {
                rocket = new BasicRocket();
            } else if (choice == 1) {
                rocket = new AdvanceRocket();
            } else {
                rocket = new BossRocket();
            }
        }
        return rocket;
    }

    public void moveFocusRocket(){
        int rocketSize = RocketPane.getInstance().getRockets().size();
        if(rocketSize>1){
            Rocket currentRocket = getFocusRocket();
            ImageView currentRocketView = RocketPane.getInstance().getRocketViews().get(currentRocket);
            int nextRocketIndex = (getFocusRocketIndex()+1)%rocketSize;
            Rocket nextRocket = RocketPane.getInstance().getRocketAtIndex(nextRocketIndex);
            setFocusRocket(nextRocketIndex,nextRocket);
            RocketPane.getInstance().removeFocusRocket(currentRocketView);
            RocketPane.getInstance().moveFocusRocketUI();
        }
    }

    public void scoreIncrement(Rocket rocket){
        int RocketScore = rocket.getScore();
        if(RocketScore<0){
            RocketScore= 0;
        }
        setScore(getScore()+RocketScore);
        RocketPane.getInstance().setScoreForUpDifficultyLv(RocketPane.getInstance().getScoreForUpDifficultyLv() + RocketScore);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentLives() {
        return currentLives;
    }

    public void setCurrentLives(int currentLives) {
        if(currentLives<0){
            currentLives = 0;
        }
        this.currentLives = currentLives;
    }
    public void reduceLife(){
        int currentLife = getCurrentLives();
        setCurrentLives(currentLife-1);
        checkGameOver();
    }
    public void setFocusRocket(int index, Rocket rocket) {
        this.focusRocket = new Pair<>(index, rocket);

    }
    public void checkOneRocketOnScreen(){
        RocketPane rocketPane = RocketPane.getInstance();

       if(rocketPane.getRockets().size()==1){
            Rocket rocket = rocketPane.getRocketAtIndex(0);
            setFocusRocket(0,rocket);
            rocketPane.setFocusRocket(rocketPane.getRocketViews().get(rocket));
            setFocusRocket(0,rocket);
        }
    }

    public int getFocusRocketIndex(){
        return focusRocket.getKey();
    }

    public Rocket getFocusRocket(){
        return focusRocket.getValue();
    }




}
