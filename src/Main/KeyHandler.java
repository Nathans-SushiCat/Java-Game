package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, actionPressed, dropPressed;
    public boolean upPressed2, downPressed2, leftPressed2, rightPressed2, actionPressed2, dropPressed2;
    public boolean actionReleased, actionReleased2;

    public boolean esc, F11, space;

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {


        int code = e.getKeyCode();
        //Managing
        if(code == KeyEvent.VK_ESCAPE){
            esc = true;
        }
        if(code == KeyEvent.VK_F11){
            F11 = true;
        }
        if(code == KeyEvent.VK_SPACE){
            space = true;
        }

        ///Walk
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }
        if(code == KeyEvent.VK_S){
            downPressed = true;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }

        //Action
        if(code == KeyEvent.VK_E){
            actionReleased = false;
            actionPressed = true;
        }
        if(code == KeyEvent.VK_Q){
            dropPressed = true;
        }

        //Walk Player 2
        if(code == KeyEvent.VK_UP){
            upPressed2 = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed2 = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed2 = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed2 = true;
        }

        //Action Player 2
        if(code == KeyEvent.VK_CONTROL){
            actionReleased2 = false;
            actionPressed2 = true;
        }
        if(code == KeyEvent.VK_SHIFT){
            dropPressed2 = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        //Game managing
        if(code == KeyEvent.VK_ESCAPE){
            esc = false;
        }
        if(code == KeyEvent.VK_F11){
            F11 = false;
        }
        if(code == KeyEvent.VK_SPACE){
            space = false;
        }


        //Walking
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }
        if(code == KeyEvent.VK_S){
            downPressed = false;
        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;
        }

        //Action
        if(code == KeyEvent.VK_E){
            actionPressed = false;
            actionReleased = true;
        }
        if(code == KeyEvent.VK_Q){
            dropPressed = false;
        }

        //Walk Player 2
        if(code == KeyEvent.VK_UP){
            upPressed2 = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed2 = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed2 = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed2 = false;
        }

        //Action Player 2
        if(code == KeyEvent.VK_CONTROL){
            actionPressed2 = false;
            actionReleased2 = true;
        }
        if(code == KeyEvent.VK_SHIFT){
            dropPressed2 = false;
        }
    }
}
