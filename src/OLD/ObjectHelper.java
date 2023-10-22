package OLD;

import entity.Player;
import entity.Player2;

public class ObjectHelper {
    /*
    public float distancetoObject(float x1, float y1, float x2, float y2){
        float deltaX, deltaY;
        if(x1 < x2){
            deltaX = x2-x1;
        }else{
            deltaX = x1-x2;
        }
        if(y1 < y2){
            deltaY = y2-y1;
        }else{
            deltaY = y1-y2;
        }
        return (float) Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
    }
    public float distancetoObject(Entity object1, Entity object2){
        float x1,x2,y1,y2;
        x1 = object1.x;
        y1 = object1.y;
        x2 = object2.x;
        y2 = object2.y;
        float deltaX, deltaY;
        if(x1 < x2){
            deltaX = x2-x1;
        }else{
            deltaX = x1-x2;
        }
        if(y1 < y2){
            deltaY = y2-y1;
        }else{
            deltaY = y1-y2;
        }
        return (float) Math.sqrt((deltaX*deltaX)+(deltaY*deltaY));
    }

    public void checkLock(Player player){
        float xdif = player.x - player.gp.player2.x;
        float ydif = player.y - player.gp.player2.y;
        float size = player.gp.tileSize;

        resetLock(player);
        if(ydif < size && ydif > 0){
            player.LockY_P = true;
        }else if(ydif > -size) {
            player.LockY_N = true;
        }
        if(xdif < size && xdif < 0){
            player.LockX_P = true;
        }else if(xdif > -size) {
            player.LockX_N = true;
        }
    }
    public void resetLock(Player player){
        player.LockY_N = false;
        player.LockY_P = false;
        player.LockX_P = false;
        player.LockX_N = false;
    }

    public void checkLock(Player2 player){
        float xdif = player.x - player.gp.player.x;
        float ydif = player.y - player.gp.player.y;
        float size = player.gp.tileSize;
        resetLock(player);
        if(ydif < size && ydif > 0){
            player.LockY_P = true;
        }else if(ydif > -size) {
            player.LockY_N = true;
        }
        if(xdif < size && xdif < 0){
            player.LockX_P = true;
        }else if(xdif > -size) {
            player.LockX_N = true;
        }
    }


    public void resetLock(Player2 player){
        player.LockY_N = false;
        player.LockY_P = false;
        player.LockX_P = false;
        player.LockX_N = false;
    }

*/
}
