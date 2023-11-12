package entity;

import Main.GamePanel;
import World.TypeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gameboy extends Entity{
    TypeWriter writer;

    Phase phase;

    String[] starTexts = new String[]{"Hello there,", "I am the king of all games", "and you're not going to beat me", "It's time to play...", "for your life", ""};
    String[] game1Text = new String[]{"I guess now there should be some kind of boss fight", "but my programmer kind of lost the passion...", "to continue on making the game", "sooo i guess that's it for now", ""};
    int currentTextIndex = 0;

    boolean move1 = true, move2 = false;

    enum Phase{
        START,
        GAME1,
        GAME2
    }
    public Gameboy(GamePanel gp, int x, int y){
        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = 10;
        sizeHorizontal = 14*GamePanel.scale;
        sizeVertical = GamePanel.tileSize;
        phase = Phase.START;
        solid = true;
        direction = "left";
        writer = new TypeWriter(x,y-GamePanel.scale*6, 4f, 15);
        getSprites();
    }

    public void getSprites() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Left.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Right.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        writer.update();

        if(phase.equals(Phase.START)){

            if(writer.finished){
                writer.changeText(starTexts[currentTextIndex]);
                if(currentTextIndex+1 < starTexts.length)
                    currentTextIndex++;
                else{
                    phase = Phase.GAME1;
                    currentTextIndex = 0;
                }
            }

        }else if (phase.equals(Phase.GAME1)){

            if(x == GamePanel.tileSize*4){
                move1 = false;
                move2 = true;
            }

            if(move1){
                moveTowards(GamePanel.tileSize*4, y);
            }
            if(move2){
                moveTowards(GamePanel.tileSize*10, y);
            }

            if(writer.finished){
                writer.waitTimeMultiplier = 40;
                writer.changeText(game1Text[currentTextIndex]);
                if(currentTextIndex+1 < game1Text.length)
                    currentTextIndex++;
                else
                    phase = Phase.GAME1;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        writer.draw(g2);

        BufferedImage image = null;
        if(direction.equals("left") ||direction.equals("idle"))
            image = left1;
        else if(direction.equals("right"))
            image = right1;

        g2.drawImage(image, x, y, sizeHorizontal, sizeVertical, null);
    }
}
