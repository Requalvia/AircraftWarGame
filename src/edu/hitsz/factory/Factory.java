package edu.hitsz.factory;

import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.item.FlyingItem;

public interface Factory {
    AbstractFlyingObject Create();


    FlyingItem CreateItem(int locationX, int locationY);
}
