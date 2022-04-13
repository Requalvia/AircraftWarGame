package edu.hitsz.shootStrategies.bossStrategies;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.BossBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossPricise implements BossBasic{
    /**
     * 难度非常高的“定点打击”！
     * 甚至我自己都打不过！
     * 甚至为了这个，把速度都改成double了！
     * */
    @Override
    public List<AbstractBullet> shoot(int x, int y, int direction, int vx, int vy, int power, int shootNum, int herox, int heroy) {
        int dy=heroy-y;
        int dx=herox-x;
        double theta=(Math.atan(((double)dy)/dx));
        LinkedList<AbstractBullet> res=new LinkedList<>();
        //我也不知道为什么这样就好使
        if (herox>=x) {
            res.add(new BossBullet(x,y,
                    (int) (20*Math.cos(theta)),
                    (int) (20*Math.sin(theta)),
                    30,
                    (20*Math.cos(theta)),
                    (20*Math.sin(theta))));
        }
        else{
            res.add(new BossBullet(x,y,
                    (int) (20*Math.cos(theta)),
                    (int) (20*Math.sin(theta)),
                    30,
                    -(20*Math.cos(theta)),
                    -(20*Math.sin(theta))));
        }
        return res;
    }
}
