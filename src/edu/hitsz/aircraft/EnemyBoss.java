package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.shootStrategies.DontShoot;
import edu.hitsz.shootStrategies.bossStrategies.BossBasic;
import edu.hitsz.shootStrategies.bossStrategies.BossPricise;
import edu.hitsz.shootStrategies.bossStrategies.BossRandom;
import edu.hitsz.shootStrategies.bossStrategies.BossShotgun;

import java.util.List;

public class EnemyBoss extends EnemyAircraft{
    public EnemyBoss(int locationX, int locationY, int speedX, int speedY, int hp, BossBasic bossBasic) {
        super(locationX, locationY, speedX, speedY, hp, new DontShoot());
        this.bSS=bossBasic;
    }

    private BossBasic bSS;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 5;
    @Override
    public List<AbstractBullet> shoot() {
        return this.bSS.shoot(this.getLocationX(),
                this.getLocationY(),
                this.direction,
                0,
                this.getSpeedY(),
                this.power,
                this.shootNum,
                HeroAircraft.GetInstance().getLocationX(),
                HeroAircraft.GetInstance().getLocationY()
        );
    }


    public void changeBSS(){
        int a= (int) (Math.random()*3);
        switch (a){
            case 0:
                bSS=new BossRandom();
                break;
            case 1:
                bSS=new BossShotgun();
                break;
            case 2:
                bSS=new BossPricise();
                break;
            default:
                break;
        }
    }
}
