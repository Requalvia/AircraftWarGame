package edu.hitsz.shootStrategies;

import edu.hitsz.bullet.AbstractBullet;

import java.util.LinkedList;
import java.util.List;

public class DontShoot implements ShootStrategy{
    /**
     * 不发射子弹，MobEnemy专用
     * */
    @Override
    public List<AbstractBullet> shoot(int x, int y, int direction, int vx, int vy, int power,int shootNum) {
        return new LinkedList<>();
    }
}
