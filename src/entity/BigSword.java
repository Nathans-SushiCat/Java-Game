package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BigSword extends Entity {

    KeyHandler keyH;
    boolean punch = false;
    int punchCooldown;
    int punchCooldownTimer;
    int xScale = 1, yScale = 1;


    public BigSword(GamePanel gp, KeyHandler keyHandler, int x, int y, int cooldown) {
        this.x = x;
        this.y = y;
        this.gp = gp;
        solid = false;
        sizeVertical = 16 * GamePanel.scale;
        sizeHorizontal = 16* GamePanel.scale;
        punchCooldown = cooldown;
        this.keyH = keyHandler;
        getSprites();
    }


    public void getSprites() {
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/BigSword.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/BigSword-Punch1.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/BigSword-Punch2.png"));
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

        if(punchCooldownTimer > 0)
            punchCooldownTimer--;

        if (connectedToEntity instanceof Player player && ((index = player.playerIndex) == 1 ? keyH.actionPressed : keyH.actionPressed2) && punchCooldownTimer == 0) {

            punch = true;
            punchCooldownTimer = punchCooldown;
            spriteNum = 1;
        }
        String s = connectedToEntity.direction;

        if(s.equals("left"))
            xScale = -1;
        else
            xScale = 1;

        if(s.equals("up"))
            yScale = -1;
        else
            yScale = 1;

        x = connectedToEntity.x + (xScale == 1 && !(punch ? (s.equals("down") || s.equals("up")) : false) ? 6 * GamePanel.scale : 0) + (punch && (s.equals("idle") ||s.equals("left") || s.equals("right")) ? GamePanel.scale * spriteNum * xScale : 0);
        y = connectedToEntity.y + (punch && (s.equals("down") ||s.equals("up"))? GamePanel.scale * spriteNum * yScale: 0);

        if (s.equals("idle") && !punch)
            spriteNum = connectedToEntity.spriteNum;
        else if(!punch)
            spriteNum = 1;

    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image;

        if(!punch || connectedToEntity == null){
            if(spriteNum == 1){
                g2.drawImage(image1, x + (xScale == -1 ? GamePanel.scale*10: 0 ), y, GamePanel.tileSize * xScale, GamePanel.tileSize, null);
            }else {
                g2.drawImage(image1, x + (xScale == -1 ? GamePanel.scale*10: 0 ), y+(GamePanel.scale), GamePanel.tileSize * xScale, GamePanel.tileSize, null);
            }
        }else {

            spriteNum++;

            if(connectedToEntity.direction.equals("up") || connectedToEntity.direction.equals("down")){
                image = image3;
                sizeVertical = 20 * GamePanel.scale;
                sizeHorizontal = 10* GamePanel.scale;
            } else{
                image = image2;
                sizeVertical = 10 * GamePanel.scale;
                sizeHorizontal = 20* GamePanel.scale;
            }

            g2.drawImage(image, x+ (xScale == -1 ? GamePanel.scale * 10 : 0), y, GamePanel.tileSize * xScale, GamePanel.tileSize*yScale, null);

            //Punch State
            if(spriteNum <= 12)
                return;

            punch = false;
            spriteNum = 1;
        }
    }

    @Override
    public void handleCollision(Collision collision) {
        if (collision.hasCollided()) {
            if(collision.collidedEntity.hostile && punch){
                collision.collidedEntity.angryAt = connectedToEntity;
                collision.collidedEntity.removeLife(10);
            }
            if(collision.collidedEntity instanceof Ein_Etwas_Bullet bullet && punch){
                gp.entities.remove(bullet);
                AudioController.playHitSound();
            }
            if(connectedToEntity != null)
                return;

            if (collision.collidedEntity instanceof Player player && player.handItem == null && player.droppedItem != this ){
                player.pickupItem(this);
            }
        }
    }
}