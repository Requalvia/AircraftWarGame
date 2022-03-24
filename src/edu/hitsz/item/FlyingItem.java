package edu.hitsz.item;

import edu.hitsz.basic.AbstractFlyingObject;

public abstract class FlyingItem extends AbstractFlyingObject {


    public FlyingItem(int locationX, int locationY, int speedX, int speedY,String type) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.type=new String(type);
    }
    protected String type;
    public String getType(){return type;}
    /*
    * type有三种类型
    * CureSupply
    * FireSupply
    * BombSupply
    * */

}
