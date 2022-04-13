package edu.hitsz.aircraft;

import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.shootStrategies.NormalShoot;
import edu.hitsz.shootStrategies.ShootStrategy;

import java.util.List;

/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    private static HeroAircraft heroAircraft=new HeroAircraft(Main.WINDOW_WIDTH / 2,
            Main.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
            0, 0, 100, new NormalShoot());
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp,ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp, shootStrategy);
    }
    public static HeroAircraft GetInstance(){
        return heroAircraft;
    }


    /**攻击方式 */


    /**子弹一次发射数量*/
    private int shootNum = 1;

    public void changeShootNum(int x){
        this.shootNum=x;
        return;
    }

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = -1;



    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


    @Override
    /**
     * 通过射击产生子弹
     * @return 射击出的子弹List
     */
    public List<AbstractBullet> shoot() {
        return super.shootStrategy.shoot(this.getLocationX(),this.getLocationY(),this.direction,
                0,this.getSpeedY(),this.power,this.shootNum);
    }

    public void getCure(int cureAmount){
        if(this.getHp()+cureAmount<=this.maxHp){
            this.hp+=cureAmount;
        }
        else{
            this.hp=this.maxHp;
        }
    }

    public void fireUp(int num){
        shootNum+=num;
    }
    public void fireDown(){
        shootNum=1;
    }

}
