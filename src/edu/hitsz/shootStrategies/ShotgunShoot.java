package edu.hitsz.shootStrategies;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;

public class ShotgunShoot implements ShootStrategy{
    /**
     * 这个是散射。
     * */
    @Override
    public List<AbstractBullet> shoot(int x, int y, int direction, int vx, int vy, int power,int shootNum) {

        List<AbstractBullet> res = new LinkedList<>();

        int speedY = vy+ direction*5;
        AbstractBullet abstractBullet;
        if(shootNum%2==0){
            //子弹是偶数个
            for(int i=0; i<shootNum; i++){
                if(direction<0){abstractBullet = new HeroBullet(x, y, i<shootNum/2 ? i-shootNum/2 : i-shootNum/2+1 , speedY, power);}
                else{abstractBullet = new EnemyBullet(x, y, i<shootNum/2 ? i-shootNum/2 : i-shootNum/2+1 , speedY, power);}
                res.add(abstractBullet);
            }
        }
        else{
            //子弹是奇数个
            for(int i=0; i<shootNum; i++){
                if(direction<0){abstractBullet = new HeroBullet(x, y, i-(shootNum-1)/2, speedY, power);}
                else{abstractBullet = new EnemyBullet(x, y, i-(shootNum-1)/2, speedY, power);}

                res.add(abstractBullet);
            }
        }

        return res;


    }
}
