package entity;

import Main.Collidable;
import Main.Collision;
import Main.GamePanel;
import World.AudioController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Entity implements Collidable {
    public int x;
    public int y;
    public int speed;
    public boolean solid;
    boolean hostile = false;
    boolean moving = false;
    Entity angryAt;
    boolean isHoldableItem = false;
    public int lifes = 0;

    int immunityTimer = 0;

    public void removeLife(){
        if(immunityTimer == 0) {
            immunityTimer = 60;
            lifes--;
            AudioController.playHurtSound();
            if (lifes == 0) {
                gp.entities.remove(this);
            }
            return;
        }
    }
    public void removeLife(int immunityTime){
        if(immunityTimer == 0) {
            immunityTimer = immunityTime;
            lifes--;
            AudioController.playHurtSound();
            if (lifes == 0) {
                gp.entities.remove(this);
            }
            return;
        }
    }
    public void removeLifeIgnoreImunity(){
        if (lifes >= 1) {
            lifes--;
            AudioController.playHurtSound();
            if (lifes == 0) {
                gp.entities.remove(this);
            }
            return;
        }
    }
    public ArrayList<Collision> collisions = new ArrayList<Collision>();
    public boolean LockX_P = false, LockX_N = false, LockY_P = false, LockY_N = false;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2;
    public BufferedImage image1, image2, image3, image4, image5;
    public String direction;
    public Entity connectedToEntity = null;
    public GamePanel gp;
    public int sizeVertical = GamePanel.tileSize;
    public int sizeHorizontal = GamePanel.tileSize;;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public boolean teleportable = true;

    public float distanceToObject(Entity object1, Entity object2){
        float x1,x2,y1,y2;
        if(object1 == null || object2 == null)
            return 0;

        x1 = object1.x+ object1.sizeHorizontal/2f;
        y1 = object1.y+ object1.sizeVertical/2f;;
        x2 = object2.x+ object2.sizeHorizontal/2f;;
        y2 = object2.y+ object2.sizeVertical/2f;;
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
    public float distancetoPos(int distX, int distY){
        float x1,x2,y1,y2;
        x1 = x;
        y1 = y;
        x2 = distX;
        y2 = distY;
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

    public void moveTowards(int targetX, int targetY) {
        moving = true;
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

        if(distance < speed){
            direction = "idle";
            return;
        }
        // Move towards the target with the specified speed
        boolean moved = false;
        if(!LockY_P && directionY > 0){
            y += (int)(directionY * speed);
            direction = "up";
            moved = true;
        }
        else if(!LockY_N && directionY < 0){
            y += (int)(directionY * speed);
            direction = "down";
            moved = true;
        }else {
            direction = "idle";
        }
        if(!LockX_P && directionX > 0){
            x += (int)(directionX * speed);
            direction = "right";
        }
        else if(!LockX_N && directionX < 0){
            x += (int)(directionX * speed);
            direction = "left";
        }else if(!moved){
            direction = "idle";
        }
    }

    @Override
    public Collision createCollisionWith(Collidable other) {
        if (other instanceof Entity otherObject) {

            double horizontalDifference = (this.x + this.sizeHorizontal / 2.0) - (otherObject.x + otherObject.sizeHorizontal / 2.0);
            double verticalDifference = (this.y + this.sizeVertical / 2.0) - (otherObject.y + otherObject.sizeVertical / 2.0);

            double verticalSize = this.sizeVertical / 2.0 + otherObject.sizeVertical / 2.0;
            double horizontalSize = this.sizeHorizontal / 2.0 + otherObject.sizeHorizontal / 2.0;

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
    public void handleNoCollision() {
    }

    public boolean currentlyCollidingWith(Entity entity){
        for(Collision c : collisions){
            if(c.collidedEntity != null &&  c.collidedEntity.equals(entity) && c.collided){
                return true;
            }
        }
        return false;
    }
    public void handleCollision(Collision collision, Entity ignore) {
        // Handle the collision, e.g., change the object's state
        if ( collision.hasCollided()) {
            if(!collision.collidedEntity.solid)
                return;

            if(ignore != null && ignore.equals(collision.collidedEntity))
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