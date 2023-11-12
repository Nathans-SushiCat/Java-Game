package entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class R141496 extends Entity{
    int popupTimer;
    boolean visible = false;

    public R141496(){
        this.x = 0;
        this.y = 0;
        getSprites();
        popupTimer = (int)(Math.random()*28800+7200);
    }

    @Override
    public void update() {
        if(popupTimer <= 0){
            visible = true;
            popupTimer = (int)(Math.random()*28800+7200);
        }
        popupTimer--;
    }
    public void getSprites() {
        try {
            image1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/Resources/Objects/image.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        if(visible){
            g2.drawImage(image1, x,y, GamePanel.tileSize, GamePanel.tileSize, null);
            visible = false;
            x = (int)(Math.random()*GamePanel.screenWidth);
            y = (int)(Math.random()*GamePanel.screenHeight);
        }
    }
}
