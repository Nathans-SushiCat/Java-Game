package entity;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;
import World.AudioController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Lever_Handle extends Entity{
    KeyHandler keyH;
    Lever lever;
    int leftState =(2*GamePanel.scale);
    int rightState =(10*GamePanel.scale);
    public enum State{
        LEFT,
        RIGHT,
        NONE
    }
    State currentState = State.NONE;
    public Lever_Handle(GamePanel gp, KeyHandler keyH, int x, int y, Lever lever){
        this.gp = gp;
        this.keyH = keyH;
        solid = true;
        this.x = x;
        this.y = y+GamePanel.scale;
        this.lever = lever;
        this.sizeVertical = 10 * GamePanel.scale;
        this.sizeHorizontal = 2 * GamePanel.scale;
        getSprite();
        teleportable = false;
    }
    public Lever_Handle(GamePanel gp, KeyHandler keyH, int x, int y, State state, Lever lever){
        this.gp = gp;
        this.keyH = keyH;
        solid = true;
        this.currentState = state;
        teleportable = false;

        if(state == State.LEFT){
            this.x = x+leftState;
        }else if(state == State.RIGHT){
            this.x = x+rightState;
        }else {
            this.x = x;
        }
        this.y = y+GamePanel.scale;


        this.lever = lever;
        this.sizeVertical = 10 * GamePanel.scale;
        this.sizeHorizontal = 2 * GamePanel.scale;
        getSprite();
    }

    public void getSprite(){
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Lever_Handle.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void handleCollision(Collision collision) {
        //+size/4
        if ( collision.hasCollided()) {
            if(!collision.collidedEntity.solid)
                return;

            // Handle the collision direction
            switch (collision.getDirection()) {
                case LEFT:
                    if(currentState.equals(State.LEFT))
                        break;

                    if(x > lever.x+leftState){
                        x -= 1;
                        AudioController.playPushLeverSound();
                        currentState = State.NONE;
                    }else {
                        currentState = State.LEFT;
                        AudioController.playLeverEndSound();
                    }
                    break;
                case RIGHT:
                    if(currentState.equals(State.RIGHT))
                        break;

                    if(x < lever.x+rightState){
                        x += 1;
                        AudioController.playPushLeverSound();
                        currentState = State.NONE;
                    }else {
                        AudioController.playLeverEndSound();
                        currentState = State.RIGHT;
                    }
                    break;
            }
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image1, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
    }
}
