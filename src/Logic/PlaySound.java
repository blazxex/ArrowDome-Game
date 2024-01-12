package Logic;

import GUI.KeyPane;
import javafx.scene.media.AudioClip;

public class PlaySound {
    private static PlaySound instance = new PlaySound();
    public static PlaySound getInstance() {
        if (instance == null) {
            instance = new PlaySound();
        }
        return instance;
    }

    public void playKeyClickSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/keyClick.wav").toExternalForm());
        sound.play();
    }

    public void playSpaceCorrectSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/SpaceCorrect.wav").toExternalForm());
        sound.play();
    }

    public void playBackSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/Back.wav").toExternalForm());
        sound.play();
    }

    public void playStartMenuSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/Startmenu.wav").toExternalForm());
        sound.play();
    }

    public void playWrongSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/Wrong.wav").toExternalForm());
        sound.play();
    }
    public void playExplosionSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/Explosion.wav").toExternalForm());
        sound.play();
    }
    public void playStartPageBGMSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/StartPageBGM.wav").toExternalForm());
        sound.play();
        sound.setCycleCount(AudioClip.INDEFINITE);
    }public void playGamePageBGMSound() {
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/GamePageBGM.wav").toExternalForm());
        sound.play();
        sound.setCycleCount(AudioClip.INDEFINITE);
    }
    public void stopPlayStartPageBGMSound(){
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/StartPageBGM.wav").toExternalForm());
        sound.stop();
    }
    public void stopPlayGamePageBGMSound(){
        AudioClip sound = new AudioClip(getClass().getResource("/resource/sound/GamePageBGM.wav").toExternalForm());
        sound.stop();
    }

}
