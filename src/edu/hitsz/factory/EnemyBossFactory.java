package edu.hitsz.factory;

import edu.hitsz.aircraft.EnemyAircraft;
import edu.hitsz.item.FlyingItem;

public class EnemyBossFactory implements Factory {
    @Override
    public EnemyAircraft Create() {
        return null;
    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        return null;
    }
}
