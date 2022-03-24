package edu.hitsz.item;

public class CureSupply extends FlyingItem{
    private int cureAmount;
    public CureSupply(int locationX, int locationY, int speedX, int speedY, String type) {
        super(locationX, locationY, speedX, speedY, type);
        cureAmount= (int) (Math.random()*16+15);
        //治疗量时15-30的随机值
    }
    public int getCureAmount(){return cureAmount;}
}
