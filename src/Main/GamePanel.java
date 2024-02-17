package Main;

import World.*;
import World.TextField;
import entity.*;

import javax.swing.*;
import java.awt.*;
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
    public static Font dafaultFont = new Font ("Consolas", Font.PLAIN, 12);
    public Font mainFont = new Font ("Consolas", Font.PLAIN, 12);
    public Font boldFont = new Font ("Consolas", Font.BOLD, 30);
    public Font iconFont = new Font("Segoe MDL2", Font.PLAIN, 12);
    public static int tileSize = originalTileSize * scale;
    static final int maxScreenCol = 16;
    static final int maxScreenRow = 9;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;
    int FPS = 60;
    int backgroundMusicTimer = 0;
    int Room;
    private double drawInterval;
    long startTime = System.nanoTime();
    long previousTime = startTime;
    int frames = 0;
    double actualFPS= 0;


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
        LoadRoom(3);
    }
    public void changeFPS(int FPS){
        this.FPS = FPS;
        drawInterval = 1000000000/FPS;

    }
    public int getHorizontalScreenMid(int width){
        return tileSize*maxScreenCol/2 - (width/2);
    }
    public int getVerticalScreenMid(int height){
        return tileSize*maxScreenRow/2 - (height/2);
    }

    public void LoadRoom(int Room){
        changeFPS(60);
        this.Room = Room;
        entities.clear();
        simpleEntities.clear();
        if(Room == 0){
            backgroundMusicTimer = 0;
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize) - (int)(tileSize*2.25), 1, "START"));
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize)  - (int)(tileSize*0.75), 2, "GAMEBOY"));
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize)  + (int)(tileSize*0.75), 3, "Name"));
            entities.add(new Button(this,getHorizontalScreenMid(tileSize*2), getVerticalScreenMid(tileSize) + (int)(tileSize*2.25), 4, "EXIT"));
            entities.add(new Player(this, keyH,100,300, 1,  DataManager.saveData.playerName1));
            entities.add(new Player(this, keyH,100,400, 2,  DataManager.saveData.playerName2));
            entities.add(new R141496());
            simpleEntities.add(new TypeWriter("Version 0.1.6.2",2*GamePanel.scale,GamePanel.screenHeight-GamePanel.scale, 3, false));

        }else if(Room == 1){
            TypeWriter typeWriter = new TypeWriter("Hello i am the Human",400,300, 5);
            simpleEntities.add(typeWriter);
            typeWriter.changeTexts(new String[]{"Hello there","Welcome to Java-Game", "Version 0.1.6.2.1"}, 4);

            Lever lever = new Lever(this,keyH, 250,100, Lever_Handle.State.LEFT);
            Lever lever2 = new Lever(this, keyH, 250, 450, Lever_Handle.State.RIGHT);

            entities.add(lever);
            entities.add(lever2);
            entities.add(new R141496());

            Collections.addAll(entities,
                    new Crate(this, keyH, 400, 200),
                    new Lava(this, keyH, tileSize*14,scale*6),
                    new Lava(this, keyH, tileSize*14,scale*6),
                    new Lava(this, keyH, (int) (tileSize*14.5),scale*6),
                    new Lava(this, keyH, (int) (tileSize*14.5),scale*10),
                    new Lava(this, keyH, tileSize*15,scale*4),
                    new Lava(this, keyH, tileSize*15,scale*10),
                    new NPC(this, keyH, "NPC", new String[]{"Hello", "my friend", "What's up?", "have you already done your homework?"}, 5),
                    new Spike(this, keyH, 500, tileSize, lever),
                    new Spike(this, keyH, 500+tileSize, tileSize, lever),
                    new Spike(this, keyH, 500+2*tileSize, tileSize, lever),
                    new Ein_Etwas(this, 900,200, 180, lever2),
                    new Sushi(this, keyH,200, 600,1),
                    new Sushi(this, keyH,200+tileSize, 600,1),
                    new Sushi(this, keyH,200+tileSize*2, 600,1),
                    new Cat(this, 100,500, 4, false),
                    new Cat(this, 100,600, 4, true),
                    new Player(this, keyH,100,400, 2, DataManager.saveData.playerName1),
                    new Player(this, keyH,100,300, 1, DataManager.saveData.playerName2),
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

            entities.add(new Player(this, keyH,100,400, 1, DataManager.saveData.playerName1));
            entities.add(new Player(this, keyH,100,300, 2, DataManager.saveData.playerName2));
            entities.add(new R141496());
            entities.add(new Gameboy(this, GamePanel.tileSize*10, GamePanel.tileSize*4));
        }else if(Room == 3){

            World.TextField txt = new TextField("", boldFont,14, getHorizontalScreenMid(0), getVerticalScreenMid(0));
            Lever lever = new Lever(this, keyH, getHorizontalScreenMid(tileSize), tileSize*6, Lever_Handle.State.RIGHT);

            Collections.addAll(entities,
                    lever,
                    new Keyboard(this, keyH, getHorizontalScreenMid(10*GamePanel.tileSize), scale*8, txt),
                    txt,
                    new Player(this, keyH,100,300, 1, DataManager.saveData.playerName1),
                new Player(this, keyH,100,400, 2, DataManager.saveData.playerName2)
            );
            simpleEntities.add(new NameChanger(this, lever, txt));

        }else if(Room == 4){
            World.TextField txt = new TextField("", boldFont,20, getHorizontalScreenMid((int)(tileSize*3.5f)), getVerticalScreenMid(0));
            Collections.addAll(entities,
                    txt,
                    new KeyButton(this, keyH, getHorizontalScreenMid(0)-tileSize*5,getVerticalScreenMid(0),"X",txt),
                    new KeyButton(this, keyH, getHorizontalScreenMid(0)+tileSize*5,getVerticalScreenMid(0),"←",txt),
                    new Player(this, keyH,100,300, 1, DataManager.saveData.playerName1),
                    new Player(this, keyH,100,400, 2, DataManager.saveData.playerName2)
            );
        }
    }
    public boolean objectExists(Entity e){
        return entities.contains(e);
    }

    public void PrintFPS(){
        long currentTime = System.nanoTime();
        float elapsedTime = (currentTime - previousTime) / 1000000000f;
        previousTime = currentTime;

        float fps = 1f / elapsedTime;

        frames++;
        if (currentTime - startTime >= 1000000000L){
            actualFPS = fps;
            frames = 0;
            startTime = currentTime;
        }
    }

    @Override
    public void run() {

        drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null){
            if(keyH.F11)
                PrintFPS();

            long startTime = System.nanoTime();

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
            gameManager.dataManager.StoreData();
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
        if(keyH.F11){
            for(int i = 0; i < entities.size(); i++){
                Entity e = entities.get(i);
                g2.drawRect(e.x,e.y, e.sizeHorizontal, e.sizeVertical);
            }
        }
        if(keyH.F11)
            g2.drawString("FPS: " + Math.round((actualFPS*100f))/100f, GamePanel.screenWidth-GamePanel.tileSize, GamePanel.tileSize);

        g2.dispose();
    }
}
