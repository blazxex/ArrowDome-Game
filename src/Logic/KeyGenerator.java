package Logic;

import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyGenerator {
    private static final Random random = new Random();

    public static List<KeyCode> generateRandomKeys(int maxKey) {
        List<KeyCode> keySet = new ArrayList<>();
        int numberOfKeys = maxKey;
        List<KeyCode> arrows = List.of(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT);
        for (int i = 0; i < numberOfKeys; i++) {
            keySet.add(arrows.get(random.nextInt(arrows.size())));
        }
        return keySet;
    }
}
