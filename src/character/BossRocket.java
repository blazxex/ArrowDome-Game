package character;//package character;

import GUI.KeyPane;
import GUI.RocketPane;
import Logic.GameLogic;
import Logic.KeyGenerator;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;

public class BossRocket extends Rocket implements Clearable,Accelerator {
    private Random random = new Random();

    public BossRocket() {
        setMaxKey(3);
        randomKey();
        setScore(5);
        setRocketImagePath("resource/image/BossRocket.png");
    }

    @Override
    public List<KeyCode> randomKey() {
        int numberOfKey =  5+ random.nextInt(getMaxKey());
        return KeyGenerator.generateRandomKeys(numberOfKey);
    }

    @Override
    public void clearAllRockets() {
        RocketPane.getInstance().BossClearRocket();
    }

    @Override
    public void acceleration(){
          this.setMaxSpeed(this.getMaxSpeed()-1);
    }

}
