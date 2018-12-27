/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.imageio.ImageIO.read;

/**
 *
 * @author abdi
 */
public class GameWorld extends JPanel {

    public static int WORLD_WIDTH = 1920;
    public static int WORLD_HEIGHT = 1440;
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int TILE_SIZE = 1280/32;
    public static boolean isWonplayer1 = false;
    public static boolean isWonplayer2 = false;

    private Graphics2D buffer;
    private JFrame jf;

    public static Tank t1;
    public static Tank t2;
    int speed = 1, move = 0;

    public static ArrayList<Wall> healthArrayList = new ArrayList<>();
    public static ArrayList<Wall> wallArrayList = new ArrayList<>();
    public static ArrayList<Wall> wallArrayList2 = new ArrayList<>();
    public static ArrayList<Explosion> explosionArrayList = new ArrayList<>();
    public static ArrayList<Wall> powerUpArrayList = new ArrayList<>();

    private BufferedImage borderWall, powerUp, healthUp;
    private BufferedImage world;
    private Image background;
    private static Collision collision = new Collision();
    private BufferedImage gameWall;
    private BufferedImage player2Wins;
    private BufferedImage player1Wins;
    private static boolean gameStart = true;

    public static void main(String[] args) {
        Thread x;
        GameWorld trex = new GameWorld();
        trex.init();

        try {

            while (true) {
                if (gameStart == true) {
                    trex.t1.update();

                    trex.t2.update();
                    updateCollisions();
                    //System.out.println(trex.t1.getPowerList().size() + " " + trex.t2.getPowerList().size());
                    trex.repaint();
                    //  System.out.println("trex t1: " + trex.t1);
                    //  System.out.println("trex t2: " + trex.t2);
                    // System.out.println(trex.t2);
                    Thread.sleep(1000 / 144);
                }else{
                    break;
                }
            }
        } catch (InterruptedException ignored) {

        }
    }

    public  static void updateCollisions(){

        collision.playerVSplayer(GameWorld.t1, GameWorld.t2);
        collision.playerVSbullet(GameWorld.t1, GameWorld.t2);
        collision.playerVSbullet(GameWorld.t2, GameWorld.t1);
        collision.playerVSgameWall(GameWorld.t1);
        collision.playerVSgameWall(GameWorld.t2);
        collision.player1VSpowerbullet(GameWorld.t1, GameWorld.t2);
        collision.player2VSpowerbullet(GameWorld.t2, GameWorld.t1);
        collision.playerVSborderWall(GameWorld.t1);
        collision.playerVSborderWall(GameWorld.t2);
        collision.playerVSpowerUp(GameWorld.t1);
        collision.playerVSpowerUp(GameWorld.t2);
        collision.detectHealth(GameWorld.t1);
        collision.detectHealth(GameWorld.t2);


    }

    private void init() {
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage(GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null;

        try {
            //System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            t1img = read(new File("Resources/tank1.png"));
            background = read(new File("Resources/Background.bmp"));
            borderWall = read(new File("Resources/Wall1.gif"));
            gameWall = read(new File("Resources/Wall2.gif"));
            powerUp = read(new File("Resources/Pickup.gif"));
            healthUp = read(new File("Resources/Shield2.png"));
            player1Wins = read(new File("Resources/player1wins.png"));
            player2Wins = read(new File("Resources/player2Wins.png"));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        t1 = new Tank(200, 200, 0, 0, 0, t1img);
        t2 = new Tank(600, 600, 0, 0, 0, t1img);//Tank 2 starting position




        for (int a = 0; a < Wall.map.length; a++) {
            for (int b = 0; b < Wall.map[a].length; b++) {
                if (Wall.map[a][b] == 1) {
                   Wall mapWalls = new Wall(a * 32, b * 32, borderWall);
                    wallArrayList.add(mapWalls);
                }
                if(Wall.map[a][b] == 2){
                     Wall power = new Wall(a* 32, b* 32, powerUp );
                    powerUpArrayList.add(power);
                }
                if(Wall.map[a][b] == 3){
                    Wall health = new Wall(a* 32, b* 32, healthUp );
                    healthArrayList.add(health);
                }
                if (Wall.map[a][b] == 4) {
                    Wall mapWalls = new Wall(a * 32, b * 32, gameWall);
                    wallArrayList2.add(mapWalls);
                }
            }
        }


        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);

        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);


        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);


