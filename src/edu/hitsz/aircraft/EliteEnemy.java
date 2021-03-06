package edu.hitsz.aircraft;

import edu.hitsz.application.Main;
import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.bullet.EnemyBullet;
import edu.hitsz.bullet.HeroBullet;
import edu.hitsz.factory.BombSupplyFactory;
import edu.hitsz.factory.CureSupplyFactory;
import edu.hitsz.factory.FireSupplyFactory;
import edu.hitsz.item.FlyingItem;
import edu.hitsz.shootStrategies.ShootStrategy;

import java.util.LinkedList;
import java.util.List;

public class EliteEnemy extends EnemyAircraft{
    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp,shootStrategy);
    }
    /**攻击方式 */
    private ShootStrategy shootStrategy;

    @Override
    public void setShootStrategy(ShootStrategy shootStrategy){
        this.shootStrategy=shootStrategy;
    }

    /**
     * 子弹一次发射数量
     */
    private int shootNum = 1;

    /**
     * 子弹伤害
     */
    private int power = 30;

    /**
     * 子弹射击方向 (向上发射：1，向下发射：-1)
     */
    private int direction = 1;

    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= Main.WINDOW_HEIGHT ) {
            vanish();
        }
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
    public static List<FlyingItem> generateItemUponDeath(int x,
                                                      int y,
                                                      BombSupplyFactory f1,
                                                      CureSupplyFactory f2,
                                                      FireSupplyFactory f3){
        double ifItem=Math.random();
        LinkedList<FlyingItem> l=new LinkedList<>();
        if(ifItem>=0 && ifItem<0.25){
            l.add(f1.CreateItem(x*1, y*1));
        }
        else if(ifItem>=0.25 && ifItem<0.50){
            l.add(f2.CreateItem(x*1, y*1));
        }
        else if(ifItem>=0.50 && ifItem<0.75){
            l.add(f3.CreateItem(x*1, y*1));
        }
        return l;
    }
}
