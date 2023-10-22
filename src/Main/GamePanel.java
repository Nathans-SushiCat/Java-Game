package Main;

import World.TypeWriter;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    public static final int originalTileSize = 16;
    public static final int scale = 5;
    public Font mainFont = new Font ("Consolas", 1, 17);
    public static final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 9;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    int FPS = 60;


    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    TypeWriter typeWriter = new TypeWriter("Hello i am the Human",400,300, 5);


    public ArrayList<Entity> entities = new ArrayList<Entity>();

    GameManager gameManager = new GameManager(keyH);
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);

    }
    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();

        typeWriter.changeTexts(new String[]{"Hello", "and Welcome to this mess", "idk what this is all for"}, 10);

        Collections.addAll(entities,
                new Player(this, keyH),
                new Player2(this, keyH),
                new Crate(this, keyH),
                new Crate(this, keyH, 200, 400),
                new Crate(this, keyH, 100, 300),
                new Lava(this, keyH)
        );
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;


        while (gameThread != null){

            // 1 UPDATE: update information
            update();
            //2 DRAW: draw the screen
            repaint();


            //Set Game FPS
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime+= drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void update(){

        gameManager.update();
        typeWriter.update();

        for (Entity e : entities){
            e.update();
        }

        for( int j = 0; j < entities.size(); j++){
            try {
                for(int i = 0; i < entities.size(); i++){
                    if(entities.get(j) != entities.get(i))
                        entities.get(j).collisions.add(entities.get(j).createCollisionWith(entities.get(i)));
                }
                boolean anyCollision = false;
                for(Collision c : entities.get(j).collisions){
                    if(c.collided){
                        anyCollision = true;
                        entities.get(j).handleCollision(c);
                        continue;
                    }
                    if(!anyCollision)
                        entities.get(j).handleCollision(c);
                }
                entities.get(j).collisions.clear();
            }catch (Exception e){

            }

        }

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        Font consolasFont = new Font("Consolas", Font.PLAIN, 12);
        g2.setFont(consolasFont);
        for (Entity e : entities){
            e.draw(g2);
        }
        typeWriter.draw(g2);

        g2.dispose();
    }
}
