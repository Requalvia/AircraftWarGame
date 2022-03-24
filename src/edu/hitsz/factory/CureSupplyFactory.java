package edu.hitsz.factory;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.CureSupply;
import edu.hitsz.item.FlyingItem;

public class CureSupplyFactory implements Factory{
    @Override
    public AbstractFlyingObject Create() {
        return null;
    }

    @Override
    public FlyingItem CreateItem(int locationX, int locationY) {
        CureSupply cureSupply=new CureSupply(locationX*1,
                locationY*1,
                0,
                2,
                "CureSupply");
        return cureSupply;
    }
}
