package edu.hitsz.shootStrategies.bossStrategies;

import edu.hitsz.bullet.AbstractBullet;

import java.util.List;
/**
 * 一个美好的预想：
 * boss每隔一段时间更换一次策略
 *
 * */
public interface BossBasic {
    List<AbstractBullet> shoot(int x, int y, int direction, int vx, int vy, int power, int shootNum,
                               int herox,int heroy);
}
