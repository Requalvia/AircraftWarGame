package edu.hitsz.aircraft;

import edu.hitsz.item.FlyingItem;
import edu.hitsz.shootStrategies.ShootStrategy;

import java.util.LinkedList;
import java.util.List;

public abstract class EnemyAircraft extends AbstractAircraft{
    public EnemyAircraft(int locationX, int locationY, int speedX, int speedY, int hp, ShootStrategy shootStrategy) {
        super(locationX, locationY, speedX, speedY, hp,shootStrategy);
    }
}
