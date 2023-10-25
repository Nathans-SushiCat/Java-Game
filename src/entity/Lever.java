package entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Lever extends Entity{
    KeyHandler keyH;
    Lever_Handle leverHandle;
    public Lever(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getSprite();
        leverHandle = new Lever_Handle(gp,keyH,x,y, this);
        gp.entities.add(leverHandle);
    }
    public Lever(GamePanel gp, KeyHandler keyH, int x, int y){
        this.gp = gp;
        this.keyH = keyH;
        solid = false;
        this.x = x;
        this.y = y;
        getSprite();
        leverHandle = new Lever_Handle(gp,keyH,x,y, this);
        gp.entities.add(leverHandle);
    }
    public Lever(GamePanel gp, KeyHandler keyH, int x, int y, Lever_Handle.State state){
        this.gp = gp;
        this.keyH = keyH;
        solid = false;
        this.x = x;
        this.y = y;
        getSprite();
        leverHandle = new Lever_Handle(gp,keyH,x,y, state ,this);
        gp.entities.add(leverHandle);
    }

    public void getSprite(){
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Lever_Base.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setDefaultValues(){
        x = 400;
        y = 400;
        solid = false;
    }

    @Override
    public void update() {
        leverHandle.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.drawImage(image1, x, y, GamePanel.tileSize, GamePanel.tileSize, null);
        leverHandle.draw(g2);
    }
}
