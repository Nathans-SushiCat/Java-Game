package entity;

import Main.GamePanel;
import Main.KeyHandler;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    KeyHandler keyH;
    BufferedImage Heart, HeartHalf, HeartEmpty;

    int immunityTimer;

    Heart[] hearts;
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }
    public Player(GamePanel gp, KeyHandler keyH, int x, int y){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
        this.x = x;
        this.y = y;
    }
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        lifes = 6;

        direction = "idle";
        solid = true;
    }

    public void getPlayerImage(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-down-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-right-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-left-2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player/player-2.png"));

            Heart = ImageIO.read(getClass().getResourceAsStream("/Resources/Hearts/HeartBlue.png"));
            HeartHalf = ImageIO.read(getClass().getResourceAsStream("/Resources/Hearts/HeartBlueHalf.png"));
            HeartEmpty = ImageIO.read(getClass().getResourceAsStream("/Resources/Hearts/HeartEmpty.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        hearts = new Heart[]{new Heart(Heart, HeartHalf, HeartEmpty), new Heart(Heart, HeartHalf, HeartEmpty), new Heart(Heart, HeartHalf, HeartEmpty)};

    }
    @Override
    public void update(){

        //Key Inputs
        if(keyH.upPressed && !LockY_P){
            direction = "up";
            y -= speed;
        }
        if(keyH.downPressed && !LockY_N){
            direction = "down";
            y += speed;
        }
        if(keyH.rightPressed && !LockX_P){
            direction = "right";
            x += speed;
        }
        if(keyH.leftPressed && !LockX_N){
            direction = "left";
            x -= speed;
        }

        if(!(keyH.upPressed || keyH.downPressed || keyH.rightPressed || keyH.leftPressed)){
            direction = "idle";
        }

        spriteCounter ++;
        int animationspeed = 10;
        if(direction.equals("idle")) animationspeed = 30;

        if(immunityTimer > 0){
            immunityTimer -= 1;
        }

        if(spriteCounter > animationspeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void removeLife(){
        for(int i = hearts.length-1; i >= 0; i--) {
            if(immunityTimer == 0){
                if(hearts[i].getLifes() >= 1){
                    hearts[i].removeLife();
                    immunityTimer = 60;
                    lifes--;
                    if(lifes == 0){
                        gp.entities.remove(this);
                    }
                    AudioController.playHurtSound();
                    return;
                }
            }
        }
    }
    public void removeLifeIgnoreImunity(){
        for(int i = hearts.length-1; i >= 0; i--) {
            if (hearts[i].getLifes() >= 1) {
                hearts[i].removeLife();
                lifes--;
                if (lifes == 0) {
                    gp.entities.remove(this);
                }
                AudioController.playHurtSound();
                return;
            }
        }
    }
    public void addLife(){
        for(int i = 0; i < hearts.length; i++){
            if(hearts[i].getLifes() < 2){
                hearts[i].addLife();
                lifes++;
                return;
            }
        }
    }
    @Override
    public void draw(Graphics2D g2){
         // g2.setColor(Color.WHITE)
        // g2.fillRect(x,y,gp.tileSize, gp.tileSize);
        BufferedImage image = null;
        switch (direction){
            case "down":
                if(spriteNum == 1){
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "up":
                if(spriteNum == 1){
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "right":
                if(spriteNum == 1){
                    image = right1;
                }
                if(spriteNum == 2){
                    image = right2;
                }
                break;
            case "left":
                if(spriteNum == 1){
                    image = left1;
                }
                if(spriteNum == 2){
                    image = left2;
                }
                break;
            case "idle":
                if(spriteNum == 1){
                    image = idle1;
                }
                if(spriteNum == 2){
                    image = idle2;
                }
        }
        int size = GamePanel.tileSize;
        g2.drawString("Sushicat", x+(gp.tileSize/2)-(g2.getFontMetrics().stringWidth("Sushicat")/2), y-5);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        for(int i = 0; i < hearts.length; i++){
            g2.drawImage(hearts[i].image, 10 + (size/2)*i,10,size/2, size/2, null);
        }
    }
}

