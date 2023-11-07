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
    BufferedImage FatIdle1, FatIdle2, FatEat1, FatEat2;

    int lvl = 0;

    int maxLifes;
    boolean fat = false;
    int EatingTimer = 0;
    int EatingTime = 120;
    int EatingWaitTimer = 0;
    int EatingWaitTime = 180;
    int EatIndex;


    public Cat(GamePanel gp, int x, int y, int speed){
        this.gp = gp;
        this.x = x;
        this.y = y;
        getSprites();
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
            FatEat1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Fatty-Cat-Eat1.png")));
            FatEat2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Fatty-Cat-Eat2.png")));
            FatIdle1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Fatty-Cat2.png")));
            FatIdle2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Cutie-Fatty-Cat2.png")));
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

        if(Owner != null && !fat){
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
        if(fat)
            moving = false;
        spriteCounter ++;
        int animationSpeed = moving ? 10 : 45;

        if(spriteCounter > animationSpeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(fat && EatingTimer > 0){
            EatingTimer--;
            if(EatingTimer <= 0){
                EatingWaitTimer = EatingTime;
                EatIndex++;
                if(EatIndex >= 3){
                    fat = false;
                    lvlUp();
                    lifes = maxLifes;
                    EatIndex = 0;
                }
            }
        }

        if(fat && EatingWaitTimer >= 0){
            EatingWaitTimer--;
            if(EatingWaitTimer <= 0){
                EatingTimer = EatingWaitTime;
            }
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
        if(angryAt != null && !fat)
            moveTowards(angryAt.x,angryAt.y);
    }

    public void draw(Graphics2D g2){
        healthBar.draw(g2);
        BufferedImage image = null;

        if(spriteNum == 1 && !fat){
            if(moving){
                image = angry ? image3 : up1;
            }else {
                image = angry ? image1 : idle1;
            }
        }else if(spriteNum == 2 && !fat){
            if(moving){
                image = angry ? image4 : up2;
            }else {
                image = angry ? image2 : idle2;
            }
        }

        if(fat){
            if(EatingTimer == 0)
                image = spriteNum == 1 ? FatIdle1 : FatIdle2;
            else
                image = spriteNum == 1 ? FatEat1 : FatEat2;
        }

        g2.drawString("lvl "+ lvl, x,y-GamePanel.scale);
        g2.drawImage(image, x+ ((direction == "right") ? GamePanel.tileSize : 0),y, GamePanel.tileSize * ((direction == "right") ? -1 : 1), GamePanel.tileSize, null);
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
                    lvlUp();
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

    public void lvlUp(){
        lvl++;
        maxLifes++;
    }
}
