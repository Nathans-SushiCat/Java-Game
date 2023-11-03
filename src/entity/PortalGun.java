package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PortalGun extends Entity {

    Entity connectedToEntity = null;
    KeyHandler keyH;
    Portal portalA;
    Portal portalB;
    int currentPortal = 0;


    public PortalGun(GamePanel gp, KeyHandler keyHandler, int x, int y) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        solid = false;
        sizeVertical = 5;
        sizeHorizontal = 10;
        this.keyH = keyHandler;
        getSprites();
    }


    public void getSprites() {
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Portal-Gun.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        if(!gp.objectExists(connectedToEntity))
           connectedToEntity = null;

        if (connectedToEntity == null) {
            spriteCounter++;
            int animationSpeed = 30;
            if (spriteCounter > animationSpeed) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            return;
        }

        int index = 1;
        if (connectedToEntity instanceof Player player ? ((index = player.playerIndex) == 1 ? keyH.actionPressed : keyH.actionPressed2) : false) {

            currentPortal = currentPortal == 1 ? 2 : 1;
            if (portalB != null && currentPortal == 1) {
                gp.entities.remove(portalA);
                portalA = null;
            }else if (portalA != null && currentPortal == 2) {
                gp.entities.remove(portalB);
                portalB = null;
            }

            if (portalA == null) {
                if(portalB != null)
                    portalA = new Portal(gp, x, y, portalB, 1);
                else
                    portalA = new Portal(gp, x, y);

                gp.entities.add(portalA);
                if (index == 1)
                    keyH.actionPressed = false;
                else
                    keyH.actionPressed2 = false;
            } else {
                portalB = new Portal(gp, x, y, portalA,2);

                gp.entities.add(portalB);
                if (index == 1)
                    keyH.actionPressed = false;
                else
                    keyH.actionPressed2 = false;
            }
        }
        x = connectedToEntity.x + (3 * GamePanel.scale);
        y = connectedToEntity.y + (4 * GamePanel.scale);
        if (connectedToEntity.direction.equals("idle"))
            spriteNum = connectedToEntity.spriteNum;
        else
            spriteNum = 1;
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image;
        int xScale = 1;

        if(connectedToEntity != null && connectedToEntity.direction.equals("left")){
            xScale = -1;
        }

        if(spriteNum == 1){
            g2.drawImage(image1, x+(xScale == -1 ? GamePanel.scale*10: 0 ), y, GamePanel.tileSize * xScale, GamePanel.tileSize, null);
        }else {
            g2.drawImage(image1, x +(xScale == -1 ? GamePanel.scale*10: 0 ), y+(GamePanel.scale), GamePanel.tileSize * xScale, GamePanel.tileSize, null);
        }


    }

    @Override
    public void handleCollision(Collision collision) {

        if(connectedToEntity != null)
            return;

        if (collision.hasCollided()) {
            if (collision.collidedEntity instanceof Player player)
                    connectedToEntity = player;
        }
    }
}