package Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;




public class Collision {

    private Image Explosionimage;


    public void playerVSplayer(Tank tank1, Tank tank2){
        Rectangle tank1Box = new Rectangle(tank1.getX(), tank1.getY(), tank1.getWidth(), tank1.getHeight());
        Rectangle tank2Box = new Rectangle(tank2.getX(), tank2.getY(), tank2.getWidth(), tank2.getHeight());

        if(tank1Box.intersects(tank2Box)){

        }
    }


    public void playerVSbullet(Tank tank1, Tank tank2){
        Bullet player1bullet;


        Rectangle tank2Box = new Rectangle(tank2.getX(), tank2.getY(), tank2.getWidth(), tank2.getHeight());

            //Rectangle bulletBox = new Rectangle(player1bullet.getX(), player1bullet.getY(),player1bullet.getWidth(), player1bullet.getHeight());

        for (int i = 0; i < tank1.getBulletlist().size(); i++){

            player1bullet = tank1.getBulletlist().get(i);
            Rectangle bulletBox = new Rectangle(player1bullet.getX(), player1bullet.getY(),player1bullet.getWidth(), player1bullet.getHeight());

            if (bulletBox.intersects(tank2Box))
            {

                tank1.getBulletlist().remove(i);
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
                    Explosion epl = new Explosion(player1bullet.getX(), player1bullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    tank2.hit(true, player1bullet.getDamage());
                }
                catch (IOException e){

                }
            }
        }
    }



    public void player1VSpowerbullet(Tank tank1, Tank tank2){
        Bullet playerbullet;


        Rectangle tank2Box = new Rectangle(tank2.getX(), tank2.getY(), tank2.getWidth(), tank2.getHeight());

        //Rectangle bulletBox = new Rectangle(player1bullet.getX(), player1bullet.getY(),player1bullet.getWidth(), player1bullet.getHeight());

        for (int i = 0; i < tank1.getPowerList().size(); i++){

            playerbullet = tank1.getPowerList().get(i);
            Rectangle bulletBox = new Rectangle(playerbullet.getX(), playerbullet.getY(),playerbullet.getWidth(), playerbullet.getHeight());

            if (bulletBox.intersects(tank2Box))
            {
                tank1.getPowerList().remove(i);
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
                    Explosion epl = new Explosion(playerbullet.getX(), playerbullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    GameWorld.t2.hit(true, playerbullet.getDamage());
                }
                catch (IOException e){

                }

            }
        }
    }

    public void player2VSpowerbullet(Tank tank1, Tank tank2){
        Bullet playerbullet;

        Rectangle tank2Box = new Rectangle(tank2.getX(), tank2.getY(), tank2.getWidth(), tank2.getHeight());

        for (int i = 0; i < tank1.getPowerList().size(); i++){

            playerbullet = tank1.getPowerList().get(i);
            Rectangle bulletBox = new Rectangle(playerbullet.getX(), playerbullet.getY(),playerbullet.getWidth(), playerbullet.getHeight());

            if (bulletBox.intersects(tank2Box))
            {
                tank1.getPowerList().remove(i);
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
                    Explosion epl = new Explosion(playerbullet.getX(), playerbullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    GameWorld.t1.hit(true, playerbullet.getDamage());
                }
                catch (IOException e){

                }

            }
        }
    }


    public void playerVSborderWall(Tank tank1){
        Rectangle tank1Box = new Rectangle(tank1.getX(), tank1.getY(), tank1.getWidth(), tank1.getHeight());

        for (int i = 0; i < GameWorld.wallArrayList.size(); i++){
            Wall wall = GameWorld.wallArrayList.get(i);
            int leftwallxpos = wall.getX();
            int topwallypos = wall.getY()+1;
            int bottomwallypos = wall.getY()+wall.getHeight()+1;
            int rightwallxpos = wall.getX() + wall.getWidth()+1;
            Rectangle WallBox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (tank1Box.intersects(WallBox)) {
                if (tank1.getX() + tank1.getWidth() == leftwallxpos+2) {
                    tank1.Collision(true, false, false, false, leftwallxpos);
                }
                if (tank1.getY() + tank1.getHeight() == topwallypos+2) {
                    tank1.Collision(false, true, false, false, topwallypos);
                }
                if (tank1.getY() == bottomwallypos-2) {
                    tank1.Collision(false, false, true, false, bottomwallypos);
                }
                if (tank1.getX() == rightwallxpos-2) {
                    tank1.Collision(false, false, false, true, rightwallxpos);
                }
            }
        }
    }

    public void playerVSgameWall(Tank tank1){
        Rectangle tank1Box = new Rectangle(tank1.getX(), tank1.getY(), tank1.getWidth(), tank1.getHeight());

        for (int i = 0; i < GameWorld.wallArrayList2.size(); i++){
            Wall wall = GameWorld.wallArrayList2.get(i);
            int leftwallxpos = wall.getX();
            int topwallypos = wall.getY()+1;
            int bottomwallypos = wall.getY()+wall.getHeight()+1;
            int rightwallxpos = wall.getX() + wall.getWidth()+1;
            Rectangle WallBox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (tank1Box.intersects(WallBox)) {
                if (tank1.getX() + tank1.getWidth() == leftwallxpos+2) {
                    tank1.Collision(true, false, false, false, leftwallxpos);
                    //System.out.println("Collision");
                }
                if (tank1.getY() + tank1.getHeight() == topwallypos+2) {
                    tank1.Collision(false, true, false, false, topwallypos);
                    //System.out.println("Collision");
                }
                if (tank1.getY() == bottomwallypos-2) {
                    tank1.Collision(false, false, true, false, bottomwallypos);
                    //System.out.println("Collision");
                }
                if (tank1.getX() == rightwallxpos-2) {
                    tank1.Collision(false, false, false, true, rightwallxpos);
                    //System.out.println("Collision");
                }
                //System.out.println("Collision");
            }
        }
    }


