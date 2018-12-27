package Game;



import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class Bullet {

    private final int R = 2;
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int angle;
    private int damage;
    private BufferedImage img;




    Bullet(int x,int y, int angle, BufferedImage img, int damage){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;
        this.damage = damage;
    }

    public int getDamage(){return damage;}
    public int getX(){return x;}
    public int getY(){return y;}
    public int getWidth(){return img.getWidth();}
    public int getHeight(){return img.getHeight();}


    public void update(){

        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;

    }

    void drawImage(Graphics g) {
        AffineTransform shoot = AffineTransform.getTranslateInstance(x, y);
        shoot.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        //Graphics2D g2d = (Graphics2D) g;
        Graphics2D b2d = (Graphics2D) g;
        b2d.drawImage(this.img, shoot, null);
        g.setColor(Color.blue);
        g.drawRect(x+8,y+8,1,1);




    }

}
