package edu.hitsz.shootStrategies;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class NormalShoot implements ShootStrategy{
    /**
     * 只返回单个子弹的list
     * 英雄的direction为负数，敌机的direction为正数。通过它来判断子弹的种类
     * 呐呐呐呐呐
     * */
    @Override
    public List<AbstractBullet> shoot(int x, int y,int direction, int vx, int vy,int power,int shootNum) {
        List<AbstractBullet> res = new LinkedList<>();
        AbstractBullet abstractBullet;
        if(direction<0){
            abstractBullet = new HeroBullet( x , y+ direction*2, 0, vy+ direction*5, power);
        }
        else{
            abstractBullet = new EnemyBullet( x , y+ direction*2, 0, vy+ direction*5, power);
        }
        res.add(abstractBullet);
        return res;
    }
}
