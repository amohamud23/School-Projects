package Game;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class Explosion {

    private int x;
    private int y;
    private Image img;

    Explosion(int x, int y, Image img){
        this.x = x;
        this.y = y;
        this.img = img;
    }

    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        //rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

    }
}
