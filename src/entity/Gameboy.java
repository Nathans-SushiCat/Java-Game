package entity;

import Main.GamePanel;
import World.TypeWriter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gameboy extends Entity{
    TypeWriter writer;

    Gameboy_BitBullet bitBullet;
    Phase phase;

    BufferedImage[] loadingImages = new BufferedImage[8];
    HealthBar healthBar;


    String[] startTexts = new String[]{"Hello there,", "I am the king of all games", "and you're not going to beat me", "It's time to play...", "for your life", ""};
    int currentTextIndex = 0;

    double currentLoadingIndex = 1;
    double totalLoadingTime = 0;

    public Cartridge cartridge;
    int phaseSection= 0;
    boolean cartridgePlaced = false;
    int nextPhaseSectionTimer = 0;
    boolean flashImage = false;
    int flashImageTimer = 0;
    BufferedImage lastFrameImage = null;

    enum Phase{
        NONE,
        START,
        LOADING,
        SLOWTIME,
        GAME2,
        Game3
    }
    public Gameboy(GamePanel gp, int x, int y){
        this.gp = gp;
        this.x = x;
        this.y = y;
        speed = 10;
        this.lifes = 100;
        sizeHorizontal = 14*GamePanel.scale;
        sizeVertical = GamePanel.tileSize;
        phase = Phase.START;
        solid = true;
        direction = "left";
        writer = new TypeWriter(x,y-GamePanel.scale*6, 4f, 15);
        getSprites();
        healthBar = new HealthBar(gp,this, gp.getHorizontalScreenMid(lifes*GamePanel.scale), GamePanel.scale*5);
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

        switch (phase) {
            case START:
                if(writer.finished){
                    writer.changeText(startTexts[currentTextIndex]);
                    if(currentTextIndex+1 < startTexts.length)
                        currentTextIndex++;
                    else{
                        phase = Phase.LOADING;
                        currentTextIndex = 0;
                    }
                }   break;
            case LOADING:
                if(totalLoadingTime > 320){
                    phase = Phase.values()[(int)(Math.random()*(5-3)+3 )];
                    totalLoadingTime = 0;
                }   if(currentLoadingIndex <= 8)
                    currentLoadingIndex += 0.1f;
                else
                    currentLoadingIndex = 1;
                totalLoadingTime++;
                break;
            case SLOWTIME:
                switch (phaseSection) {
                    case 0:
                        moveTowards(GamePanel.tileSize * 4, y);
                        if (direction.equals("idle")) {
                            phaseSection++;
                        }
                        break;
                    case 1:
                        moveTowards(GamePanel.tileSize * 10, y);
                        if (direction.equals("idle"))
                            phaseSection++;
                        break;
                    case 2:
                        if (!cartridgePlaced) {
                            cartridge = new Cartridge(gp, this, Cartridge.Cart.SLOWTIME, x - GamePanel.scale, y - (5 * GamePanel.tileSize), x - GamePanel.scale, y);
                            cartridgePlaced = true;
                        }
                        if (cartridge == null) {
                            writer.changeText("I can controll the time");
                            gp.changeFPS(30);
                            phaseSection++;
                        }
                        break;
                    case 3:
                        flashImage = true;
                        flashImageTimer++;
                        if (writer.finished)
                            phaseSection++;
                        break;
                    case 4:
                        flashImage = false;
                        writer.changeText("be ready for this");
                        phaseSection++;
                        break;
                    case 5:
                        if (writer.finished) {
                            gp.changeFPS(120);
                            phaseSection++;
                        }
                        break;
                    case 6:
                        if (!gp.entities.contains(bitBullet)) {
                            bitBullet = new Gameboy_BitBullet(gp, x + 4, y - 1, 3, this);
                            gp.entities.add(bitBullet);
                            nextPhaseSectionTimer++;
                        }
                        if(nextPhaseSectionTimer <= 120)
                            break;
                        nextPhaseSectionTimer = 0;
                        
                        break;
                }   break;
            default:
                break;
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
        if(direction.equals("left") ||direction.equals("idle")){
            if(!flashImage)
                image = left1;
            else{
                if(flashImageTimer <= 5){
                    image = lastFrameImage;
                }
                else if(lastFrameImage == left1){
                    image = left2;
                    flashImageTimer = 0;
                }
                else if(lastFrameImage == left2){
                    image = left1;
                    flashImageTimer = 0;
                }
            }
        }
        else if(direction.equals("right"))
            image = right1;
        g2.drawImage(image, x, y, sizeHorizontal, sizeVertical, null);
        lastFrameImage = image;
    }
}
