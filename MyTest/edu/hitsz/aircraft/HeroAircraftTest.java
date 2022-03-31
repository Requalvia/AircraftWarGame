package edu.hitsz.aircraft;

import edu.hitsz.bullet.AbstractBullet;
import edu.hitsz.factory.CureSupplyFactory;
import edu.hitsz.item.CureSupply;
import edu.hitsz.item.FlyingItem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HeroAircraftTest {
    private HeroAircraft heroAircraft;
    @BeforeEach
    void setUp() {
        heroAircraft= HeroAircraft.GetInstance();
    }

    @AfterEach
    void tearDown() {
        heroAircraft=null;
    }

    public boolean IllegalHp(HeroAircraft heroAircraft){
        return heroAircraft.getHp()<0 || heroAircraft.getHp()>heroAircraft.maxHp;
    }

    @Test
    @DisplayName("测试血量减少")
    void hpTest1() {
        while(heroAircraft.getHp()>0){
            heroAircraft.decreaseHp(30);
            System.out.println("Current hp: "+heroAircraft.getHp());
            assertFalse(IllegalHp(heroAircraft));
        }
    }

    @Test
    @DisplayName("测试血量增加")
    void hpTest2() {
        LinkedList<FlyingItem> cureSupplies = new LinkedList<>();
        CureSupplyFactory cureSupplyFactory = new CureSupplyFactory();
        //创造十个血瓶
        for (int i = 0; i < 10; i++) {
            cureSupplies.add(cureSupplyFactory.CreateItem(1, 1));
        }
        //将飞机血量置为1
        heroAircraft.decreaseHp(99);
        //模拟飞机吃血瓶
        for(int i = 0; i < 10; i++){
            heroAircraft.getCure(((CureSupply)cureSupplies.get(i)).getCureAmount());
            System.out.println("Current hp: "+heroAircraft.getHp());
            assertFalse(IllegalHp(heroAircraft));
        }
    }

    @ParameterizedTest
    @DisplayName("测试发射子弹")
    @ValueSource(ints = {1,2,3,4,5})
    void shoot(int num) {
        heroAircraft.changeShootNum(num);
        LinkedList<AbstractBullet> heroAircraftBullet=new LinkedList<>();
        heroAircraftBullet.addAll(heroAircraft.shoot());
        assertEquals(num,heroAircraftBullet.size());
    }
}