package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PortalGun extends Entity {

    KeyHandler keyH;
    Portal portalA;
    Portal portalB;
    int currentPortal = 0;

    int portalCooldown;
    int portalCooldownTimer;

    public PortalGun(GamePanel gp, KeyHandler keyHandler, int x, int y, int cooldown) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        solid = false;
        sizeVertical = 5 * GamePanel.scale;
        sizeHorizontal = 10* GamePanel.scale;
        portalCooldown = cooldown;
        this.keyH = keyHandler;
        isHoldableItem = true;
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

        if(portalCooldownTimer > 0)
            portalCooldownTimer--;

        if (connectedToEntity instanceof Player player && ((index = player.playerIndex) == 1 ? keyH.actionPressed : keyH.actionPressed2) && portalCooldownTimer == 0) {

            portalCooldownTimer = portalCooldown;
            currentPortal = currentPortal == 1 ? 2 : 1;
            if (portalB != null && currentPortal == 1) {
                gp.entities.remove(portalA);
                portalA = null;
            }else if (portalA != null && currentPortal == 2) {
                gp.entities.remove(portalB);
                portalB = null;
            }
            AudioController.playPortalGunSpawnSound();

            if (portalA == null) {
                if(portalB != null)
                    portalA = new Portal(gp, connectedToEntity.x, connectedToEntity.y - GamePanel.tileSize/2, portalB, 1);
                else
                    portalA = new Portal(gp, connectedToEntity.x, connectedToEntity.y - GamePanel.tileSize/2);

                gp.entities.add(portalA);
                if (index == 1)
                    keyH.actionPressed = false;
                else
                    keyH.actionPressed2 = false;
            } else {
                portalB = new Portal(gp, connectedToEntity.x, connectedToEntity.y - GamePanel.tileSize/2, portalA,2);

                gp.entities.add(portalB);
                if (index == 1)
                    keyH.actionPressed = false;
                else
                    keyH.actionPressed2 = false;
            }
        }
        x = connectedToEntity.x + (connectedToEntity.direction.equals("left") ? 0 : (6 * GamePanel.scale));
        y = connectedToEntity.y + (10 * GamePanel.scale);
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
        return;
    }
}