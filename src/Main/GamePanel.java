package Main;

import World.AudioController;
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

        typeWriter.changeTexts(new String[]{"Hello there","Welcome to Java-Game", "Version 0.1.5"}, 4);

        Lever lever = new Lever(this,keyH, 250,100, Lever_Handle.State.LEFT);
        Lever lever2 = new Lever(this, keyH, 250, 450, Lever_Handle.State.RIGHT);


        entities.add(lever);
        entities.add(lever2);

        Collections.addAll(entities,
                new Crate(this, keyH, 400, 200),
                new Crate(this, keyH, 400, 400),
                new Lava(this, keyH),
                new NPC(this, keyH, "NPC", new String[]{"Hello", "my friend", "What's up?", "have you already done your homework?"}, 5),
                new Spike(this, keyH, 500, 100, lever),
                new Spike(this, keyH, 500+tileSize, 100, lever),
                new Spike(this, keyH, 500+2*tileSize, 100, lever),
                new Ein_Etwas(this, 900,200, 180, lever2),
                new Sushi(this, keyH,200, 600,1),
                new Sushi(this, keyH,200+GamePanel.tileSize, 600,1),
                new Cat(this, 100,500, 4),
                new Player(this, keyH,100,300, 1),
                new Player(this, keyH,100,400, 2),
                new PortalGun(this, keyH, 100, 600, 60),
                new Star(this,800,600, 5, 4),
                new Dingeldodel(this, 400,600, 4)
        );
    }
    public boolean objectExists(Entity e){
        return entities.contains(e);
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

        for (int i = 0; i < entities.size(); i++){
            entities.get(i).update();
        }

        //Collision Controller
        for( int j = 0; j < entities.size(); j++){
            try {
                entities.get(j).collisions.clear();

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
            }catch (Exception e){
                e.printStackTrace();
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
