package edu.hitsz.factory;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.FireSupply;
import edu.hitsz.item.FlyingItem;

public class FireSupplyFactory implements Factory{
    @Override
    public AbstractFlyingObject Create() {
        return null;
    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        FireSupply fireSupply=new FireSupply(locationX*1,
                locationY*1,
                0,
                2,
                "FireSupply");
        return fireSupply;
    }
}
