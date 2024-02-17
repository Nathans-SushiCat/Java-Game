package entity;

import Main.Collision;
import Main.GamePanel;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Vector;

public class Gameboy_BitBullet  extends Entity {
    Gameboy parent;
    int lifeTime = 600;


    public Gameboy_BitBullet(GamePanel gp, int x, int y, int speed, Gameboy parent) {
        sizeVertical = 0;
        sizeHorizontal = 0;
        this.speed = speed;
        this.gp = gp;
        this.x = x;
        this.y = y;
        solid = true;
        this.parent = parent;
        AudioController.playShootSound();
    }

    public Gameboy_BitBullet(GamePanel gp, int x, int y, int speed) {
        sizeVertical = 0;
        sizeHorizontal = 0;
        this.speed = speed;
        this.gp = gp;
        this.x = x;
        this.y = y;
        solid = true;
        AudioController.playShootSound();
    }


    @Override
    public void handleCollision(Collision collision) {

        if (collision.hasCollided()) {
            if (!collision.collidedEntity.solid)
                return;

            if (collision.collidedEntity instanceof Player player) {
                player.removeLifeIgnoreImunity();
                gp.entities.remove(this);
                return;
            }

            if (parent == null || collision.collidedEntity != parent) {
                gp.entities.remove(this);
                AudioController.playHitSound();
            }
        }
    }

    @Override
    public void update() {
        x -= speed;
        lifeTime--;
        if (lifeTime <= 0)
            gp.entities.remove(this);
    }

    @Override
    public void draw(Graphics2D g2) {
        StringBuilder randomByteText = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (Math.random() > 0.5f)
                randomByteText.append("1");
            else
                randomByteText.append("0");
        }
        if(sizeHorizontal == 0){
            FontMetrics metrics = g2.getFontMetrics(gp.mainFont);
            sizeVertical = metrics.getHeight();
            sizeHorizontal = metrics.stringWidth(randomByteText.toString());
        }
        g2.drawString(randomByteText.toString(), x, y+sizeVertical);
    }
}