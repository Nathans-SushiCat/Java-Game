package entity;

import Main.Collision;
import Main.GamePanel;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Cat extends Entity{


    boolean angry = false;
    Player Owner;
    HealthBar healthBar;

    int lvl = 0;

    int maxLifes;

    public Cat(GamePanel gp, int x, int y, int speed){
        this.gp = gp;
        this.x = x;
        this.y = y;
        getSprites();
        direction = "idle";
        solid = true;
        this.speed = speed;
        teleportable = true;
        lifes = 14;
        maxLifes = lifes;
        healthBar = new HealthBar(gp,this);
    }

    public void getSprites(){
        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Angry-Cat-Idle1.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Angry-Cat-Idle2.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Angry-Cat-Walk1.png")));
            image4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Angry-Cat-Walk2.png")));
            idle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Cat-Idle1.png")));
            idle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Cat-Idle2.png")));
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Cat-Walk1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Cat-Walk2.png")));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update() {
        healthBar.maxLifes = maxLifes;
        healthBar.update();
        if(immunityTimer > 0){
            immunityTimer -= 1;
        }

        if(lifes <= 0)
            gp.entities.remove(this);

        if(Owner != null && !gp.objectExists(Owner))
            Owner = null;

        if(Owner != null){
            float distanceToOwner = distancetoObject(this, Owner);
            if( distanceToOwner > 3* GamePanel.tileSize)
                moveTowards(Owner.x, Owner.y);
            else
                moving = false;

            if(distanceToOwner > 6*GamePanel.tileSize){
                x = Owner.x;
                y = Owner.y;
            }
        }

        spriteCounter ++;
        int animationSpeed;
        if(moving)
            animationSpeed = 15;
        else
            animationSpeed = 45;

        if(spriteCounter > animationSpeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }


        for (Entity e : gp.entities){
            if(angry && gp.entities.contains(angryAt) && angryAt.angryAt.equals(this))
                break;


            if(e.angryAt != null && (e.angryAt.equals(Owner) || e.angryAt.equals(this))){
                angryAt = e;
                angry = true;
                break;
            }
            angryAt = null;
            angry = false;
        }
        if(angryAt != null)
            moveTowards(angryAt.x,angryAt.y);

    }

    public void draw(Graphics2D g2){
        healthBar.draw(g2);
        BufferedImage image = null;

        if(spriteNum == 1){
            if(moving){
                if(angry)
                    image = image3;
                else
                    image = up1;
            }else {
                if(angry)
                    image = image1;
                else
                    image = idle1;
            }
        }else if(spriteNum == 2){
            if(moving){
                if(angry)
                    image = image4;
                else
                    image = up2;
            }else {
                if (angry)
                    image = image2;
                else
                    image = idle2;
            }
        }
        g2.drawString("lvl "+ lvl, x,y-GamePanel.scale);
        g2.drawImage(image, x,y, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    @Override
    public void handleCollision(Collision collision) {
        super.handleCollision(collision, Owner);

        if ( collision.hasCollided()) {
            if(collision.collidedEntity instanceof Ein_Etwas_Bullet)
                lifes--;

            if(collision.collidedEntity == angryAt){
                angryAt.angryAt = this;
                angryAt.removeLife();
                if(angryAt.lifes <= 0){
                    lvl++;
                    maxLifes++;
                }
            }

            if(collision.collidedEntity instanceof Player player){
                if(Owner == null){
                    AudioController.playCatSound();
                    Owner = player;
                }
            }
        }
    }
}
