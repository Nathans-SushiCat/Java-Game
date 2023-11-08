package Main;

import World.AudioController;
import entity.Ein_Etwas_Bullet;
import entity.Entity;
import entity.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.IndexColorModel;
import java.io.IOException;

public class Button extends Entity {
    int onClickFunction;
    String text;
    BufferedImage ButtonImage;
    BufferedImage[] ButtonDownImages = new BufferedImage[7];
    int buttonPressTimer;
    int buttonPressIndex = 0;
    int buttonPressTime = 15;
    boolean pressed = false;

    public Button(GamePanel gp, int x, int y, int onClickFunction, String text){
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.onClickFunction = onClickFunction;
        this.text = text;
        getSprites();
    }

    public void getSprites() {
        try {
            ButtonImage = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button.png"));
            ButtonDownImages[0] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down.png"));
            ButtonDownImages[1] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down-1.png"));
            ButtonDownImages[2] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down-2.png"));
            ButtonDownImages[3] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down-3.png"));
            ButtonDownImages[4] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down-4.png"));
            ButtonDownImages[5] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down-5.png"));
            ButtonDownImages[6] = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Button-Down-6.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g2){
        g2.setFont(gp.boldFont);
        BufferedImage image = ButtonDownImages[buttonPressIndex];

        g2.drawImage(pressed ? image : ButtonImage, x,y,2*GamePanel.tileSize, GamePanel.tileSize, null);
        g2.drawString(text, x+GamePanel.tileSize - (g2.getFontMetrics().stringWidth(text)/2),(int)(y+GamePanel.tileSize/(pressed ? 2 : 2.8)));
        g2.setFont(gp.mainFont);
    }

    @Override
    public void update(){
        if(pressed && connectedToEntity != null && distancetoObject(this, connectedToEntity) > GamePanel.tileSize){
            //ButtonPressed();
        }

        if(pressed){
            buttonPressTimer--;
            if(buttonPressTimer <= 0){
                buttonPressTimer = buttonPressTime;
                buttonPressIndex++;
            }
            if(buttonPressIndex > 6){
                ButtonPressed();
            }
        }
    }

    public void ButtonPressed(){
        if(onClickFunction == 1){
            gp.LoadRoom(1);
        }
        if(onClickFunction == 2){
            gp.LoadRoom(2);
        }
        if(onClickFunction == 3){
            System.exit(0);
        }
    }

    @Override
    public void handleCollision(Collision collision) {

        if (collision.hasCollided() && collision.collidedEntity instanceof Player) {
            pressed = true;
            connectedToEntity = collision.collidedEntity;
        }
        System.out.println("COLLIDE");
    }
}
