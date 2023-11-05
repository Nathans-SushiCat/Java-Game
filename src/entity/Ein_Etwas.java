package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Ein_Etwas extends Entity{
    //Shots bullets
    //Deactivate by Lever
    //Or just by a function that deactivates the shooting

    final int shootInterval;
    int lastShootTime;
    boolean active;
    Lever connectedLever;


    public Ein_Etwas(GamePanel gp,  int x, int y, int shootInterval, Lever lever){

        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = true;
        this.shootInterval = shootInterval;
        this.connectedLever = lever;
        active = true;
    }

    public void getSprites(){
        try {
            idle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Ein_Etwas/Ein_Etwas-Idle1.png")));
            idle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Ein_Etwas/Ein_Etwas-Idle2.png")));
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Ein_Etwas/Ein_Etwas-Idle1_Happy.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Ein_Etwas/Ein_Etwas-Idle2_Happy.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(){

        if( connectedLever != null && connectedLever.leverHandle.currentState == Lever_Handle.State.RIGHT)
            active = true;
        if( connectedLever != null && connectedLever.leverHandle.currentState == Lever_Handle.State.LEFT)
            active = false;

        if(lastShootTime >= shootInterval){
            gp.entities.add(new Ein_Etwas_Bullet(gp, x, y+2*GamePanel.scale, 7, this));
            lastShootTime = 0;
        }
        if(active)
            lastShootTime++;

        spriteCounter ++;
        int animationSpeed = 10;

        if(spriteCounter > animationSpeed){
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

        BufferedImage image = null;

        if(spriteNum == 1){
            if(active)
                image = idle1;
            else
                image = image1;
        }
        if(spriteNum == 2){
            if(active)
                image = idle2;
            else
                image = image2;
        }

        g2.drawImage(image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);

    }

}