    public void bulletVSgamewall(Bullet bullet, Tank tank, int ind){
        Rectangle bulletbox = new Rectangle(bullet.getX()+8,bullet.getY()+8,1,1);

        for (int i = 0; i < GameWorld.wallArrayList2.size(); i++){
            Wall wall = GameWorld.wallArrayList2.get(i);
            Rectangle WallBox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth()-1, wall.getHeight()-1);
            if (bulletbox.intersects(WallBox)) {
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
                    Explosion epl = new Explosion( bullet.getX(), bullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    GameWorld.wallArrayList2.remove(i);
                    tank.getBulletlist().remove(ind);
                }
                catch (IOException e){

                }
            }
        }
    }

    public void powerbulletVSgamewall(Bullet bullet, Tank tank, int ind){
        Rectangle bulletbox = new Rectangle(bullet.getX()+8,bullet.getY()+8,1,1);

        for (int i = 0; i < GameWorld.wallArrayList2.size(); i++){
            Wall wall = GameWorld.wallArrayList2.get(i);
            Rectangle WallBox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth()-1, wall.getHeight()-1);
            if (bulletbox.intersects(WallBox)) {
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_large.png"));
                    Explosion epl = new Explosion( bullet.getX(), bullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    GameWorld.wallArrayList2.remove(i);
                    tank.getPowerList().remove(ind);
                }
                catch (IOException e){

                }
            }
        }
    }



    public void bulletVSborderwall(Bullet bullet, Tank tank, int ind ){
        Rectangle bulletbox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

        int i = 0;
        while(i < GameWorld.wallArrayList.size()){
            Wall wall = GameWorld.wallArrayList.get(i);
            Rectangle wallbox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());

            if (bulletbox.intersects(wallbox)){
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
                    Explosion epl = new Explosion( bullet.getX(), bullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                }
                catch (IOException e){

                }
                tank.getBulletlist().remove(ind);
                break;
            }
            i++;
        }
    }

    public void powerbulletVSborderwall(Bullet bullet, Tank tank, int ind ){
        Rectangle bulletbox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

        int i = 0;
        while(i < GameWorld.wallArrayList.size()){
            Wall wall = GameWorld.wallArrayList.get(i);
            Rectangle wallbox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());

            if (bulletbox.intersects(wallbox)){
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_large.png"));
                    Explosion epl = new Explosion( bullet.getX(), bullet.getY(),Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    tank.getPowerList().remove(ind);
                }
                catch (IOException e){

                }
                break;
            }
            i++;
        }
    }

//    public void bulletVSwall2(Bullet bullet, Tank tank, int ind ){
//        Rectangle bulletbox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());
//
//        int i = 0;
//        while(i < GameWorld.wallArrayList.size()){
//            Wall wall = GameWorld.wallArrayList.get(i);
//            Rectangle wallbox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
//
//            if (bulletbox.intersects(wallbox)){
//                try {
//                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
//                    Explosion epl = new Explosion(bullet.getX(), bullet.getY(), Explosionimage);
//                    GameWorld.explosionArrayList.add(epl);
//                    tank.getBulletlist().remove(ind);
//                }
//                catch (IOException e){
//
//                }
//                break;
//            }
//            i++;
//        }
//    }



    public void powerUpVSwall2(Bullet bullet, Tank tank, int ind){
        Rectangle bulletbox = new Rectangle(bullet.getX(), bullet.getY(), bullet.getWidth(), bullet.getHeight());

        int i = 0;
        while(i < GameWorld.wallArrayList.size()){
            Wall wall = GameWorld.wallArrayList.get(i);
            Rectangle wallbox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());

            if (bulletbox.intersects(wallbox)){
                try {
                    Explosionimage = ImageIO.read(new File("Resources/Explosion_small.png"));
                    Explosion epl = new Explosion(bullet.getX(), bullet.getY(), Explosionimage);
                    GameWorld.explosionArrayList.add(epl);
                    tank.getPowerList().remove(ind);
                }
                catch (IOException e){

                }
                break;
            }
            i++;
        }
    }


    public void detectHealth(Tank tank1) {
        Rectangle tank1Box = new Rectangle(tank1.getX(), tank1.getY(), tank1.getWidth(), tank1.getHeight());

        for (int i = 0; i < GameWorld.healthArrayList.size(); i++) {
            Wall health = GameWorld.healthArrayList.get(i);

            Rectangle healthBox = new Rectangle(health.getX(), health.getY(), health.getWidth(), health.getHeight());

            if (tank1Box.intersects(healthBox)) {

                tank1.getHealth(true);

            }
        }
    }


    public void playerVSpowerUp(Tank tank){

        Rectangle tank1Box = new Rectangle(tank.getX(), tank.getY(), tank.getWidth(), tank.getHeight());

        for (int i = 0; i < GameWorld.powerUpArrayList.size(); i++){
            Wall wall = GameWorld.powerUpArrayList.get(i);

            Rectangle WallBox = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (tank1Box.intersects(WallBox)) {
                tank.setPower(true);

                GameWorld.powerUpArrayList.remove(i);

            }
        }
    }
}
