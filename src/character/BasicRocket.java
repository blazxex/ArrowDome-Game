package character;

import Logic.KeyGenerator;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Random;

public class BasicRocket extends Rocket {
    private Random random = new Random();

    public BasicRocket(int ) {

        setMaxKey(1);
        randomKey();
        setScore(1);
        setRocketImagePath("resource/image/rocket.png");
    }

    @Override

    public List<KeyCode> randomKey() {
        int numberOfKey =  3+ random.nextInt(getMaxKey());
        return KeyGenerator.generateRandomKeys(numberOfKey);
    }
}
