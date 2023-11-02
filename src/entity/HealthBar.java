package entity;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HealthBar extends Entity{

    Entity connectedEntity;
    int maxLifes;
    public HealthBar(GamePanel gp,  Entity connectedEntity) {
        this.connectedEntity = connectedEntity;
        maxLifes = connectedEntity.lifes;
        this.gp = gp;
        getSprites();
    }

        public void getSprites(){
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Healthbar-Empty.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/Resources/Objects/Healthbar-Full.png"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void update(){
        lifes = connectedEntity.lifes;
        x = connectedEntity.x + (int)((GamePanel.originalTileSize-maxLifes)/2f*GamePanel.scale);
        y = connectedEntity.y - GamePanel.scale;
    }

    @Override
    public void draw(Graphics2D g2){
        for(int i = 0; i < maxLifes; i++){
            if(lifes > i)
                g2.drawImage(image2, x+(i*GamePanel.scale),y, GamePanel.scale, GamePanel.scale, null);
            else
                g2.drawImage(image1, x+(i*GamePanel.scale),y, GamePanel.scale, GamePanel.scale, null);
        }
    }
}
