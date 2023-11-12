package Main;

import World.AudioController;
import World.TypeWriter;
import entity.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    public static final int originalTileSize = 16;
    public static int scale = 5;
    public Font mainFont = new Font ("Consolas", Font.PLAIN, 12);
    public Font boldFont = new Font ("Consolas", Font.BOLD, 30);
    public static int tileSize = originalTileSize * scale;
    static final int maxScreenCol = 16;
    static final int maxScreenRow = 9;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;
    int FPS = 60;
    int backgroundMusicTimer = 0;

    int Room;


    public static int getScale(){
        String filePath = "file.txt";

        try {
            Path resourcePath = Paths.get(Objects.requireNonNull(GamePanel.class.getClassLoader().getResource(filePath)).toURI());
            return Integer.parseInt(Files.readString(resourcePath));
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return 0;
    }
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public ArrayList<SimpleEntity> simpleEntities = new ArrayList<SimpleEntity>();

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
        LoadRoom(0);
    }
    public int getHorizontalScreenMid(int width){
        return tileSize*maxScreenCol/2 - (width/2);
    }
    public int getVerticalScreenMid(int height){
        return tileSize*maxScreenRow/2 - (height/2);
    }

    public void LoadRoom(int Room){
        this.Room = Room;
        entities.clear();
        simpleEntities.clear();
        if(Room == 0){
            backgroundMusicTimer = 0;
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize) - (int)(tileSize*1.5), 1, "START"));
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize) , 2, "GAMEBOY"));
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize) + (int)(tileSize*1.5), 3, "EXIT"));
            entities.add(new Player(this, keyH,100,300, 1, "Sushicat"));
            entities.add(new Player(this, keyH,100,400, 2, "Schmillizidado"));
            entities.add(new R141496());

        }else if(Room == 1){
            TypeWriter typeWriter = new TypeWriter("Hello i am the Human",400,300, 5);
            simpleEntities.add(typeWriter);
            typeWriter.changeTexts(new String[]{"Hello there","Welcome to Java-Game", "Version 0.1.6"}, 4);

            Lever lever = new Lever(this,keyH, 250,100, Lever_Handle.State.LEFT);
            Lever lever2 = new Lever(this, keyH, 250, 450, Lever_Handle.State.RIGHT);

            entities.add(lever);
            entities.add(lever2);
            entities.add(new R141496());

            Collections.addAll(entities,
                    new Crate(this, keyH, 400, 200),
                    new Lava(this, keyH, tileSize*14,30),
                    new Lava(this, keyH, tileSize*14,30),
                    new Lava(this, keyH, (int) (tileSize*14.5),30),
                    new Lava(this, keyH, (int) (tileSize*14.5),50),
                    new Lava(this, keyH, tileSize*15,20),
                    new Lava(this, keyH, tileSize*15,50),
                    new NPC(this, keyH, "NPC", new String[]{"Hello", "my friend", "What's up?", "have you already done your homework?"}, 5),
                    new Spike(this, keyH, 500, tileSize, lever),
                    new Spike(this, keyH, 500+tileSize, tileSize, lever),
                    new Spike(this, keyH, 500+2*tileSize, tileSize, lever),
                    new Ein_Etwas(this, 900,200, 180, lever2),
                    new Sushi(this, keyH,200, 600,1),
                    new Sushi(this, keyH,200+tileSize, 600,1),
                    new Sushi(this, keyH,200+tileSize*2, 600,1),
                    new Cat(this, 100,500, 4),
                    new Player(this, keyH,100,400, 2, "Schmillizidado"),
                    new Player(this, keyH,100,300, 1, "Sushicat"),
                    new PortalGun(this, keyH, 500, 400, 60),
                    new Sword(this, keyH, 500, 500, 30),
                    new BigSword(this, keyH, 500, 600, 30),
                    new Star(this,900,600, 5, 4),

                    new Dingeldodel(this, 1200,600, 4),
                    new Dingeldodel(this, 1200,450, 4),
                    new Dingeldodel(this, 1200,300, 4),
                    new Dingeldodel(this, 1200,150, 4)
            );
        }else if(Room == 2){
            backgroundMusicTimer = 0;

            entities.add(new Player(this, keyH,100,400, 2, "Schmillizidado"));
            entities.add(new Player(this, keyH,100,300, 1, "Sushicat"));
            entities.add(new Gameboy(this, GamePanel.tileSize*10, GamePanel.tileSize*4));
        }
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
        if(keyH.F11){
        }

        backgroundMusicTimer--;

        if(backgroundMusicTimer < 0){
            if(Room == 2){
                AudioController.playBackGroundMusicGameBoySound();
                backgroundMusicTimer =(int)(AudioController.AudioFileLength("GameBoyMusic.wav")*60);
            }
            else{
                AudioController.playBackGroundMusicSound();
                backgroundMusicTimer =(int)(AudioController.AudioFileLength("Java-Game.wav")*60);
            }
        }
        if(keyH.esc){
            LoadRoom(0);
        }
        gameManager.update();

        for (int i = 0; i < simpleEntities.size(); i++){
            simpleEntities.get(i).update();
        }

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
                if(!anyCollision)
                    entities.get(j).handleNoCollision();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.setFont(mainFont);
        for (int i = 0; i < entities.size(); i++){
            entities.get(i).draw(g2);
        }
        for (SimpleEntity e : simpleEntities){
            e.draw(g2);
        }

        g2.dispose();
    }
}