        this.jf.setSize(GameWorld.SCREEN_WIDTH, GameWorld.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);

    }

    public void paintComponent(Graphics g) {

        background.getGraphics();
        Graphics2D g2 = (Graphics2D) g;
        buffer = world.createGraphics();

        super.paintComponent(g2);


        g2.drawImage(world,0,0,null);

        int offsetMaxX = (WORLD_WIDTH - (SCREEN_WIDTH/2)) ;
        int offsetMaxY = WORLD_HEIGHT - SCREEN_HEIGHT;
        int offsetMinX = 0;
        int offsetMinY = 0;

        int camX = (t1.getX() - SCREEN_WIDTH/4);
        int camY = t1.getY() - SCREEN_HEIGHT/2;

        int camX2 = (t2.getX() - SCREEN_WIDTH/4);
        int camY2 = t2.getY() - SCREEN_HEIGHT/2;

        /////////////////////////////////////////////////////////////////////

        if(camX > offsetMaxX){
            camX = offsetMaxX;
        }
        else if(camX < offsetMinX){
            camX = offsetMinX;
        }
        if(camY > offsetMaxY){
            camY = offsetMaxY;
        }
        else if(camY < offsetMinY){
            camY = offsetMinY;
        }

        /////////////////////////////////////////////////////////////////////

        if(camX2 > offsetMaxX){
            camX2 = offsetMaxX;
        }
        else if(camX2 < offsetMinX){
            camX2 = offsetMinX;
        }
        if(camY2 > offsetMaxY){
            camY2 = offsetMaxY;
        }
        else if(camY2 < offsetMinY){
            camY2 = offsetMinY;
        }

        /////////////////////////////////////////////////////////////////////////

        BufferedImage bufferImgOne = (world.getSubimage(camX, camY, SCREEN_WIDTH/2, SCREEN_HEIGHT)) ;
        BufferedImage bufferImgTwo = (world.getSubimage(camX2, camY2, SCREEN_WIDTH/2, SCREEN_HEIGHT)) ;

        g2.drawImage(bufferImgOne, 0,0, this);
        g2.drawImage(bufferImgTwo, 640,0, this);

        draw();

        this.t1.drawImage(buffer);
        this.t2.drawImage(buffer);
        Image scaledMap = world.getScaledInstance(200, 200, Image.SCALE_FAST);
        g2.drawImage(scaledMap, SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 - 100, 200, 200, this);

        if (isWonplayer1){
            g2.drawImage(player1Wins, 250 , 200 ,null);
        }

        if (isWonplayer2){
            g2.drawImage(player2Wins, 250 , 200 ,null);
        }
    }

    public void drawBackGroundWithTileImage() {
        int TileWidth = background.getWidth(this);
        int TileHeight = background.getHeight(this);

        int NumberX = (int) (WORLD_WIDTH / TileWidth);
        int NumberY = (int) (WORLD_HEIGHT / TileHeight);

        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                buffer.drawImage(background, j * TileWidth,
                        i * TileHeight + (move % TileHeight), TileWidth,
                        TileHeight, this);

            }
        }
    }

    public void startGame(boolean isStart){
        //  gameStart = true;
        if(isStart == false){
            gameStart = false;
        }else{
            gameStart = true;
        }
    }

    public void draw(){

        drawBackGroundWithTileImage();

        for (int i = 0; i < t1.getBulletlist().size(); i++){
            this.t1.getBulletlist().get(i).drawImage(buffer);
            collision.bulletVSgamewall(this.t1.getBulletlist().get(i), this.t1, i);
            collision.bulletVSborderwall(this.t1.getBulletlist().get(i),this.t1, i);
        }

        for (int i = 0; i < t2.getBulletlist().size(); i++){
            this.t2.getBulletlist().get(i).drawImage(buffer);
            collision.bulletVSgamewall(this.t2.getBulletlist().get(i), this.t2, i);
            collision.bulletVSborderwall(this.t2.getBulletlist().get(i),this.t2, i);
        }

        for (int i = 0; i < wallArrayList.size(); i++){
            wallArrayList.get(i).drawImage(buffer);
        }

        for (int i = 0; i < wallArrayList2.size(); i++){
            wallArrayList2.get(i).drawImage(buffer);
        }

        for (int i = 0; i < powerUpArrayList.size(); i++){
           powerUpArrayList.get(i).drawImage(buffer);
        }

        for (int i = 0; i < healthArrayList.size(); i++){
            healthArrayList.get(i).drawImage(buffer);
        }

        for (int i = 0; i < explosionArrayList.size(); i++){
            explosionArrayList.get(i).drawImage(buffer);
            explosionArrayList.remove(i);
        }

        for (int i = 0; i < t1.getPowerList().size(); i++){
            this.t1.getPowerList().get(i).drawImage(buffer);
            collision.powerbulletVSgamewall(this.t1.getPowerList().get(i), this.t1, i);
            //           collision.powerbulletVSborderwall(this.t1.getPowerList().get(i),this.t1, i);

        }

        for (int i = 0; i < t2.getPowerList().size(); ++i){
            this.t2.getPowerList().get(i).drawImage(buffer);
            collision.powerbulletVSgamewall(this.t2.getPowerList().get(i), this.t2, i);
//            collision.powerbulletVSborderwall(this.t2.getPowerList().get(i),this.t2, i);

        }
    }






}
