package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Spike extends Entity{
    //Retractable by Lever
    //Walk Trouable
    KeyHandler keyH;

    boolean active = false;
    boolean retracting = false;

    int retractingTime = 0;

    Lever connectedLever;


    public Spike(GamePanel gp, KeyHandler keyH, int x, int y){

        this.gp = gp;
        this.keyH = keyH;
        getSprites();
        this.x = x;
        this.y = y;
        solid = false;
    }
    public Spike(GamePanel gp, KeyHandler keyH, int x, int y, Lever lever){

        this.gp = gp;
        this.keyH = keyH;
        getSprites();
        this.x = x;
        this.y = y;
        solid = false;
        this.connectedLever = lever;
        active = connectedLever.leverHandle.currentState == Lever_Handle.State.RIGHT;
    }

    public void getSprites(){
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Spike-Base.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Spike-Half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Spike-Full.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void update(){


        if( connectedLever != null && connectedLever.leverHandle.currentState == Lever_Handle.State.RIGHT){
            if(!active && !retracting){
                retracting = true;
                retractingTime = 10;
            }
            if(retracting){
                retractingTime --;
                if(retractingTime == 0){
                    active = true;
                    retracting = false;
                }
            }
        }
        if(connectedLever != null && connectedLever.leverHandle.currentState == Lever_Handle.State.LEFT){
            if(active && !retracting){
                retracting = true;
                retractingTime = 10;
            }
            if(retracting){
                retractingTime --;
                if(retractingTime == 0){
                    active = false;
                    retracting = false;
                }
            }
        }
    }

    @Override
    public void handleCollision(Collision collision) {

        if ( active && collision.hasCollided()){
            if(!collision.collidedEntity.solid)
                return;
            if(collision.collidedEntity instanceof Player player){
                player.removeLife();
            }
            if(collision.collidedEntity instanceof Player2 player){
                player.removeLife();
            }

        }
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(image1, x, y, gp.tileSize, gp.tileSize, null);

        if(retracting)
            g2.drawImage(image2, x,y, gp.tileSize, gp.tileSize,null);

        if(active && !retracting)
            g2.drawImage(image3, x,y, gp.tileSize, gp.tileSize,null);



    }

}
