package edu.hitsz.factory;

import edu.hitsz.aircraft.EliteEnemy;
import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.item.FlyingItem;

public class EliteEnemyFactory implements Factory{
    @Override
    public EnemyAircraft Create() {

        int directionx=Math.rint(Math.random())==0?-5:5;
        EliteEnemy eliteEnemy=new EliteEnemy((int) ( Math.random() * (Main.WINDOW_WIDTH - ImageManager.MOB_ENEMY_IMAGE.getWidth()))*1,
                (int) (Math.random() * Main.WINDOW_HEIGHT * 0.2)*1,
                (int)directionx*1,
                10,
                60);
        return eliteEnemy;

    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        return null;
    }
}
