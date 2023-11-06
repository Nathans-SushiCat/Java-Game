package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Lava extends Entity{

    KeyHandler keyH;

    public Lava(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getImage();
    }
    public Lava(GamePanel gp, KeyHandler keyH , int x, int y){

        this.gp = gp;
        this.keyH = keyH;
        this.y = y;
        this.x = x;
        solid = false;
        getImage();
    }

    public void setDefaultValues(){
        x = 800;
        y = 80;
        solid = false;
    }


    @Override
    public void update(){
        spriteCounter ++;
        int animationSpeed = 20;

        if(spriteCounter > animationSpeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void getImage(){
        try{
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Lava.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Lava1.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Lava2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleCollision(Collision collision) {
        if(collision.hasCollided()) {
            if(collision.collidedEntity instanceof Player player){
                player.removeLife();

            }
            collision.collidedEntity.removeLife();
        }
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(spriteNum == 1 ? image2 : image3, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
