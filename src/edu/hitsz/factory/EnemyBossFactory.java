package edu.hitsz.factory;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.EnemyBoss;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.item.FlyingItem;
import edu.hitsz.shootStrategies.bossStrategies.BossBasic;
import edu.hitsz.shootStrategies.bossStrategies.BossPricise;
import edu.hitsz.shootStrategies.bossStrategies.BossRandom;
import edu.hitsz.shootStrategies.bossStrategies.BossShotgun;

public class EnemyBossFactory implements Factory {
    @Override
    public EnemyAircraft Create() {
        double a=Math.random();
        BossBasic bb;
        if(a<0.333){bb=new BossPricise();}
        else if(a<0.666){bb=new BossRandom();}
        else {bb=new BossShotgun();}
        EnemyBoss enemyBoss=new EnemyBoss((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                2,
                0,
                300,
                bb);
        return enemyBoss;
    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        return null;
    }
}
