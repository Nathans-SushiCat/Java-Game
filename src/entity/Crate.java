package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

public class Crate extends Entity{

    KeyHandler keyH;

    public Crate(GamePanel gp, KeyHandler keyH, int x, int y){

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getSprite();
        this.x = x;
        this.y = y;
    }
    public Crate(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getSprite();
    }
    public void setDefaultValues(){
        x = 200;
        y = 300;
        solid = true;
        speed = 2;
    }
    public void getSprite(){
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Crate.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
    }
    @Override
    public void handleCollision(Collision collision) {
        // Handle the collision, e.g., change the object's state
        if (collision.hasCollided()) {
            // Handle the collision direction
            switch (collision.getDirection()) {
                case TOP:
                    if(!HasSolidInDirection(Collision.CollisionDirection.DOWN))
                        y-=speed;
                    break;
                case DOWN:
                    if(!HasSolidInDirection(Collision.CollisionDirection.TOP))
                        y+=speed;
                    break;
                case RIGHT:
                    if(!HasSolidInDirection(Collision.CollisionDirection.LEFT))
                        x+=speed;
                    break;
                case LEFT:
                    if(!HasSolidInDirection(Collision.CollisionDirection.RIGHT))
                        x-=speed;
                    break;
                case NONE:
                    break;
            }
        }
    }

    public boolean HasSolidInDirection(Collision.CollisionDirection direction){
        for(int i = 0; i < collisions.size(); i++){
            if(collisions.get(i).direction == direction)
                return true;
        }
        return false;
    }
    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(image1, x, y, gp.tileSize, gp.tileSize, null);
    }
}
