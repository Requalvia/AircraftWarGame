package edu.hitsz.shootStrategies.bossStrategies;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.BossBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossRandom implements BossBasic {
    @Override
    public List<AbstractBullet> shoot(int x, int y, int direction, int vx, int vy, int power, int shootNum, int herox, int heroy) {
        shootNum= (int) (1+Math.random()*5);
        List<AbstractBullet> res = new LinkedList<>();
        AbstractBullet abstractBullet;
        for(int i=0;i<shootNum;i++){
            abstractBullet = new BossBullet(x,
                    y,
                    ((int) (Math.random() * 5))*(Math.random()>=0.5?1:-1) ,
                    (int) (Math.random()*6)+2, power,
                    ((Math.random() * 5))*(Math.random()>=0.5?1:-1) ,
                    (Math.random()*6)+1);
            res.add(abstractBullet);
        }
        return res;
    }
}
