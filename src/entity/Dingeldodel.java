package entity;

import Main.Collidable;
import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static World.AudioController.playHurtDingeldodelSound;

public class Dingeldodel extends Entity{

    int mouthSpriteCounter = 0;
    int mouthSpriteTime = 2;
    int mouthSpriteNum = 1;
    boolean openMouth = false;
    Entity currentPlayer;

    boolean moving = false;
    int LastFrameXpos;
    int LastFrameYpos;

    HealthBar healthBar;

    public Dingeldodel(GamePanel gp, int x, int y, int speed){
        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = true;
        this.speed = speed;
        lifes = 3;
        healthBar = new HealthBar( gp,this);
    }


    public void getSprites(){
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-idle2.png"));
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Mouth.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Mouth1.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Mouth2.png"));
            image4 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Mouth3.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Right2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Dingeldodel/Dingeldodel-Down2.png"));


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
        healthBar.update();
        spriteCounter ++;
        int animationspeed;
        if(!moving)
            animationspeed = 40;
        else
            animationspeed = 10;

        if(spriteCounter > animationspeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(openMouth){
            mouthSpriteCounter++;
            if(mouthSpriteCounter > mouthSpriteTime){
                if(mouthSpriteNum == 1){
                    mouthSpriteNum = 2;
                }else if(mouthSpriteNum == 2){
                    mouthSpriteNum = 3;
                }else if(mouthSpriteNum == 3){
                    mouthSpriteNum = 4;
                }else if(mouthSpriteNum == 4){
                    mouthSpriteNum = 1;
                    openMouth = false;
                    if(currentPlayer instanceof Player p)
                        p.removeLifeIgnoreImunity();
                }
                mouthSpriteCounter = 0;
            }
        }

        if(immunityTimer > 0){
            immunityTimer -= 1;
        }


        if(currentPlayer != null && distancetoObject(this, currentPlayer) < GamePanel.tileSize*4){
            moving = true;
            moveTowards(currentPlayer);
        }else
            moving = false;

        if(lifes == 0)
            gp.entities.remove(this);
    }

    public void moveTowards(Entity target) {
        if(currentPlayer.lifes == 0){
            moving = false;
            currentPlayer = null;
        }
        // Calculate the direction vector from your position to the target
        double directionX = target.x - x;
        double directionY = target.y - y;

        // Calculate the distance between you and the target
        double distance = Math.sqrt(directionX * directionX + directionY * directionY);

        // Normalize the direction vector (make it a unit vector)
        if (distance > 0) {
            directionX /= distance;
            directionY /= distance;
        }

        // Move towards the target with the specified speed
        x += (int)(directionX * speed);
        y += (int)(directionY * speed);
    }


    @Override
    public void handleCollision(Collision collision) {
        if ( collision.hasCollided()) {
            if(collision.collidedEntity instanceof Ein_Etwas_Bullet)
                lifes--;

            if (!collision.collidedEntity.solid || !(collision.collidedEntity instanceof Player))
                return;

            openMouth = true;
            currentPlayer = collision.collidedEntity;
        }
    }

    @Override
    public void draw(Graphics2D g2){
        healthBar.draw(g2);

        BufferedImage image = null;
        BufferedImage imageMouth = null;

        String movedir = "";
        if(!moving){
            if(spriteNum == 1){
                image = idle1;
            }else if(spriteNum == 2){
                image = idle2;
            }
        }else {
            if(LastFrameXpos > x){
                //Left
                movedir = "Left";
                if(spriteNum == 1){
                    image = left1;
                }else if(spriteNum == 2){
                    image = left2;
                }
            }else if(LastFrameXpos < x){
                //Right
                movedir = "Right";
                if(spriteNum == 1){
                    image = right1;
                }else if(spriteNum == 2){
                    image = right2;
                }
            }else if(LastFrameYpos < y){
                //Down
                movedir = "Down";
                if(spriteNum == 1){
                    image = down1;
                }else if(spriteNum == 2){
                    image = down2;
                }
            }else if(LastFrameYpos > y){
                //Up
                movedir = "Up";
                if(spriteNum == 1){
                    image = up1;
                }else if(spriteNum == 2){
                    image = up2;
                }
            }else if(spriteNum == 1){
                image = idle1;
            }else if(spriteNum == 2){
                image = idle2;
            }
        }


        if(mouthSpriteNum == 1){
            imageMouth = image1;
        }else if(mouthSpriteNum == 2){
            imageMouth = image2;
        }else if(mouthSpriteNum == 3){
            imageMouth = image3;
        }else if(mouthSpriteNum == 4){
            imageMouth = image4;
        }

        LastFrameXpos = x;
        LastFrameYpos = y;

        g2.drawImage(image, x, y, GamePanel.tileSize, GamePanel.tileSize, null);

        if(movedir.equals("Up"))
            return;

        if(spriteNum == 1 || moving )
            g2.drawImage(imageMouth, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
        else
            g2.drawImage(imageMouth, x, y+GamePanel.scale, GamePanel.tileSize, GamePanel.tileSize, null);

    }
}
