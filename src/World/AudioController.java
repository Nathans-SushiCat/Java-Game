package World;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

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
    public static float AudioFileLength(String fileString) {
        URL url = AudioController.class.getResource("/Resources/Audio/" + fileString);
        if (url == null) {
            // Handle the case where the resource is not found
            return 0;
        }

        try {
            File file = new File(url.toURI());
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            AudioFormat format = audioInputStream.getFormat();
            long frames = audioInputStream.getFrameLength();
            return (float) ((frames + 0.0) / format.getFrameRate());
        } catch (UnsupportedAudioFileException | IOException  | URISyntaxException e) {
            e.printStackTrace();
        }
        return 0;
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
    public static void playPortalGunSpawnSound(){
        playSound("PortalGunSpawn.wav");
    }
    public static void playPickupSound(){
        playSound("PortalGunPickup.wav");
    }
    public static void playTeleportSound(){
        playSound("Teleport.wav");
    }
    public static void playHealSound(){
        playSound("Heal.wav");
    }
    public static void playSushiEatSound(){
        playSound("Sushi.wav");
    }
    public static void playCatSound(){
        playSound("Cat.wav");
    }
    public static void playDropSound(){
        playSound("Drop.wav");
    }
    public static void playBackGroundMusicSound(){
        playSound("Java-Game.wav");
    }



}
