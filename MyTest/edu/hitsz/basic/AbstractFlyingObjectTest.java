package edu.hitsz.basic;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.aircraft.MobEnemy;
import edu.hitsz.shootStrategies.NormalShoot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFlyingObjectTest {
    private HeroAircraft heroAircraft;
    private MobEnemy mobEnemy;
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @ParameterizedTest
    @DisplayName("碰撞测试")
    @CsvSource({"150,680","250,680","350,680","150,550","250,550","350,550","150,810","250,810","350,810"})
    void crash(int mobX,int mobY) {
        heroAircraft=HeroAircraft.GetInstance();
        mobEnemy=new MobEnemy(mobX*1,mobY*1,0,0,1,new NormalShoot());
        assertEquals(mobX==250 && mobY==680,heroAircraft.crash(mobEnemy));
    }

    @ParameterizedTest
    @DisplayName("前进测试")
    @ValueSource(ints={1,2,3,4,5,6,7,8})
    void forward(int times) {
        int locationY=200,speedY=10;
        mobEnemy=new MobEnemy(100,locationY*1,0,speedY*1,1,new NormalShoot());
        for(int i=0;i<times;i++){mobEnemy.forward();}
        assertEquals(locationY+times*speedY,mobEnemy.getLocationY());
    }
}