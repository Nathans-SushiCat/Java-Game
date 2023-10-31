package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;
import World.TypeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class NPC extends Entity{


    KeyHandler keyH;
    String name;
    int currentTextIndex = 0;

    String[] texts;
    TypeWriter writer;

    public NPC(GamePanel gp, KeyHandler keyH, String name, String[] texts, float speed){
        this.gp = gp;
        this.keyH = keyH;
        this.name = name;
        setDefaultValues();
        getNPCImage();
        this.texts = texts;
        writer = new TypeWriter(x,y-30, speed);
    }

    public void setDefaultValues(){
        x = 900;
        y = 400;
        solid = true;
    }

    public void getNPCImage(){
        try{
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/NPC.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/NPC_Idle1.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleCollision(Collision collision) {


        // Handle the collision, e.g., change the object's state
        if ( collision.hasCollided()) {
            if(!collision.collidedEntity.solid)
                return;

            if(collision.collidedEntity instanceof Player){
                if(writer.finished){
                    writer.changeText(texts[currentTextIndex]);
                    if(currentTextIndex+1 < texts.length)
                        currentTextIndex++;
                    else
                        currentTextIndex = 0;
                }
            }
        }
    }

    @Override
    public void update() {
        writer.update();


        spriteCounter ++;
        int animationspeed = 40;


        if(spriteCounter > animationspeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }


    }

    @Override
    public void draw(Graphics2D g2){
        writer.draw(g2);

        BufferedImage image = null;

        if(spriteNum == 1){
            image = idle1;
        }
        if(spriteNum == 2){
            image = idle2;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        g2.drawString(name, x+(gp.tileSize/2)-(g2.getFontMetrics().stringWidth(name)/2), y-5);
    }
}
