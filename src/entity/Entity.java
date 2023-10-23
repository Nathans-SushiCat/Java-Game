package entity;

import Main.Collidable;
import Main.Collision;
import Main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity implements Collidable {
    public int x;
    public int y;
    public int speed;
    public boolean solid;

    public int lifes;
    public ArrayList<Collision> collisions = new ArrayList<Collision>();
    public boolean LockX_P = false, LockX_N = false, LockY_P = false, LockY_N = false;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2;
    public BufferedImage image1, image2, image3, image4;
    public String direction;
    public GamePanel gp;
    public int sizeVertical = GamePanel.tileSize;
    public int sizeHorizontal = GamePanel.tileSize;;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public float distancetoObject(Entity object1, Entity object2){
        float x1,x2,y1,y2;
        x1 = object1.x;
        y1 = object1.y;
        x2 = object2.x;
        y2 = object2.y;
        float deltaX, deltaY;
        if(x1 < x2){
            deltaX = x2-x1;
        }else{
            deltaX = x1-x2;
        }
        if(y1 < y2){
            deltaY = y2-y1;
        }else{
            deltaY = y1-y2;
        }
        return (float) Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
    }

    @Override
    public Collision createCollisionWith(Collidable other) {
        if (other instanceof Entity) {
            Entity otherObject = (Entity) other;

            if (distancetoObject(this, otherObject) > GamePanel.tileSize)
                return new Collision();

            double horizontalDifference = this.x - otherObject.x;
            double verticalDifference = this.y - otherObject.y;

            double verticalSize;
            double horizontalSize;

            verticalSize = sizeVertical/2.0 + otherObject.sizeVertical/2.0;
            horizontalSize = sizeHorizontal/2.0 + otherObject.sizeHorizontal/2.0;

            if (Math.abs(horizontalDifference) <= horizontalSize && Math.abs(verticalDifference) <= verticalSize) {
                if (Math.abs(horizontalDifference) >= Math.abs(verticalDifference)) {
                    if (horizontalDifference > 0) {
                        return new Collision(Collision.CollisionDirection.RIGHT, otherObject);
                    } else {
                        return new Collision(Collision.CollisionDirection.LEFT, otherObject);
                    }
                } else {
                    if (verticalDifference > 0) {
                        return new Collision(Collision.CollisionDirection.DOWN, otherObject);
                    } else {
                        return new Collision(Collision.CollisionDirection.TOP, otherObject);
                    }
                }
            }
        }
        return new Collision();
    }

    public void update(){
    }

    public void draw(Graphics2D g2){
        g2.drawString("X", x, y);
    }

    @Override
    public void handleCollision(Collision collision) {


        // Handle the collision, e.g., change the object's state
        if ( collision.hasCollided()) {
            if(!collision.collidedEntity.solid)
                return;
            // Handle the collision direction
            switch (collision.getDirection()) {
                case TOP:
                    LockY_N = true;
                    break;
                case RIGHT:
                    LockX_N = true;
                    break;
                case LEFT:
                    LockX_P = true;
                    break;
                case DOWN:
                    LockY_P = true;
                    break;
                case NONE:
                    LockY_N = false;
                    LockY_P = false;
                    LockX_P = false;
                    LockX_N = false;
                    break;
            }
        }else  {
            LockY_N = false;
            LockY_P = false;
            LockX_P = false;
            LockX_N = false;
        }

    }

    @Override
    public String toString() {
        return super.toString();
    }
}