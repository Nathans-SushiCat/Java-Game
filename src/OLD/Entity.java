package OLD;

import OLD.ObjectHelper;

import java.awt.image.BufferedImage;

public class Entity extends ObjectHelper {

    public int x, y;
    public int speed;
    public boolean LockX_P = false, LockX_N = false, LockY_P = false, LockY_N = false;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle1, idle2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
