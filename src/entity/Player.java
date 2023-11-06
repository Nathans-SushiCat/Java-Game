package entity;

import Main.GamePanel;
import Main.KeyHandler;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity {
    KeyHandler keyH;
    BufferedImage Heart, HeartHalf, HeartEmpty;
    int playerIndex;

    Entity handItem = null;
    int immunityTimer;

    ArrayList<Heart> hearts = new ArrayList<Heart>();
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        getPlayerImage();
        setDefaultValues();
        playerIndex = 0;
    }
    public Player(GamePanel gp, KeyHandler keyH, int x, int y, int playerIndex){
        this.gp = gp;
        this.keyH = keyH;
        if(playerIndex == 1)
            getPlayerImage();
        else
            getPlayer2Image();
        setDefaultValues();
        this.playerIndex = playerIndex;
        this.x = x;
        this.y = y;
    }
    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "idle";
        for(int i = 0; i < 3; i ++)
            addHeart();
        lifes = hearts.size()*2;
        solid = true;
    }
    public void addHeart(){
        hearts.add(new Heart(Heart, HeartHalf, HeartEmpty));
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
    }

    public void getPlayer2Image(){
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-up-1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-up-2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-down-1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-down-2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-right-1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-right-2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-left-1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-left-2.png"));
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Resources/player2/player2-2.png"));

            Heart = ImageIO.read(getClass().getResourceAsStream("/Resources/Hearts/HeartRed.png"));
            HeartHalf = ImageIO.read(getClass().getResourceAsStream("/Resources/Hearts/HeartRedHalf.png"));
            HeartEmpty = ImageIO.read(getClass().getResourceAsStream("/Resources/Hearts/HeartEmpty.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void update(){


        //Key Inputs
        if(playerIndex == 1){
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
        }else {
            //Key Inputs
            if(keyH.upPressed2 && !LockY_P){
                direction = "up";
                y -= speed;
            }
            if(keyH.downPressed2 && !LockY_N){
                direction = "down";
                y += speed;
            }
            if(keyH.rightPressed2 && !LockX_P){
                direction = "right";
                x += speed;
            }
            if(keyH.leftPressed2 && !LockX_N){
                direction = "left";
                x -= speed;
            }

            if(!(keyH.upPressed2 || keyH.downPressed2 || keyH.rightPressed2 || keyH.leftPressed2)){
                direction = "idle";
            }
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

    @Override
    public void removeLife(){
        for(int i = hearts.size()-1; i >= 0; i--) {
            if(immunityTimer == 0){
                if(hearts.get(i).getLifes() >= 1){
                    hearts.get(i).removeLife();
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
    @Override
    public void removeLifeIgnoreImunity(){
        for(int i = hearts.size()-1; i >= 0; i--) {
            if (hearts.get(i).getLifes() >= 1) {
                hearts.get(i).removeLife();
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
        for(int i = 0; i < hearts.size(); i++){
            if(hearts.get(i).getLifes() < 2){
                hearts.get(i).addLife();
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

        g2.drawImage(image, x, y, size, size, null);

        if(playerIndex == 1){
            g2.drawString("Sushicat", x+(size/2)-(g2.getFontMetrics().stringWidth("Sushicat")/2), y-5);
            for(int i = 0; i < hearts.size(); i++){
                g2.drawImage(hearts.get(i).image, 10 + (size/2)*i,10,size/2, size/2, null);
            }
        }else {
            g2.drawString("Schmillizidado", x+(size/2)-(g2.getFontMetrics().stringWidth("Schmillizidado")/2), y-5);
            for(int i = 0; i < hearts.size(); i++){
                g2.drawImage(hearts.get(i).image, 10 + (size/2)*i,15+ size/2,size/2, size/2, null);
            }
        }
    }
}

