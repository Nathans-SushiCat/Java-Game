package entity;

import Main.Collision;
import Main.GamePanel;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Objects;

public class Ein_Etwas_Bullet extends Entity{


    Ein_Etwas parent;
    int lifeTime = 600;

    public Ein_Etwas_Bullet(GamePanel gp, int x, int y, int speed, Ein_Etwas parent){

        this.speed = speed;
        sizeHorizontal = 4 * GamePanel.scale;;
        sizeVertical = 2 * GamePanel.scale;
        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = true;
        this.parent = parent;
        AudioController.playShootSound();
    }

    public Ein_Etwas_Bullet(GamePanel gp, int x, int y, int speed){

        this.speed = speed;
        sizeHorizontal = 4 * GamePanel.scale;;
        sizeVertical = 2 * GamePanel.scale;
        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = true;
        AudioController.playShootSound();
    }

    public void getSprites(){
        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Ein_Etwas/Ein_Etwas-Bullet.png")));
            //image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Ein_Etwas/Ein_Etwas-Bullet.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleCollision(Collision collision) {

        if ( collision.hasCollided()) {
            if(!collision.collidedEntity.solid)
                return;

            if(collision.collidedEntity instanceof Player player){
                player.removeLifeIgnoreImunity();
                gp.entities.remove(this);
                return;
            }

            if(collision.collidedEntity instanceof Player2 player2){
                player2.removeLifeIgnoreImunity();
                gp.entities.remove(this);
                return;
            }

            if(parent == null || collision.collidedEntity != parent){
                gp.entities.remove(this);
                AudioController.playHitSound();
            }
        }
    }


    @Override
    public void update() {
        x -= speed;
        lifeTime--;
        if(lifeTime <= 0)
            gp.entities.remove(this);
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image1, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
