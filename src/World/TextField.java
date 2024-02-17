package World;

import Main.GamePanel;
import entity.Entity;

import java.awt.*;

public class TextField extends Entity {
    String text;
    Font font;
    int Fontheight;
    int maxLength = 99999;

    public TextField(String text, Font font, int maxLength, int x, int y){
        this.text = text;
        this.font = font;
        this.y = y;
        this.x = x;
        this.maxLength = maxLength;
    }
    public TextField(String text, Font font, int x, int y){
        this.text = text;
        this.font = font;
        this.y = y;
        this.x = x;
    }
    public TextField(String text, int x, int y){
        this.text = text;
        this.font = GamePanel.dafaultFont;
        this.y = y;
        this.x = x;
    }
    public TextField(Font font, int x, int y){
        this.font = font;
        this.y = y;
        this.x = x;
    }
    public TextField(int x, int y){
        this.font = GamePanel.dafaultFont;
        this.y = y;
        this.x = x;
    }

    public void setText(String text){
        this.text = text;
    }
    public void appendText(String text){
        if(this.text.length()+text.length() <= maxLength)
            this.text += text;
    }
    public void deleteLast(){
        if(!text.equals("") && text != null)
            text = text.substring(0,text.length()-1);
    }

    @Override
    public void draw(Graphics2D g2){
        if(Fontheight == 0){
            Fontheight =  g2.getFontMetrics(font).getHeight();
        }

        g2.setFont(font);
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < ( maxLength != 0 ? maxLength : text.length()); i++){
            stringBuilder.append("-");
        }
        g2.drawString(text, x, y);
        g2.drawString(stringBuilder.toString(), x, y+ Fontheight/2);
        g2.setFont(GamePanel.dafaultFont);


    }
}
