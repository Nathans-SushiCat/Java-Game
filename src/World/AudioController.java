package World;

import Main.Main;
import org.w3c.dom.ls.LSOutput;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class AudioController {
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    // Use the ClassLoader to load resources from the JAR file
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            getClass().getResource("/Resources/Audio/" + url)
                    );

                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    public static void playTypeWriterSound(){
        playSound("TypeWriter.wav");
    }

    public static void playPushLeverSound(){
        playSound("PushLever.wav");
    }
    public static void playLeverEndSound(){
        playSound("LeverEnd.wav");
    }
    public static void playHitSound(){
        playSound("Hit.wav");
    }
    public static void playHurtSound(){
        playSound("Hurt.wav");
    }
    public static void playShootSound(){
        playSound("Shoot.wav");
    }
    public static void playHurtDingeldodelSound(){
        playSound("HurtDingeldodel.wav");
    }
}
