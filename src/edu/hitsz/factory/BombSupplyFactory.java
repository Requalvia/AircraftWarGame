package edu.hitsz.factory;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.BombSupply;
import edu.hitsz.item.FlyingItem;

public class BombSupplyFactory implements Factory{

    @Override
    public AbstractFlyingObject Create() {
        return null;
    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        BombSupply bombSupply=new BombSupply(locationX*1,
                locationY*1,
                0,
                2,
                "BombSupply");
        return bombSupply;
    }
}
