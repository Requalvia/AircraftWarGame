package edu.hitsz.shootStrategies;

import edu.hitsz.bullet.AbstractBullet;

import java.util.List;

public interface ShootStrategy {
    List<AbstractBullet> shoot(int x, int y,int direction,int vx,int vy,int power,int shootNum);
}
