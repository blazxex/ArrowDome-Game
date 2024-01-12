package character;

import Logic.KeyGenerator;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;

public class AdvanceRocket extends Rocket implements Accelerator {
    private Random random = new Random();

    public AdvanceRocket() {

        setMaxKey(2);
        randomKey();
        setScore(3);
        setRocketImagePath("resource/image/advanceRocket.png");
    }

    @Override
    public List<KeyCode> randomKey() {
        int numberOfKey = 4+ random.nextInt(getMaxKey());
        return KeyGenerator.generateRandomKeys(numberOfKey);
    }

    @Override
    public void acceleration(){
        this.setMaxSpeed(this.getMaxSpeed()-1);
    }
}
