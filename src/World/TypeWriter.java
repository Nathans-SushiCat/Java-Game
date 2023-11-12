package World;

import Main.GamePanel;
import Main.SimpleEntity;
import jdk.jfr.Description;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TypeWriter extends SimpleEntity {

    private int counter;
    private String[]  texts;
    private int textsIndex;
    private String currentText = "";
    public String text;
    private float speed;

    private boolean multipleTextsMode = false;
    private int currentChar;

    public float waitTime;

    public boolean finished = false;

    public int waitTimeMultiplier;

    /**
     * @param speed higher speed means it takes longer 60 = 1/s
    */
    public TypeWriter(String text, int x, int y , float speed, int multiplier){
        this.x = x;
        this.y = y;
        this.text = text;
        this.speed = speed;
        waitTimeMultiplier = multiplier;
        waitTime = speed*waitTimeMultiplier;
    }
    public TypeWriter(String text, int x, int y , float speed){
        this.x = x;
        this.y = y;
        this.text = text;
        this.speed = speed;
        waitTimeMultiplier = 4;
        waitTime = speed*waitTimeMultiplier;
    }


    public TypeWriter(String[] texts, int x, int y, float speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        resetvals();
        waitTimeMultiplier = 4;
        waitTime = speed*waitTimeMultiplier;
        multipleTextsMode = true;
        this.texts = texts;
        this.text = "";
    }

    public TypeWriter(int x, int y, float speed, int multiplier){
        this.x = x;
        this.y = y;
        this.speed = speed;
        waitTimeMultiplier = multiplier;
        waitTime = speed*waitTimeMultiplier;
        text = "";
    }
    public TypeWriter(int x, int y, float speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
        waitTimeMultiplier = 4;
        waitTime = speed*waitTimeMultiplier;
        text = "";
    }

    public void changeText(String text){
        this.text = text;
        resetvals();
        waitTime = speed*waitTimeMultiplier;
        multipleTextsMode = false;

    }

    public void changeText(String text, float speed){
        this.text = text;
        resetvals();
        this.speed = speed;
        waitTime = speed*waitTimeMultiplier;
        multipleTextsMode = false;
    }
    public void changeTexts(String[] texts){
        this.texts = texts;
        resetvals();
        waitTime = speed*waitTimeMultiplier;
        multipleTextsMode = true;
        textsIndex = 0;

    }
    public void changeTexts(String[] texts, float speed){
        this.texts = texts;
        resetvals();
        waitTime = speed*waitTimeMultiplier;
        multipleTextsMode = true;
        textsIndex = 0;
        this.speed = speed;
    }

    private void resetvals(){
        currentText = "";
        counter = 0;
        currentChar = 0;
        finished = false;
    }

    public void update(){
        if(multipleTextsMode)
            text = texts[textsIndex];
        //Finished Writing
        if(currentChar +1 > text.length()){
            waitTime -= 1;
            //End Time After Finished Writing
            if(waitTime == 0){
                waitTime = speed*waitTimeMultiplier;
                finished = true;
                //Multiple Texts
                if(multipleTextsMode && texts.length-1 > textsIndex){
                    textsIndex++;
                    resetvals();
                }
            }
            return;
        }

        counter++;
        if(counter >= speed){
            counter = 0;
            addNextChar(text.charAt(currentChar));
            currentChar++;
        }
    }

    @Override
    public String toString() {
        return currentText;
    }

    public void addNextChar(Character nextChar){
        currentText+= nextChar;
        if(nextChar != ' ')
            AudioController.playTypeWriterSound();
    }

    public void draw(Graphics2D g2){
        g2.drawString(currentText, x+(GamePanel.tileSize/2)-(g2.getFontMetrics().stringWidth(text)/2),y);
    }
}
