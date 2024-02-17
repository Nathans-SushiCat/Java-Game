package entity;

import Main.GamePanel;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cartridge extends Entity {

    BufferedImage[] images = new BufferedImage[6];
    public enum Cart {
        BACKWADS,
        BUTTON,
        LIGHTING,
        SOUNDWAVE,
        TETRIS,
        SLOWTIME
        }
    public Cart cartidge;
    public boolean slotetIn = false;

    int moveX, moveY;

    public Cartridge(GamePanel gp, Entity connectetTo, Cart cartidge, int x, int y, int Xdirection, int Ydirection){
        this.gp = gp;
        this.cartidge = cartidge;
        connectedToEntity = connectetTo;
        this.x = x;
        this.y = y;
        solid = false;
        speed = 3;
        sizeVertical = GamePanel.scale*16;
        sizeHorizontal = GamePanel.scale*16;
        moveX = Xdirection;
        moveY = Ydirection;
        getSprites();
        image1 = images[cartidge.ordinal()];
        direction = "down";
    }

    public void getSprites() {
        try {
            images[0] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Cartridge-Backwards.png"));
            images[1] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Cartridge-Button.png"));
            images[2] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Cartridge-Lighting.png"));
            images[3] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Cartridge-Sound.png"));
            images[4] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Cartridge-Tetris.png"));
            images[5] = ImageIO.read(getClass().getResourceAsStream("/Resources/Gameboy/Gameboy-Cartridge-Time.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        moveTowards(moveX, moveY);
        if(direction.equals("idle")){
            slotetIn = true;
        }

        if (slotetIn){
            AudioController.playCartridgeSound();
            ((Gameboy)connectedToEntity).cartridge = null;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image1, x,y, sizeHorizontal, sizeVertical, null);
    }
}
