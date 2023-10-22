package entity;

import java.awt.image.BufferedImage;

public class Heart {
    public BufferedImage image;
    private BufferedImage Heart, HeartHalf, HeartEmpty;

    private int lifes;

    public Heart(BufferedImage Heart, BufferedImage HeartHalf, BufferedImage HeartEmpty){
        this.lifes = 2;
        this.HeartEmpty = HeartEmpty;
        this.Heart = Heart;
        this.HeartHalf = HeartHalf;
        image = Heart;
    }

    private void updateImage(){
        if(lifes == 2)
            image = Heart;
        else if(lifes == 1)
            image = HeartHalf;
        else
            image = HeartEmpty;
    }

    public void addLife(int lifes) {
        this.lifes += lifes;
        updateImage();
    }
    public void addLife() {
        this.lifes++;
        updateImage();
    }
    public void removeLife() {
        this.lifes--;
        updateImage();
    }
    public void removeLife(int lifes) {
        this.lifes -= lifes;
        updateImage();
    }

    public int getLifes(){
        return lifes;
    }
}
