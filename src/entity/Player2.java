package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player2 extends Entity {
    KeyHandler keyH;

    BufferedImage Heart, HeartHalf, HeartEmpty;
    int immunityTimer;

    Heart[] hearts;

    public Player2(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();

    }
    
    public void setDefaultValues(){
        x = 150;
        y = 150;
        speed = 4;
        lifes = 6;
        direction = "idle";
        solid = true;
    }

    public void getPlayerImage(){
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
        hearts = new Heart[]{new Heart(Heart, HeartHalf, HeartEmpty), new Heart(Heart, HeartHalf, HeartEmpty), new Heart(Heart, HeartHalf, HeartEmpty)};
    }
    @Override
    public void update(){

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
        if(lifes < 1)
            return;

        for(int i = hearts.length-1; i >= 0; i--) {
            if(immunityTimer == 0){
                if(hearts[i].getLifes() >= 1){
                    hearts[i].removeLife();
                    immunityTimer = 60;
                    lifes--;
                    if(lifes == 0){
                        gp.entities.remove(this);
                    }
                    System.out.println(lifes);
                    return;
                }
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

        g2.drawString("Schmillizidado", x+(gp.tileSize/2)-(g2.getFontMetrics().stringWidth("Schmillizidado")/2), y-5);
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        for(int i = 0; i < hearts.length; i++){
            g2.drawImage(hearts[i].image, 10 + (size/2)*i,15+ size/2,size/2, size/2, null);
        }
    }
}
