package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Sushi extends Entity {

    KeyHandler keyH;
    public Sushi(GamePanel gp, KeyHandler keyH, int x, int y, int speed) {
        this.gp = gp;
        this.x = x;
        this.y = y;
        this.speed = speed;
        sizeVertical = 9*GamePanel.scale;
        sizeHorizontal = 7* GamePanel.scale;
        this.keyH = keyH;
        solid = true;
        getSprites();
    }

    public void getSprites() {
        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Cat/Sushi.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image1, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }

    public void handleCollision(Collision collision) {
        super.handleCollision(collision);


        if(collision.collidedEntity instanceof Cat cat && !cat.fat){
            if(cat.lifes != cat.maxLifes)
                cat.lifes = cat.maxLifes;
            else{
                cat.fat = true;
            }

            AudioController.playSushiEatSound();
            gp.entities.remove(this);
        }

        if ( collision.hasCollided()) {
            if(!collision.collidedEntity.solid)
                return;

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
}