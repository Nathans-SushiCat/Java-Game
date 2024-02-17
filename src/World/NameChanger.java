package World;

import Main.DataManager;
import Main.GamePanel;
import Main.SimpleEntity;
import entity.Gameboy_BitBullet;
import entity.Lever;
import entity.Lever_Handle;
import entity.Player;

import java.awt.*;
import java.util.ArrayList;

public class NameChanger extends SimpleEntity {

    Lever lever;
    TextField textField;
    ArrayList<Player> player = new ArrayList<>();
    Lever_Handle.State currentstate;

    public NameChanger(GamePanel gp, Lever lever, TextField textField){
        this.lever = lever;
        this.textField = textField;
        for(int i = 0; i < gp.entities.size(); i++){
            if(gp.entities.get(i) instanceof Player p)
                player.add(p);
        }
        currentstate = lever.leverHandle.currentState;
        if(lever.leverHandle.currentState == Lever_Handle.State.RIGHT){
            textField.setText(player.get(0).name);
        }else if (lever.leverHandle.currentState == Lever_Handle.State.LEFT){
            textField.setText(player.get(1).name);
        }
    }

    public void update(){
        if(currentstate != lever.leverHandle.currentState)
        {
            if(lever.leverHandle.currentState == Lever_Handle.State.RIGHT){
                textField.setText(player.get(0).name);
            }else if (lever.leverHandle.currentState == Lever_Handle.State.LEFT){
                textField.setText(player.get(1).name);
            }
        }

        if(lever.leverHandle.currentState == Lever_Handle.State.RIGHT){
            player.get(0).name = textField.text;
            DataManager.saveData.playerName1 = textField.text;
            currentstate = Lever_Handle.State.RIGHT;
        }else if (lever.leverHandle.currentState == Lever_Handle.State.LEFT){
            player.get(1).name = textField.text;
            DataManager.saveData.playerName2 = textField.text;
            currentstate = Lever_Handle.State.LEFT;
        }
    }

    public void draw(Graphics2D g2){

    }
}
