package character;

import javafx.scene.input.KeyCode;

import java.util.List;

public abstract class Rocket {


    protected int maxKey;
    protected int maxSpeed = 5;
    protected int score;
    protected String rocketImagePath;
    private boolean bombed = false;



    public abstract List<KeyCode> randomKey();

    public String getRocketImagePath() {
        return rocketImagePath;
    }


    public int getMaxKey() {
        return maxKey;
    }

    public void setMaxKey(int maxKey) {
        this.maxKey = maxKey;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setRocketImagePath(String rocketImagePath) {
        this.rocketImagePath = rocketImagePath;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }



}
