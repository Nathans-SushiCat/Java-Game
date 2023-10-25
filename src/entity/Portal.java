package entity;

import Main.Collision;
import Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Portal extends Entity {

    Portal connectedPortal;
    final int getTpCooldown = 10;
    int tpCooldown = getTpCooldown;

    ArrayList<Entity> touchedEntitys = new ArrayList<Entity>();
    public Portal(GamePanel gp, int x, int y) {
        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = false;

    }

    public Portal(GamePanel gp, int x, int y, Portal connectedPortal) {
        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = false;
        this.connectedPortal = connectedPortal;
        connectedPortal.connectedPortal = this;
    }



    public void getSprites() {
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Portal_1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Portal_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){

        spriteCounter ++;
        int animationspeed = 5;


        if(spriteCounter > animationspeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
        if(tpCooldown > 0){
            tpCooldown--;
        }else {
            for(int i = 0; i < touchedEntitys.size(); i++){
                boolean colliding = false;
                for(Collision c : collisions){
                    if(c.collidedEntity != null &&  c.collidedEntity.equals(touchedEntitys.get(i)) && c.collided){
                        colliding = true;
                        break;
                    }
                }
                //If The Entity does not collide with portal anymore it gets reset
                if(!colliding)
                    touchedEntitys.remove(touchedEntitys.get(i));
            }
        }


    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if(spriteNum == 1){
            image = idle1;
        }
        if(spriteNum == 2){
            image = idle2;
        }
        g2.drawImage(image, x, y, GamePanel.tileSize, 2*GamePanel.tileSize, null);

    }

    @Override
    public void handleCollision(Collision collision) {

        if (!collision.hasCollided() || !collision.collidedEntity.solid || collision.collidedEntity instanceof Portal)
            return;

        Entity e = collision.collidedEntity;

        if(touchedEntitys.contains(e) || tpCooldown > 0){
            return;
        }

        tpCooldown = getTpCooldown;
        connectedPortal.tpCooldown = getTpCooldown;
        int Xoffset = e.x-x;
        int Yoffset = e.y-y;

        e.x = connectedPortal.x+Xoffset;
        e.y = connectedPortal.y+Yoffset;
        connectedPortal.touchedEntitys.add(e);

    }
}