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

    BufferedImage[] loadingImages = new BufferedImage[8];
    HealthBar healthBar;


    String[] startTexts = new String[]{"Hello there,", "I am the king of all games", "and you're not going to beat me", "It's time to play...", "for your life", ""};
    int currentTextIndex = 0;

    double currentLoadingIndex = 1;
    double totalLoadingTime = 0;

    public Cartridge cartridge;
    int phaseSection= 0;

    enum Phase{
        NONE,
        START,
        LOADING,
        GAME1,
        GAME2
    }
    public Gameboy(GamePanel gp, int x, int y){
        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = 10;
        this.lifes = 10;
        sizeHorizontal = 14*GamePanel.scale;
        sizeVertical = GamePanel.tileSize;
        phase = Phase.LOADING;
        solid = true;
        direction = "left";
        writer = new TypeWriter(x,y-GamePanel.scale*6, 4f, 15);
        getSprites();
        healthBar = new HealthBar(gp,this);
    }

    public void getSprites() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Left.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Left-Bright.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Right.png"));
            loadingImages[0] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-1.png"));
            loadingImages[1] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-2.png"));
            loadingImages[2] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-3.png"));
            loadingImages[3] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-4.png"));
            loadingImages[4] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-5.png"));
            loadingImages[5] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-6.png"));
            loadingImages[6] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-7.png"));
            loadingImages[7] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Loading-8.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        healthBar.update();
        writer.update();
        if(cartridge != null)
            cartridge.update();

        if(phase.equals(Phase.START)){

            if(writer.finished){
                writer.changeText(startTexts[currentTextIndex]);
                if(currentTextIndex+1 < startTexts.length)
                    currentTextIndex++;
                else{
                    phase = Phase.GAME1;
                    currentTextIndex = 0;
                }
            }

        }else if(phase.equals(Phase.LOADING)){
            if(totalLoadingTime > 320){
                phase = Phase.GAME1;
                totalLoadingTime = 0;
            }

            if(currentLoadingIndex <= 8)
                currentLoadingIndex += 0.1f;
            else
                currentLoadingIndex = 1;
            totalLoadingTime++;
        }else if (phase.equals(Phase.GAME1)){
            switch (phaseSection){
                case 0:
                    moveTowards(GamePanel.tileSize*4, y);
                    if(direction.equals("idle")){
                        phaseSection++;
                    }
                    break;
                case 1:
                    moveTowards(GamePanel.tileSize*10, y);
                    if(direction.equals("idle"))
                        phaseSection++;
                    break;
                case 2:
                    phase = Phase.NONE;
                    cartridge = new Cartridge(gp,this, Cartridge.Cart.SLOWTIME, x-GamePanel.scale,y-(5*GamePanel.tileSize), x-GamePanel.scale,y);
                    phaseSection = 0;
                    break;
                case 3:
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(phase.equals(Phase.LOADING))
            g2.drawImage( loadingImages[(int) (currentLoadingIndex-1)], x+sizeHorizontal/2-(GamePanel.scale*4), y- GamePanel.scale*10, GamePanel.scale*8, GamePanel.scale*8, null);


        healthBar.draw(g2);
        writer.draw(g2);
        if(cartridge != null)
            cartridge.draw(g2);

        BufferedImage image = null;
        if(direction.equals("left") ||direction.equals("idle"))
            image = left1;
        else if(direction.equals("right"))
            image = right1;

        g2.drawImage(image, x, y, sizeHorizontal, sizeVertical, null);
    }
}
