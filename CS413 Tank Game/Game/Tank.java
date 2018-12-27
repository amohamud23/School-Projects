package Game;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Font;

import static Game.GameWorld.SCREEN_HEIGHT;
import static javax.imageio.ImageIO.read;

/**
 *
 * @author anthony-pc
 */
public class  Tank extends  GameWorld {

    private int tankHealth = 60;
    private int addHealth = 0;
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private final int R = 1;
    private final int ROTATIONSPEED = 2;
    private int j =1;
    private int fire_rate = 50;
    private int counter = 0;

    private ArrayList<Bullet> bulletlist = new ArrayList<>();
    private ArrayList<Bullet> powerList = new ArrayList<>();
    private BufferedImage bulletImg;
    private BufferedImage rocketImg;


    private BufferedImage img;
    private BufferedImage player1Wins;
    private BufferedImage player2Wins;
    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean EnterPressed;
    private Collision tankCollison = new Collision();
    private static boolean power;
    private int tankLives = 3;


    Tank(int x, int y, int vx, int vy, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.angle = angle;
        try{
            bulletImg = read(new File("Resources/Shell.png"));
            rocketImg = read(new File("Resources/Rocket.png"));

        }
        catch (IOException e){

        }

    }


    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() { this.RightPressed = false; }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void toggleEnterPressed() {this.EnterPressed = true;}

    void untoggleEnterPressed() {this.EnterPressed = false;}


    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }

        if (this.EnterPressed) {

            if (j%fire_rate == 0) {
                this.shoot();
            }
            j++;
        }

        for (int i = 0; i < getBulletlist().size(); i++){
            getBulletlist().get(i).update();
//            tankCollison.bulletVSgamewall(t2.getBulletlist().get(i), t2, i);
//            tankCollison.bulletVSgamewall(t1.getBulletlist().get(i), t1, i);
        }

        for (int i = 0; i < getPowerList().size(); ++i){
            getPowerList().get(i).update();
//            tankCollison.bulletVSgamewall(t2.getPowerList().get(i), t2, i);
//            tankCollison.bulletVSgamewall(t1.getPowerList().get(i), t1, i);
        }

    }
//
    public void getHealth(boolean isHealth) {
        if (isHealth) {
            // int i = 0;
            if (tankLives<3){
                tankLives = tankLives+1;
            }
            tankHealth = 60;

            }//tankHealth += addHealth;
    }

    public void hit(boolean isHit, int damage){
        if (isHit){
            try {
                int i = 0;

                while(i < 50){
                    img = read(new File("Resources/tank1_inverted.png"));
                    i++;
                }
                tankHealth -= damage;
                if(tankLives == 3){
                    if(tankHealth == 0 ){
                        tankHealth = 60;
                        //   tankHealth -= damage;
                        tankLives -= 1;
                    }
                }
                if(tankLives == 2){
                    if(tankHealth == 0 ){
                        tankHealth = 60;
                        //   tankHealth -= damage;
                        tankLives -= 1;
                    }
                }
                if(tankLives == 1){
                    if(tankHealth == 0 ){
                        tankHealth = 60;
                        //   tankHealth -= damage;
                        tankLives -= 1;
                    }
                }
                if(tankLives == 0){
                    if(tankHealth == 0 ){
                        tankHealth = 60;
                        //   tankHealth -= damage;
                        //  tankLives -= 1;
                    }
                }

                img = read(new File("Resources/tank1.png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Bullet> getBulletlist() { return this.bulletlist; }
    public ArrayList<Bullet> getPowerList(){return powerList;}

    public int getWidth(){ return img.getWidth();}
    public int getHeight(){ return img.getHeight();}

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
        checkBorder();
    }

    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
        checkBorder();
    }

    private void checkBorder() {
        if (x < 30) {
            x = 30;
        }
        if (x >= GameWorld.WORLD_WIDTH - 88) {
            x = GameWorld.WORLD_WIDTH - 88;
        }
        if (y < 40) {
            y = 40;
        }
        if (y >= GameWorld.WORLD_HEIGHT - 80) {
            y = GameWorld.WORLD_HEIGHT - 80;
        }
    }

    public void Collision( boolean leftWall, boolean topWall, boolean bottomWall, boolean rightWall, int pos){

        if (leftWall){
                x = pos - getWidth();
        }
        if (topWall){
                y = pos - getHeight();
        }
        if (bottomWall){
            y = pos;
        }
        if (rightWall){
            x = pos;
        }
    }

    public void setPower(boolean isPower){
        power = isPower;
    }

    private void shoot(){
    if (power && counter < 10){
        Bullet powerBullet;
        powerBullet = new Bullet(x,y,angle,rocketImg,20);
        powerList.add(powerBullet);
        counter++;
    }
    else {
        power = false;
        Bullet newbullet;
        newbullet = new Bullet(x+8, y+8, angle, bulletImg, 6);
        bulletlist.add(newbullet);
    }

    }


    void drawImage(Graphics2D g) {

        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        if (tankLives == 3) {
            g.setColor(Color.red);
            g.fill(new Rectangle2D.Double(x + 30, y + 70, 10, 10));
            g.setColor(Color.red);
            g.fill(new Rectangle2D.Double(x + 50, y + 70, 10, 10));
            g.setColor(Color.red);
            g.fill(new Rectangle2D.Double(x + 70, y + 70, 10, 10));
        }
        if(tankLives == 2) {
            g.setColor(Color.red);
            g.fill(new Rectangle2D.Double(x + 30, y + 70, 10, 10));
            g.setColor(Color.red);
            g.fill(new Rectangle2D.Double(x + 50, y + 70, 10, 10));
        }
        if(tankLives == 1) {
            g.setColor(Color.red);
            g.fill(new Rectangle2D.Double(x + 30, y + 70, 10, 10));
        }

            if(tankLives == 0) {
                try {
                    img = read(new File("Resources/Explosion_large.png"));
                    player1Wins = read(new File("Resources/player1wins.png"));
                    player2Wins = read(new File("Resources/player2Wins.png"));
                    if(GameWorld.t1.tankLives == 0){
                        GameWorld.isWonplayer2 = true;
                        GameWorld.t1.startGame(false);
                        //g.drawImage(player1Wins,0,0,null);

                    }else if(GameWorld.t2.tankLives == 0){
                        GameWorld.isWonplayer1 = true;
                        GameWorld.t2.startGame(false);
                        //g.drawImage(player1Wins,0,0,null);
                    }
                }catch(IOException e){

                    g.setColor(Color.green);
                    g.fill(new Rectangle2D.Double(x +30,y + 50, tankHealth ,10));
                }

            }else {
                g.setColor(Color.green);
                g.fill(new Rectangle2D.Double(x + 30, y + 50, tankHealth, 10));
            }



        //System.out.println("lifes: " + GameWorld.t1.tankLives);
    }





}
