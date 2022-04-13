package edu.hitsz.shootStrategies.bossStrategies;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.BossBullet;
import edu.hitsz.bullet.EnemyBullet;

import java.util.LinkedList;
import java.util.List;

public class BossShotgun implements BossBasic {

    @Override
    public List<AbstractBullet> shoot(int x, int y, int direction, int vx, int vy, int power, int shootNum, int herox, int heroy) {
        List<AbstractBullet> res = new LinkedList<>();
        //shootNum= (int) (1+Math.random()*10);

        int speedY = vy+ direction*5;
        AbstractBullet abstractBullet;
        if(shootNum%2==0){
            //子弹是偶数个
            for(int i=0; i<shootNum; i++){
                abstractBullet = new BossBullet(x, y, i<shootNum/2 ? (i-shootNum/2)*2 : (i-shootNum/2+1)*2 , speedY, power,
                        i<shootNum/2 ? (i-shootNum/2)*2 : (i-shootNum/2+1)*2,
                        speedY);
                res.add(abstractBullet);
            }
        }
        else{
            //子弹是奇数个
            for(int i=0; i<shootNum; i++){
               abstractBullet = new BossBullet(x, y,
                       (i-(shootNum-1)/2)*2,
                       speedY, power,
                       (i-(shootNum-1)/2)*2,
                       speedY);
                res.add(abstractBullet);
            }
        }

        return res;
    }
}
