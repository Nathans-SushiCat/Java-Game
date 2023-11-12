package World;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Objects;

public class AudioController {

    private static Clip currentMusic;

    public static synchronized void playBackGroundMusic(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (currentMusic != null && currentMusic.isRunning()) {
                        currentMusic.stop();
                    }

                    Clip newClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            getClass().getResource("/Resources/Audio/" + url)
                    );

                    newClip.open(inputStream);
                    newClip.start();

                    // Set the newClip as the currentClip
                    currentMusic = newClip;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    // Use the ClassLoader to load resources from the JAR file
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            getClass().getResource("/Resources/Audio/" + url)
                    );

                    clip.getFrameLength();
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }



    public static float AudioFileLength(String url) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(AudioController.class.getResource("/Resources/Audio/" + url)));

            AudioFormat format = audioInputStream.getFormat();
            long frameLength = audioInputStream.getFrameLength();

            return (frameLength + 0.0f) / format.getFrameRate();
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            return 0;
        }
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
        playBackGroundMusic("Java-Game.wav");
    }
    public static void playBackGroundMusicGameBoySound(){
        playBackGroundMusic("GameBoyMusic.wav");
    }



}
