package Main;

import java.awt.*;

public class GameManager {
    KeyHandler keyH = new KeyHandler();
    DataManager dataManager = new DataManager();

    public GameManager(KeyHandler keyH){
        this.keyH = keyH;
        dataManager.LoadData();
    }
    public void update(){
        if(keyH.esc){
        }
        if(keyH.F11){

        }

    }
}
