package edu.hitsz.factory;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.item.FlyingItem;

public class MobEnemyFactory implements Factory{


    @Override
    public EnemyAircraft Create() {
        MobEnemy mobEnemy=new MobEnemy((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                0,
                10,
                30);
        return mobEnemy;
    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        return null;
    }
}
