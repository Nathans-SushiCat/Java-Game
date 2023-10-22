package World;

import jdk.jfr.Description;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TypeWriter {

    private int counter;
    private String[]  texts;
    private int textsIndex;
    private String currentText = "";
    public String text;
    private float speed;

    private boolean multipleTextsMode = false;
    private int currentChar;
    public int x,y;

    public float waitTime;

    public boolean finished = false;

    /**
     * @param speed higher speed means it takes longer 60 = 1/s
    */
    public TypeWriter(String text, int x, int y , float speed){
        this.x = x;
        this.y = y;
        this.text = text;
        this.speed = speed;
        waitTime = speed*4;
    }

    public void changeText(String text){
        this.text = text;
        resetvals();
        waitTime = speed*4;
        multipleTextsMode = false;

    }

    public void changeText(String text, float speed){
        this.text = text;
        resetvals();
        waitTime = speed*4;
        multipleTextsMode = false;
        this.speed = speed;
    }
    public void changeTexts(String[] texts){
        this.texts = texts;
        resetvals();
        waitTime = speed*4;
        multipleTextsMode = true;
        textsIndex = 0;

    }
    public void changeTexts(String[] texts, float speed){
        this.texts = texts;
        resetvals();
        waitTime = speed*4;
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
        //Finished Writing
        if(currentChar +1 > text.length()){
            waitTime -= 1;
            //End Time After Finished Writing
            if(waitTime == 0){
                waitTime = speed*4;
                finished = true;
                //Multiple Texts
                if(multipleTextsMode && texts.length-1 > textsIndex){
                    textsIndex++;
                    resetvals();
                }
            }
            return;
        }

        if(multipleTextsMode)
            text = texts[textsIndex];

        counter++;
        if(counter >= speed){
            counter = 0;
            addNextChar(text.charAt(currentChar));
            currentChar++;
        }
    }

    public void addNextChar(Character nextChar){
        currentText+= nextChar;
    }

    public void draw(Graphics2D g2){
        g2.drawString(currentText, x,y);
    }
}
