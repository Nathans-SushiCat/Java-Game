package entity;

import Main.Collision;
import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Star extends Entity{
    private Player p1;
    private Player p2;
    private int startX;
    private int startY;
    private int radius;

    public Star(GamePanel gp, int x, int y, int speed, int radius){
        this.gp = gp;
        getSprites();
        this.x = x;
        this.y = y;
        solid = true;
        lifes = 10;
        this.speed = speed;
        this.radius = radius;
        startX = x;
        startY = y;
    }


    public void getSprites(){
        try {
            idle1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Star/Star-Idle1.png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Star/Star-Idle2.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(){

        if(lifes <= 0){
                gp.entities.remove(this);
        }

        if(p1 == null || p2 == null){
            for(Entity e : gp.entities){
                if(e instanceof Player player){
                    if(p1 == null)
                        p1 = player;
                    else
                        p2 = player;
                }
            }
            System.out.println(p1 +"\n"+ p2);
        }


        spriteCounter ++;
        int animationSpeed = 10;
        if(spriteCounter > animationSpeed){
            if(spriteNum == 1){
                spriteNum = 2;
            }else if(spriteNum == 2){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        boolean moving = false;
        if(p1 != null && distancetoObject(this,p1) < radius*GamePanel.tileSize){
            moveAgainst(p1);
            moving = true;
        }
        if(p2 != null && distancetoObject(this,p2) < radius*GamePanel.tileSize){
            moveAgainst(p2);
            moving = true;
        }


        if(!moving && distancetoObject(this,p2) > (radius+1)*GamePanel.tileSize && distancetoObject(this,p1) > (radius+1)*GamePanel.tileSize){
            if(distancetoPos(startX,startY) > speed)
                moveTowards(startX, startY);
        }
    }

    public void moveAgainst(Entity target) {
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
        x += (int)(-directionX * speed);
        y += (int)(-directionY * speed);
    }

    public void moveTowards(int targetX, int targetY) {
        // Calculate the direction vector from your position to the target
        double directionX = targetX - x;
        double directionY = targetY - y;

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
    public void draw(Graphics2D g2){
        BufferedImage image;

        if(spriteNum == 1){
            image = idle1;
        }else{
            image = idle2;
        }
        g2.drawImage(image, x,y,GamePanel.tileSize, GamePanel.tileSize, null);
    }

    @Override
    public void handleCollision(Collision collision) {
        if ( collision.hasCollided()) {
            if(collision.collidedEntity instanceof Ein_Etwas_Bullet)
                lifes--;

            if (!collision.collidedEntity.solid)
                return;

            if(collision.collidedEntity instanceof Player player){
                while(player.lifes < player.hearts.size()*2)
                    player.addLife();
                System.out.println(player.lifes);
                gp.entities.remove(this);
            }
        }
    }

}
