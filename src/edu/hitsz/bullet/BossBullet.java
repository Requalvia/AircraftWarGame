package edu.hitsz.bullet;

import edu.hitsz.application.Main;

public class BossBullet extends AbstractBullet{
    public BossBullet(int locationX, int locationY, int speedX, int speedY, int power,double priciseSpeedX,double priciseSpeedY) {
        super(locationX, locationY, speedX, speedY, power);
        this.vx=priciseSpeedX;
        this.vy=priciseSpeedY;
        plx= locationX;
        ply=locationY;
    }
    public double vx,vy;
    public double plx,ply;//pricise location x,y

    @Override
    public void forward(){
        plx=plx+vx;
        ply=ply+vy;

        locationX = (int) Math.round (locationX+vx);
        locationY = (int) Math.round (locationY+vy);

        /*
        locationX = (int) Math.round(locationX+vx);
        locationY = (int) Math.round(locationX+vy);

         */

        // 判定 x 轴出界
        if (locationX <= 0 || locationX >= Main.WINDOW_WIDTH) {
            vanish();
        }

        // 判定 y 轴出界
        if (speedY > 0 && locationY >= Main.WINDOW_HEIGHT ) {
            // 向下飞行出界
            vanish();
        }else if (locationY <= 0){
            // 向上飞行出界
            vanish();
        }
    }
}
