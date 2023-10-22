package Main;

import entity.Entity;

public class Collision {

    public enum CollisionDirection {
        TOP, RIGHT, LEFT, DOWN, TOP_RIGHT, TOP_LEFT, DOWN_RIGHT, DOWN_LEFT,  NONE
    }
    public CollisionDirection direction;
    public boolean collided;
    public Entity collidedEntity;

    public Collision() {
        this.direction = CollisionDirection.NONE;
        this.collided = false;
    }

    public Collision(CollisionDirection direction, Entity object) {
        this.direction = direction;
        this.collided = true;
        collidedEntity = object;
    }
    public boolean hasCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }


    public CollisionDirection getDirection() {
        return direction;
    }

    public void setDirection(CollisionDirection direction) {
        this.direction = direction;
    }


    @Override
    public String toString() {
        return "Collision [Direction: " + direction + ", Collided: " + collided +   "]";
    }
}