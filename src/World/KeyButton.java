package World;

import Main.Collision;
import Main.GamePanel;
import Main.KeyHandler;
import entity.Entity;
import entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class KeyButton extends Entity {
    String text;
    BufferedImage ButtonImage;
    BufferedImage ButtonDownImage;
    BufferedImage ButtonPressedImage;
    boolean pressed = false;
    boolean actionPerformed = false;
    World.TextField connectedTextField;
    KeyHandler keyH;
    float xTextpos, yTextpos;
    boolean playerOverSelf = false;

    public KeyButton(GamePanel gp, KeyHandler keyH, int x, int y, String button, TextField textField){
        this.keyH = keyH;
        connectedTextField = textField;
        this.gp = gp;
        this.x = x;
        this.y = y;
        sizeHorizontal = GamePanel.scale*8;
        sizeVertical = GamePanel.scale*8;
        this.text = button;
        solid = false;
        getSprites();

    }

    public void getSprites() {
        try {
            ButtonImage = ImageIO.read(getClass().getResourceAsStream("/Resources/Button/Button.png"));
            ButtonDownImage = ImageIO.read(getClass().getResourceAsStream("/Resources/Button/Button-pressed2.png"));
            ButtonPressedImage = ImageIO.read(getClass().getResourceAsStream("/Resources/Button/Button-pressed4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g2){

        if(xTextpos == 0){
            xTextpos = x+sizeHorizontal/2 - (g2.getFontMetrics().stringWidth(text)/2);
        }

        yTextpos = (int)(y+(GamePanel.scale*11)/(playerOverSelf ? (pressed ? 1.7 : 2) : 2.4));

        g2.drawImage(pressed ? ButtonPressedImage : playerOverSelf ? ButtonDownImage : ButtonImage, x,y,sizeHorizontal,sizeVertical, null);
        g2.drawString(text, xTextpos, yTextpos);
    }

    @Override
    public void update(){

        if(pressed){
            if(!actionPerformed && !getconnectedActionReleasedKey()){
                ButtonPressed();
                pressed = false;
                actionPerformed = true;
            }
        }
        if(connectedToEntity != null && getconnectedActionReleasedKey()){
            actionPerformed = false;
            pressed = false;
        }
        playerOverSelf = false;
    }

    public boolean getconnectedActionReleasedKey(){
        return (((Player)connectedToEntity).playerIndex == 1 && keyH.actionReleased) || (((Player)connectedToEntity).playerIndex == 2 && keyH.actionReleased2);
    }

    public void ButtonPressed(){
        if(text.equals("←")){
            connectedTextField.deleteLast();
        }else
            connectedTextField.appendText(text);
    }

    @Override
    public void handleCollision(Collision collision) {
        //Player Is over Self
        if (collision.hasCollided() && collision.collidedEntity instanceof Player p) {
            connectedToEntity = collision.collidedEntity;
            playerOverSelf = true;
            //Only over or also trigger press
            if((p.playerIndex == 1 && keyH.actionPressed) || (p.playerIndex == 2 && keyH.actionPressed2)){
                pressed = true;
            }
        }
    }
    @Override
    public void handleNoCollision() {
        pressed = false;
    }

}
